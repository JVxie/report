package com.jvxie.report.vos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ReportVo {
    private Integer id;

    private Integer userId;

    private Byte userRole;

    private String number;

    private String name;

    private Byte sex;

    private String phone;

    private BigDecimal temperature;

    private Integer nowProvinceId;

    private String nowProvinceName;

    private Integer nowCityId;

    private String nowCityName;

    private Integer nowAreaId;

    private String nowAreaName;

    private String nowAddress;

    private Integer homeProvinceId;

    private String homeProvinceName;

    private Integer homeCityId;

    private String homeCityName;

    private Integer homeAreaId;

    private String homeAreaName;

    private String homeAddress;

    private String isFeverCough;

    private String isFeverCoughName;

    private Set<String> isFeverCoughSet;

    private Byte isCovid;

    private String isCovidName;

    private Byte isInfection;

    private String isInfectionName;

    private Byte areaLevel;

    private String areaLevelName;

    private Byte isHealthCode;

    private String isHealthCodeName;

    private Byte isBackForeign;

    private String isBackForeignName;

    private Byte isTouchForeigner;

    private String isTouchForeignerName;

    private Boolean isAbnormal;

    private String description;
}
