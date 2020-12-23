package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.forms.teacher.TeacherForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.forms.teacherClassRel.ClassIdSetForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.services.impls.ReportServiceImpl;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminTeacherController")
@RequestMapping("/admin")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping("/teacher")
    private ResponseVo list(@ParameterModel TeacherListForm form) {
        return teacherService.list(form, form.getPageNum(), form.getPageSize());
    }

    @GetMapping("/teacher/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return teacherService.show(id);
    }

    @PostMapping("/teacher")
    private ResponseVo create(@RequestBody TeacherForm form) {
        return teacherService.create(form);
    }

    @PutMapping("/teacher/{id}")
    private ResponseVo update(@RequestBody TeacherForm form, @PathVariable Integer id) {
        return teacherService.update(form, id);
    }

    @DeleteMapping("/teacher/{id}")
    private ResponseVo delete(@PathVariable Integer id) {
        return teacherService.delete(id);
    }

    @GetMapping("/teacher/{id}/classes")
    private ResponseVo classes(@PathVariable Integer id) {
        return teacherService.classes(id);
    }

    @PostMapping("/teacher/{id}/classes")
    private ResponseVo changeClasses(@RequestBody ClassIdSetForm form, @PathVariable Integer id) {
        return teacherService.changeClasses(form.getClassIdSet(), id);
    }

    @PostMapping("/teacher/{id}/register")
    private ResponseVo register(@Valid @RequestBody UserRegisterForm form, @PathVariable Integer id) {
        form.setUserRole(UserRoleEnum.TEACHER.getCode());
        return userService.registerTeacher(form, id);
    }

    @GetMapping("/teacher/report")
    private ResponseVo teacherReportList(@ParameterModel ReportListForm form) {
        return reportService.getTeacherReportList(form, form.getPageNum(), form.getPageSize());
    }

    @DeleteMapping("/teacher/report/{id}")
    private ResponseVo deleteTeacherReport(@PathVariable Integer id) {
        return reportService.delete(id);
    }
}
