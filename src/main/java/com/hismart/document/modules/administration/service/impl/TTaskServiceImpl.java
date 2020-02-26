package com.hismart.document.modules.administration.service.impl;

import com.hismart.document.modules.administration.entity.TTask;
import com.hismart.document.modules.administration.mapper.TTaskMapper;
import com.hismart.document.modules.administration.service.ITTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Echo_Sxl
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TTaskServiceImpl extends ServiceImpl<TTaskMapper, TTask> implements ITTaskService {

    @Autowired
    private TTaskMapper iTTaskMapper;

    @Override
    public IPage<TTask> getTTaskPageList(IPage<TTask> iPage, TTask iTTask) {
        try {
            LambdaQueryWrapper<TTask> queryWrapper = new LambdaQueryWrapper<>();
            return this.page(iPage, queryWrapper);
        } catch (Exception e) {
            log.error("getTTaskPageList", e);
            return null;
        }
    }

    @Override
    public IPage<TTask> getTTaskSqlPageList(IPage<TTask> iPage, TTask iTTask) {
        iPage.setRecords(iTTaskMapper.getTTaskSqlPageList(iPage, iTTask));
        return iPage;
    }

    @Override
    public int updateTeacherInfo(String taskId, String teacherId, String teacher) {
        return iTTaskMapper.updateTeacherInfo(taskId, teacherId, teacher);
    }
}
