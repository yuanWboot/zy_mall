package com.zy.mall.service.impl;

import com.zy.mall.model.dao.UserMapper;
import com.zy.mall.model.pojo.User;
import com.zy.mall.service.UerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void register(String userName, String password) {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null){

        }
    }
}
