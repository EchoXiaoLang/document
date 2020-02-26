package com.hismart.document.modules.administration.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author Echo_Sxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID")
    private String id;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 上传人ID
     */
    private String uploadId;

    /**
     * 上传人
     */
    private String uploadName;

    /**
     * 视频类型
     */
    private Integer videoType;

    /**
     * 播放量
     */
    private Integer playNumber;

    /**
     * 购买量
     */
    private Integer purchaseNumber;

    /**
     * 上传时间
     */
    private Date uploadTime;


    private transient String startTime;

    private transient String endTime;

}
