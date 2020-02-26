package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TOrder;
import com.hismart.document.modules.administration.mapper.TOrderMapper;
import com.hismart.document.modules.administration.service.ITOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Autowired
    private  TOrderMapper  iTOrderMapper;

    @Override
    public IPage<TOrder> getTOrderPageList(IPage<TOrder> iPage, TOrder  iTOrder) {
      try {
           LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<>();

          if (StringUtils.isNotBlank(iTOrder.getOrderNumber())) {
              queryWrapper.like(TOrder::getOrderNumber, iTOrder.getOrderNumber());
          }
          if (StringUtils.isNotBlank(iTOrder.getTradeName())) {
              queryWrapper.like(TOrder::getTradeName, iTOrder.getTradeName());
          }
          if (StringUtils.isNotBlank(iTOrder.getPurchaserName())) {
              queryWrapper.like(TOrder::getPurchaserName, iTOrder.getPurchaserName());
          }
          if (StringUtils.isNotBlank(iTOrder.getStartTime())) {
              queryWrapper.ge(TOrder::getTradeTime, iTOrder.getStartTime());

          }
          if (StringUtils.isNotBlank(iTOrder.getEndTime())) {
              queryWrapper.le(TOrder::getTradeTime, iTOrder.getEndTime());

          }
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTOrderPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TOrder> getTOrderSqlPageList(IPage<TOrder> iPage, TOrder iTOrder) {
        iPage.setRecords( iTOrderMapper.getTOrderSqlPageList(iPage, iTOrder));
        return iPage;
        }

    @Override
    public int updateRemarks(TOrder iTOrder) {
        return iTOrderMapper.updateRemarks(iTOrder);
    }
}
