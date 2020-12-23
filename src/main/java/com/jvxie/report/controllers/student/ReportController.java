package com.jvxie.report.controllers.student;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.exception.ApiException;
import com.jvxie.report.forms.report.ReportForm;
import com.jvxie.report.services.impls.ReportServiceImpl;
import com.jvxie.report.services.impls.StudentServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.StudentVo;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController("StudentReportController")
@RequestMapping("/student")
public class ReportController {
    @Autowired
    ReportServiceImpl reportService;

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    TokenUtil tokenUtil;

    private StudentVo getStudentByToken(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = studentService.getStudentByUserId(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            throw new ApiException(responseVo.getMsg());
        }
        return (StudentVo) responseVo.getData();
    }

    @GetMapping("/report")
    private ResponseVo info(HttpServletRequest request) {
        StudentVo studentVo = getStudentByToken(request);
        return reportService.getStudentReport(studentVo.getId());
    }

    @PostMapping("/report")
    private ResponseVo store(HttpServletRequest request, @Valid @RequestBody ReportForm reportForm) {
        StudentVo studentVo = getStudentByToken(request);
        reportForm.setUserId(studentVo.getUserId());
        reportForm.setUserRole(UserRoleEnum.STUDENT.getCode());
        reportForm.setNumber(studentVo.getNumber());
        reportForm.setName(studentVo.getName());
        reportForm.setSex(studentVo.getSex());
        reportForm.setIsFeverCough(StringUtils.join(reportForm.getIsFeverCoughSet(), ','));
        return reportService.create(reportForm);
    }
}
