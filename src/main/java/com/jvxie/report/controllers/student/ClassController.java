package com.jvxie.report.controllers.student;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.services.impls.ClassServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StudentClassController")
@RequestMapping("/student")
public class ClassController {
    @Autowired
    ClassServiceImpl classService;

    @GetMapping("/class")
    private ResponseVo list(@ParameterModel ClassListForm form) {
        return classService.list(form, form.getPageNum(), form.getPageSize());
    }

    @GetMapping("/class/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return classService.show(id);
    }

    @GetMapping("/class/{id}/teachers")
    private ResponseVo teachers(@PathVariable Integer id) {
        return classService.teachers(id);
    }
}
