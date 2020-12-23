package com.jvxie.report.exception.handler;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.AccessException;
import com.jvxie.report.exception.ApiException;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.exception.UserNeedLoginException;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class RuntimeExceptionHandler {
    /**
     * 捕获运行异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVo runtimeHandle(RuntimeException e) {
        e.printStackTrace();
        return ResponseVo.error(ResponseEnum.SYSTEM_ERROR, e.getMessage());
    }

    /**
     * 捕获未登录异常
     */
    @ExceptionHandler(UserNeedLoginException.class)
    @ResponseBody
    public ResponseVo userNeedLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

    /**
     * 捕获Api异常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseVo apiHandle(ApiException e) {
        return ResponseVo.error(ResponseEnum.API_ERROR, e.getMessage());
    }

    @ExceptionHandler(AccessException.class)
    @ResponseBody
    public ResponseVo accessHandle(AccessException e) {
        return ResponseVo.error(ResponseEnum.ACCESS_ERROR);
    }

    /**
     * 捕获表单异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVo.error(ResponseEnum.PARAMS_ERROR, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(SystemErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVo systemErrorHandle(SystemErrorException e) {
        return ResponseVo.error(ResponseEnum.SYSTEM_ERROR);
    }
}
