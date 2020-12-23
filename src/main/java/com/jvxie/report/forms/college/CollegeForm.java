package com.jvxie.report.forms.college;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CollegeForm {
    @NotBlank(message = "学院名称不能为空")
    private String name;
}
