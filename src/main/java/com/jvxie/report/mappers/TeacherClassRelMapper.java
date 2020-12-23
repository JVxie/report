package com.jvxie.report.mappers;

import com.jvxie.report.models.TeacherClassRel;

import java.util.List;

public interface TeacherClassRelMapper {
    int deleteById(Integer id);

    int insertSelective(TeacherClassRel record);

    TeacherClassRel selectById(Integer id);

    List<TeacherClassRel> selectAllByTeacherId(Integer teacherId);

    List<TeacherClassRel> selectAllByClassId(Integer classId);

    int deleteByTeacherId(Integer teacherId);

    int deleteByClassId(Integer classId);
}