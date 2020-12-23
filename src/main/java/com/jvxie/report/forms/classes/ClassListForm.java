package com.jvxie.report.forms.classes;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassListForm extends BaseListForm {
    private Integer idBefore;

    private Integer idAfter;

    private String abbrName;

    private Integer num;

    private Integer gradeId;

    private Integer majorId;

    private Set<Integer> idSet;
}
