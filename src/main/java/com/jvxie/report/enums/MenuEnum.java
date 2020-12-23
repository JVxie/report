package com.jvxie.report.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 菜单常量
 */
@Getter
public enum MenuEnum {
    /**
     * 管理员菜单
     */
      ADMIN_HOME("主页", "admin-home", "layui-icon-home", "/admin/", Collections.emptyList())
    , ADMIN_GRADE_LIST("年级管理", "admin-grade-list", "", "/admin/grade/", Collections.emptyList())
    , ADMIN_COLLEGE_LIST("学院管理", "admin-college-list", "", "/admin/college/", Collections.emptyList())
    , ADMIN_MAJOR_LIST("专业管理", "admin-major-list", "", "/admin/major/", Collections.emptyList())
    , ADMIN_CLASS_LIST("班级管理", "admin-class-list", "", "/admin/class/", Collections.emptyList())
    , ADMIN_SCHOOL_MANAGE("学校基本信息管理", "admin-school-manage", "", "", Arrays.asList(
              ADMIN_COLLEGE_LIST
            , ADMIN_GRADE_LIST
            , ADMIN_MAJOR_LIST
            , ADMIN_CLASS_LIST

    ))
    , ADMIN_TEACHER_LIST("教师管理", "admin-teacher-list", "", "/admin/teacher/", Collections.emptyList())
    , ADMIN_STUDENT_LIST("学生管理", "admin-student-list", "", "/admin/student/", Collections.emptyList())
    , ADMIN_APP_SEARCH("教务信息管理", "admin-search", "", "", Arrays.asList(
              ADMIN_TEACHER_LIST
            , ADMIN_STUDENT_LIST
    ))
    , ADMIN_USER_LIST("用户管理", "admin-user-list", "", "/admin/user/", Collections.emptyList())
    , ADMIN_SET_CLASS_TEACHER("查看班级的分管教师", "admin-set-class-teacher", "", "/admin/class/teachers", Collections.emptyList())
    , ADMIN_SET_TEACHER_CLASS("设置教师管理的班级", "admin-set-teacher-class", "", "/admin/teacher/classes", Collections.emptyList())
    , ADMIN_TEACHER_REPORT_LIST("教师疫情填报管理", "admin-teacher-report-list", "", "admin/teacher/report/", Collections.emptyList())
    , ADMIN_STUDENT_REPORT_LIST("学生疫情填报管理", "admin-student-report-list", "", "admin/student/report/", Collections.emptyList())
    , ADMIN_APP_SET("其他", "admin-app-set", "", "", Arrays.asList(
              ADMIN_USER_LIST
            , ADMIN_SET_CLASS_TEACHER
            , ADMIN_SET_TEACHER_CLASS
            , ADMIN_TEACHER_REPORT_LIST
            , ADMIN_STUDENT_REPORT_LIST
    ))
    , ADMIN_APP("应用", "admin-app", "layui-icon-app", "admin-app", Arrays.asList(
              ADMIN_SCHOOL_MANAGE
            , ADMIN_APP_SEARCH
            , ADMIN_APP_SET
    ))

    , ADMIN_SENIOR_COUNT("数据统计", "admin-senior-count", "", "/admin/senior/count", Collections.emptyList())
    , ADMIN_SENIOR_HELPER("帮助中心", "admin-senior-helper", "", "/admin/senior/helper", Collections.emptyList())
    , ADMIN_SENIOR("高级", "admin-senior", "layui-icon-senior", "", Arrays.asList(
              ADMIN_SENIOR_COUNT
            , ADMIN_SENIOR_HELPER
    ))

    , ADMIN_SET_USER_INFO("个人资料", "admin-set-user-info", "", "/set/user/info", Collections.emptyList())
    , ADMIN_SET_USER_PASSWORD("密码修改", "admin-set-user-password", "", "/set/user/password", Collections.emptyList())
    , ADMIN_SET("设置", "admin-set", "layui-icon-set", "", Arrays.asList(
              ADMIN_SET_USER_INFO
            , ADMIN_SET_USER_PASSWORD
    ))

    /**
     * 教师菜单
     */
    , TEACHER_HOME("主页", "teacher-home", "layui-icon-home", "/teacher/", Collections.emptyList())

    , TEACHER_MAJOR_LIST("查询专业信息", "teacher-major-list", "", "/teacher/major/", Collections.emptyList())
    , TEACHER_CLASS_LIST("查询班级信息", "teacher-class-list", "", "/teacher/class/", Collections.emptyList())
    , TEACHER_TEACHER_LIST("查询教师信息", "teacher-teacher-list", "", "/teacher/teacher/", Collections.emptyList())
    , TEACHER_APP_SEARCH("信息查询", "teacher-app-search", "", "", Arrays.asList(
              TEACHER_MAJOR_LIST
            , TEACHER_CLASS_LIST
            , TEACHER_TEACHER_LIST
    ))
    , TEACHER_STUDENT_LIST("管理学生信息", "teacher-student-list", "", "/teacher/student/", Collections.emptyList())
    , TEACHER_USER_LIST("管理学生用户信息", "teacher-user-list", "", "/teacher/user/", Collections.emptyList())
    , TEACHER_INFO("修改个人教师信息", "teacher-info", "", "/teacher/info", Collections.emptyList())
    , TEACHER_APP_SET("信息管理", "teacher-app-set", "", "", Arrays.asList(
              TEACHER_STUDENT_LIST
            , TEACHER_USER_LIST
            , TEACHER_INFO
    ))
    , TEACHER_STUDENT_REPORT_LIST("学生填报信息管理", "teacher-student-report-list", "", "/teacher/student/report/",  Collections.emptyList())
    , TEACHER_REPORT("个人疫情填报", "teacher-report", "", "/teacher/report", Collections.emptyList())
    , TEACHER_REPORT_MANAGER("疫情填报管理", "teacher-report-manager", "", "teacher-report-manager", Arrays.asList(
              TEACHER_STUDENT_REPORT_LIST
            , TEACHER_REPORT
    ))
    , TEACHER_APP("应用", "teacher-app", "layui-icon-app", "teacher-app", Arrays.asList(
              TEACHER_APP_SEARCH
            , TEACHER_APP_SET
            , TEACHER_REPORT_MANAGER
    ))

    , TEACHER_SET_USER_INFO("个人资料", "teacher-set-user-info", "", "/set/user/info", Collections.emptyList())
    , TEACHER_SET_USER_PASSWORD("密码修改", "teacher-set-user-password", "", "/set/user/password", Collections.emptyList())
    , TEACHER_SET("设置", "teacher-set", "layui-icon-set", "", Arrays.asList(
              TEACHER_SET_USER_INFO
            , TEACHER_SET_USER_PASSWORD
    ))
    /**
     * 学生菜单
     */
    , STUDENT_HOME("主页", "student-home", "layui-icon-home", "/student/", Collections.emptyList())

    , STUDENT_MAJOR_LIST("查询专业信息", "student-major-list", "", "/student/major/", Collections.emptyList())
    , STUDENT_CLASS_LIST("查询班级信息", "student-class-list", "", "/student/class/", Collections.emptyList())
    , STUDENT_TEACHER_LIST("查询教师信息", "student-teacher-list", "", "/student/teacher/", Collections.emptyList())
    , STUDENT_APP_SEARCH("信息查询", "student-app-search", "", "", Arrays.asList(
              STUDENT_MAJOR_LIST
            , STUDENT_CLASS_LIST
            , STUDENT_TEACHER_LIST
    ))
    , STUDENT_INFO("修改个人学生信息", "student-info", "", "/student/info", Collections.emptyList())
    , STUDENT_APP_SET("信息管理", "student-app-set", "", "", Arrays.asList(
            STUDENT_INFO
    ))
    , STUDENT_APP_REPORT("疫情填报", "student-report", "", "/student/report", Collections.emptyList())
    , STUDENT_APP("应用", "student-app", "layui-icon-app", "student-app", Arrays.asList(
              STUDENT_APP_SEARCH
            , STUDENT_APP_SET
            , STUDENT_APP_REPORT
    ))

    , STUDENT_SET_USER_INFO("个人资料", "student-set-user-info", "", "/set/user/info", Collections.emptyList())
    , STUDENT_SET_USER_PASSWORD("密码修改", "student-set-user-password", "", "/set/user/password", Collections.emptyList())
    , STUDENT_SET("设置", "student-set", "layui-icon-set", "", Arrays.asList(
              STUDENT_SET_USER_INFO
            , STUDENT_SET_USER_PASSWORD
    ))
    ;

    String title;

    String icon;

    String name;

    String jump;

    List<MenuEnum> list;

    MenuEnum(String title, String name, String icon,  String jump, List<MenuEnum> list) {
        this.title = title;
        this.name = name;
        this.icon = icon;
        this.jump = jump;
        this.list = list;
    }
}
