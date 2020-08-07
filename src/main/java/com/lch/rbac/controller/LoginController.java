package com.lch.rbac.controller;

import com.lch.rbac.entities.Menu;
import com.lch.rbac.entities.Role;
import com.lch.rbac.entities.User;
import com.lch.rbac.service.RoleService;
import com.lch.rbac.service.UserService;
import com.lch.rbac.util.MD5;
import com.lch.rbac.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private  RoleService roleService;


    @RequestMapping("/login")
    public Map<String,Object> login(@RequestParam String username, @RequestParam String password){
        Map<String,Object> model = new HashMap<String, Object>();
        if(username!=null && password!=null){
            User user = userService.selectUserByUsername(username);
            if(user!=null){
                if(user.getStatus()==0){
                    model.put("success",false);
                    model.put("errMsg","用户无效!");
                    return model;
                }
                if(user.getPassword().equals(MD5.getMd5(password))){
                    model.put("success",true);
                    user = userService.selectUserById(user.getId());
                    String token = TokenUtil.sign(user);
                    List<Role> roleList = new ArrayList<>();
                    for(Role r : user.getRoles()){
                        Role rr = roleService.selectRoleById(r.getId());
                        if(rr.getStatus()==1){
                            roleList.add(rr);
                        }
                    }
                    List<Integer> menuIdList = new ArrayList<>();
                    List<String> menuAddressList = new ArrayList<>();
                    for(Role role:roleList){
                        for(Menu menu : role.getMenus()){
                            if(menu.getStatus()==1){
                                menuIdList.add(menu.getId());
                                String menuAddress = menu.getMenuAddress();
                                if(menuAddress==null || menuAddress.equals("")){
                                    menuAddress = "*";
                                }
                                menuAddressList.add(menuAddress);
                            }
                        }
                    }
                    List<Integer> distinctMenuIdList = menuIdList.stream().distinct().collect(Collectors.toList());
                    List<String> distinctMenuAddressList = menuAddressList.stream().distinct().collect(Collectors.toList());
                    Integer[] menuIdArr = new Integer[distinctMenuIdList.size()];
                    distinctMenuIdList.toArray(menuIdArr);
                    String[] menuAddressArr = new String[distinctMenuAddressList.size()];
                    distinctMenuAddressList.toArray(menuAddressArr);
                    model.put("token",token);
                    model.put("menuIdArr",menuIdArr);
                    model.put("menuAddressArr", menuAddressArr);
                }else{
                    model.put("success",false);
                    model.put("errMsg","密码错误！");
                }
            }else{
                model.put("success",false);
                model.put("errMsg","用户不存在！");
            }
        }else{
            model.put("success",false);
            model.put("errMsg","用户名和密码不能为空！");
        }
        return model;
    }

    /*@RequestMapping("/logout")
    public Map<String,Object> logout(HttpServletRequest request) {
        Map<String,Object> model = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        model.put("success",true);
        return model;
    }*/
}
