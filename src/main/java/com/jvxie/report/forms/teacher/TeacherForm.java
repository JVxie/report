package com.jvxie.report.forms.teacher;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TeacherForm {
    @NotBlank(message = "教师工号不能为空")
    private String number;

    @NotBlank(message = "教师姓名不能为空")
    private String name;

    @NotNull(message = "教师性别不能为空")
    private Byte sex;

    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)", message = "请填写正确的手机号码")
    private String phone;
}
