package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TVideo;
import com.hismart.document.modules.administration.mapper.TVideoMapper;
import com.hismart.document.modules.administration.service.ITVideoService;
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
public class TVideoServiceImpl extends ServiceImpl<TVideoMapper, TVideo> implements ITVideoService {

    @Autowired
    private  TVideoMapper  iTVideoMapper;

    @Override
    public IPage<TVideo> getTVideoPageList(IPage<TVideo> iPage, TVideo  iTVideo) {
      try {
           LambdaQueryWrapper<TVideo> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTVideoPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TVideo> getTVideoSqlPageList(IPage<TVideo> iPage, TVideo iTVideo) {
        iPage.setRecords( iTVideoMapper.getTVideoSqlPageList(iPage, iTVideo));
        return iPage;
        }
}
