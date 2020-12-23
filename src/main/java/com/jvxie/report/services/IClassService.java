package com.jvxie.report.services;

import com.jvxie.report.forms.classes.ClassForm;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.vos.ResponseVo;

public interface IClassService {
    ResponseVo list(ClassListForm form, Integer pageNum, Integer pageSize);

    ResponseVo show(Integer id);

    ResponseVo update(ClassForm form, Integer id);

    ResponseVo create(ClassForm form);

    ResponseVo delete(Integer id);

    ResponseVo teachers(Integer id);
}
