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
import com.hismart.document.modules.administration.service.ITOrderService;
import com.hismart.document.modules.administration.entity.TOrder;

import org.springframework.web.bind.annotation.RestController;

/**
* @author Echo_Sxl
* @since 2020-02-21
*/
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/torder/api/v1")

    public class TOrderController extends BaseController{
    private final ITOrderService  iITOrderService;

    @Autowired
    public  TOrderController ( ITOrderService   iITOrderService) {
    this. iITOrderService =  iITOrderService;
      }

    /**
    * TOrder 分页查询数据
    *
    * @param request       request
    * @param iTOrder    iTOrder
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse getTOrderPageList(QueryRequest request, TOrder iTOrder) {
        try {
            IPage< TOrder> page = this.getPage(request);
            return success(this. iITOrderService.getTOrderPageList(page, iTOrder));
        } catch (Exception e) {
            log.error("/administration/torder/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TOrder sql分页查询数据
    *
    * @param request       request
    * @param iTOrder    iTOrder
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse getTOrderSqlPageList(QueryRequest request, TOrder iTOrder) {
        try {
            IPage< TOrder> page = this.getPage(request);
            return success(this. iITOrderService.getTOrderSqlPageList(page, iTOrder));
        } catch (Exception e) {
            log.error("/administration/torder/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TOrder 新增
    *
    * @param iTOrder    iTOrder
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/add")
    public HismartResponse addTOrder(@Valid TOrder iTOrder, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.iITOrderService.save(iTOrder));
        } catch (Exception e) {
            log.error("/administration/torder/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * TOrder 修改
    *
    * @param iTOrder     iTOrder
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TOrder iTOrder, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.iITOrderService.updateById(iTOrder));
        } catch (Exception e) {
            log.error("/administration/torder/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }

    /**
     * TOrder 修改备注
     *
     * @param iTOrder     iTOrder
     * @return HismartResponse
     */
    @PostMapping("/update_remarks")
    public HismartResponse updateRemarks(@Valid TOrder iTOrder) {
        try {
            return success(this.iITOrderService.updateRemarks(iTOrder));
        } catch (Exception e) {
            log.error("/administration/torder/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * TOrder 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITOrderService.removeById(id));
        } catch (Exception e) {
           log.error("/administration/torder/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

