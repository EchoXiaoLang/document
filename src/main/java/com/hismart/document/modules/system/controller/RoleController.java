package com.hismart.document.modules.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.modules.system.entity.SinkRole;
import com.hismart.document.modules.system.entity.SinkRoleMenu;
import com.hismart.document.modules.system.service.RoleService;
import com.hismart.document.common.annotation.Log;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.modules.system.service.RoleMenuService;
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

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Echo_Sxl
 * @since 2019-08-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/sink_role/api/v1")

public class RoleController extends BaseController {
    private final RoleService iRoleService;
    private final RoleMenuService roleMenuService;

    @Autowired
    public RoleController(RoleService iRoleService, RoleMenuService roleMenuService) {
        this.iRoleService = iRoleService;
        this.roleMenuService = roleMenuService;
    }

    /**
     * SinkRole 分页查询数据
     *
     * @param request   request
     * @param iSinkRole iSinkRole
     * @return HismartResponse
     */
    @Log("角色分页查询")
    @GetMapping("/get/page")
    public HismartResponse getSinkRolePageList(QueryRequest request, SinkRole iSinkRole) {
        try {
            IPage<SinkRole> page = this.getPage(request);
            return success(this.iRoleService.getSinkRolePageList(page, iSinkRole));
        } catch (Exception e) {
            log.error("/center/sink_role/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * SinkRole sql分页查询数据
     *
     * @param request   request
     * @param iSinkRole iSinkRole
     * @return HismartResponse
     */
    @Log("角色分页sql查询")
    @GetMapping("/get/sql/page")
    public HismartResponse getSinkRoleSqlPageList(QueryRequest request, SinkRole iSinkRole) {
        try {
            IPage<SinkRole> page = this.getPage(request);
            return success(this.iRoleService.getSinkRoleSqlPageList(page, iSinkRole));
        } catch (Exception e) {
            log.error("/center/sink_role/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * SinkRole 新增
     *
     * @param iSinkRole     iSinkRole
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    @Log("角色新增")
    public HismartResponse addSinkRole(@Valid SinkRole iSinkRole, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            this.iRoleService.createRole(iSinkRole);
            return success("新增角色成功");
        } catch (Exception e) {
            log.error("/center/sink_role/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }

    @Log("角色资源获取")
    @GetMapping("menu/{roleId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String roleId) {
        List<SinkRoleMenu> list = this.roleMenuService.getRoleMenusByRoleId(roleId);
        return list.stream().map(roleMenu -> String.valueOf(roleMenu.getMenuId())).collect(Collectors.toList());
    }

    /**
     * SinkRole 修改
     *
     * @param iSinkRole     iSinkRole
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @Log("角色更新")
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid SinkRole iSinkRole, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            this.iRoleService.updateRole(iSinkRole);
            return success("修改角色成功");
        } catch (Exception e) {
            log.error("/center/sink_role/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
     * SinkRole 根据id删除
     *
     * @param roleIds 角色Ids
     * @return HismartResponse
     */
    @Log("角色删除")
    @DeleteMapping("/delete/{roleIds}")
    public HismartResponse deleteTUserById(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        try {
            String[] ids = roleIds.split(StringPool.COMMA);
            this.iRoleService.deleteRoles(ids);
            return success("删除角色成功");
        } catch (Exception e) {
            log.error("/center/sink_role/api/v1/delete/{}  error,[{}]", roleIds, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

