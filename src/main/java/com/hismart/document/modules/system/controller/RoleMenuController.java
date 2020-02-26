package com.hismart.document.modules.system.controller;


import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.hismart.document.common.service.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.system.service.RoleMenuService;

import org.springframework.web.bind.annotation.RestController;

/**
* @author Echo_Sxl
* @since 2019-08-08
*/
@Slf4j
@Validated
@RestController
@RequestMapping("/system/sink_role_menu/api/v1")

    public class RoleMenuController extends BaseController{
    private final RoleMenuService iRoleMenuService;

    @Autowired
    public RoleMenuController(RoleMenuService iRoleMenuService) {
    this.iRoleMenuService = iRoleMenuService;
      }

    /**
    * SinkRoleMenu 分页查询数据
    *
    * @param request       request
    * @param iSinkRoleMenu    iSinkRoleMenu
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse getSinkRoleMenuPageList(QueryRequest request, SinkRoleMenu iSinkRoleMenu) {
        try {
            IPage< SinkRoleMenu> page = this.getPage(request);
            return success(this.iRoleMenuService.getSinkRoleMenuPageList(page, iSinkRoleMenu));
        } catch (Exception e) {
            log.error("/center/sink_role_menu/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * SinkRoleMenu sql分页查询数据
    *
    * @param request       request
    * @param iSinkRoleMenu    iSinkRoleMenu
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse getSinkRoleMenuSqlPageList(QueryRequest request, SinkRoleMenu iSinkRoleMenu) {
        try {
            IPage< SinkRoleMenu> page = this.getPage(request);
            return success(this.iRoleMenuService.getSinkRoleMenuSqlPageList(page, iSinkRoleMenu));
        } catch (Exception e) {
            log.error("/center/sink_role_menu/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * SinkRoleMenu 新增
    *
    * @param iSinkRoleMenu    iSinkRoleMenu
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PutMapping("/add")
    public HismartResponse addSinkRoleMenu(@Valid SinkRoleMenu iSinkRoleMenu, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.iRoleMenuService.save(iSinkRoleMenu));
        } catch (Exception e) {
            log.error("/center/sink_role_menu/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * SinkRoleMenu 修改
    *
    * @param iSinkRoleMenu     iSinkRoleMenu
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid SinkRoleMenu iSinkRoleMenu, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.iRoleMenuService.updateById(iSinkRoleMenu));
        } catch (Exception e) {
            log.error("/center/sink_role_menu/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * SinkRoleMenu 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iRoleMenuService.removeById(id));
        } catch (Exception e) {
           log.error("/center/sink_role_menu/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

