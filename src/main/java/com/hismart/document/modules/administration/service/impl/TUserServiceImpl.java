package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TUser;
import com.hismart.document.modules.administration.mapper.TUserMapper;
import com.hismart.document.modules.administration.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    @Autowired
    private  TUserMapper  iTUserMapper;

    @Override
    public IPage<TUser> getTUserPageList(IPage<TUser> iPage, TUser  iTUser) {
      try {
           LambdaQueryWrapper<TUser> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTUserPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TUser> getTUserSqlPageList(IPage<TUser> iPage, TUser iTUser) {
        iPage.setRecords( iTUserMapper.getTUserSqlPageList(iPage, iTUser));
        return iPage;
        }
}
