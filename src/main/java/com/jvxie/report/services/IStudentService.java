package com.jvxie.report.services;

import com.jvxie.report.forms.student.StudentForm;
import com.jvxie.report.forms.student.StudentListForm;
import com.jvxie.report.vos.ResponseVo;

import java.util.Set;

public interface IStudentService {
    ResponseVo create(StudentForm form);

    ResponseVo delete(Integer id);

    ResponseVo update(StudentForm form, Integer id);

    ResponseVo show(Integer id);

    ResponseVo list(StudentListForm form, Integer pageNum, Integer pageSize);

    ResponseVo getStudentByUserId(Integer userId);

    ResponseVo getStudentByIdSetNotRegistered(Set<Integer> idSet);
}
