package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TUserMapper extends BaseMapper<TUser> {

  List<TUser>  getTUserSqlPageList(IPage<TUser> iPage, @Param("user") TUser user);
}
