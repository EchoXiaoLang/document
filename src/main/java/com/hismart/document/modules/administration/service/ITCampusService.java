package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TCampus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface ITCampusService extends IService<TCampus> {

 IPage<TCampus> getTCampusPageList(IPage<TCampus> iPage , TCampus iTCampus);

 IPage<TCampus> getTCampusSqlPageList(IPage<TCampus> iPage , TCampus iTCampus);
}
