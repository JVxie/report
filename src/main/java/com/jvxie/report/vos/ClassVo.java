package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;

@Data
public class ClassVo {
    private Integer id;

    private String name;

    private String abbrName;

    private Integer num;

    private Integer gradeId;

    private String gradeName;

    private Integer majorId;

    private String majorName;

    private Date createdAt;

    private Date updatedAt;
}
