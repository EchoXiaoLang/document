package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface ITUserService extends IService<TUser> {

 IPage<TUser> getTUserPageList(IPage<TUser> iPage , TUser iTUser);

 IPage<TUser> getTUserSqlPageList(IPage<TUser> iPage , TUser iTUser);
}
