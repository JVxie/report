package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.student.StudentForm;
import com.jvxie.report.forms.student.StudentListForm;
import com.jvxie.report.mappers.ClassMapper;
import com.jvxie.report.mappers.StudentMapper;
import com.jvxie.report.mappers.UserMapper;
import com.jvxie.report.models.Class;
import com.jvxie.report.models.Student;
import com.jvxie.report.models.User;
import com.jvxie.report.services.IStudentService;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.StudentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassMapper classMapper;

    private static final Map<Byte, String> sexNameMap = new HashMap<Byte, String>(){{
        put((byte) 1, "男");
        put((byte) 2, "女");
    }};

    @Override
    public ResponseVo create(StudentForm form) {
        Student student = studentMapper.selectByNumber(form.getNumber());
        if (student != null) {
            return ResponseVo.error(ResponseEnum.STUDENT_EXIST);
        }
        student = new Student();
        BeanUtils.copyProperties(form, student);
        int row = studentMapper.insertSelective(student);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.STUDENT_NOT_FOUND);
        }
        int row = studentMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(StudentForm form, Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.STUDENT_NOT_FOUND);
        }
        student = new Student();
        BeanUtils.copyProperties(form, student);
        student.setId(id);
        int row = studentMapper.updateByIdSelective(student);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo show(Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.STUDENT_NOT_FOUND);
        }
        StudentVo studentVo = changeStudentToStudentVo(student);
        return ResponseVo.success(studentVo);
    }

    @Override
    public ResponseVo list(StudentListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<Student> studentList = studentMapper.selectList(form);
        List<StudentVo> studentVoList = studentList.stream()
                .map(this::changeStudentToStudentVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(studentList);
        pageInfo.setList(studentVoList);
        return ResponseVo.success(pageInfo);
    }

    private StudentVo changeStudentToStudentVo(Student student) {
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(student, studentVo);
        User user = userMapper.selectById(student.getUserId());
        if (user != null) {
            studentVo.setUserName(user.getUsername());
            String userNameId = user.getUsername() + '(' +
                    user.getId() +
                    ')';
            studentVo.setUserNameId(userNameId);
        } else {
            studentVo.setUserId(null);
        }
        Class aClass = classMapper.selectById(student.getClassId());
        if (aClass != null) {
            studentVo.setClassAbbrName(aClass.getAbbrName());
        }
        studentVo.setSexName(sexNameMap.get(student.getSex()));
        return studentVo;
    }

    @Override
    public ResponseVo getStudentByUserId(Integer userId) {
        Student student = studentMapper.selectByUserId(userId);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.STUDENT_NOT_FOUND);
        }
        StudentVo studentVo = changeStudentToStudentVo(student);
        return ResponseVo.success(studentVo);
    }

    @Override
    public ResponseVo getStudentByIdSetNotRegistered(Set<Integer> idSet) {
        return null;
    }
}
