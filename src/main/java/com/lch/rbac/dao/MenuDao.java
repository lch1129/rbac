package com.lch.rbac.dao;

import com.lch.rbac.entities.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuDao {

    List<Menu> selectMenuList(@Param("menuCondition") Menu menuCondition,
                              @Param("rowIndex") int rowIndex,
                              @Param("pageSize") int pageSize);

    int selectCount(@Param("menuCondition") Menu menuCondition);

    Menu selectMenuById(int id);

    Menu selectMenuByMenuAddress(String menuAddress);

    List<Menu> selectMenuListByMenuType(int menuType);

    List<Menu> selectChildrenMenu(int parentId);

    int insertMenu(Menu menu);

    int updateMenu(Menu menu);

    void deleteRoleMenuByMenuId(int menuId);

    void deleteMenuByMenuId(int menuId);
}
