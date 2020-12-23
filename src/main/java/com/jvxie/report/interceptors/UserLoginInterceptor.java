package com.jvxie.report.interceptors;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.UserNeedLoginException;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录拦截器
 * 用于拦截未登录状态下的请求
 * 若未登录不能访问部分接口
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
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
        // 若redis中有该token则更新时间
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer userId = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        ResponseVo responseVo = userService.show(userId);
        if (!responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            throw new UserNeedLoginException();
        }
        tokenUtil.setToken(tokenMap.get("token"), tokenMap.get("tokenFromRedis"));
        return true;
    }
}
