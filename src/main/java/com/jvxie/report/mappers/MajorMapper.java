package com.jvxie.report.mappers;

import com.jvxie.report.forms.major.MajorListForm;
import com.jvxie.report.models.Major;

import java.util.List;

public interface MajorMapper {
    int deleteById(Integer id);

    int insertSelective(Major record);

    Major selectById(Integer id);

    int updateByIdSelective(Major record);

    List<Major> selectList(MajorListForm form);
}