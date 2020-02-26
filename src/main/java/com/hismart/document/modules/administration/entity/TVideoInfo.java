package com.hismart.document.modules.administration.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class TVideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID")
    private String id;
    /**
     * 路径
     */
    private String path;

    /**
     * 后缀
     */
    private String ext;

    /**
     * 大小
     */
    private Long size;

    /**
     * 名称
     */
    private String name;


}
