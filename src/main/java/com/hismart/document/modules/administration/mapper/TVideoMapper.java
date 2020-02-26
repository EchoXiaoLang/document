package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author Echo_Sxl
 */
public interface TVideoMapper extends BaseMapper<TVideo> {

  List<TVideo>  getTVideoSqlPageList(IPage<TVideo> iPage, @Param("video")TVideo iTVideo);
}
