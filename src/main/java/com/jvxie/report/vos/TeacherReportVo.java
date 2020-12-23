package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherReportVo {
    private Integer id;

    private String number;

    private String name;

    private Byte sex;

    private String sexName;

    private Integer userId;

    private String userName;

    private String userNameId;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

    private ReportVo reportToday;

    private ReportVo reportRecent;
}
