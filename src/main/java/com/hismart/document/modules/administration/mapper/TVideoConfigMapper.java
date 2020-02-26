package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TVideoConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TVideoConfigMapper extends BaseMapper<TVideoConfig> {

  List<TVideoConfig>  getTVideoConfigSqlPageList(IPage<TVideoConfig> iPage, TVideoConfig iTVideoConfig);
}
