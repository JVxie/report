package com.jvxie.report.forms.user;

import com.jvxie.report.forms.BaseListForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserListForm extends BaseListForm {
    private String username;

    private String nickname;

    private Byte userRole;

    private String userEmail;

    private String userPhone;

    private Set<Integer> classIdSet;
}