package com.jvxie.report.vos;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    private String title;

    private String icon;

    private String name;

    private String jump;

    private List<MenuVo> list;
}
