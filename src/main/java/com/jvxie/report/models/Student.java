package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * student
 * @author
 */
@Data
public class Student {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 学号
     */
    private String number;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别 1:男 2:女
     */
    private Byte sex;

    /**
     * 学生对应用户id
     */
    private Integer userId;

    /**
     * 学生对应班级id
     */
    private Integer classId;

    /**
     * 学生联系方式（手机号）
     */
    private String phone;

    /**
     * 学生身份证号
     */
    private String identityNumber;

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