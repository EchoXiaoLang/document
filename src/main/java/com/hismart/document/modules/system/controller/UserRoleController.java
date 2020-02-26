package com.hismart.document.modules.system.controller;


import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.modules.system.service.UserRoleService;
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
import com.hismart.document.modules.system.entity.SinkUserRole;

import org.springframework.web.bind.annotation.RestController;

/**
* @author Echo_Sxl
* @since 2019-08-08
*/
@Slf4j
@Validated
@RestController
@RequestMapping("/system/sink_user_role/api/v1")

    public class UserRoleController extends BaseController{
    private final UserRoleService iUserRoleService;

    @Autowired
    public UserRoleController(UserRoleService iUserRoleService) {
    this.iUserRoleService = iUserRoleService;
      }

    /**
    * SinkUserRole 分页查询数据
    *
    * @param request       request
    * @param iSinkUserRole    iSinkUserRole
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse getSinkUserRolePageList(QueryRequest request, SinkUserRole iSinkUserRole) {
        try {
            IPage< SinkUserRole> page = this.getPage(request);
            return success(this.iUserRoleService.getSinkUserRolePageList(page, iSinkUserRole));
        } catch (Exception e) {
            log.error("/center/sink_user_role/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * SinkUserRole sql分页查询数据
    *
    * @param request       request
    * @param iSinkUserRole    iSinkUserRole
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse getSinkUserRoleSqlPageList(QueryRequest request, SinkUserRole iSinkUserRole) {
        try {
            IPage< SinkUserRole> page = this.getPage(request);
            return success(this.iUserRoleService.getSinkUserRoleSqlPageList(page, iSinkUserRole));
        } catch (Exception e) {
            log.error("/center/sink_user_role/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * SinkUserRole 新增
    *
    * @param iSinkUserRole    iSinkUserRole
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PutMapping("/add")
    public HismartResponse addSinkUserRole(@Valid SinkUserRole iSinkUserRole, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.iUserRoleService.save(iSinkUserRole));
        } catch (Exception e) {
            log.error("/center/sink_user_role/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * SinkUserRole 修改
    *
    * @param iSinkUserRole     iSinkUserRole
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid SinkUserRole iSinkUserRole, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.iUserRoleService.updateById(iSinkUserRole));
        } catch (Exception e) {
            log.error("/center/sink_user_role/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * SinkUserRole 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iUserRoleService.removeById(id));
        } catch (Exception e) {
           log.error("/center/sink_user_role/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

