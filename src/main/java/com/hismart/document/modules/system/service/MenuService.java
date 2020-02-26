package com.hismart.document.modules.system.service;

import com.hismart.document.modules.system.entity.SinkMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * @author Echo_Sxl
 */
public interface MenuService extends IService<SinkMenu> {

 IPage<SinkMenu> getSinkMenuPageList(IPage<SinkMenu> iPage , SinkMenu iSinkMenu);

 IPage<SinkMenu> getSinkMenuSqlPageList(IPage<SinkMenu> iPage , SinkMenu iSinkMenu);

 /**
  * 递归删除菜单/按钮
  *
  * @param menuIds menuIds
  */
 void deleteMeuns(String[] menuIds) throws Exception;


 Map<String, Object> findMenus(SinkMenu menu);


 List<SinkMenu> findUserPermissions(String username);

 /**
  * 查找当前菜单/按钮关联的用户 ID
  *
  * @param menuId menuId
  * @return 用户 ID集合
  */
 List<String> findUserIdsByMenuId(String menuId);


 List<SinkMenu> findUserMenus(String username);

}
