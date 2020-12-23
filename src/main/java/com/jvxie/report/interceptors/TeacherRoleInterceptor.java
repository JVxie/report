package com.jvxie.report.interceptors;

import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.exception.AccessException;
import com.jvxie.report.exception.UserNeedLoginException;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TeacherRoleInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检验请求头内的token合法性
        if (!tokenUtil.checkToken(request)) {
            throw new UserNeedLoginException();
        }
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer id = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        UserVo userVo = (UserVo) userService.show(id).getData();
        if (!(userVo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))) {
            throw new AccessException();
        }
        return true;
    }
}
