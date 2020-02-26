package com.hismart.document.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sink_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -8878596941954995444L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private String username;

    private String operation;

    private Long time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;

    private String location;

}