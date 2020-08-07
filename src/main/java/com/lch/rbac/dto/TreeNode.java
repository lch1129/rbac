package com.lch.rbac.dto;

import java.util.List;

public class TreeNode {
    private Integer id;
    private String menuName;
    private String menuAddress;
    private Integer status;
    private List<TreeNode> children;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getMenuAddress() {
        return menuAddress;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", menuAddress='" + menuAddress + '\'' +
                ", status=" + status +
                ", children=" + children +
                '}';
    }

    public void setMenuAddress(String menuAddress) {
        this.menuAddress = menuAddress;
    }


}
