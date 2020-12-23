package com.jvxie.report.mappers;

import com.jvxie.report.models.Report;

public interface ReportMapper {
    int deleteById(Integer id);

    int insertSelective(Report record);

    Report selectById(Integer id);

    Report selectByNumberToday(String number);

    Report selectByNumberRecent(String number);
}