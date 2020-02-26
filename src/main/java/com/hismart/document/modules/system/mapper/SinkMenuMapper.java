package com.hismart.document.modules.system.mapper;

import com.hismart.document.modules.system.entity.SinkMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface SinkMenuMapper extends BaseMapper<SinkMenu> {

  List<SinkMenu>  getSinkMenuSqlPageList(IPage<SinkMenu> iPage, SinkMenu iSinkMenu);
  /**
   * 查找当前菜单/按钮关联的用户 ID
   *
   * @param menuId menuId
   * @return 用户 ID集合
   */
  List<String> findUserIdsByMenuId(String menuId);



  /**
   * 递归删除菜单/按钮
   *
   * @param menuId menuId
   */
  void deleteMenus(String menuId);

  List<SinkMenu> findUserPermissions(String userName);


  List<SinkMenu> findUserMenus(String userName);

}
