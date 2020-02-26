package com.hismart.document.modules.administration.mapper;

import com.hismart.document.modules.administration.entity.TTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Echo_Sxl
 */
public interface TTaskMapper extends BaseMapper<TTask> {

    List<TTask> getTTaskSqlPageList(IPage<TTask> iPage, @Param("iTask") TTask iTask);

    int updateTeacherInfo(@Param("taskId") String taskId, @Param("teacherId") String teacherId, @Param("teacher") String teacher);
}
