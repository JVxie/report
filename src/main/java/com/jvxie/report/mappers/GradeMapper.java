package com.jvxie.report.mappers;

import com.jvxie.report.forms.grade.GradeListForm;
import com.jvxie.report.models.Grade;

import java.util.List;

public interface GradeMapper {
    int deleteById(Integer id);

    int insertSelective(Grade record);

    Grade selectById(Integer id);

    int updateByIdSelective(Grade record);

    List<Grade> selectList(GradeListForm form);
}