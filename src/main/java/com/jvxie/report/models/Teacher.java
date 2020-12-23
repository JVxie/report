package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * teacher
 * @author 
 */
@Data
public class Teacher {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 教师工号
     */
    private String number;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 教师性别 1:男 2:女
     */
    private Byte sex;

    /**
     * 教师对应用户id
     */
    private Integer userId;

    /**
     * 教师联系方式（手机）
     */
    private String phone;

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