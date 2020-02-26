package com.hismart.document.modules.administration.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hismart.document.common.authentication.JWTUtil;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.modules.administration.entity.TOrder;
import com.hismart.document.modules.system.entity.SinkUser;
import com.hismart.document.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.common.domain.HismartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.administration.service.ITTaskService;
import com.hismart.document.modules.administration.entity.TTask;

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
@RequestMapping("/administration/ttask/api/v1")

public class TTaskController extends BaseController {
    private final ITTaskService iITTaskService;
    private final UserService userService;

    @Autowired
    public TTaskController(ITTaskService iITTaskService, UserService userService) {
        this.iITTaskService = iITTaskService;
        this.userService = userService;
    }

    /**
     * TTask 分页查询数据
     *
     * @param request request
     * @param iTTask  iTTask
     * @return HismartResponse
     */
    @GetMapping("/get/page")
    public HismartResponse getTTaskPageList(QueryRequest request, TTask iTTask) {
        try {
            IPage<TTask> page = this.getPage(request);
            return success(this.iITTaskService.getTTaskPageList(page, iTTask));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TTask sql分页查询数据
     *
     * @param request request
     * @param iTTask  iTTask
     * @return HismartResponse
     */
    @GetMapping("/get/sql/page")
    public HismartResponse getTTaskSqlPageList(QueryRequest request, TTask iTTask) {
        try {
            IPage<TTask> page = this.getPage(request);
            return success(this.iITTaskService.getTTaskSqlPageList(page, iTTask));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TTask 新增
     *
     * @param iTTask        iTTask
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    public HismartResponse addTTask(@Valid TTask iTTask,@RequestParam("file") MultipartFile img, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            getUploadInfo(iTTask);
            byte[] data;
            data = img.getBytes();
            //toDo   将图片不失真比例压缩
            iTTask.setTaskPhoto(data);
            return success(this.iITTaskService.save(iTTask));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }


    /**
     * TTask 修改
     *
     * @param iTTask        iTTask
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TTask iTTask, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            getUploadInfo(iTTask);

            return success(this.iITTaskService.updateById(iTTask));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }

    /**
     * TTask 上传图片
     *
     * @param img 图片
     * @return HismartResponse
     */
    @PostMapping("/save_img")
    public HismartResponse saveImg(@NotBlank String taskId, @RequestParam("file") MultipartFile img) {
        try {
            LambdaQueryWrapper<TTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TTask::getTaskId, taskId);
            TTask task = iITTaskService.getOne(queryWrapper);
            byte[] data;
            data = img.getBytes();
            //toDo   将图片不失真比例压缩
            task.setTaskPhoto(data);
            return success(this.iITTaskService.updateById(task));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/save_img/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "上传图片异常");
        }
    }

    /**
     * TTask 分配老师
     *
     * @param taskId    作业Id
     * @param teacherId 教师Id
     * @param teacher   教师名
     * @return HismartResponse
     */
    @PostMapping("/distribution")
    public HismartResponse distributionTeacher(@NotBlank String taskId, @NotBlank String teacherId, @NotBlank String teacher) {
        try {
            return success(this.iITTaskService.updateTeacherInfo(taskId, teacherId, teacher));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    private void getUploadInfo(@Valid TTask iTTask) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = "";
        if (StringUtils.isNotBlank(token)) {
            username = JWTUtil.getUsername(token);
        }
        iTTask.setUploadId(username);
        SinkUser user = userService.findByName(username);
        iTTask.setUploadName(user.getName());
        iTTask.setUploadTime(new Date());
    }


    /**
     * TTask 根据id删除
     *
     * @param id 主键Id
     * @return HismartResponse
     */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITTaskService.removeById(id));
        } catch (Exception e) {
            log.error("/administration/ttask/api/v1/delete/{}  error,[{}]", id, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

}

