package com.jvxie.report.forms.grade;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GradeForm {
    @NotBlank(message = "年级名称不能为空")
    private String name;
}
