package com.jvxie.report.services;

import com.jvxie.report.forms.grade.GradeForm;
import com.jvxie.report.forms.grade.GradeListForm;
import com.jvxie.report.vos.ResponseVo;

public interface IGradeService {
    /**
     * 新增年级
     * @param form
     * @return
     */
    ResponseVo create(GradeForm form);

    /**
     * 删除年级
     * @param id
     * @return
     */
    ResponseVo delete(Integer id);

    /**
     * 修改年级
     * @param form
     * @param id
     * @return
     */
    ResponseVo update(GradeForm form, Integer id);

    /**
     * 年级列表
     * @param form
     * @return
     */
    ResponseVo list(GradeListForm form, Integer pageNum, Integer pageSize);

//    ResponseVo show(Integer id);

    ResponseVo getAll();
}
