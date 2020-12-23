package com.jvxie.report.controllers.teacher;

import com.jvxie.report.services.impls.GradeServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TeacherGradeController")
@RequestMapping("/teacher")
public class GradeController {
    @Autowired
    GradeServiceImpl gradeService;

    @GetMapping("/grade")
    private ResponseVo all() {
        return gradeService.getAll();
    }
}
