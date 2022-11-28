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
}
