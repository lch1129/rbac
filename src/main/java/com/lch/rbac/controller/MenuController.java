package com.lch.rbac.controller;

import com.lch.rbac.dto.TreeNode;
import com.lch.rbac.entities.Menu;
import com.lch.rbac.exceptions.MenuException;
import com.lch.rbac.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/getmenus")
    public Map<String,Object> getMenus(@RequestParam String menuName,
                                       @RequestParam String menuType,
                                       @RequestParam String status,
                                       @RequestParam Integer rowIndex,
                                       @RequestParam Integer pageSize){
        Map<String,Object> model = new HashMap<String,Object>();
        List<Menu> menuList;
        Menu menuCondition = new Menu();
        if(menuName!=null && !menuName.trim().equals("")){
            menuCondition.setMenuName(menuName);
        }
        if(menuType!=null && !menuType.trim().equals("")){
            menuCondition.setMenuType(Integer.parseInt(menuType));
        }
        if(status!=null && !status.trim().equals("")){
            menuCondition.setStatus(Integer.parseInt(status));
        }
        menuList = menuService.selectMenuList(menuCondition,rowIndex,pageSize);
        if(menuList!=null){
            int count = menuService.selectCount(menuCondition);
            model.put("success",true);
            model.put("menuList", menuList);
            model.put("count", count);
        }else {
            model.put("success",false);
            model.put("errMsg", "查询失败！");
        }
        return model;
    }

    @RequestMapping("/getmenu")
    public Map<String,Object> getMenu(String id){
        Map<String,Object> model = new HashMap<String,Object>();
        if(id!=null && !id.trim().equals("")){
            Menu menu = menuService.selectMenuById(Integer.parseInt(id));
            if(menu!=null){
                model.put("success",true);
                model.put("menu",menu);
            }else {
                model.put("success",false);
                model.put("errMsg","查无此菜单！");
            }
        }else{
            model.put("success",false);
            model.put("errMsg","菜单回显失败！");
        }
        return model;
    }

    @PostMapping("/addmenu")
    public Map<String,Object> addMenu(@RequestBody Menu menu){
        Map<String,Object> model = new HashMap<String,Object>();
        int effectNum = menuService.addMenu(menu);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "添加菜单失败！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @PostMapping("/modifymenu")
    public Map<String,Object> modifyMenu(@RequestBody Menu menu){
        Map<String,Object> model = new HashMap<String,Object>();
        int effectNum = menuService.modifyMenu(menu);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "修改菜单失败！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @RequestMapping("/deletemenu")
    public Map<String,Object> deleteMenu(Integer menuId){
        Map<String,Object> model = new HashMap<String,Object>();
        try{
            menuService.deleteMenuByMenuId(menuId);
            model.put("success",true);
        }catch (MenuException e){
            model.put("success",false);
            model.put("errMsg",e.getMessage());
        }
        return model;
    }

    @RequestMapping("/deletemenus")
    public Map<String,Object> deleteMenus(@RequestBody Integer[] deleteMenuIdArray){
        Map<String,Object> model = new HashMap<String,Object>();
        if(deleteMenuIdArray.length<1){
            model.put("success",false);
            model.put("errMsg", "删除项为空!!");
        }else {
            try{
                menuService.batchDeleteMenu(deleteMenuIdArray);
                model.put("success",true);
            }catch (MenuException e){
                model.put("success",false);
                model.put("errMsg", e.getMessage());
            }
        }
        return model;
    }

    @RequestMapping("/initsetmenu")
    public Map<String,Object> initSetMenu(){
        Map<String,Object> model = new HashMap<String, Object>();
        List<TreeNode> data = new ArrayList<TreeNode>();

        List<Menu> menuListType1 = menuService.selectMenuListByMenuType(1);
        if(menuListType1.size()>0){
            for(Menu m : menuListType1){
                TreeNode node1 = new TreeNode();
                node1.setId(m.getId());
                node1.setMenuName(m.getMenuName());
                node1.setMenuAddress(m.getMenuAddress());
                node1.setStatus(m.getStatus());
                List<Menu> menuListType2 = menuService.selectChildrenMenu(m.getId());
                if(menuListType2.size()>0){
                    List<TreeNode> children1 = new ArrayList<TreeNode>();
                    for(Menu m2 : menuListType2){
                        TreeNode node2 = new TreeNode();
                        node2.setId(m2.getId());
                        node2.setMenuName(m2.getMenuName());
                        node2.setMenuAddress(m2.getMenuAddress());
                        node2.setStatus(m2.getStatus());
                        List<Menu> menuListType3 = menuService.selectChildrenMenu(m2.getId());
                        if(menuListType3.size()>0){
                            List<TreeNode> children2 = new ArrayList<TreeNode>();
                            for(Menu m3 : menuListType3){
                                TreeNode node3 = new TreeNode();
                                node3.setId(m3.getId());
                                node3.setMenuName(m3.getMenuName());
                                node3.setMenuAddress(m3.getMenuAddress());
                                node3.setStatus(m3.getStatus());
                                children2.add(node3);
                            }
                            node2.setChildren(children2);
                        }
                        children1.add(node2);
                    }
                    node1.setChildren(children1);
                }
                data.add(node1);
            }
        }
        model.put("data",data);
        return model;
    }
}
