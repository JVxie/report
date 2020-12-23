package com.jvxie.report.forms.college;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeListForm extends BaseListForm {
    private Integer idBefore;

    private Integer idAfter;

    private String name;
}
