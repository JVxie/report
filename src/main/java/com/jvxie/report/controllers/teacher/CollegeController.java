package com.jvxie.report.controllers.teacher;

import com.jvxie.report.services.impls.CollegeServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TeacherCollegeController")
@RequestMapping("/teacher")
public class CollegeController {
    @Autowired
    CollegeServiceImpl collegeService;

    @GetMapping("/college")
    private ResponseVo all() {
        return collegeService.getAll();
    }
}
