package com.jvxie.report.services;

import com.jvxie.report.forms.major.MajorForm;
import com.jvxie.report.forms.major.MajorListForm;
import com.jvxie.report.vos.ResponseVo;

public interface IMajorService {
    ResponseVo create(MajorForm form);

    ResponseVo delete(Integer id);

    ResponseVo update(MajorForm form, Integer id);

    ResponseVo show(Integer id);

    ResponseVo list(MajorListForm form, Integer pageNum, Integer pageSize);
}
