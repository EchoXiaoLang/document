package com.hismart.document.modules.system.service;

import com.hismart.document.modules.system.entity.SinkRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author Echo_Sxl
 */
public interface RoleService extends IService<SinkRole> {

 IPage<SinkRole> getSinkRolePageList(IPage<SinkRole> iPage , SinkRole iSinkRole);

 IPage<SinkRole> getSinkRoleSqlPageList(IPage<SinkRole> iPage , SinkRole iSinkRole);


 void createRole(SinkRole role);


 void updateRole(SinkRole role) throws Exception;

 void deleteRoles(String[] roleIds) throws Exception;


 List<SinkRole> findUserRole(String userName);

}
