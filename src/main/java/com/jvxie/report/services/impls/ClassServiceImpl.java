package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.classes.ClassForm;
import com.jvxie.report.forms.classes.ClassIndexForm;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.mappers.*;
import com.jvxie.report.models.*;
import com.jvxie.report.models.Class;
import com.jvxie.report.services.IClassService;
import com.jvxie.report.vos.ClassVo;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.TeacherVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements IClassService {
    @Autowired
    ClassMapper classMapper;

    @Autowired
    TeacherClassRelMapper teacherClassRelMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    MajorMapper majorMapper;

    @Override
    public ResponseVo list(ClassListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<Class> classList = classMapper.selectList(form);
        List<ClassVo> classVoList = classList.stream()
                .map(this::changeClassToClassVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(classList);
        pageInfo.setList(classVoList);
        return ResponseVo.success(pageInfo);
    }

    private ClassVo changeClassToClassVo(Class aClass) {
        ClassVo classVo = new ClassVo();
        BeanUtils.copyProperties(aClass, classVo);
        Grade grade = gradeMapper.selectById(aClass.getGradeId());
        if (grade != null) {
            classVo.setGradeName(grade.getName());
        }
        Major major = majorMapper.selectById(aClass.getMajorId());
        if (major != null) {
            classVo.setMajorName(major.getName());
        }
        classVo.setName(
                classVo.getGradeName() + classVo.getMajorName() + classVo.getNum() + "班"
        );
        return classVo;
    }

    @Override
    public ResponseVo show(Integer id) {
        Class aClass = classMapper.selectById(id);
        if (aClass == null) {
            return ResponseVo.error(ResponseEnum.CLASS_NOT_FOUND);
        }
        ClassVo classVo = changeClassToClassVo(aClass);
        return ResponseVo.success(classVo);
    }

    @Override
    public ResponseVo update(ClassForm form, Integer id) {
        if (checkGradeAndMajor(form.getGradeId(), form.getMajorId()).equals(false)) {
            return ResponseVo.error(ResponseEnum.GRADE_OR_MAJOR_NOT_FOUND);
        }
        Class aClass = new Class();
        BeanUtils.copyProperties(form, aClass);
        aClass.setId(id);
        int row = classMapper.updateByIdSelective(aClass);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo create(ClassForm form) {
        if (checkGradeAndMajor(form.getGradeId(), form.getMajorId()).equals(false)) {
            return ResponseVo.error(ResponseEnum.GRADE_OR_MAJOR_NOT_FOUND);
        }
        ClassIndexForm classIndexForm = new ClassIndexForm();
        BeanUtils.copyProperties(form, classIndexForm);
        Class aClass = classMapper.selectByIndex(classIndexForm);
        if (aClass != null) {
            return ResponseVo.error(ResponseEnum.CLASS_EXIST);
        }
        aClass = new Class();
        BeanUtils.copyProperties(form, aClass);
        int row = classMapper.insertSelective(aClass);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    private Boolean checkGradeAndMajor(Integer gradeId, Integer majorId) {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade == null) {
            return false;
        }
        Major major = majorMapper.selectById(majorId);
        if (major == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public ResponseVo delete(Integer id) {
        int row = classMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        teacherClassRelMapper.deleteByClassId(id);
        return ResponseVo.success();
    }

    @Override
    public ResponseVo teachers(Integer id) {
        List<TeacherClassRel> teacherClassRelList = teacherClassRelMapper.selectAllByClassId(id);
        Set<Integer> teacherIdSet = new HashSet<>();
        teacherIdSet.add(0);
        for (TeacherClassRel teacherClassRel:
             teacherClassRelList) {
            teacherIdSet.add(teacherClassRel.getTeacherId());
        }
        TeacherListForm form = new TeacherListForm();
        form.setIdSet(teacherIdSet);
        List<Teacher> teacherList = teacherMapper.selectList(form);
        List<TeacherVo> teacherVoList = teacherList.stream().map(e -> {
            TeacherVo teacherVo = new TeacherVo();
            BeanUtils.copyProperties(e, teacherVo);
            return teacherVo;
        }).collect(Collectors.toList());
        return ResponseVo.success(teacherVoList);
    }
}
