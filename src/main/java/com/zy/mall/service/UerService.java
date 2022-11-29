package com.zy.mall.service;

import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.model.pojo.User;

/**
 * UserService
 */
public interface UerService {
    User getUser();
    /**
     * 注册接口
     */
    void  register(String userName , String password) throws ImoocMallException;

    /**
     * 登录接口
     * @param userName
     * @param password
     * @return
     * @throws ImoocMallException
     */
    User login(String userName, String password) throws ImoocMallException;

    /**
     * 更新签名接口
     * @param user
     * @throws ImoocMallException
     */
    void updateUserInformation(User user) throws ImoocMallException;

    boolean checkAdminRole(User user);
}
