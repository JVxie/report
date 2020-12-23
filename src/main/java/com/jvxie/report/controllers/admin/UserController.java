package com.jvxie.report.controllers.admin;

import com.jvxie.report.ParameterModel;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.forms.user.UserForm;
import com.jvxie.report.forms.user.UserListForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.services.impls.UserServiceImpl;
import com.jvxie.report.utils.TokenUtil;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController("AdminUserController")
@RequestMapping("admin")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenUtil tokenUtil;

    @PostMapping("/user")
    private ResponseVo store(@Valid @RequestBody UserRegisterForm form) {
        return userService.register(form);
    }

    @GetMapping("/user")
    private ResponseVo list(@ParameterModel UserListForm form) {
        return userService.list(form, form.getPageNum(), form.getPageSize());
    }

    @PutMapping("/user/{id}")
    private ResponseVo update(@Valid @RequestBody UserForm form, @PathVariable Integer id) {
        return userService.update(form, id);
    }

    @DeleteMapping("/user/{id}")
    private ResponseVo delete(HttpServletRequest request, @PathVariable Integer id) {
        Map<String, String> tokenMap = tokenUtil.getTokenMap(request);
        Integer idFromRedis = Integer.valueOf(tokenMap.get("tokenFromRedis"));
        if (id.equals(idFromRedis)) {
            return ResponseVo.error(ResponseEnum.NOT_DELETE_SELF);
        }
        return userService.delete(id);
    }

    @PostMapping("/user/{id}/reset_password")
    private ResponseVo resetPassword(@PathVariable Integer id) {
        return userService.resetPassword(id);
    }
}
