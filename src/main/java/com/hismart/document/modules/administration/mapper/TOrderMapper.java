package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TOrderMapper extends BaseMapper<TOrder> {

  List<TOrder>  getTOrderSqlPageList(IPage<TOrder> iPage, TOrder iTOrder);

  int  updateRemarks(@Param("order") TOrder order);
}
