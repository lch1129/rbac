package com.lch.rbac.service;

import com.lch.rbac.entities.Role;
import com.lch.rbac.exceptions.RoleException;

import java.util.List;

public interface RoleService {

    List<Role> selectAllRoleList();

    List<Role> selectRoleList(Role roleCondition,int rowIndex, int pageSize);

    int selectCount(Role roleCondition);

    Role selectRoleById(int id);

    int addRole(Role role);

    int modifyRole(Role role);

    void setMenu(int roleId,Integer[] menuIdArray)throws RoleException;

    void batchDeleteRole(Integer[] roleIdArray)throws RoleException;
}
