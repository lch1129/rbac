package com.lch.rbac.service;

import com.lch.rbac.entities.Menu;
import com.lch.rbac.exceptions.MenuException;

import java.util.List;

public interface MenuService {

    List<Menu> selectMenuList(Menu menuCondition, int rowIndex, int pageSize);

    int selectCount(Menu menuCondition);

    Menu selectMenuById(int id);

    List<Menu> selectMenuListByMenuType(int menuType);

    Menu selectMenuByMenuAddress(String menuAddress);

    List<Menu> selectChildrenMenu(int parentId);

    int addMenu(Menu menu);

    int modifyMenu(Menu menu);

    void deleteMenuByMenuId(int menuId);

    void batchDeleteMenu(Integer[] menuIdArray)throws MenuException;
}
