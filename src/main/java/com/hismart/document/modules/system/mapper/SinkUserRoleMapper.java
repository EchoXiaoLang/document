package com.hismart.document.modules.system.mapper;

import com.hismart.document.modules.system.entity.SinkUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Echo_Sxl
 */
public interface SinkUserRoleMapper extends BaseMapper<SinkUserRole> {

    List<SinkUserRole> getSinkUserRoleSqlPageList(IPage<SinkUserRole> iPage, SinkUserRole iSinkUserRole);


    /**
     * 根据角色Id删除该角色的用户关系
     *
     * @param roleId 角色ID
     * @return boolean
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);


    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author lzx
     * @date 2019年03月04日17:46:49
     */
    Boolean deleteByUserId(@Param("userId") Long userId);
}
