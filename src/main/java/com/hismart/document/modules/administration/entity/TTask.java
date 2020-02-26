package com.hismart.document.modules.administration.entity;

import java.time.LocalDateTime;
import java.sql.Blob;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Echo_Sxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTask implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 作业ID
     */
    private String taskId;

    /**
     * 作业名称
     */
    private String taskName;

    /**
     * 作业照片
     */
    private byte[] taskPhoto;

    /**
     * 上传人id
     */
    @TableField("upload_Id")
    private String uploadId;

    /**
     * 上传人
     */
    private String uploadName;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 门店
     */
    private String store;


    /**
     * 教师
     */
    private String teacherId;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 备注
     */
    private String remarks;


    private transient String startTime;

    private transient String endTime;



}
