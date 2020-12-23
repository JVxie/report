package com.jvxie.report.mappers;

import com.jvxie.report.forms.college.CollegeListForm;
import com.jvxie.report.models.College;

import java.util.List;

public interface CollegeMapper {
    int deleteById(Integer id);

    int insertSelective(College record);

    int updateByIdSelective(College record);

    List<College> selectList(CollegeListForm form);

    College selectById(Integer id);
}