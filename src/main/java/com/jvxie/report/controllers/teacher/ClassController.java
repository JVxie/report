package com.jvxie.report.controllers.teacher;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.services.impls.ClassServiceImpl;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("TeacherClassContoller")
@RequestMapping("/teacher")
public class ClassController {
    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    ClassServiceImpl classService;

    @Autowired
    TokenUtil tokenUtil;

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

    @GetMapping("/myClasses")
    private ResponseVo myClasses(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = teacherService.getTeacherByUserId(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        TeacherVo teacher = (TeacherVo) responseVo.getData();
        return teacherService.classes(teacher.getId());
    }
}
