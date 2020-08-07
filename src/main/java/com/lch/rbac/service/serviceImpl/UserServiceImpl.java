package com.lch.rbac.service.serviceImpl;

import com.lch.rbac.dao.UserDao;
import com.lch.rbac.dto.UserRoleSetter;
import com.lch.rbac.entities.User;
import com.lch.rbac.exceptions.UserException;
import com.lch.rbac.service.UserService;
import com.lch.rbac.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User selectUserById(int id) {
        User user = userDao.selectUserById(id);
        user.setPassword("");
        return user;
    }

    @Override
    public User selectUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    public List<User> selectUserList(User userCondition, Date startTime, Date endTime, int rowIndex, int pageSize) {
        return userDao.selectUserList(userCondition,startTime,endTime,rowIndex,pageSize);
    }

    @Override
    public int selectCount(User userCondition, Date startTime, Date endTime) {
        return userDao.selectCount(userCondition,startTime,endTime);
    }

    @Override
    public int addUser(User user) {
        String password = user.getPassword();
        String encryptedPassword = MD5.getMd5(password);
        user.setPassword(encryptedPassword);
        return userDao.insertUser(user);
    }

    @Override
    public int modifyUser(User user) {
        String password = user.getPassword();
        String encryptedPassword = MD5.getMd5(password);
        user.setPassword(encryptedPassword);
        return userDao.modifyUser(user);
    }

    @Override
    public User changeState(int userId) throws UserException {
        User user = userDao.selectUserById(userId);
        if(user!=null){
            int effNum = 0;
            if(user.getStatus()==0){
                user.setStatus(1);
                user.setLastEditTime(new Date());
                effNum = userDao.modifyUser(user);
            }else if(user.getStatus()==1){
                user.setStatus(0);
                user.setLastEditTime(new Date());
                effNum = userDao.modifyUser(user);
            }
            if(effNum>0) {
                User newUser = userDao.selectUserById(userId);
                if(newUser!=null){
                    return newUser;
                }else{
                    throw new UserException("返回用户失败！");
                }
            }else{
                throw new UserException("更改状态失败！");
            }
        }else{
            throw new UserException("查找用户失败！");
        }
    }

    @Override
    public void setRoles(int userId, Integer[] rolesIdArray) throws UserException {
        User user = userDao.selectUserById(userId);
        if(user!=null){
            if(rolesIdArray.length>0){
                List<UserRoleSetter> userRoleSetterList= new ArrayList<UserRoleSetter>();
                for(int i : rolesIdArray){
                    UserRoleSetter s = new UserRoleSetter();
                    s.setUserId(userId);
                    s.setRoleId(i);
                    userRoleSetterList.add(s);
                }
                if(user.getRoles().size()>0){
                    userDao.deleteUserRoleByUserId(userId);
                }
                userDao.batchInsertRole(userRoleSetterList);
            }else{
                if(user.getRoles().size()>0){
                    userDao.deleteUserRoleByUserId(userId);
                }
            }
        }
    }

    @Override
    public void batchDeleteUser(Integer[] userIdArray) throws UserException {
        try{
            for(Integer i : userIdArray){
                userDao.deleteUserRoleByUserId(i);
                userDao.deleteUserByUserId(i);
            }
        }catch (Exception e){
            throw new UserException("删除用户失败！"+e.getMessage());
        }
    }
}
