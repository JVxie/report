package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.major.MajorForm;
import com.jvxie.report.forms.major.MajorListForm;
import com.jvxie.report.services.impls.MajorServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminMajorController")
@RequestMapping("/admin")
public class MajorController {
    @Autowired
    MajorServiceImpl majorService;

    @GetMapping("/major")
    private ResponseVo list(@ParameterModel MajorListForm form) {
        return majorService.list(form, form.getPageNum(), form.getPageSize());
    }

    @GetMapping("/major/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return majorService.show(id);
    }

    @PostMapping("/major")
    private ResponseVo store(@Valid @RequestBody MajorForm form) {
        return majorService.create(form);
    }

    @PutMapping("/major/{id}")
    private ResponseVo update(@Valid @RequestBody MajorForm form, @PathVariable Integer id) {
        return majorService.update(form, id);
    }

    @DeleteMapping("/major/{id}")
    private ResponseVo delete(@PathVariable Integer id) {
        return majorService.delete(id);
    }
}
