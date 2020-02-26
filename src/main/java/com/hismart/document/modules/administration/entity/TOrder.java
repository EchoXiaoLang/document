package com.hismart.document.modules.administration.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

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
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 商品名称
     */
    private String tradeName;

    /**
     * 购买价格
     */
    private Float price;

    /**
     * 购买人
     */
    private String purchaserId;
    /**
     * 购买人
     */
    private String purchaserName;

    /**
     * 购买时间
     */
    private Date tradeTime;

    /**
     * 备注
     */
    private String remarks;


    private transient String startTime;

    private transient String endTime;


}
