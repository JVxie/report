package com.jvxie.report.services;

import com.jvxie.report.forms.college.CollegeForm;
import com.jvxie.report.forms.college.CollegeListForm;
import com.jvxie.report.vos.ResponseVo;

public interface ICollegeService {
    ResponseVo list(CollegeListForm form, Integer pageNum, Integer pageSize);

    ResponseVo show(Integer id);

    ResponseVo update(CollegeForm form, Integer id);

    ResponseVo delete(Integer id);

    ResponseVo create(CollegeForm form);

    ResponseVo getAll();
}
