package com.hismart.document.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hismart.document.common.domain.HismartConstant;
import com.hismart.document.common.domain.Tree;
import com.hismart.document.modules.system.entity.SinkMenu;
import com.hismart.document.modules.system.manager.UserManager;
import com.hismart.document.modules.system.service.MenuService;
import com.hismart.document.common.utils.TreeUtil;
import com.hismart.document.modules.system.mapper.SinkMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;

import java.util.*;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<SinkMenuMapper, SinkMenu> implements MenuService {

    private final SinkMenuMapper iSinkMenuMapper;
    private final UserManager userManager;

    public MenuServiceImpl(SinkMenuMapper iSinkMenuMapper, UserManager userManager) {
        this.iSinkMenuMapper = iSinkMenuMapper;
        this.userManager = userManager;
    }

    @Override
    public IPage<SinkMenu> getSinkMenuPageList(IPage<SinkMenu> iPage, SinkMenu iSinkMenu) {
        try {
            LambdaQueryWrapper<SinkMenu> queryWrapper = new LambdaQueryWrapper<>();
            return this.page(iPage, queryWrapper);
        } catch (Exception e) {
            log.error("getSinkMenuPageList", e);
            return null;
        }
    }

    @Override
    public IPage<SinkMenu> getSinkMenuSqlPageList(IPage<SinkMenu> iPage, SinkMenu iSinkMenu) {
        iPage.setRecords(iSinkMenuMapper.getSinkMenuSqlPageList(iPage, iSinkMenu));
        return iPage;
    }

    @Override
    public void deleteMeuns(String[] menuIds) throws Exception {
        this.delete(Arrays.asList(menuIds));
        for (String menuId : menuIds) {
            // 查找与这些菜单/按钮关联的用户
            List<String> userIds = this.baseMapper.findUserIdsByMenuId(String.valueOf(menuId));
            // 重新将这些用户的角色和权限缓存到 Redis中
            if (userIds != null && userIds.size() > 0) {
                this.userManager.loadUserPermissionRoleRedisCache(userIds);
            }

        }

    }

    @Override
    public Map<String, Object> findMenus(SinkMenu menu) {
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<SinkMenu> queryWrapper = new LambdaQueryWrapper<>();
            findMenuCondition(queryWrapper, menu);
            List<SinkMenu> menus = baseMapper.selectList(queryWrapper);

            List<Tree<SinkMenu>> trees = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            buildTrees(trees, menus, ids);

            result.put("ids", ids);
            if (StringUtils.equals(menu.getType(), HismartConstant.TYPE_BUTTON)) {
                result.put("rows", trees);
            } else {
                Tree<SinkMenu> menuTree = TreeUtil.build(trees);
                result.put("rows", menuTree);
            }

            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    private void findMenuCondition(LambdaQueryWrapper<SinkMenu> queryWrapper, SinkMenu menu) {
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.eq(SinkMenu::getMenuName, menu.getMenuName());
        }
        if (StringUtils.isNotBlank(menu.getCreateTimeFrom()) && StringUtils.isNotBlank(menu.getCreateTimeTo())) {
            queryWrapper
                    .ge(SinkMenu::getCreateTime, menu.getCreateTimeFrom())
                    .le(SinkMenu::getCreateTime, menu.getCreateTimeTo());
        }
        queryWrapper.orderByAsc(SinkMenu::getMenuId);

    }


    private void buildTrees(List<Tree<SinkMenu>> trees, List<SinkMenu> menus, List<String> ids) {
        menus.forEach(menu -> {
            ids.add(menu.getMenuId().toString());
            Tree<SinkMenu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            tree.setTitle(tree.getText());
            tree.setIcon(menu.getIcon());
            tree.setCreateTime(menu.getCreateTime());
            tree.setModifyTime(menu.getModifyTime());
            tree.setPath(menu.getUrl());
            tree.setOrder(menu.getOrderNum());
            tree.setPermission(menu.getPerms());
            tree.setType(menu.getType());
            trees.add(tree);
        });
    }

    @Override
    public List<SinkMenu> findUserPermissions(String username) {
        return this.iSinkMenuMapper.findUserPermissions(username);
    }

    @Override
    public List<String> findUserIdsByMenuId(String menuId) {
        return iSinkMenuMapper.findUserIdsByMenuId(menuId);
    }

    @Override
    public List<SinkMenu> findUserMenus(String username) {
        return this.iSinkMenuMapper.findUserMenus(username);
    }


    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<SinkMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SinkMenu::getParentId, menuIds);
        List<SinkMenu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }
}
