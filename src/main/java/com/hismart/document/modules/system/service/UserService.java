package com.hismart.document.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hismart.document.modules.administration.entity.TTask;
import com.hismart.document.modules.system.entity.SinkUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface UserService extends IService<SinkUser> {

    IPage<SinkUser> getSinkUserPageList(IPage<SinkUser> iPage, SinkUser iSinkUser);

    IPage<SinkUser> getSinkUserSqlPageList(IPage<SinkUser> iPage, SinkUser iSinkUser);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(SinkUser user) throws Exception;

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(SinkUser user) throws Exception;


    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds) throws Exception;

    /**
     * 通过用户名查找用户
     *
     * @param username username
     * @return user
     */
    SinkUser findByName(String username);


    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username) throws Exception;

}
