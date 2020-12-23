package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * college
 * @author 
 */
@Data
public class College {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 学院名称
     */
    private String name;

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