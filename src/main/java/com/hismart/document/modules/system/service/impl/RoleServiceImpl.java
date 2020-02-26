package com.hismart.document.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismart.document.modules.system.service.RoleService;
import com.hismart.document.modules.system.service.UserRoleService;
import com.hismart.document.modules.system.entity.SinkRole;
import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.hismart.document.modules.system.manager.UserManager;
import com.hismart.document.modules.system.mapper.SinkRoleMapper;
import com.hismart.document.modules.system.mapper.SinkRoleMenuMapper;
import com.hismart.document.modules.system.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<SinkRoleMapper, SinkRole> implements RoleService {

    private final SinkRoleMapper iSinkRoleMapper;
    private final SinkRoleMenuMapper sinkRoleMenuMapper;
    private final RoleMenuService roleMenuService;
    private final UserManager userManager;
    private final UserRoleService userRoleService;

    public RoleServiceImpl(SinkRoleMapper iSinkRoleMapper, SinkRoleMenuMapper sinkRoleMenuMapper, RoleMenuService roleMenuService, UserManager userManager, UserRoleService userRoleService) {
        this.iSinkRoleMapper = iSinkRoleMapper;
        this.sinkRoleMenuMapper = sinkRoleMenuMapper;
        this.roleMenuService = roleMenuService;
        this.userManager = userManager;
        this.userRoleService = userRoleService;
    }

    @Override
    public IPage<SinkRole> getSinkRolePageList(IPage<SinkRole> iPage, SinkRole iSinkRole) {
        LambdaQueryWrapper<SinkRole> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(iSinkRole.getRoleName())) {
            queryWrapper.eq(SinkRole::getRoleName, iSinkRole.getRoleName());
        }
        if (StringUtils.isNotBlank(iSinkRole.getCreateTimeFrom()) && StringUtils.isNotBlank(iSinkRole.getCreateTimeTo())) {
            queryWrapper
                    .ge(SinkRole::getCreateTime, iSinkRole.getCreateTimeFrom())
                    .le(SinkRole::getCreateTime, iSinkRole.getCreateTimeTo());
        }
        Page<SinkRole> page = new Page<>();
        IPage<SinkRole> page1 = this.page(page, queryWrapper);
        List<SinkRole> records = page1.getRecords();
        records.forEach(role -> {
            List<SinkRoleMenu> roleMenusByRoleId = roleMenuService.getRoleMenusByRoleId(String.valueOf(role.getRoleId()));
            List<String> collect = roleMenusByRoleId.stream().map(roleMenu -> String.valueOf(roleMenu.getMenuId())).collect(Collectors.toList());
            String menuIds = String.join(",", collect);
            role.setMenuId(menuIds);
        });
        return this.page(page, queryWrapper);
    }

    @Override
    public IPage<SinkRole> getSinkRoleSqlPageList(IPage<SinkRole> iPage, SinkRole iSinkRole) {
        iPage.setRecords(iSinkRoleMapper.getSinkRoleSqlPageList(iPage, iSinkRole));
        return iPage;
    }

    @Override
    public void createRole(SinkRole role) {
        role.setCreateTime(new Date());
        this.save(role);
        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);

    }

    @Override
    public void updateRole(SinkRole role) throws Exception {
        role.setModifyTime(new Date());
        String[] roleId = {String.valueOf(role.getRoleId())};
        baseMapper.updateById(role);
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleId);
        sinkRoleMenuMapper.delete(new LambdaQueryWrapper<SinkRoleMenu>().eq(SinkRoleMenu::getRoleId, role.getRoleId()));

        sinkRoleMenuMapper.delete(new LambdaQueryWrapper<SinkRoleMenu>().eq(SinkRoleMenu::getRoleId, role.getRoleId()));

        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    @Override
    public void deleteRoles(String[] roleIds) throws Exception {
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleIds);

        List<String> list = Arrays.asList(roleIds);
        baseMapper.deleteBatchIds(list);
        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        this.userRoleService.deleteUserRolesByRoleId(roleIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    private void setRoleMenus(SinkRole role, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            SinkRoleMenu rm = new SinkRoleMenu();
            rm.setMenuId(Long.valueOf(menuId));
            rm.setRoleId(role.getRoleId());
            this.sinkRoleMenuMapper.insert(rm);
        });
    }

    @Override
    public List<SinkRole> findUserRole(String userName) {
        return baseMapper.findUserRole(userName);
    }
}
