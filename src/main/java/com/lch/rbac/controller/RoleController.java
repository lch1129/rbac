package com.lch.rbac.controller;


import com.lch.rbac.entities.Role;
import com.lch.rbac.exceptions.RoleException;
import com.lch.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getroles")
    public Map<String,Object> getRoles(@RequestParam String roleName,
                                       @RequestParam String status,
                                       @RequestParam Integer rowIndex,
                                       @RequestParam Integer pageSize){
        Map<String,Object> model = new HashMap<String,Object>();
        List<Role> roleList;
        Role roleCondition = new Role();
        if(roleName!=null && !roleName.trim().equals("")){
            roleCondition.setRoleName(roleName);
        }
        if(status!=null && !status.trim().equals("")){
            roleCondition.setStatus(Integer.parseInt(status));
        }
        roleList = roleService.selectRoleList(roleCondition,rowIndex,pageSize);
        if(roleList!=null){
            int count = roleService.selectCount(roleCondition);
            model.put("count",count);
            model.put("success",true);
            model.put("roleList",roleList);
        }else{
            model.put("success",false);
            model.put("errMsg","查询失败！");
        }
        return model;
    }

    @RequestMapping("/getrole")
    public Map<String,Object> getRole(String id){
        Map<String,Object> model = new HashMap<String,Object>();
        if(id!=null && !id.trim().equals("")){
            Role role = roleService.selectRoleById(Integer.parseInt(id));
            if(role!=null){
                model.put("success",true);
                model.put("role",role);
            }else {
                model.put("success",false);
                model.put("errMsg","查无此人！");
            }
        }else{
            model.put("success",false);
            model.put("errMsg","用户回显失败！");
        }
        return model;
    }

    @RequestMapping("/getallroles")
    public Map<String,Object> getAllRoles(){
        Map<String,Object> model = new HashMap<String, Object>();
        List<Role> roleList = roleService.selectAllRoleList();
        model.put("roleList",roleList);
        return model;
    }

    @PostMapping("/addrole")
    public Map<String,Object> addRole(@RequestBody Role role){
        Map<String,Object> model = new HashMap<String,Object>();
        int effectNum = roleService.addRole(role);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "添加角色失败，请重试！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @PostMapping("/modifyrole")
    public Map<String,Object> modifyRole(@RequestBody Role role){
        Map<String,Object> model = new HashMap<String,Object>();
        int effectNum = roleService.modifyRole(role);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "修改角色失败，请重试！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @RequestMapping("/deleteroles")
    public Map<String,Object> deleteRoles(@RequestBody Integer[] deleteRoleIdArray){
        Map<String,Object> model = new HashMap<String,Object>();
        if(deleteRoleIdArray.length<1){
            model.put("success",false);
            model.put("errMsg", "删除项为空!!");
        }else {
            try{
                roleService.batchDeleteRole(deleteRoleIdArray);
                model.put("success",true);
            }catch (RoleException e){
                model.put("success",false);
                model.put("errMsg", e.getMessage());
            }
        }
        return model;
    }

    @RequestMapping("/setmenu")
    public Map<String,Object> setMenu(@RequestBody Integer[] menuIdArray, HttpServletRequest request){
        Map<String,Object> model = new HashMap<String,Object>();
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        try{
            roleService.setMenu(roleId,menuIdArray);
            model.put("success",true);
        }catch(RoleException e){
            model.put("success",false);
            model.put("errMsg", e.getMessage());
        }
        return model;
    }

}
