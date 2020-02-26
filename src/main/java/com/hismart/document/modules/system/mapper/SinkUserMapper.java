package com.hismart.document.modules.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismart.document.modules.system.entity.SinkUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface SinkUserMapper extends BaseMapper<SinkUser> {

  List<SinkUser>  getSinkUserSqlPageList(IPage<SinkUser> iPage, SinkUser iSinkUser);

  IPage<SinkUser> findUserDetail(Page page, @Param("user") SinkUser user);


  /**
   * 获取单个用户详情
   *
   * @param username 用户名
   * @return 用户信息
   */
  SinkUser findDetail(String username);


}
