package com.jvxie.report.mappers;

import com.jvxie.report.models.Class;
import com.jvxie.report.forms.classes.ClassIndexForm;
import com.jvxie.report.forms.classes.ClassListForm;

import java.util.List;

public interface ClassMapper {
    int deleteById(Integer id);

    int insertSelective(Class record);

    Class selectById(Integer id);

    int updateByIdSelective(Class record);

    List<Class> selectList(ClassListForm form);

    Class selectByIndex(ClassIndexForm form);
}