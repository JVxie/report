package com.jvxie.report.controllers.student;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StudentTeacherController")
@RequestMapping("/student")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;

    @GetMapping("/teacher")
    private ResponseVo list(@ParameterModel TeacherListForm form) {
        return teacherService.list(form, form.getPageNum(), form.getPageSize());
    }

    @GetMapping("/teacher/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return teacherService.show(id);
    }

    @GetMapping("/teacher/{id}/classes")
    private ResponseVo classes(@PathVariable Integer id) {
        return teacherService.classes(id);
    }
}
