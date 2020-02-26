package com.hismart.document.modules.system.mapper;

import com.hismart.document.modules.system.entity.SinkRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface SinkRoleMapper extends BaseMapper<SinkRole> {

  List<SinkRole>  getSinkRoleSqlPageList(IPage<SinkRole> iPage, SinkRole iSinkRole);


  List<SinkRole> findUserRole(String userName);
}
