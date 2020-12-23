package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.college.CollegeForm;
import com.jvxie.report.forms.college.CollegeListForm;
import com.jvxie.report.mappers.CollegeMapper;
import com.jvxie.report.models.College;
import com.jvxie.report.services.ICollegeService;
import com.jvxie.report.vos.CollegeVo;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegeServiceImpl implements ICollegeService {
    @Autowired
    CollegeMapper collegeMapper;

    @Override
    public ResponseVo list(CollegeListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<College> collegeList = collegeMapper.selectList(form);
        List<CollegeVo> collegeVoList = collegeList.stream()
                .map(this::changeCollegeToCollegeVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(collegeList);
        pageInfo.setList(collegeVoList);
        return ResponseVo.success(pageInfo);
    }

    private CollegeVo changeCollegeToCollegeVo(College college) {
        CollegeVo collegeVo = new CollegeVo();
        BeanUtils.copyProperties(college, collegeVo);
        return collegeVo;
    }

    @Override
    public ResponseVo show(Integer id) {
        College college = collegeMapper.selectById(id);
        if (college == null) {
            return ResponseVo.error(ResponseEnum.COLLEGE_EXIST);
        }
        CollegeVo collegeVo = changeCollegeToCollegeVo(college);
        return ResponseVo.success(collegeVo);
    }

    @Override
    public ResponseVo update(CollegeForm form, Integer id) {
        College college = new College();
        BeanUtils.copyProperties(form, college);
        college.setId(id);
        int row = collegeMapper.updateByIdSelective(college);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        int row = collegeMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo create(CollegeForm form) {
        College college = new College();
        BeanUtils.copyProperties(form, college);
        int row = collegeMapper.insertSelective(college);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo getAll() {
        CollegeListForm form = new CollegeListForm();
        form.setPageNum(null);
        form.setPageSize(null);
        List<College> collegeList = collegeMapper.selectList(form);
        List<CollegeVo> collegeVoList = collegeList.stream()
                .map(this::changeCollegeToCollegeVo)
                .collect(Collectors.toList());
        return ResponseVo.success(collegeVoList);
    }
}
