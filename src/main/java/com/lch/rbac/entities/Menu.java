package com.lch.rbac.entities;

public class Menu {
    private Integer id;
    private String menuName;
    private String description;
    private Integer menuType;//菜单类型（目录：1；菜单：2；功能：3）
    private String menuAddress;
    private Integer status;//状态（无效：0；有效：1）s

    private Menu parentMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getMenuAddress() {
        return menuAddress;
    }

    public void setMenuAddress(String menuAddress) {
        this.menuAddress = menuAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", description='" + description + '\'' +
                ", menuType=" + menuType +
                ", menuAddress='" + menuAddress + '\'' +
                ", status=" + status +
                ", parentMenu=" + parentMenu +
                '}';
    }
}
