package com.jvxie.report.forms.grade;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GradeListForm extends BaseListForm {
    private Integer idBefore;

    private Integer idAfter;

    private String name;
}
