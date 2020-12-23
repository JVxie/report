package com.jvxie.report.mappers;

import com.jvxie.report.models.AreaMap;

import java.util.List;

public interface AreaMapMapper {
    int deleteById(Integer id);

    int insertSelective(AreaMap record);

    AreaMap selectById(Integer id);

    int updateByIdSelective(AreaMap record);

    List<AreaMap> selectByPid(Integer pid);
}