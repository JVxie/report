package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * teacher_class_rel
 * @author 
 */
@Data
public class TeacherClassRel {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 班级id
     */
    private Integer classId;

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