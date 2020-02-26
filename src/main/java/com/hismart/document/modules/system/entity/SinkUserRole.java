package com.hismart.document.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色关联表
 *
 * @author Echo_Sxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SinkUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
        @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
        @TableField("ROLE_ID")
    private Long roleId;


}
