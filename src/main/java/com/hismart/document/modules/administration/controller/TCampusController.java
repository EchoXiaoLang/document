package com.hismart.document.modules.administration.controller;


import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.service.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.common.domain.HismartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.administration.service.ITCampusService;
import com.hismart.document.modules.administration.entity.TCampus;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo_Sxl
 * @since 2020-02-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/tcampus/api/v1")

public class TCampusController extends BaseController {
    private final ITCampusService iITCampusService;

    @Autowired
    public TCampusController(ITCampusService iITCampusService) {
        this.iITCampusService = iITCampusService;
    }

    /**
     * TCampus 分页查询数据
     *
     * @param request  request
     * @param iTCampus iTCampus
     * @return HismartResponse
     */
    @GetMapping("/get/page")
    public HismartResponse getTCampusPageList(QueryRequest request, TCampus iTCampus) {
        try {
            IPage<TCampus> page = this.getPage(request);
            return success(this.iITCampusService.getTCampusPageList(page, iTCampus));
        } catch (Exception e) {
            log.error("/administration/tcampus/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TCampus sql分页查询数据
     *
     * @param request  request
     * @param iTCampus iTCampus
     * @return HismartResponse
     */
    @GetMapping("/get/sql/page")
    public HismartResponse getTCampusSqlPageList(QueryRequest request, TCampus iTCampus) {
        try {
            IPage<TCampus> page = this.getPage(request);
            return success(this.iITCampusService.getTCampusSqlPageList(page, iTCampus));
        } catch (Exception e) {
            log.error("/administration/tcampus/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TCampus 新增
     *
     * @param iTCampus      iTCampus
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    public HismartResponse addTCampus(@Valid TCampus iTCampus, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            return success(this.iITCampusService.save(iTCampus));
        } catch (Exception e) {
            log.error("/administration/tcampus/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }


    /**
     * TCampus 修改
     *
     * @param iTCampus      iTCampus
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TCampus iTCampus, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            return success(this.iITCampusService.updateById(iTCampus));
        } catch (Exception e) {
            log.error("/administration/tcampus/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
     * TCampus 根据id删除
     *
     * @param id 主键Id
     * @return HismartResponse
     */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITCampusService.removeById(id));
        } catch (Exception e) {
            log.error("/administration/tcampus/api/v1/delete/{}  error,[{}]", id, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

