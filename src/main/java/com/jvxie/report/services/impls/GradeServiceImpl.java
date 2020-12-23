package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.grade.GradeForm;
import com.jvxie.report.forms.grade.GradeListForm;
import com.jvxie.report.mappers.GradeMapper;
import com.jvxie.report.models.Grade;
import com.jvxie.report.services.IGradeService;
import com.jvxie.report.vos.GradeVo;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements IGradeService {
    @Autowired
    GradeMapper gradeMapper;

    @Override
    public ResponseVo create(GradeForm form) {
        Grade grade = new Grade();
        BeanUtils.copyProperties(form, grade);
        int row = gradeMapper.insertSelective(grade);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        Grade grade = gradeMapper.selectById(id);
        if (grade == null) {
            return ResponseVo.error(ResponseEnum.GRADE_NOT_FOUND);
        }
        int row = gradeMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(GradeForm form, Integer id) {
        Grade grade = gradeMapper.selectById(id);
        if (grade == null) {
            return ResponseVo.error(ResponseEnum.GRADE_NOT_FOUND);
        }
        grade = new Grade();
        BeanUtils.copyProperties(form, grade);
        grade.setId(id);
        int row = gradeMapper.insertSelective(grade);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo list(GradeListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        List<Grade> gradeList = gradeMapper.selectList(form);
        List<GradeVo> gradeVoList = gradeList.stream()
                .map(this::changeGradeToGradeVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(gradeList);
        pageInfo.setList(gradeVoList);
        return ResponseVo.success(pageInfo);
    }

    private GradeVo changeGradeToGradeVo(Grade grade) {
        GradeVo gradeVo = new GradeVo();
        BeanUtils.copyProperties(grade, gradeVo);
        return gradeVo;
    }

    @Override
    public ResponseVo getAll() {
        GradeListForm form = new GradeListForm();
        form.setPageNum(null);
        form.setPageSize(null);
        List<Grade> gradeList = gradeMapper.selectList(form);
        Collections.sort(gradeList, (o1, o2) -> {
            if (!o1.getName().equals(o2.getName())) {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getId().compareTo(o2.getId());
        });
        List<GradeVo> gradeVoList = gradeList.stream()
                .map(this::changeGradeToGradeVo)
                .collect(Collectors.toList());
        return ResponseVo.success(gradeVoList);
    }
}
