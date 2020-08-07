package com.lch.rbac.dao;

import com.lch.rbac.dto.RoleMenuSetter;
import com.lch.rbac.entities.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    List<Role> selectRoleList(@Param("roleCondition") Role RoleCondition,
                              @Param("rowIndex") int rowIndex,
                              @Param("pageSize") int pageSize);

    int selectCount(@Param("roleCondition") Role RoleCondition);

    List<Role> selectAllRoleList();

    Role selectRoleById(int id);

    int insertRole(Role role);

    int modifyRole(Role role);

    int batchInsertMenu(List<RoleMenuSetter> roleMenuSetterList);

    int deleteRoleMenuByRoleId(@Param("roleId") int roleId);

    int deleteUserRoleByRoleId(@Param("roleId") int roleId);

    int deleteRoleByRoleId(int roleId);
}
