package com.hismart.document.modules.administration.controller;


import com.hismart.document.common.authentication.JWTUtil;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.modules.administration.service.ITVideoInfoService;
import com.hismart.document.modules.system.entity.SinkUser;
import com.hismart.document.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.shiro.SecurityUtils;
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
import com.hismart.document.modules.administration.service.ITVideoService;
import com.hismart.document.modules.administration.entity.TVideo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Echo_Sxl
 * @since 2020-02-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/tvideo/api/v1")

public class TVideoController extends BaseController {
    private final ITVideoService iITVideoService;
    private final UserService userService;
    private final ITVideoInfoService   itVideoInfoService;

    @Autowired
    public TVideoController(ITVideoService iITVideoService, UserService userService, ITVideoInfoService itVideoInfoService) {
        this.iITVideoService = iITVideoService;
        this.userService = userService;
        this.itVideoInfoService = itVideoInfoService;
    }

    /**
     * TVideo 分页查询数据
     *
     * @param request request
     * @param iTVideo iTVideo
     * @return HismartResponse
     */
    @GetMapping("/get/page")
    public HismartResponse getTVideoPageList(QueryRequest request, TVideo iTVideo) {
        try {
            IPage<TVideo> page = this.getPage(request);
            return success(this.iITVideoService.getTVideoPageList(page, iTVideo));
        } catch (Exception e) {
            log.error("/administration/tvideo/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TVideo sql分页查询数据
     *
     * @param request request
     * @param iTVideo iTVideo
     * @return HismartResponse
     */
    @GetMapping("/get/sql/page")
    public HismartResponse getTVideoSqlPageList(QueryRequest request, TVideo iTVideo) {
        try {
            IPage<TVideo> page = this.getPage(request);
            return success(this.iITVideoService.getTVideoSqlPageList(page, iTVideo));
        } catch (Exception e) {
            log.error("/administration/tvideo/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TVideo 新增
     *
     * @param iTVideo       iTVideo
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    public HismartResponse addTVideo(@Valid TVideo iTVideo, BindingResult bindingResult,@RequestParam("file") MultipartFile file) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            getUploadInfo(iTVideo);
            iTVideo.setUploadTime(new Date());
            //上传视频
            itVideoInfoService.upload(file,iTVideo.getId());
            return success(this.iITVideoService.save(iTVideo));
        } catch (Exception e) {
            log.error("/administration/tvideo/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }

    private void getUploadInfo(@Valid TVideo iTVideo) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = "";
        if (StringUtils.isNotBlank(token)) {
            username = JWTUtil.getUsername(token);
        }
        iTVideo.setUploadId(username);
        SinkUser user = userService.findByName(username);
        iTVideo.setUploadName(user.getName());
    }


    /**
     * TVideo 修改
     *
     * @param iTVideo       iTVideo
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TVideo iTVideo, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            return success(this.iITVideoService.updateById(iTVideo));
        } catch (Exception e) {
            log.error("/administration/tvideo/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
     * TVideo 根据id删除
     *
     * @param id 主键Id
     * @return HismartResponse
     */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITVideoService.removeById(id));
        } catch (Exception e) {
            log.error("/administration/tvideo/api/v1/delete/{}  error,[{}]", id, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

