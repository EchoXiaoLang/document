package com.hismart.document.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 菜单表
 *
 * @author Echo_Sxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SinkMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单/按钮ID
     */
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField("MENU_NAME")
    private String menuName;

    /**
     * 菜单URL
     */
    @TableField("URL")
    private String url;

    /**
     * 权限标识
     */
    @TableField("PERMS")
    private String perms;

    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;


    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    private Double orderNum;

    @NotBlank(message = "{required}")
    private String type;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;


    private transient String createTimeFrom;
    private transient String createTimeTo;


    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";


}
