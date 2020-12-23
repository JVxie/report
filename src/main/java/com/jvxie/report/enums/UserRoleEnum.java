package com.jvxie.report.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN((byte) 0, "管理员"),
    TEACHER((byte) 1, "教师"),
    STUDENT((byte) 2, "学生"),
    ;

    UserRoleEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    Byte code;
    String desc;
}
