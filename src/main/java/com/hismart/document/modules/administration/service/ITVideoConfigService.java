package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TVideoConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface ITVideoConfigService extends IService<TVideoConfig> {

 IPage<TVideoConfig> getTVideoConfigPageList(IPage<TVideoConfig> iPage , TVideoConfig iTVideoConfig);

 IPage<TVideoConfig> getTVideoConfigSqlPageList(IPage<TVideoConfig> iPage , TVideoConfig iTVideoConfig);
}
