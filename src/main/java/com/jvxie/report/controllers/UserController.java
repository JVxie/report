package com.jvxie.report.controllers;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.forms.user.UserForm;
import com.jvxie.report.forms.user.UserLoginForm;
import com.jvxie.report.forms.user.UserPasswordForm;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("BaseUserController")
@Slf4j
public class UserController {
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user/checkToken")
    private ResponseVo checkToken(HttpServletRequest request) {
        return ResponseVo.success();
    }

    @PostMapping("/user/login")
    private ResponseVo login(@Valid @RequestBody UserLoginForm form, HttpServletRequest request) {
        if (tokenUtil.checkToken(request)) {
            return ResponseVo.error(ResponseEnum.LOGIN_EXIST);
        }
        ResponseVo responseVo = userService.login(form.getUsername(), form.getPassword());
        // 若登录失败
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
//            log.error("vo = {}", responseVo);
            return responseVo;
        }
        Map<String, String> resultMap = new HashMap<>();
        String token = UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "");
        tokenUtil.setToken(token, ((UserVo) (responseVo.getData())).getId().toString());
        resultMap.put("token", token);
        return ResponseVo.success(resultMap);
    }

    @PostMapping("/user/logout")
    private ResponseVo logout(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        tokenUtil.removeToken(tokenMap.get("token"));
        return ResponseVo.success();
    }

//    @GetMapping("/user")
//    private ResponseVo list(
//            @ParameterModel UserListForm form,
//            HttpServletRequest request
//    ) {
//        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
//        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
//        UserVo userVo = (UserVo) userService.show(id).getData();
//        if (userVo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
//            // 若为老师，只能查询其班级内的学生信息
//            Set<Integer> classIdSet = new HashSet<>();
//            // TODO 判断为老师时只查询其班级学生
//            form.setClassIdSet(classIdSet);
//        } else if (userVo.getUserRole().equals(UserRoleEnum.STUDENT.getCode())) {
//            // 若为学生，只能查询自己的信息
//            return ResponseVo.success(userVo);
//        }
//        ResponseVo responseVo = userService.list(form, form.getPageNum(), form.getPageSize());
//        return responseVo;
//    }

    @GetMapping("/user/info")
    private ResponseVo info(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return userService.show(id);
    }

    @PutMapping("/user/info")
    private ResponseVo updateInfo(HttpServletRequest request, @Valid @RequestBody UserForm form) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return userService.update(form, id);
    }

    @GetMapping("/user/menu")
    private ResponseVo menu(HttpServletRequest request) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return userService.menu(id);
    }

    @PostMapping("/user/password")
    private ResponseVo changePassword(HttpServletRequest request, @Valid @RequestBody UserPasswordForm form) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        return userService.changePassword(form, id);
    }
//    @GetMapping("/user/{id}")
//    private ResponseVo show(@PathVariable Integer id, HttpServletRequest request) {
//        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
//        Integer idFromToken = Integer.valueOf(tokenMap.get("tokenFromRedis"));
//        UserVo userVo = (UserVo) userService.show(idFromToken).getData();
//        ResponseVo responseVo = userService.show(id);
//        if (userVo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
//            // 若为老师，只能查询其班级内的学生信息
//            Set<Integer> classIdSet = new HashSet<>();
//            // TODO 判断为老师时只查询其班级学生
//        } else if (userVo.getUserRole().equals(UserRoleEnum.STUDENT.getCode())) {
//            // 若为学生，只能查询自己的信息
//            if (!id.equals(idFromToken)) {
//                return ResponseVo.error(ResponseEnum.ACCESS_ERROR);
//            }
//        }
//        return responseVo;
//    }

//    @PutMapping("/user/{id}")
//    private ResponseVo update(@Valid @RequestBody UserForm form, @PathVariable Integer id, HttpServletRequest request) {
//        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
//        Integer idFromToken = Integer.valueOf(tokenMap.get("tokenFromRedis"));
//        UserVo userVo = (UserVo) userService.show(idFromToken).getData();
//        if (userVo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
//            // 若为教师，则只能修改其班级内的学生信息
//            Set<Integer> classIdSet = new HashSet<>();
//            // TODO 判断为老师时只修改其班级学生
//        } else if (userVo.getUserRole().equals(UserRoleEnum.STUDENT.getCode())) {
//            // 若为学生，只能修改自己的信息，且要验证密码
//            if (!id.equals(idFromToken)) {
//                return ResponseVo.error(ResponseEnum.ACCESS_ERROR);
//            } else if (StringUtils.isEmpty(form.getNewPassword())) {
//                return ResponseVo.error(ResponseEnum.OLD_PASSWORD_NOT_NULL);
//            }
//            ResponseVo login = userService.login(userVo.getUsername(), form.getPassword());
//            if (!login.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
//                return ResponseVo.error(ResponseEnum.OLD_PASSWORD_ERROR);
//            }
//            if (!StringUtils.isEmpty(form.getNewPassword())) {
//                form.setPassword(form.getNewPassword());
//            }
//        }
//        return userService.update(form, id);
//    }

//    @DeleteMapping("/user/{id}")
//    private ResponseVo delete(@Valid @PathVariable Integer id, HttpServletRequest request) {
//        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
//        Integer idFromToken = Integer.valueOf(tokenMap.get("tokenFromRedis"));
//        UserVo userVo = (UserVo) userService.show(idFromToken).getData();
//        if (userVo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
//            // 若为教师，则只能删除其班级内的学生用户
//            Set<Integer> classIdSet = new HashSet<>();
//            // TODO 判断为老师时只删除其班级学生
//        } else if (userVo.getUserRole().equals(UserRoleEnum.STUDENT.getCode())) {
//            // 若为学生，不可删除用户
//            return ResponseVo.error(ResponseEnum.ACCESS_ERROR);
//        }
//        return userService.delete(id);
//    }
}
