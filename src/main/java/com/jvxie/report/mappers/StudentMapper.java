package com.jvxie.report.mappers;

import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.forms.student.StudentListForm;
import com.jvxie.report.models.Student;

import java.util.List;
import java.util.Set;

public interface StudentMapper {
    int deleteById(Integer id);

    int insertSelective(Student record);

    Student selectById(Integer id);

    int updateByIdSelective(Student record);

    List<Student> selectList(StudentListForm form);

    Student selectByNumber(String number);

    Student selectByUserId(Integer userId);

    List<Student> selectByIdSetNotRegister(Set<Integer> idSet);

    List<Student> selectReport(ReportListForm form);
}