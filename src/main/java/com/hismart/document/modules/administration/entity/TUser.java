package com.hismart.document.modules.administration.entity;

import java.time.LocalDate;
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
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电话
     */

    private String phone;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    private Integer age;

    private Date birthday;

    private transient String birthdayStr;

    /**
     * 校区
     */
    private String campus;

    /**
     * 身份
     */
    private Integer identity;

    /**
     * 查看次数
     */
    private Integer viewNumber;

    /**
     * 作业批改次数
     */
    private Integer editNumber;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 头像URl
     */
    private String headPortrait;
    /**
     * 微信号
     */
    @TableId(value = "wx_number")
    private String wxNumber;

    /**
     * 加密后的微信号
     */
    private String wxAlgorithm;


}
