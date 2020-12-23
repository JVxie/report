package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.major.MajorForm;
import com.jvxie.report.forms.major.MajorListForm;
import com.jvxie.report.mappers.CollegeMapper;
import com.jvxie.report.mappers.MajorMapper;
import com.jvxie.report.models.College;
import com.jvxie.report.models.Major;
import com.jvxie.report.services.IMajorService;
import com.jvxie.report.vos.MajorVo;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MajorServiceImpl implements IMajorService {
    @Autowired
    MajorMapper majorMapper;

    @Autowired
    CollegeMapper collegeMapper;

    private static final Map<Byte, String> categoryNameMap = new HashMap<Byte, String>(){{
//        put((byte) 1, "理科");
//        put((byte) 2, "工科");
//        put((byte) 3, "文科");
//        put((byte) 4, "商科");
//        put((byte) 5, "艺术设计");
        put((byte) 1, "文学");
        put((byte) 2, "法学");
        put((byte) 3, "管理学");
        put((byte) 4, "理学");
        put((byte) 5, "工学");
        put((byte) 6, "教育学");
        put((byte) 7, "历史学");
        put((byte) 8, "经济学");
        put((byte) 9, "农学");
        put((byte) 10, "艺术学");
    }};

    @Override
    public ResponseVo create(MajorForm form) {
        Major major = new Major();
        BeanUtils.copyProperties(form, major);
        int row = majorMapper.insertSelective(major);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        Major major = majorMapper.selectById(id);
        if (major == null) {
            return ResponseVo.error(ResponseEnum.MAJOR_NOT_FOUND);
        }
        int row = majorMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(MajorForm form, Integer id) {
        Major major = majorMapper.selectById(id);
        if (major == null) {
            return ResponseVo.error(ResponseEnum.MAJOR_NOT_FOUND);
        }
        major = new Major();
        BeanUtils.copyProperties(form, major);
        major.setId(id);
        int row = majorMapper.updateByIdSelective(major);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo show(Integer id) {
        Major major = majorMapper.selectById(id);
        if (major == null) {
            return ResponseVo.error(ResponseEnum.MAJOR_NOT_FOUND);
        }
        MajorVo majorVo = changeMajorToMajorVo(major);
        return ResponseVo.success(majorVo);
    }

    @Override
    public ResponseVo list(MajorListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<Major> majorList = majorMapper.selectList(form);
        List<MajorVo> majorVoList = majorList.stream()
                .map(this::changeMajorToMajorVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(majorList);
        pageInfo.setList(majorVoList);
        return ResponseVo.success(pageInfo);
    }

    private MajorVo changeMajorToMajorVo(Major major) {
        MajorVo majorVo = new MajorVo();
        BeanUtils.copyProperties(major, majorVo);
        College college = collegeMapper.selectById(major.getCollegeId());
        if (college != null) {
            majorVo.setCollegeName(college.getName());
        }
        majorVo.setCategoryName(categoryNameMap.get(major.getCategory()));
        return majorVo;
    }
}
