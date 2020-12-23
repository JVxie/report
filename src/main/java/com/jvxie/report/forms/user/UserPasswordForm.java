package com.jvxie.report.forms.user;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserPasswordForm {
    @Pattern(regexp = "^[\\w]{6,18}$", message = "密码只能包含数字、字母和下划线，长度在6~18之间")
    String password;

    @Pattern(regexp = "^[\\w]{6,18}$", message = "密码只能包含数字、字母和下划线，长度在6~18之间")
    String newPassword;

    @Pattern(regexp = "^[\\w]{6,18}$", message = "密码只能包含数字、字母和下划线，长度在6~18之间")
    String confirmPassword;
}
