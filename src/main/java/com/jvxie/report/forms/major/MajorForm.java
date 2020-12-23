package com.jvxie.report.forms.major;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MajorForm {
    @NotBlank(message = "专业名称不能为空")
    private String name;

    @NotNull(message = "必须选择学院")
    private Integer collegeId;

    @NotNull(message = "必须选择专业类别")
    private Byte category;
}
