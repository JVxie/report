package com.jvxie.report.mappers;

import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.models.Teacher;

import java.util.List;

public interface TeacherMapper {
    int deleteById(Integer id);

    int insertSelective(Teacher record);

    Teacher selectById(Integer id);

    Teacher selectByNumber(String number);

    int updateByIdSelective(Teacher record);

    List<Teacher> selectList(TeacherListForm form);

    Teacher selectByUserId(Integer userId);

    List<Teacher> selectReport(ReportListForm form);
}