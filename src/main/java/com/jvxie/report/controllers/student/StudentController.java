package com.jvxie.report.controllers.student;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.forms.student.StudentForm;
import com.jvxie.report.services.impls.StudentServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController("StudentStudentController")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping("/info")
    private ResponseVo info(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return studentService.getStudentByUserId(userId);
    }

    @PutMapping("/info")
    private ResponseVo updateMyInfo(HttpServletRequest request, @Valid @RequestBody StudentForm form) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = studentService.getStudentByUserId(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo studentVo = (StudentVo) responseVo.getData();
        return studentService.update(form, studentVo.getId());
    }
}
