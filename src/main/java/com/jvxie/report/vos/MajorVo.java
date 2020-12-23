package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;

@Data
public class MajorVo {
    private Integer id;

    private String name;

    private Integer collegeId;

    private String collegeName;

    private Byte category;

    private String categoryName;

    private Date createdAt;

    private Date updatedAt;
}
