package com.hismart.document.modules.administration.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class TCampus implements Serializable {

    private static final long serialVersionUID = 1L;



    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 校区名
     */
    private String campusName;

    /**
     * 地点
     */
    private String place;


}
