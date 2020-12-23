package com.jvxie.report.forms.classes;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClassForm {
    private String abbrName;

    @NotNull(message = "班级号不能为空")
    private Integer num;

    @NotNull(message = "年级不能为空")
    private Integer gradeId;

    @NotNull(message = "专业不能为空")
    private Integer majorId;
}
