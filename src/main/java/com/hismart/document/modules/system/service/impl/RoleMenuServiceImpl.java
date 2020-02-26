package com.hismart.document.modules.system.service.impl;

import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.hismart.document.modules.system.mapper.SinkRoleMenuMapper;
import com.hismart.document.modules.system.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;

import java.util.Arrays;
import java.util.List;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<SinkRoleMenuMapper, SinkRoleMenu> implements RoleMenuService {

    private final SinkRoleMenuMapper iSinkRoleMenuMapper;

    public RoleMenuServiceImpl(SinkRoleMenuMapper iSinkRoleMenuMapper) {
        this.iSinkRoleMenuMapper = iSinkRoleMenuMapper;
    }

    @Override
    public IPage<SinkRoleMenu> getSinkRoleMenuPageList(IPage<SinkRoleMenu> iPage, SinkRoleMenu iSinkRoleMenu) {
        try {
            LambdaQueryWrapper<SinkRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            return this.page(iPage, queryWrapper);
        } catch (Exception e) {
            log.error("getSinkRoleMenuPageList", e);
            return null;
        }
    }

    @Override
    public IPage<SinkRoleMenu> getSinkRoleMenuSqlPageList(IPage<SinkRoleMenu> iPage, SinkRoleMenu iSinkRoleMenu) {
        iPage.setRecords(iSinkRoleMenuMapper.getSinkRoleMenuSqlPageList(iPage, iSinkRoleMenu));
        return iPage;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(String[] roleIds) {
        List<String> list = Arrays.asList(roleIds);
        baseMapper.delete(new LambdaQueryWrapper<SinkRoleMenu>().in(SinkRoleMenu::getRoleId, list));
    }


    @Override
    public List<SinkRoleMenu> getRoleMenusByRoleId(String roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SinkRoleMenu>().eq(SinkRoleMenu::getRoleId, roleId));
    }


}
