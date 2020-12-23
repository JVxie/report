package com.jvxie.report.forms.teacher;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherListForm extends BaseListForm {
    private String number;

    private String name;

    private Byte sex;

    private Integer userId;

    private Byte isRegistered;

    private String phone;

    private Set<Integer> idSet;
}
