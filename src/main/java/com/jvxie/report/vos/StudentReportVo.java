package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;

@Data
public class StudentReportVo {
    private Integer id;

    private String number;

    private String name;

    private Byte sex;

    private String sexName;

    private Integer userId;

    private String userName;

    private String userNameId;

    private Integer classId;

    private String classAbbrName;

    private String phone;

    private String identityNumber;

    private Date createdAt;

    private Date updatedAt;

    private ReportVo reportToday;

    private ReportVo reportRecent;
}
