package com.jvxie.report.forms;

import lombok.Data;

import java.util.Date;

@Data
public class BaseListForm {
    private Integer idBefore;

    private Integer idAfter;

    private Date createdAtBefore;

    private Date createdAtAfter;

    private Date updatedAtBefore;

    private Date updatedAtAfter;

    private Integer pageNum = 1;

    private Integer pageSize = 20;
}
