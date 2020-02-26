package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface ITVideoService extends IService<TVideo> {

 IPage<TVideo> getTVideoPageList(IPage<TVideo> iPage , TVideo iTVideo);

 IPage<TVideo> getTVideoSqlPageList(IPage<TVideo> iPage , TVideo iTVideo);
}
