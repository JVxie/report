package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.MenuEnum;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.enums.UserRoleEnum;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.user.UserForm;
import com.jvxie.report.forms.user.UserListForm;
import com.jvxie.report.forms.user.UserPasswordForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.mappers.StudentMapper;
import com.jvxie.report.mappers.TeacherMapper;
import com.jvxie.report.mappers.UserMapper;
import com.jvxie.report.models.Student;
import com.jvxie.report.models.Teacher;
import com.jvxie.report.models.User;
import com.jvxie.report.services.IUserService;
import com.jvxie.report.vos.MenuVo;
import com.jvxie.report.vos.ResponseVo;
import com.jvxie.report.vos.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public static final Byte USER_ROLE_ADMIN = (byte) 0;
    public static final Byte USER_ROLE_TEACHER = (byte) 1;
    public static final Byte USER_ROLE_STUDENT = (byte) 2;

    public static final Map<Byte, String> userRoleNameMap = new HashMap<Byte, String>(){{
        put(USER_ROLE_ADMIN, "管理员");
        put(USER_ROLE_TEACHER, "教师");
        put(USER_ROLE_STUDENT, "学生");
    }};

    private static final String RESET_PASSWORD = "123456";

    @Override
    public ResponseVo login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        // 若用户不存在 或 用户的密码错误
        if (user == null
                || !user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        UserVo userVo = changeUserToUserVo(user);
        return ResponseVo.success(userVo);
    }

    @Override
    public ResponseVo register(UserRegisterForm form) {
        User user = userMapper.selectByUsername(form.getUsername());
        if (user != null) {
            return ResponseVo.error(ResponseEnum.USER_EXIST);
        }
        user = new User();
        BeanUtils.copyProperties(form, user);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        int row = userMapper.insertSelective(user);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    @Transactional
    public ResponseVo registerStudent(UserRegisterForm form, Integer studentId) {
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.SYSTEM_ERROR);
        }
        ResponseVo register = register(form);
        if (!register.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return register;
        }
        User user = userMapper.selectByUsername(form.getUsername());
        student = new Student();
        student.setUserId(user.getId());
        student.setId(studentId);
        int row = studentMapper.updateByIdSelective(student);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo registerTeacher(UserRegisterForm form, Integer teacherId) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        ResponseVo register = register(form);
        if (!register.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            return register;
        }
        User user = userMapper.selectByUsername(form.getUsername());
        teacher = new Teacher();
        teacher.setUserId(user.getId());
        teacher.setId(teacherId);
        int row = teacherMapper.updateByIdSelective(teacher);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo show(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USER_NOT_FOUND);
        }
        UserVo userVo = changeUserToUserVo(user);
        return ResponseVo.success(userVo);
    }

    @Override
    public ResponseVo update(UserForm form, Integer id) {
        User user = userMapper.selectByUsername(form.getUsername());
        if (user != null && !user.getId().equals(id)) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        user = new User();
        BeanUtils.copyProperties(form, user);
        user.setId(id);
        int row = userMapper.updateByIdSelective(user);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USER_NOT_FOUND);
        }
        int row = userMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo list(UserListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectList(form);
        List<UserVo> userVoList = userList.stream()
                .map(this::changeUserToUserVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(userList);
        pageInfo.setList(userVoList);
        return ResponseVo.success(pageInfo);
    }

    private UserVo changeUserToUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setUserRoleName(userRoleNameMap.get(user.getUserRole()));
        return userVo;
    }

    @Override
    public ResponseVo resetPassword(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USER_NOT_FOUND);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(RESET_PASSWORD.getBytes(StandardCharsets.UTF_8)));
        int row = userMapper.updateByIdSelective(user);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo changePassword(UserPasswordForm form, Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USER_NOT_FOUND);
        }
        form.setPassword(DigestUtils.md5DigestAsHex(form.getPassword().getBytes(StandardCharsets.UTF_8)));
        form.setNewPassword(DigestUtils.md5DigestAsHex(form.getNewPassword().getBytes(StandardCharsets.UTF_8)));
        form.setConfirmPassword(DigestUtils.md5DigestAsHex(form.getConfirmPassword().getBytes(StandardCharsets.UTF_8)));
        if (!form.getPassword().equals(user.getPassword())) {
            return ResponseVo.error(ResponseEnum.USER_PASSWORD_ERROR);
        }
        if (!form.getConfirmPassword().equals(form.getNewPassword())) {
            return ResponseVo.error(ResponseEnum.USER_CONFIRM_PASSWORD_ERROR);
        }
        user = new User();
        user.setId(id);
        user.setPassword(form.getNewPassword());
        int row = userMapper.updateByIdSelective(user);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    private static MenuVo changeEnumToMenuVo(MenuEnum e) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(e, menuVo);
        List<MenuVo> menuVoList = new ArrayList<>();
        for (MenuEnum ep :
                e.getList()) {
            menuVoList.add(changeEnumToMenuVo(ep));
        }
        menuVo.setList(menuVoList);
        return menuVo;
    }

    private static final List<MenuVo> adminMenuList = Arrays.asList(
              changeEnumToMenuVo(MenuEnum.ADMIN_HOME)
            , changeEnumToMenuVo(MenuEnum.ADMIN_APP)
//            , changeEnumToMenuVo(MenuEnum.ADMIN_SENIOR)
            , changeEnumToMenuVo(MenuEnum.ADMIN_SET)
    );

    private static final List<MenuVo> teacherMenuList = Arrays.asList(
            changeEnumToMenuVo(MenuEnum.TEACHER_HOME)
            , changeEnumToMenuVo(MenuEnum.TEACHER_APP)
            , changeEnumToMenuVo(MenuEnum.TEACHER_SET)
    );

    private static final List<MenuVo> studentMenuList = Arrays.asList(
            changeEnumToMenuVo(MenuEnum.STUDENT_HOME)
            , changeEnumToMenuVo(MenuEnum.STUDENT_APP)
            , changeEnumToMenuVo(MenuEnum.STUDENT_SET)
    );

    @Override
    public ResponseVo menu(Integer id) {
        User user = userMapper.selectById(id);
        List<MenuVo> menu = null;
        if (user.getUserRole().equals(UserRoleEnum.ADMIN.getCode())) {
            menu = adminMenuList;
        } else if (user.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
            menu = teacherMenuList;
        } else if (user.getUserRole().equals(UserRoleEnum.STUDENT.getCode())) {
            menu = studentMenuList;
        }
//        log.info("1111");
        return ResponseVo.success(menu);
    }
}
