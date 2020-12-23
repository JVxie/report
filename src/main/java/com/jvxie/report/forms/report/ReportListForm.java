package com.jvxie.report.forms.report;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReportListForm extends BaseListForm {
    private Byte userRole;

    private String number;

    private String name;

    private Byte sex;

    private Byte isReportToday;

    private Byte isAbnormal;

    private Integer nowProvinceId;

    private Integer nowCityId;

    private Integer nowAreaId;

    private Integer homeProvinceId;

    private Integer homeCityId;

    private Integer homeAreaId;

    private Integer classId;

    private Set<Integer> classIdSet;
}
