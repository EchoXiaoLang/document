package com.hismart.document.modules.system.service.impl;

import com.hismart.document.modules.system.service.UserRoleService;
import com.hismart.document.modules.system.entity.SinkUserRole;
import com.hismart.document.modules.system.mapper.SinkUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<SinkUserRoleMapper, SinkUserRole> implements UserRoleService {

    @Autowired
    private SinkUserRoleMapper iSinkUserRoleMapper;

    @Override
    public IPage<SinkUserRole> getSinkUserRolePageList(IPage<SinkUserRole> iPage, SinkUserRole iSinkUserRole) {
        try {
            LambdaQueryWrapper<SinkUserRole> queryWrapper = new LambdaQueryWrapper<>();
            return this.page(iPage, queryWrapper);
        } catch (Exception e) {
            log.error("getSinkUserRolePageList", e);
            return null;
        }
    }

    @Override
    public IPage<SinkUserRole> getSinkUserRoleSqlPageList(IPage<SinkUserRole> iPage, SinkUserRole iSinkUserRole) {
        iPage.setRecords(iSinkUserRoleMapper.getSinkUserRoleSqlPageList(iPage, iSinkUserRole));
        return iPage;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void deleteUserRolesByRoleId(String[] roleIds) {
        Arrays.stream(roleIds).forEach(id -> baseMapper.deleteByRoleId(Long.valueOf(id)));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void deleteUserRolesByUserId(String[] userIds) {
        Arrays.stream(userIds).forEach(id -> baseMapper.deleteByUserId(Long.valueOf(id)));
    }

    @Override
    public List<String> findUserIdsByRoleId(String[] roleIds) {

        List<SinkUserRole> list = baseMapper.selectList(new LambdaQueryWrapper<SinkUserRole>().in(SinkUserRole::getRoleId, String.join(",", roleIds)));
        return list.stream().map(userRole -> String.valueOf(userRole.getUserId())).collect(Collectors.toList());
    }

}
