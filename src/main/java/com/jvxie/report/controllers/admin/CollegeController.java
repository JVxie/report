package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.forms.college.CollegeForm;
import com.jvxie.report.forms.college.CollegeListForm;
import com.jvxie.report.services.impls.CollegeServiceImpl;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("AdminCollegeController")
@RequestMapping("/admin")
//@Slf4j
public class CollegeController {
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

    @Autowired
    CollegeServiceImpl collegeService;

    @GetMapping("/college")
    private ResponseVo list(@ParameterModel CollegeListForm form) {
        return collegeService.list(form, form.getPageNum(), form.getPageSize());
    }

    @PostMapping("/college")
    private ResponseVo store(@Valid @RequestBody CollegeForm form) {
        return collegeService.create(form);
    }

    @PutMapping("/college/{id}")
    ResponseVo update(@Valid @RequestBody CollegeForm form, @PathVariable Integer id) {
//        log.info("id = {}", id);
        return collegeService.update(form, id);
    }

    @DeleteMapping("/college/{id}")
    ResponseVo delete(@PathVariable Integer id) {
        return collegeService.delete(id);
    }
}
