package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.classes.ClassForm;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.services.impls.ClassServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminClassController")
@RequestMapping("/admin")
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

    @PutMapping("/class/{id}")
    private ResponseVo update(@Valid @RequestBody ClassForm form, @PathVariable Integer id) {
        return classService.update(form, id);
    }

    @GetMapping("/class/{id}/teachers")
    private ResponseVo teachers(@PathVariable Integer id) {
        return classService.teachers(id);
    }

    @PostMapping("/class")
    private ResponseVo store(@Valid @RequestBody ClassForm form) {
        return classService.create(form);
    }

    @DeleteMapping("/class/{id}")
    private ResponseVo delete(@PathVariable Integer id) {
        return classService.delete(id);
    }
}
