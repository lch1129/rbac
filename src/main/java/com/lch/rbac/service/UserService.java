package com.lch.rbac.service;

import com.lch.rbac.entities.User;
import com.lch.rbac.exceptions.UserException;

import java.util.Date;
import java.util.List;

public interface UserService {
    User selectUserById(int id);

    User selectUserByUsername(String username);

    List<User> selectUserList(User UserCondition, Date startTime, Date endTime, int rowIndex, int pageSize);

    int selectCount(User userCondition, Date startTime, Date endTime);

    int addUser(User user);

    int modifyUser(User user);

    User changeState(int id)throws UserException;

    void setRoles(int id,Integer[] rolesIdArray)throws UserException;

    void batchDeleteUser(Integer[] userIdArray)throws UserException;
}
