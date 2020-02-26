package com.hismart.document.modules.system.service;

import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author Echo_Sxl
 */
public interface RoleMenuService extends IService<SinkRoleMenu> {

 IPage<SinkRoleMenu> getSinkRoleMenuPageList(IPage<SinkRoleMenu> iPage , SinkRoleMenu iSinkRoleMenu);

 IPage<SinkRoleMenu> getSinkRoleMenuSqlPageList(IPage<SinkRoleMenu> iPage , SinkRoleMenu iSinkRoleMenu);

 void deleteRoleMenusByRoleId(String[] roleIds);

 List<SinkRoleMenu> getRoleMenusByRoleId(String roleId);

}
