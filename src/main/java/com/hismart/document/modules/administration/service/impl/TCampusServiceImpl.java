package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TCampus;
import com.hismart.document.modules.administration.mapper.TCampusMapper;
import com.hismart.document.modules.administration.service.ITCampusService;
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
public class TCampusServiceImpl extends ServiceImpl<TCampusMapper, TCampus> implements ITCampusService {

    @Autowired
    private  TCampusMapper  iTCampusMapper;

    @Override
    public IPage<TCampus> getTCampusPageList(IPage<TCampus> iPage, TCampus  iTCampus) {
      try {
           LambdaQueryWrapper<TCampus> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTCampusPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TCampus> getTCampusSqlPageList(IPage<TCampus> iPage, TCampus iTCampus) {
        iPage.setRecords( iTCampusMapper.getTCampusSqlPageList(iPage, iTCampus));
        return iPage;
        }
}
