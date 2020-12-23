package com.jvxie.report.forms.report;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ReportForm {
//    @NotNull(message = "用户id不能为空")
    private Integer userId;

//    @NotNull(message = "用户角色不能为空")
    private Byte userRole;

//    @NotBlank(message = "学号/工号不能为空")
    private String number;

//    @NotBlank(message = "姓名不能为空")
    private String name;

//    @NotNull(message = "性别不能为空")
    private Byte sex;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请填写正确的手机号码")
    private String phone;

    @NotNull(message = "体温不能为空")
    private BigDecimal temperature;

    @NotNull(message = "请选择省/直辖市")
    private Integer nowProvinceId;

    @NotNull(message = "请选择市")
    private Integer nowCityId;

    @NotNull(message = "请选择区/县")
    private Integer nowAreaId;

    @NotBlank(message = "地址不能为空")
    private String nowAddress;

    @NotNull(message = "请选择省/直辖市")
    private Integer homeProvinceId;

    @NotNull(message = "请选择市")
    private Integer homeCityId;

    @NotNull(message = "请选择区/县")
    private Integer homeAreaId;

    @NotBlank(message = "地址不能为空")
    private String homeAddress;

    @NotNull(message = "请选择发烧咳嗽状态")
    @Size(min = 1)
    private Set<String> isFeverCoughSet;

    @NotNull(message = "请选择是否确诊新型肺炎")
    private Byte isCovid;

    @NotNull(message = "请选择是否疑似感染")
    private Byte isInfection;

    @NotNull(message = "请选择所在地区疫情等级")
    private Byte areaLevel;

    @NotNull(message = "请选择是否有绿码")
    private Byte isHealthCode;

    @NotNull(message = "请选择是否从国（境）外返回")
    private Byte isBackForeign;

    @NotNull(message = "请选择是否与国（境）外返回人员有接触")
    private Byte isTouchForeigner;

    private String description;

    private String isFeverCough;
}
