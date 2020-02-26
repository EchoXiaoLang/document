package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TCampus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TCampusMapper extends BaseMapper<TCampus> {

  List<TCampus>  getTCampusSqlPageList(IPage<TCampus> iPage, TCampus iTCampus);
}
