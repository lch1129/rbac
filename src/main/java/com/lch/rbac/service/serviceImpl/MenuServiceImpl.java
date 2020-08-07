package com.lch.rbac.service.serviceImpl;

import com.lch.rbac.dao.MenuDao;
import com.lch.rbac.entities.Menu;
import com.lch.rbac.exceptions.MenuException;
import com.lch.rbac.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> selectMenuList(Menu menuCondition, int rowIndex, int pageSize) {
        return menuDao.selectMenuList(menuCondition,rowIndex,pageSize);
    }

    @Override
    public int selectCount(Menu menuCondition) {
        return menuDao.selectCount(menuCondition);
    }

    @Override
    public Menu selectMenuById(int id) {
        return menuDao.selectMenuById(id);
    }

    @Override
    public List<Menu> selectMenuListByMenuType(int menuType) {
        return menuDao.selectMenuListByMenuType(menuType);
    }

    @Override
    public Menu selectMenuByMenuAddress(String menuAddress) {
        return menuDao.selectMenuByMenuAddress(menuAddress);
    }

    @Override
    public List<Menu> selectChildrenMenu(int parentId) {
        return menuDao.selectChildrenMenu(parentId);
    }

    @Override
    public int addMenu(Menu menu) {
        return menuDao.insertMenu(menu);
    }

    @Override
    public int modifyMenu(Menu menu) {
        return menuDao.updateMenu(menu);
    }

    @Override
    public void deleteMenuByMenuId(int menuId) throws MenuException{
        try{
            List<Menu> menuList = menuDao.selectChildrenMenu(menuId);
            if(menuList.size()<1){
                menuDao.deleteRoleMenuByMenuId(menuId);
                menuDao.deleteMenuByMenuId(menuId);
            }else {
                Menu menu = menuDao.selectMenuById(menuId);
                throw new MenuException("["+menu.getMenuName()+"]菜单下有子级菜单，禁止删除！");
            }
        }catch (Exception e){
            throw new MenuException("菜单删除操作失败！---"+e.getMessage());
        }
    }

    @Override
    public void batchDeleteMenu(Integer[] menuIdArray) throws MenuException {
        try{
            for(Integer i : menuIdArray){
                deleteMenuByMenuId(i);
            }
        }catch (Exception e){
            throw new MenuException("批量菜单删除操作失败！---"+e.getMessage());
        }
    }

}
