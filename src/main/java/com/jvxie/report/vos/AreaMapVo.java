package com.jvxie.report.vos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AreaMapVo {
    private Integer id;

    private Integer pid;

    private Integer deep;

    private String name;

    private String pinyinPrefix;

    private String pinyin;

    private String extId;

    private String extName;

    private Date createdAt;

    private Date updatedAt;

    List<AreaMapVo> list;
}
