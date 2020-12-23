package com.jvxie.report.forms.student;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class StudentRegistersForm {
    @NotNull(message = "至少选择一名学生")
    private Set<Integer> idSet;
}
