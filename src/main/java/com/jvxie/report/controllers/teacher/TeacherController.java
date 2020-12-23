package com.jvxie.report.controllers.teacher;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.forms.teacher.TeacherForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController("TeacherTeacherController")
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    TeacherServiceImpl teacherService;

    @GetMapping("/teacher")
    private ResponseVo list(@ParameterModel TeacherListForm form) {
        return teacherService.list(form, form.getPageNum(), form.getPageSize());
    }

    @GetMapping("/teacher/{id}")
    private ResponseVo info(@PathVariable Integer id) {
        return teacherService.show(id);
    }

    @GetMapping("/teacher/{id}/classes")
    private ResponseVo classes(@PathVariable Integer id) {
        return teacherService.classes(id);
    }

    @GetMapping("/info")
    private ResponseVo myInfo(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return teacherService.getTeacherByUserId(userId);
    }

    @PutMapping("/info")
    private ResponseVo updateMyInfo(HttpServletRequest request, @Valid @RequestBody TeacherForm form) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = teacherService.getTeacherByUserId(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        TeacherVo teacherVo = (TeacherVo) responseVo.getData();
        return teacherService.update(form, teacherVo.getId());
    }
}
