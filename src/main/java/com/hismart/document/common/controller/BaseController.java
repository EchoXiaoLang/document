package com.hismart.document.common.controller;

import com.hismart.document.common.domain.HismartConstant;
import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.utils.JsonUtils;
import com.hismart.document.common.utils.SortUtil;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.common.service.StringCode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author echo_
 */
public class BaseController {

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getRecords());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
    }


    /**
     * 创建分页对象
     *
     * @param request request
     * @return Page
     */
    protected <T> Page<T> getPage(QueryRequest request) {
        Page<T> page = new Page<T>(request.getPageNum(), request.getPageSize());
        if (request.getSortField() != null) {
            SortUtil.handlePageSort(request, page, request.getSortField(), request.getSortOrder() == null ? HismartConstant.ORDER_DESC : request.getSortOrder(), true);
        }
        return page;
    }


    /**
     * 返回成功状态信息
     *
     * @return EagleFaceResponse
     */
    protected HismartResponse success() {
        return new HismartResponse<>(StatusCode.CODE_10000);
    }

    /**
     * 返回成功状态信息
     *
     * @param content 返回内容
     * @return EagleFaceResponse
     */
    protected HismartResponse success(Object content) {
        return new HismartResponse<>(StatusCode.CODE_10000, null, content);
    }

    /**
     * 返回成功状态信息
     *
     * @param message 提示信息
     * @param content 返回内容
     * @return EagleFaceResponse
     */
    protected HismartResponse success(String message, Object content) {
        return new HismartResponse<>(StatusCode.CODE_10000, message, content);
    }

    /**
     * 返回失败状态信息
     *
     * @param statusCode 失败状态码
     * @param message    提示信息
     * @return EagleFaceResponse
     */
    protected HismartResponse fail(String statusCode, String message) {
        return new HismartResponse<>(statusCode, message, null);
    }

    /**
     * 绑定检查
     *
     * @param bindingResult bindingResult
     * @return HismartResponse
     */
    protected HismartResponse bindingResult(BindingResult bindingResult) {
        FieldError error;
        error = (FieldError) bindingResult.getAllErrors().get(0);
        return fail(StatusCode.CODE_400, error.getDefaultMessage());
    }


    protected HismartResponse returnHismartResponse(@NotBlank String taskId, String s) {
        HashMap valueMap = JsonUtils.parseObject(s, HashMap.class);
        if (valueMap != null && valueMap.get(StringCode.STATUS) != null && StringCode.OK.equals(valueMap.get(StringCode.STATUS))) {
            return success("启动成功");
        } else {
            return fail(StatusCode.CODE_500, "启动失败");
        }
    }

}
