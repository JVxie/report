package com.jvxie.report.models;

import lombok.Data;

import java.util.Date;

/**
 * area_map
 * @author 
 */
@Data
public class AreaMap {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 深度
     */
    private Integer deep;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 拼音前缀
     */
    private String pinyinPrefix;

    /**
     * 完整拼音
     */
    private String pinyin;

    /**
     * 完整地区id
     */
    private String extId;

    /**
     * 完整地区名字
     */
    private String extName;

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