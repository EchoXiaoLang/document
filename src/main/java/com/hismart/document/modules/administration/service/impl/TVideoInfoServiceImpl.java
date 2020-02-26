package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.common.utils.FileUtils;
import com.hismart.document.common.utils.NUUID;
import com.hismart.document.modules.administration.entity.TVideoInfo;
import com.hismart.document.modules.administration.mapper.TVideoInfoMapper;
import com.hismart.document.modules.administration.service.ITVideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TVideoInfoServiceImpl extends ServiceImpl<TVideoInfoMapper, TVideoInfo> implements ITVideoInfoService {

    private final TVideoInfoMapper  iTVideoInfoMapper;

    @Value("${hismart.config.upload.path}")
    private String getPath;

    @Value("${hismart.config.upload.publicPath}")
    private String publicPath;

    public TVideoInfoServiceImpl(TVideoInfoMapper iTVideoInfoMapper) {
        this.iTVideoInfoMapper = iTVideoInfoMapper;
    }

    @Override
    public IPage<TVideoInfo> getTVideoInfoPageList(IPage<TVideoInfo> iPage, TVideoInfo  iTVideoInfo) {
      try {
           LambdaQueryWrapper<TVideoInfo> queryWrapper = new LambdaQueryWrapper<>();
           return this.page(iPage, queryWrapper);
       } catch (Exception e) {
           log.error("getTVideoInfoPageList", e);
           return null;
       }
    }

    @Override
    public IPage<TVideoInfo> getTVideoInfoSqlPageList(IPage<TVideoInfo> iPage, TVideoInfo iTVideoInfo) {
        iPage.setRecords( iTVideoInfoMapper.getTVideoInfoSqlPageList(iPage, iTVideoInfo));
        return iPage;
        }

    @Override
    public TVideoInfo upload(MultipartFile file,String videoId) throws Exception {
        TVideoInfo tVideoInfo=new TVideoInfo();
        tVideoInfo.setId(videoId);
        String folderAndName = FileUtils.getFileUriGeneratedPart(file, NUUID.randomUUID());
        String path = getPath + folderAndName;
        File realFile = new File(path);
        if (!realFile.getParentFile().exists()) {
            if (!realFile.getParentFile().mkdirs()) {
                log.error("创建文件上传目录失败", new Date());
                throw new Exception("创建文件上传目录失败");
            }
        }
        tVideoInfo.setName(FilenameUtils.getBaseName(new String(file.getOriginalFilename().getBytes("UTF-8"), "UTF-8")));
        tVideoInfo.setPath(publicPath + folderAndName);
        tVideoInfo.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
        tVideoInfo.setSize(file.getSize());
        file.transferTo(realFile);
        iTVideoInfoMapper.insert(tVideoInfo);
        return tVideoInfo;
    }
}
