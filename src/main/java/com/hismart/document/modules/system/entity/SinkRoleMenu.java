package com.hismart.document.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色菜单关联表
 *
 * @author Echo_Sxl
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SinkRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
        @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 菜单/按钮ID
     */
        @TableField("MENU_ID")
    private Long menuId;


}
