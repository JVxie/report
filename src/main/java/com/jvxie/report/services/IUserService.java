package com.jvxie.report.services;

import com.jvxie.report.forms.user.UserForm;
import com.jvxie.report.forms.user.UserListForm;
import com.jvxie.report.forms.user.UserPasswordForm;
import com.jvxie.report.forms.user.UserRegisterForm;
import com.jvxie.report.vos.ResponseVo;

public interface IUserService {
    /**
     * 登录
     * @param username String
     * @param password String
     */
    ResponseVo login(String username, String password);

    /**
     * 注册用户
     * @param form UserRegForm
     */
    ResponseVo register(UserRegisterForm form);

    ResponseVo registerStudent(UserRegisterForm form, Integer studentId);

    ResponseVo registerTeacher(UserRegisterForm form, Integer teacherId);

    /**
     * 获取单个用户信息
     * @param id Integer
     */
    ResponseVo show(Integer id);

    /**
     * 修改用户信息
     * @param user User
     */
    ResponseVo update(UserForm user, Integer id);

    /**
     * 删除用户
     * @param id Integer
     */
    ResponseVo delete(Integer id);

    /**
     * 获取用户信息列表
     * @param form
     * @param pageNum
     * @param pageSize
     */
    ResponseVo list(UserListForm form, Integer pageNum, Integer pageSize);

    /**
     * 重置用户密码为123456
     * @param id
     */
    ResponseVo resetPassword(Integer id);

    /**
     * 修改密码
     * @param form
     * @param id
     */
    ResponseVo changePassword(UserPasswordForm form, Integer id);

    /**
     * 获取各角色用户菜单
     */
    ResponseVo menu(Integer id);
}
