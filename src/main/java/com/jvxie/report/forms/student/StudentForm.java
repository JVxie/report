package com.jvxie.report.forms.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class StudentForm {
    @NotBlank(message = "学生学号不能为空")
    private String number;

    @NotBlank(message = "学生姓名不能为空")
    private String name;

    @NotNull(message = "学生性别不能为空")
    private Byte sex;

    @NotNull(message = "必须选择学生的班级")
    private Integer classId;

    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)", message = "请填写正确的手机号码")
    private String phone;

    @Pattern(regexp = "(^$)|(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", message = "请输入正确的身份证号码")
    private String identityNumber;
}
