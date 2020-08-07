package com.lch.rbac.dao;

import com.lch.rbac.dto.UserRoleSetter;
import com.lch.rbac.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {
    User selectUserById(int id);

    User selectUserByUsername(String username);

    List<User> selectUserList(@Param("userCondition") User UserCondition,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime,
                              @Param("rowIndex") int rowIndex,
                              @Param("pageSize") int pageSize);

    int selectCount(@Param("userCondition") User UserCondition,
                    @Param("startTime") Date startTime,
                    @Param("endTime") Date endTime);

    int insertUser(User user);

    int modifyUser(User user);

    int batchInsertRole(List<UserRoleSetter> userRoleSetterList);

    void deleteUserRoleByUserId(int userId);

    void deleteUserByUserId(int userId);
}
