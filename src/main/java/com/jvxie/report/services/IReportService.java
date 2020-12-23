package com.jvxie.report.services;

import com.jvxie.report.forms.report.ReportForm;
import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.vos.ResponseVo;

public interface IReportService {
    ResponseVo getStudentReportList(ReportListForm form, Integer pageNum, Integer pageSize);

    ResponseVo getStudentReport(Integer studentId);

    ResponseVo create(ReportForm form);

    ResponseVo getTeacherReportList(ReportListForm form, Integer pageNum, Integer pageSize);

    ResponseVo getTeacherReport(Integer teacherId);

    ResponseVo delete(Integer id);
}
