package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TVideoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TVideoInfoMapper extends BaseMapper<TVideoInfo> {

  List<TVideoInfo>  getTVideoInfoSqlPageList(IPage<TVideoInfo> iPage, TVideoInfo iTVideoInfo);
}
