package com.hismart.document.modules.system.mapper;

import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface SinkRoleMenuMapper extends BaseMapper<SinkRoleMenu> {

  List<SinkRoleMenu>  getSinkRoleMenuSqlPageList(IPage<SinkRoleMenu> iPage, SinkRoleMenu iSinkRoleMenu);
}
