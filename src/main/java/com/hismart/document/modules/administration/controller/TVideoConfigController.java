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
import com.hismart.document.modules.administration.service.ITVideoConfigService;
import com.hismart.document.modules.administration.entity.TVideoConfig;

import org.springframework.web.bind.annotation.RestController;

/**
* @author Echo_Sxl
* @since 2020-02-25
*/
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/tvideo_config/api/v1")

    public class TVideoConfigController extends BaseController{
    private final ITVideoConfigService  iITVideoConfigService;

    @Autowired
    public  TVideoConfigController ( ITVideoConfigService   iITVideoConfigService) {
    this. iITVideoConfigService =  iITVideoConfigService;
      }

    /**
    * TVideoConfig 分页查询数据
    *
    * @param request       request
    * @param iTVideoConfig    iTVideoConfig
    * @return HismartResponse
    */
    @GetMapping("/get/page")
    public HismartResponse getTVideoConfigPageList(QueryRequest request, TVideoConfig iTVideoConfig) {
        try {
            IPage< TVideoConfig> page = this.getPage(request);
            return success(this. iITVideoConfigService.getTVideoConfigPageList(page, iTVideoConfig));
        } catch (Exception e) {
            log.error("/administration/tvideo_config/api/v1/get/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TVideoConfig sql分页查询数据
    *
    * @param request       request
    * @param iTVideoConfig    iTVideoConfig
    * @return HismartResponse
    */
    @GetMapping("/get/sql/page")
    public HismartResponse getTVideoConfigSqlPageList(QueryRequest request, TVideoConfig iTVideoConfig) {
        try {
            IPage< TVideoConfig> page = this.getPage(request);
            return success(this. iITVideoConfigService.getTVideoConfigSqlPageList(page, iTVideoConfig));
        } catch (Exception e) {
            log.error("/administration/tvideo_config/api/v1/get/sql/page {}",  e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
    * TVideoConfig 新增
    *
    * @param iTVideoConfig    iTVideoConfig
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PutMapping("/add")
    public HismartResponse addTVideoConfig(@Valid TVideoConfig iTVideoConfig, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
             return success(this.iITVideoConfigService.save(iTVideoConfig));
        } catch (Exception e) {
            log.error("/administration/tvideo_config/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }



    /**
    * TVideoConfig 修改
    *
    * @param iTVideoConfig     iTVideoConfig
    * @param bindingResult 绑定检查
    * @return HismartResponse
    */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TVideoConfig iTVideoConfig, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
           return success(this.iITVideoConfigService.updateById(iTVideoConfig));
        } catch (Exception e) {
            log.error("/administration/tvideo_config/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
    * TVideoConfig 根据id删除
    *
    * @param id 主键Id
    * @return HismartResponse
    */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITVideoConfigService.removeById(id));
        } catch (Exception e) {
           log.error("/administration/tvideo_config/api/v1/delete/{}  error,[{}]", id, e.getMessage());
           return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

