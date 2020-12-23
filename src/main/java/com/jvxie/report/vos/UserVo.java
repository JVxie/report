package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Integer id;

    private String username;

    private String nickname;

    private Byte userRole;

    private String userRoleName;

    private String userEmail;

    private String userPhone;

    private Date createdAt;

    private Date updatedAt;
}
