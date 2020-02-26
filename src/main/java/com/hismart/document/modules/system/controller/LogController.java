package com.hismart.document.modules.system.controller;

import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.exception.HismartException;
import com.hismart.document.modules.system.domain.SysLog;
import com.hismart.document.common.annotation.Log;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.modules.system.service.LogService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author echo_
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/log/api/v1")
public class LogController extends BaseController {


    private String message;

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Log("系统日志查看")
    @GetMapping("/get/sql/page")
    public HismartResponse logList(QueryRequest request, SysLog sysLog) {
        return success(getDataTable(logService.findLogs(request, sysLog)));
    }

    @Log("删除系统日志")
    @DeleteMapping("/delete/{ids}")
    public HismartResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) throws HismartException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
            return success("日志删除成功");
        } catch (Exception e) {
            message = "删除日志失败";
            log.error(message, e);
            throw new HismartException(message);
        }
    }


}
