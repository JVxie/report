package com.jvxie.report.controllers.teacher;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.AccessException;
import com.jvxie.report.exception.UserNeedLoginException;
import com.jvxie.report.forms.user.UserForm;
import com.jvxie.report.forms.user.UserListForm;
import com.jvxie.report.services.impls.StudentServiceImpl;
import com.jvxie.report.services.impls.TeacherServiceImpl;
import com.jvxie.report.services.impls.UserServiceImpl;
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

@RestController("TeacherUserController")
@RequestMapping("/teacher")
public class UserController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    StudentServiceImpl studentService;

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

    @GetMapping("/user")
    private ResponseVo list(HttpServletRequest request, @ParameterModel UserListForm form) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        form.setClassIdSet(classIdSet);
        return userService.list(form, form.getPageNum(), form.getPageSize());
    }

    @PutMapping("/user/{id}")
    private ResponseVo update(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody UserForm form) {
        // TODO 判断该用户是否为该老师的学生
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        ResponseVo responseVo = studentService.getStudentByUserId(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo student = (StudentVo) responseVo.getData();
        if (!classIdSet.contains(student.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return userService.update(form, id);
    }

    @DeleteMapping("/user/{id}")
    private ResponseVo delete(HttpServletRequest request, @PathVariable Integer id) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        ResponseVo responseVo = studentService.getStudentByUserId(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo student = (StudentVo) responseVo.getData();
        if (!classIdSet.contains(student.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return userService.delete(id);
    }

    @PostMapping("/user/{id}/reset_password")
    private ResponseVo resetPassword(HttpServletRequest request, @PathVariable Integer id) {
        Set<Integer> classIdSet = getClassIdSetByToken(request);
        ResponseVo responseVo = studentService.getStudentByUserId(id);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return responseVo;
        }
        StudentVo student = (StudentVo) responseVo.getData();
        if (!classIdSet.contains(student.getClassId())) {
            return ResponseVo.error(ResponseEnum.NOT_CRUD_STUDENT);
        }
        return userService.resetPassword(id);
    }
}
