package com.hismart.document.modules.system.mapper;

import com.hismart.document.modules.system.domain.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface LogMapper extends BaseMapper<SysLog> {

    List<Map<String,Object>> test();
}