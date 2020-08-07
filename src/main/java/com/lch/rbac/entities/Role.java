package com.lch.rbac.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


public class Role {
    private Integer id;
    private String roleName;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;
    private Integer status;

    //记住所有的权限
    private List<Menu> menus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", menus=" + menus +
                '}';
    }
}
