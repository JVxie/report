package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.classes.ClassListForm;
import com.jvxie.report.forms.teacher.TeacherForm;
import com.jvxie.report.forms.teacher.TeacherListForm;
import com.jvxie.report.forms.teacherClassRel.TeacherClassRelForm;
import com.jvxie.report.mappers.ClassMapper;
import com.jvxie.report.mappers.TeacherClassRelMapper;
import com.jvxie.report.mappers.TeacherMapper;
import com.jvxie.report.mappers.UserMapper;
import com.jvxie.report.models.Class;
import com.jvxie.report.models.Teacher;
import com.jvxie.report.models.TeacherClassRel;
import com.jvxie.report.models.User;
import com.jvxie.report.services.ITeacherService;
import com.jvxie.report.vos.ClassVo;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.TeacherVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeacherClassRelMapper teacherClassRelMapper;

    @Autowired
    ClassMapper classMapper;

    private static final Map<Byte, String> sexNameMap = new HashMap<Byte, String>() {{
        put((byte) 1, "男");
        put((byte) 2, "女");
    }};

    @Override
    @Transactional
    public ResponseVo create(TeacherForm form) {
        Teacher teacher = teacherMapper.selectByNumber(form.getNumber());
        if (teacher != null) {
            return ResponseVo.error(ResponseEnum.TEACHER_EXIST);
        }
        teacher = new Teacher();
        BeanUtils.copyProperties(form, teacher);
        int row = teacherMapper.insertSelective(teacher);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    @Transactional
    public ResponseVo delete(Integer id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        int row = teacherMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        teacherClassRelMapper.deleteByTeacherId(id);
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(TeacherForm form, Integer id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        Teacher teacherByNumber = teacherMapper.selectByNumber(form.getNumber());
        if (teacherByNumber != null && !teacherByNumber.getId().equals(id)) {
            return ResponseVo.error(ResponseEnum.TEACHER_EXIST);
        }
        BeanUtils.copyProperties(form, teacher);
        int row = teacherMapper.updateByIdSelective(teacher);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo show(Integer id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        TeacherVo teacherVo = changeTeacherToTeacherVo(teacher);
        return ResponseVo.success(teacherVo);
    }

    @Override
    public ResponseVo list(TeacherListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teacherList = teacherMapper.selectList(form);
        List<TeacherVo> teacherVoList = teacherList.stream()
                .map(this::changeTeacherToTeacherVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(teacherList);
        pageInfo.setList(teacherVoList);
        return ResponseVo.success(pageInfo);
    }

    private TeacherVo changeTeacherToTeacherVo(Teacher teacher) {
        TeacherVo teacherVo = new TeacherVo();
        BeanUtils.copyProperties(teacher, teacherVo);
        User user = userMapper.selectById(teacher.getUserId());
        if (user != null) {
            teacherVo.setUserName(user.getUsername());
            String userNameId = user.getUsername() + '(' +
                    user.getId() +
                    ')';
            teacherVo.setUserNameId(userNameId);
        } else {
            teacherVo.setUserId(null);
        }
        teacherVo.setSexName(sexNameMap.get(teacher.getSex()));
        return teacherVo;
    }

    @Override
    public ResponseVo classes(Integer id) {
        List<TeacherClassRel> teacherClassRelList = teacherClassRelMapper.selectAllByTeacherId(id);
        Set<Integer> classIdSet = new HashSet<>();
        classIdSet.add(0);
        for (TeacherClassRel teacherClassRel :
                teacherClassRelList) {
            classIdSet.add(teacherClassRel.getClassId());
        }
        ClassListForm classListForm = new ClassListForm();
        classListForm.setIdSet(classIdSet);
        List<Class> classList = classMapper.selectList(classListForm);
        List<ClassVo> classVoList = classList.stream().map(e -> {
            ClassVo classVo = new ClassVo();
            BeanUtils.copyProperties(e, classVo);
            return classVo;
        }).collect(Collectors.toList());
        return ResponseVo.success(classVoList);
    }

    @Override
    @Transactional
    public ResponseVo changeClasses(Set<Integer> classIdSet, Integer id) {
        if (classIdSet == null || classIdSet.size() <= 0) {
            teacherClassRelMapper.deleteByTeacherId(id);
            return ResponseVo.success();
        }
        ClassListForm form = new ClassListForm();
        form.setIdSet(classIdSet);
        List<Class> classList = classMapper.selectList(form);
        if (classList == null || classList.size() != classIdSet.size()) {
            throw new SystemErrorException();
        }
        teacherClassRelMapper.deleteByTeacherId(id);
        for (Integer classId :
                classIdSet) {
            TeacherClassRelForm teacherClassRelForm = new TeacherClassRelForm(id, classId);
            TeacherClassRel teacherClassRel = new TeacherClassRel();
            BeanUtils.copyProperties(teacherClassRelForm, teacherClassRel);
            teacherClassRelMapper.insertSelective(teacherClassRel);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo getTeacherByUserId(Integer userId) {
        Teacher teacher = teacherMapper.selectByUserId(userId);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        TeacherVo teacherVo = changeTeacherToTeacherVo(teacher);
        return ResponseVo.success(teacherVo);
    }
}
