package com.jvxie.report.controllers.teacher;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.AccessException;
import com.jvxie.report.exception.UserNeedLoginException;
import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.forms.student.StudentForm;
import com.jvxie.report.forms.student.StudentListForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.services.impls.*;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ClassVo;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.StudentVo;
import com.jvxie.report.vos.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController("TeacherStudentController")
@RequestMapping("/teacher")
public class StudentController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ReportServiceImpl reportService;

    private Set<Integer> getClassIdSetByToken(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = teacherService.getTeacherByUserId(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            throw new UserNeedLoginException();
        }
        TeacherVo teacherVo = (TeacherVo) responseVo.getData();
        responseVo = teacherService.classes(teacherVo.getId());
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            throw new AccessException();
        }
        List<ClassVo> classVoList = (List<ClassVo>) responseVo.getData();
        Set<Integer> classIdSet = new HashSet<>();
        for (ClassVo classVo:
                classVoList) {
            classIdSet.add(classVo.getId());
        }
        return classIdSet;
    }

    @GetMapping("/student")
    private ResponseVo list(HttpServletRequest request, @ParameterModel StudentListForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        form.setClassIdSet(classIdSet);
        return studentService.list(form, form.getPageNum(), form.getPageSize());
    }

    @DeleteMapping("/student/{id}")
    private ResponseVo list(HttpServletRequest request, @PathVariable Integer id) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        ResponseVo responseVo = studentService.show(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo studentVo = (StudentVo) responseVo.getData();
        if (!classIdSet.contains(studentVo.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return studentService.delete(id);
    }

    @PostMapping("/student")
    private ResponseVo store(HttpServletRequest request, @RequestBody @Valid StudentForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        if (!classIdSet.contains(form.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return studentService.create(form);
    }

    @PutMapping("/student/{id}")
    private ResponseVo update(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody StudentForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        if (!classIdSet.contains(form.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return studentService.update(form, id);
    }

    @PostMapping("/student/{id}/register")
    private ResponseVo register(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody UserRegisterForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        ResponseVo responseVo = studentService.show(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo studentVo = (StudentVo) responseVo.getData();
        if (!classIdSet.contains(studentVo.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return userService.registerStudent(form, id);
    }


    @GetMapping("/student/report")
    private ResponseVo studentReportList(HttpServletRequest request, @ParameterModel ReportListForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        form.setClassIdSet(classIdSet);
        return reportService.getStudentReportList(form, form.getPageNum(), form.getPageSize());
    }

    @DeleteMapping("/student/report/{id}")
    private ResponseVo deleteStudentReport(@PathVariable Integer id) {
        return reportService.delete(id);
    }
}
