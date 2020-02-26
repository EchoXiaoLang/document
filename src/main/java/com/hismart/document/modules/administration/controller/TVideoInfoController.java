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
import com.hismart.document.modules.administration.service.ITVideoInfoService;
import com.hismart.document.modules.administration.entity.TVideoInfo;

import org.springframework.web.bind.annotation.RestController;

/**
* @author Echo_Sxl
* @since 2020-02-24
*/
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/tvideo_info/api/v1")

    public class TVideoInfoController extends BaseController{
    private final ITVideoInfoService  iITVideoInfoService;

    @Autowired
    public  TVideoInfoController ( ITVideoInfoService   iITVideoInfoService) {
    this. iITVideoInfoService =  iITVideoInfoService;
      }

    /**
    * TVideoInfo 分页查询数据
    *
    * @param request       request
    * @param iTVideoInfo    iTVideoInfo
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse getTVideoInfoPageList(QueryRequest request, TVideoInfo iTVideoInfo) {
        try {
            IPage< TVideoInfo> page = this.getPage(request);
            return success(this. iITVideoInfoService.getTVideoInfoPageList(page, iTVideoInfo));
        } catch (Exception e) {
            log.error("/administration/tvideo_info/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TVideoInfo sql分页查询数据
    *
    * @param request       request
    * @param iTVideoInfo    iTVideoInfo
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse getTVideoInfoSqlPageList(QueryRequest request, TVideoInfo iTVideoInfo) {
        try {
            IPage< TVideoInfo> page = this.getPage(request);
            return success(this. iITVideoInfoService.getTVideoInfoSqlPageList(page, iTVideoInfo));
        } catch (Exception e) {
            log.error("/administration/tvideo_info/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TVideoInfo 新增
    *
    * @param iTVideoInfo    iTVideoInfo
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PutMapping("/add")
    public HismartResponse addTVideoInfo(@Valid TVideoInfo iTVideoInfo, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.iITVideoInfoService.save(iTVideoInfo));
        } catch (Exception e) {
            log.error("/administration/tvideo_info/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * TVideoInfo 修改
    *
    * @param iTVideoInfo     iTVideoInfo
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TVideoInfo iTVideoInfo, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.iITVideoInfoService.updateById(iTVideoInfo));
        } catch (Exception e) {
            log.error("/administration/tvideo_info/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * TVideoInfo 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITVideoInfoService.removeById(id));
        } catch (Exception e) {
           log.error("/administration/tvideo_info/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

