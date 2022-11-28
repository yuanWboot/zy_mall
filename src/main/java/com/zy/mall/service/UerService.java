package com.zy.mall.service;

import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.model.pojo.User;

/**
 * UserService
 */
public interface UerService {
    User getUser();
    /**
     * 注册方法
     */
    void  register(String userName , String password) throws ImoocMallException;
}
