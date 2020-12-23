package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * user
 * @author 
 */
@Data
public class User{
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 用户登录名
     */
    private String username;

    /**
     * 密码，加密储存
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户角色, 0:管理员 1:教师 2:学生
     */
    private Byte userRole;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户手机
     */
    private String userPhone;

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