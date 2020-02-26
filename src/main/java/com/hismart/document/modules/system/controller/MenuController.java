package com.hismart.document.modules.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.domain.router.VueRouter;
import com.hismart.document.common.annotation.Log;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.modules.system.manager.UserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.system.service.MenuService;
import com.hismart.document.modules.system.entity.SinkMenu;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Echo_Sxl
 * @since 2019-08-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/sink_menu/api/v1")

public class MenuController extends BaseController {
    private final MenuService iMenuService;
    private final UserManager userManager;

    @Autowired
    public MenuController(MenuService iMenuService, UserManager userManager) {
        this.iMenuService = iMenuService;
        this.userManager = userManager;
    }

    /**
     * SinkMenu 资源
     *
     * @return HismartResponse
     */
    @Log("资源获取")
    @GetMapping("/get/menu")
    public HismartResponse getSinkMenuPageList(SinkMenu sinkMenu) {
        try {
            return success(iMenuService.findMenus(sinkMenu));
        } catch (Exception e) {
            log.error("/center/sink_menu/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    @Log("资源用户资源")
    @GetMapping("/{username}")
    public ArrayList<VueRouter<SinkMenu>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userManager.getUserRouters(username);
    }

    /**
     * SinkMenu sql分页查询数据
     *
     * @param request   request
     * @param iSinkMenu iSinkMenu
     * @return HismartResponse
     */
    @Log("资源分页查询")
    @GetMapping("/get/sql/page")
    public HismartResponse getSinkMenuSqlPageList(QueryRequest request, SinkMenu iSinkMenu) {
        try {
            IPage<SinkMenu> page = this.getPage(request);
            return success(this.iMenuService.getSinkMenuSqlPageList(page, iSinkMenu));
        } catch (Exception e) {
            log.error("/center/sink_menu/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * SinkMenu 新增
     *
     * @param iSinkMenu     iSinkMenu
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    @Log("资源新增")
    public HismartResponse addSinkMenu(@Valid SinkMenu iSinkMenu, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            iSinkMenu.setCreateTime(new Date());
            setMenu(iSinkMenu);

            return success(this.iMenuService.save(iSinkMenu));
        } catch (Exception e) {
            log.error("/center/sink_menu/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }


    /**
     * SinkMenu 修改
     *
     * @param iSinkMenu     iSinkMenu
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PostMapping("/update")
    @Log("资源修改")
    public HismartResponse updateTUserById(@Valid SinkMenu iSinkMenu, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            iSinkMenu.setModifyTime(new Date());
            setMenu(iSinkMenu);
            this.iMenuService.updateById(iSinkMenu);
            // 查找与这些菜单/按钮关联的用户
            List<String> userIds = this.iMenuService.findUserIdsByMenuId(String.valueOf(iSinkMenu.getMenuId()));
            // 重新将这些用户的角色和权限缓存到 Redis中
            if (userIds != null && userIds.size() > 0) {
                this.userManager.loadUserPermissionRoleRedisCache(userIds);
            }
            return success();
        } catch (Exception e) {
            log.error("/center/sink_menu/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
     * SinkMenu 根据id删除
     *
     * @param menuIds menuIds
     * @return HismartResponse
     */
    @Log("资源删除")
    @DeleteMapping("/delete/{menuIds}")
    public HismartResponse deleteTUserById(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        try {
            String[] ids = menuIds.split(StringPool.COMMA);
            this.iMenuService.deleteMeuns(ids);
            return success("删除成功");
        } catch (Exception e) {
            log.error("/center/sink_menu/api/v1/delete/{}  error,[{}]", menuIds, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }


    private void setMenu(SinkMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (SinkMenu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
    }

}

