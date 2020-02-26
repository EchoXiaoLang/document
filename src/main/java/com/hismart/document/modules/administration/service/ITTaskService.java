package com.hismart.document.modules.administration.service;

import com.hismart.document.modules.administration.entity.TTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Echo_Sxl
 */
public interface ITTaskService extends IService<TTask> {

 IPage<TTask> getTTaskPageList(IPage<TTask> iPage , TTask iTTask);

 IPage<TTask> getTTaskSqlPageList(IPage<TTask> iPage , TTask iTTask);

 int updateTeacherInfo(String taskId,String teacherId,String  teacher);
}
