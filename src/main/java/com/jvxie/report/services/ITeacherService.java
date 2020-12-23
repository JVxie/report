package com.jvxie.report.services;

import com.jvxie.report.forms.teacher.TeacherForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.vos.ResponseVo;

import java.util.Set;

public interface ITeacherService {
    ResponseVo create(TeacherForm form);

    ResponseVo delete(Integer id);

    ResponseVo update(TeacherForm form, Integer id);

    ResponseVo show(Integer id);

    ResponseVo list(TeacherListForm form, Integer pageNum, Integer pageSize);

    ResponseVo classes(Integer id);

    ResponseVo changeClasses(Set<Integer> classIdSet, Integer id);

    ResponseVo getTeacherByUserId(Integer userId);
}
