package com.hismart.document.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismart.document.common.utils.MD5Util;
import com.hismart.document.modules.system.service.UserRoleService;
import com.hismart.document.modules.system.service.UserService;
import com.hismart.document.modules.system.entity.SinkUser;
import com.hismart.document.modules.system.entity.SinkUserRole;
import com.hismart.document.modules.system.manager.UserManager;
import com.hismart.document.modules.system.mapper.SinkUserMapper;
import com.hismart.document.modules.system.mapper.SinkUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hismart.document.modules.system.service.UserCacheService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<SinkUserMapper, SinkUser> implements UserService {

    private final SinkUserMapper iSinkUserMapper;
    private final SinkUserRoleMapper sinkUserRoleMapper;
    private final UserCacheService cacheService;
    private final UserManager userManager;
    private final UserRoleService userRoleService;


    public UserServiceImpl(SinkUserMapper iSinkUserMapper, SinkUserRoleMapper sinkUserRoleMapper, UserCacheService cacheService, UserManager userManager, UserRoleService userRoleService) {
        this.iSinkUserMapper = iSinkUserMapper;
        this.sinkUserRoleMapper = sinkUserRoleMapper;
        this.cacheService = cacheService;
        this.userManager = userManager;
        this.userRoleService = userRoleService;
    }

    @Override
    public IPage<SinkUser> getSinkUserPageList(IPage<SinkUser> iPage, SinkUser iSinkUser) {
        try {
            Page<SinkUser> page = new Page<>();
            return this.baseMapper.findUserDetail(page, iSinkUser);
        } catch (Exception e) {
            log.error("查询用户异常", e);
            return null;
        }
    }

    @Override
    public IPage<SinkUser> getSinkUserSqlPageList(IPage<SinkUser> iPage, SinkUser iSinkUser) {
        iPage.setRecords(iSinkUserMapper.getSinkUserSqlPageList(iPage, iSinkUser));
        return iPage;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SinkUser user) throws Exception {
        // 创建用户
        user.setCreateTime(new Date());
        user.setPassword(MD5Util.encrypt(user.getUsername(), SinkUser.DEFAULT_PASSWORD));
        save(user);

        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
        userManager.loadUserRedisCache(user);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SinkUser user) throws Exception {
        // 更新用户
        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);
        sinkUserRoleMapper.delete(new LambdaQueryWrapper<SinkUserRole>().eq(SinkUserRole::getUserId, user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());

    }


    @Override
    @Transactional( rollbackFor = Exception.class)
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    public SinkUser findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SinkUser>().eq(SinkUser::getUsername, username));
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void updateLoginTime(String username) throws Exception {
        SinkUser user = new SinkUser();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<SinkUser>().eq(SinkUser::getUsername, username));

        // 重新将用户信息加载到 redis中
        cacheService.saveUser(username);
    }


    private void setUserRoles(SinkUser user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            SinkUserRole ur = new SinkUserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            this.sinkUserRoleMapper.insert(ur);
        });
    }
}
