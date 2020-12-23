package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * major
 * @author 
 */
@Data
public class Major {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 专业所属学院id
     */
    private Integer collegeId;

    /**
     * 专业类别 1:理科 2:工科 3:文科 4:商科 5:艺术设计
     */
    private Byte category;

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