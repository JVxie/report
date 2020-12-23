package com.jvxie.report.controllers.teacher;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.exception.ApiException;
import com.jvxie.report.forms.report.ReportForm;
import com.jvxie.report.services.impls.ReportServiceImpl;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.TeacherVo;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController("TeacherReportController")
@RequestMapping("/teacher")
public class ReportController {
    @Autowired
    ReportServiceImpl reportService;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    TokenUtil tokenUtil;

    private TeacherVo getTeacherByToken(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = teacherService.getTeacherByUserId(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            throw new ApiException(responseVo.getMsg());
        }
        return (TeacherVo) responseVo.getData();
    }

    @GetMapping("/report")
    private ResponseVo info(HttpServletRequest request) {
        TeacherVo teacherVo = getTeacherByToken(request);
        return reportService.getTeacherReport(teacherVo.getId());
    }

    @PostMapping("/report")
    private ResponseVo store(HttpServletRequest request, @Valid @RequestBody ReportForm reportForm) {
        TeacherVo teacherVo = getTeacherByToken(request);
        reportForm.setUserId(teacherVo.getUserId());
        reportForm.setUserRole(UserRoleEnum.TEACHER.getCode());
        reportForm.setNumber(teacherVo.getNumber());
        reportForm.setName(teacherVo.getName());
        reportForm.setSex(teacherVo.getSex());
        reportForm.setIsFeverCough(StringUtils.join(reportForm.getIsFeverCoughSet(), ','));
        return reportService.create(reportForm);
    }
}
