package com.jvxie.report.controllers.student;

import com.jvxie.report.services.impls.GradeServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StudentGradeController")
@RequestMapping("/student")
public class GradeController {
    @Autowired
    GradeServiceImpl gradeService;

    @GetMapping("/grade")
    private ResponseVo all() {
        return gradeService.getAll();
    }
}
