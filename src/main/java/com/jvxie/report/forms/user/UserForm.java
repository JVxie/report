package com.jvxie.report.forms.user;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserForm {
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{5,18}$", message = "用户名只能包含中文、英文、数字和下划线，长度在5~18之间")
    private String username;

    @Pattern(regexp = "^\\S{3,20}$", message = "昵称不能包含空白字符，且长度在3~20之间")
    private String nickname;

    @Pattern(regexp = "(^$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)", message = "请填写正确的邮箱")
    private String userEmail;

    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)", message = "请填写正确的手机号码")
    private String userPhone;
}
