package com.jvxie.report.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * report
 * @author 
 */
@Data
public class Report {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户角色，0: 管理员，1: 教师，2: 学生
     */
    private Byte userRole;

    /**
     * 学生学号或教师工号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，1: 男，2: 女
     */
    private Byte sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 体温
     */
    private BigDecimal temperature;

    /**
     * 当前所在省
     */
    private Integer nowProvinceId;

    /**
     * 当前所在市
     */
    private Integer nowCityId;

    /**
     * 当前所在区
     */
    private Integer nowAreaId;

    /**
     * 当前详细地址
     */
    private String nowAddress;

    /**
     * 家庭所在省
     */
    private Integer homeProvinceId;

    /**
     * 家庭所在市
     */
    private Integer homeCityId;

    /**
     * 家庭所在区
     */
    private Integer homeAreaId;

    /**
     * 家庭详细地址
     */
    private String homeAddress;

    /**
     * 逗号分隔，0: 无症状，1: 有发烧，2: 有咳嗽，3:有呼吸困难
     */
    private String isFeverCough;

    /**
     * 是否确诊新型肺炎，0: 否，1: 是
     */
    private Byte isCovid;

    /**
     * 是否感染，0: 否，1: 是
     */
    private Byte isInfection;

    /**
     * 地区等级，0: 低风险，1: 中风险，2: 高风险
     */
    private Byte areaLevel;

    /**
     * 是否有健康码，0: 无，1: 有
     */
    private Byte isHealthCode;

    /**
     * 是否从国（境）外返回，0: 否，1: 是
     */
    private Byte isBackForeign;

    /**
     * 是否接触国（境）外人员，0: 否，1: 是
     */
    private Byte isTouchForeigner;

    /**
     * 其他说明
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 删除时间
     */
    private Date deletedAt;
}