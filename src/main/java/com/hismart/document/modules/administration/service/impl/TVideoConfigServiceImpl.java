package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TVideoConfig;
import com.hismart.document.modules.administration.mapper.TVideoConfigMapper;
import com.hismart.document.modules.administration.service.ITVideoConfigService;
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
public class TVideoConfigServiceImpl extends ServiceImpl<TVideoConfigMapper, TVideoConfig> implements ITVideoConfigService {

    @Autowired
    private  TVideoConfigMapper  iTVideoConfigMapper;

    @Override
    public IPage<TVideoConfig> getTVideoConfigPageList(IPage<TVideoConfig> iPage, TVideoConfig  iTVideoConfig) {
      try {
           LambdaQueryWrapper<TVideoConfig> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTVideoConfigPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TVideoConfig> getTVideoConfigSqlPageList(IPage<TVideoConfig> iPage, TVideoConfig iTVideoConfig) {
        iPage.setRecords( iTVideoConfigMapper.getTVideoConfigSqlPageList(iPage, iTVideoConfig));
        return iPage;
        }
}
