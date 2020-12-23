package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.forms.student.StudentForm;
import com.jvxie.report.forms.student.StudentListForm;
import com.jvxie.report.forms.student.StudentRegistersForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.services.impls.ClassServiceImpl;
import com.jvxie.report.services.impls.ReportServiceImpl;
import com.jvxie.report.services.impls.StudentServiceImpl;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminStudentController")
@RequestMapping("/admin")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    ClassServiceImpl classService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping("/student")
    private ResponseVo list(@ParameterModel StudentListForm form) {
        return studentService.list(form, form.getPageNum(), form.getPageSize());
    }

    @PostMapping("/student")
    private ResponseVo store(@Valid @RequestBody StudentForm form) {
        ResponseVo responseVo = classService.show(form.getClassId());
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        return studentService.create(form);
    }

    @GetMapping("/student/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return studentService.show(id);
    }

    @PutMapping("/student/{id}")
    private ResponseVo update(@Valid @RequestBody StudentForm form, @PathVariable Integer id) {
        ResponseVo responseVo = classService.show(form.getClassId());
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        return studentService.update(form, id);
    }

    @DeleteMapping("/student/{id}")
    private ResponseVo delete(@PathVariable Integer id) {
        return studentService.delete(id);
    }

    @PostMapping("/student/{id}/register")
    private ResponseVo register(@Valid @RequestBody UserRegisterForm form, @PathVariable Integer id) {
        form.setUserRole(UserRoleEnum.STUDENT.getCode());
        return userService.registerStudent(form, id);
    }

    @PostMapping("/student/registers")
    private ResponseVo registers(@Valid @RequestBody StudentRegistersForm form) {
        return ResponseVo.success();
    }

    @GetMapping("/student/report")
    private ResponseVo studentReportList(@ParameterModel ReportListForm form) {
        return reportService.getStudentReportList(form, form.getPageNum(), form.getPageSize());
    }

    @DeleteMapping("/student/report/{id}")
    private ResponseVo deleteStudentReport(@PathVariable Integer id) {
        return reportService.delete(id);
    }
}
