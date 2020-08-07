package com.lch.rbac.service.serviceImpl;

import com.lch.rbac.dao.RoleDao;
import com.lch.rbac.dto.RoleMenuSetter;
import com.lch.rbac.entities.Role;
import com.lch.rbac.exceptions.RoleException;
import com.lch.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> selectAllRoleList() {
        return roleDao.selectAllRoleList();
    }

    @Override
    public List<Role> selectRoleList(Role roleCondition, int rowIndex, int pageSize) {
        return roleDao.selectRoleList(roleCondition,rowIndex,pageSize);
    }

    @Override
    public int selectCount(Role roleCondition) {
        return roleDao.selectCount(roleCondition);
    }

    @Override
    public Role selectRoleById(int id) {
        return roleDao.selectRoleById(id);
    }

    @Override
    public int addRole(Role role) {
        return roleDao.insertRole(role);
    }

    @Override
    public int modifyRole(Role role) {
        return roleDao.modifyRole(role);
    }

    @Override
    public void setMenu(int roleId, Integer[] menuIdArray) throws RoleException {
        Role role = roleDao.selectRoleById(roleId);
        if(role!=null){
            if(menuIdArray.length>0){
                List<RoleMenuSetter> roleMenuSetterList= new ArrayList<RoleMenuSetter>();
                for(int i : menuIdArray){
                    RoleMenuSetter s = new RoleMenuSetter();
                    s.setRoleId(roleId);
                    s.setMenuId(i);
                    roleMenuSetterList.add(s);
                }
                if(role.getMenus().size()>0){
                    int effNun1 = roleDao.deleteRoleMenuByRoleId(roleId);
                    if(effNun1<1){
                        throw new RoleException("清空角色原有权限失败！");
                    }
                }
                roleDao.batchInsertMenu(roleMenuSetterList);
            }else{
                if(role.getMenus().size()>0){
                    int effNun1 = roleDao.deleteRoleMenuByRoleId(roleId);
                    if(effNun1<1){
                        throw new RoleException("清空角色原有权限失败！");
                    }
                }
            }
        }
    }

    @Override
    public void batchDeleteRole(Integer[] roleIdArray) throws RoleException {
        try{
            for(Integer i : roleIdArray){
                roleDao.deleteRoleMenuByRoleId(i);
                roleDao.deleteUserRoleByRoleId(i);
                roleDao.deleteRoleByRoleId(i);
            }
        }catch (Exception e){
            throw new RoleException("删除角色失败！"+e.getMessage());
        }
    }

}
