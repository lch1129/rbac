package com.lch.rbac.controller;

import com.lch.rbac.entities.User;
import com.lch.rbac.exceptions.RoleException;
import com.lch.rbac.exceptions.UserException;
import com.lch.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getuser")
    public Map<String,Object> getUser(String id){
        Map<String,Object> model = new HashMap<String,Object>();
        if(id!=null && !id.trim().equals("")){
            User user = userService.selectUserById(Integer.parseInt(id));
            if(user!=null){
                model.put("success",true);
                model.put("user",user);
            }else {
                model.put("success",false);
                model.put("errMsg","查无此人！");
            }
        }else{
            model.put("success",false);
            model.put("errMsg","查询用户条件缺失！");
        }
        return model;
    }

    @RequestMapping("/getusers")
    public Map<String,Object> getUsers(@RequestParam String username,
                                       @RequestParam String nickname,
                                       @RequestParam String status,
                                       @RequestParam String betweenTime,
                                       @RequestParam Integer rowIndex,
                                       @RequestParam Integer pageSize){
        Map<String,Object> model = new HashMap<String,Object>();
        List<User> userList;
        User userCondition = new User();
        if(username!=null && !username.trim().equals("")){
            userCondition.setUsername(username);
        }
        if(nickname!=null && !nickname.trim().equals("")){
            userCondition.setNickname(nickname);
        }
        if(status!=null && !status.trim().equals("")){
            userCondition.setStatus(Integer.parseInt(status));
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(betweenTime!=null && !betweenTime.trim().equals("")){
            try{
                String[] betweenTimeArr = betweenTime.split(",");
                Date startTime = sf.parse(betweenTimeArr[0]);
                Date endTime = sf.parse(betweenTimeArr[1]);
                userList = userService.selectUserList(userCondition,startTime,endTime,rowIndex,pageSize);
                if(userList!=null){
                    int count = userService.selectCount(userCondition,startTime,endTime);
                    model.put("count",count);
                    model.put("success",true);
                    model.put("userList",userList);
                }else{
                    model.put("success",false);
                    model.put("errMsg","查询失败！");
                }
            }catch (ParseException e){
                model.put("success",false);
                model.put("errMsg","时间区间错误，请确认格式后重新输入！");
                return model;
            }
        }else{
            userList = userService.selectUserList(userCondition,null,null,rowIndex,pageSize);
            if(userList!=null){
                int count = userService.selectCount(userCondition,null,null);
                model.put("count",count);
                model.put("success",true);
                model.put("userList",userList);
            }else{
                model.put("success",false);
                model.put("errMsg","查询失败！");
            }
        }
        return model;
    }

    @PostMapping("/adduser")
    public Map<String,Object> addUser(@RequestBody User user){
        Map<String,Object> model = new HashMap<String,Object>();
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        int effectNum = userService.addUser(user);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "添加用户失败！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @PostMapping("/modifyuser")
    public Map<String,Object> modifyUser(@RequestBody User user){
        Map<String,Object> model = new HashMap<String,Object>();
        user.setLastEditTime(new Date());
        int effectNum = userService.modifyUser(user);
        if(effectNum<1){
            model.put("success",false);
            model.put("errMsg", "修改用户失败！");
        }else{
            model.put("success",true);
        }
        return model;
    }

    @RequestMapping("/deleteusers")
    public Map<String,Object> deleteUsers(@RequestBody Integer[] deleteUserIdArray){
        Map<String,Object> model = new HashMap<String,Object>();
        if(deleteUserIdArray.length<1){
            model.put("success",false);
            model.put("errMsg", "删除项为空!");
        }else {
            try{
                userService.batchDeleteUser(deleteUserIdArray);
                model.put("success",true);
            }catch (RoleException e){
                model.put("success",false);
                model.put("errMsg", "删除失败!!");
            }
        }
        return model;
    }

    @RequestMapping("/modifystatus")
    public Map<String,Object> modifyStatus(@RequestParam String id){
        Map<String,Object> model = new HashMap<String,Object>();
        try{
            User user = userService.changeState(Integer.parseInt(id));
            model.put("success",true);
            model.put("user",user);
        }catch(UserException e){
            model.put("success",false);
            model.put("errMsg",e.getMessage());
        }
        return model;
    }

    @RequestMapping("/setrole")
    public Map<String,Object> setRole(@RequestBody Integer[] rolesIdArray, HttpServletRequest request){
        Map<String,Object> model = new HashMap<String,Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        try{
            userService.setRoles(userId,rolesIdArray);
            model.put("success",true);
        }catch(RoleException e){
            model.put("success",false);
            model.put("errMsg", e.getMessage());
        }
        return model;
    }

}
