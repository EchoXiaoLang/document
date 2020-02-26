package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TVideoInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Echo_Sxl
 */
public interface ITVideoInfoService extends IService<TVideoInfo> {

 IPage<TVideoInfo> getTVideoInfoPageList(IPage<TVideoInfo> iPage , TVideoInfo iTVideoInfo);

 IPage<TVideoInfo> getTVideoInfoSqlPageList(IPage<TVideoInfo> iPage , TVideoInfo iTVideoInfo);

 TVideoInfo  upload(MultipartFile file,String videoId) throws Exception;
}
