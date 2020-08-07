package com.lch.rbac.dto;

public class UserRoleSetter {
    private int userId;
    private int roleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleSetter{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
