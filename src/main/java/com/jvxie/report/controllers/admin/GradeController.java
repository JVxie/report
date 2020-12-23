package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.grade.GradeForm;
import com.jvxie.report.forms.grade.GradeListForm;
import com.jvxie.report.services.impls.GradeServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminGradeController")
@RequestMapping("/admin")
public class GradeController {
    @Autowired
    GradeServiceImpl gradeService;

    @GetMapping("/grade")
    ResponseVo list(@ParameterModel GradeListForm form) {
        return gradeService.list(form, form.getPageNum(), form.getPageSize());
    }

//    @GetMapping("/garde/{id}")
//    ResponseVo info(@PathVariable Integer id) {
//        return ResponseVo.success();
//    }

    @PostMapping("/grade")
    ResponseVo store(@Valid @RequestBody GradeForm form) {
        return gradeService.create(form);
    }

    @PutMapping("/grade/{id}")
    ResponseVo update(@Valid @RequestBody GradeForm form, @PathVariable Integer id) {
        return gradeService.update(form, id);
    }

    @DeleteMapping("/grade/{id}")
    ResponseVo delete(@PathVariable Integer id) {
        return gradeService.delete(id);
    }
}
