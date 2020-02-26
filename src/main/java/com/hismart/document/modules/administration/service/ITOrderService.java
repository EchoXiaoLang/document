package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author Echo_Sxl
 */
public interface ITOrderService extends IService<TOrder> {

 IPage<TOrder> getTOrderPageList(IPage<TOrder> iPage , TOrder iTOrder);

 IPage<TOrder> getTOrderSqlPageList(IPage<TOrder> iPage , TOrder iTOrder);

   int  updateRemarks(TOrder iTOrder);
}
