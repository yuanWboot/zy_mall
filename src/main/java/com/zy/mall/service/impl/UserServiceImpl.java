package com.zy.mall.service.impl;


import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.dao.UserMapper;
import com.zy.mall.model.pojo.User;
import com.zy.mall.service.UerService;
import com.zy.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UerService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 注册逻辑
     * @param userName 用户名
     * @param password 密码
     */
    @Override
    public void register(String userName, String password) throws ImoocMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //写入到数据库
        User user = new User();
        user.setUsername(userName);
        //密码加盐后进行MD5
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }

    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     * @throws ImoocMallException
     */
    @Override
    public User login(String userName, String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName,md5Password);
        if (user == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新签名
     * @param user
     * @throws ImoocMallException
     */
    @Override
    public void updateUserInformation(User user) throws ImoocMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 是否管理员
     * @param user
     * @return
     */
    @Override
    public boolean checkAdminRole(User user){
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
