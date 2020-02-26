package com.hismart.document.modules.administration.entity;

import java.math.BigDecimal;
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
public class TVideoConfig implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 组合1视频数量
     */
    private Integer composeVideoNumberOne;

    /**
     * 组合1视频有效时间
     */
    private String composeValidityTimeOne;

    /**
     * 组合1视频价格
     */
    private BigDecimal composeVideoPriceOne;

    /**
     * 组合2视频数量
     */
    private Integer composeVideoNumberTwo;

    /**
     * 组合2视频有效时间
     */
    private String composeValidityTimeTwo;

    /**
     * 组合2视频价格
     */
    private BigDecimal composeVideoPriceTwo;

    /**
     * 组合3视频数量
     */
    private Integer composeVideoNumberThree;

    /**
     * 组合3视频有效时间
     */
    private String composeValidityTimeThree;

    /**
     * 组合3视频价格
     */
    private BigDecimal composeVideoPriceThree;

    /**
     * 单个视频价格
     */
    private BigDecimal singleVideoPrice;

    /**
     * 单个有效时间
     */
    private String singleValidityTime;

    /**
     * 作业批改数量1
     */
    private Integer workModifyNumberOne;

    /**
     * 作业批改价格1
     */
    private BigDecimal workModifyPriceOne;

    /**
     * 作业批改数量2
     */
    private Integer workModifyNumberTwo;

    /**
     * 作业批改价格2
     */
    private BigDecimal workModifyPriceTwo;


}
