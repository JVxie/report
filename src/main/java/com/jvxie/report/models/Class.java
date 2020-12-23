package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * class
 * @author 
 */
@Data
public class Class {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 班级名称缩写
     */
    private String abbrName;

    /**
     * 班级号（X班）
     */
    private Integer num;

    /**
     * 班级所属年级id
     */
    private Integer gradeId;

    /**
     * 班级所属专业id
     */
    private Integer majorId;

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