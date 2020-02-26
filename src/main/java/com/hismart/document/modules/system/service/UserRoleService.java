package com.hismart.document.modules.system.service;

import com.hismart.document.modules.system.entity.SinkUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author Echo_Sxl
 */
public interface UserRoleService extends IService<SinkUserRole> {

 IPage<SinkUserRole> getSinkUserRolePageList(IPage<SinkUserRole> iPage , SinkUserRole iSinkUserRole);

 IPage<SinkUserRole> getSinkUserRoleSqlPageList(IPage<SinkUserRole> iPage , SinkUserRole iSinkUserRole);

 void deleteUserRolesByRoleId(String[] roleIds);

 void deleteUserRolesByUserId(String[] userIds);

 List<String> findUserIdsByRoleId(String[] roleIds);
}
