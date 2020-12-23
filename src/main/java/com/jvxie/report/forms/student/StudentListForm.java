package com.jvxie.report.forms.student;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListForm extends BaseListForm {
    private String number;

    private String name;

    private Byte sex;

    private Integer userId;

    private Integer classId;

    private Byte isRegistered;

    private Set<Integer> classIdSet;

    private String phone;

    private String identityNumber;
}
