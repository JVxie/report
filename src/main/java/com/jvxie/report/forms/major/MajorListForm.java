package com.jvxie.report.forms.major;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MajorListForm extends BaseListForm {
    private String name;

    private Integer collegeId;

    private Byte category;
}
