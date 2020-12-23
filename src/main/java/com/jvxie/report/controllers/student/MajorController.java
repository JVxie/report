package com.jvxie.report.controllers.student;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.major.MajorListForm;
import com.jvxie.report.services.impls.MajorServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StudentMajorController")
@RequestMapping("/student")
public class MajorController {
    @Autowired
    MajorServiceImpl majorService;

    @GetMapping("/major")
    private ResponseVo list(@ParameterModel MajorListForm form) {
        return majorService.list(form, form.getPageNum(), form.getPageSize());
    }
}
