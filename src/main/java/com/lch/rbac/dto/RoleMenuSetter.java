package com.lch.rbac.dto;

public class RoleMenuSetter {
    private int roleId;
    private int menuId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "RoleMenuSetter{" +
                "roleId=" + roleId +
                ", menuId=" + menuId +
                '}';
    }
}
