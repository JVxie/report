package com.jvxie.report.mappers;

import com.jvxie.report.forms.user.UserListForm;
import com.jvxie.report.models.User;

import java.util.List;

public interface UserMapper {
    User selectById(Integer id);

    int insertSelective(User record);

    int updateByIdSelective(User record);

    User selectByUsername(String username);

    int deleteById(Integer id);

    List<User> selectList(UserListForm form);
}