package com.jvxie.report.forms.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterForm {
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{5,18}$", message = "用户名只能包含中文、英文、数字和下划线，长度在5~18之间")
    String username;

    @Pattern(regexp = "^[\\w]{6,18}$", message = "密码只能包含数字、字母和下划线，长度在6~18之间")
    String password;

    @Pattern(regexp = "^\\S{3,20}$", message = "昵称不能包含空白字符，且长度在3~20之间")
    private String nickname;

    @NotNull(message = "用户角色不能为空")
    private Byte userRole;

    @Pattern(regexp = "(^$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)", message = "请填写正确的邮箱")
    private String userEmail;

    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)")
    private String userPhone;
}
