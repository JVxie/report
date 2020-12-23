package com.jvxie.report.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SUCCESS(0, "成功"),
    USERNAME_OR_PASSWORD_ERROR(1, "用户名或密码错误"),
    NEED_LOGIN(2, "需要登录"),
    LOGIN_EXIST(3, "用户已登录，请退出后再重新登录"),
    OLD_PASSWORD_NOT_NULL(4, "原密码不能为空"),
    OLD_PASSWORD_ERROR(5, "原密码错误"),
    GRADE_NOT_FOUND(6, "年级不存在"),
    GRADE_EXIST(7, "年级已存在"),
    COLLEGE_NOT_FOUND(8, "学院不存在"),
    COLLEGE_EXIST(9, "学院已存在"),
    USER_EXIST(10, "用户已存在"),
    USER_NOT_FOUND(11, "用户不存在"),
    CLASS_EXIST(12, "班级已存在"),
    CLASS_NOT_FOUND(13, "班级不存在"),
    MAJOR_EXIST(14, "专业已存在"),
    MAJOR_NOT_FOUND(15, "专业不存在"),
    TEACHER_EXIST(16, "教师已存在"),
    TEACHER_NOT_FOUND(17, "教师不存在"),
    STUDENT_EXIST(18, "学生已存在"),
    STUDENT_NOT_FOUND(19, "学生不存在"),
    CLASS_CHOOSE_ERROR(20, "班级选择错误"),
    GRADE_OR_MAJOR_NOT_FOUND(21, "专业或班级不存在"),
    USERNAME_EXIST(22, "用户名已存在"),
    USER_PASSWORD_ERROR(23, "原密码错误"),
    USER_CONFIRM_PASSWORD_ERROR(24, "两次输入的密码不一致"),
    NOT_DELETE_SELF(25, "不可删除自己"),
    NOT_CRUD_STUDENT(26, "只能对自己班级的学生进行管理"),
    AREA_MAP_NOT_FOUND(27, "该地区不存在"),
    REPORT_TODAY_NOT_FOUND(28, "该用户今天未填报，无法删除"),
    REPORT_TODAY_EXISTS(29, "您今天已填报，无法重复填报"),
    PARAMS_ERROR(55, "参数错误"),
    SYSTEM_ERROR(-1, "系统错误"),
    API_ERROR(-2, "Api错误"),
    ACCESS_ERROR(-3, "权限不足"),
    ;

    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
