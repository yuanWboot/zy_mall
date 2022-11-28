package com.zy.mall.controller;

import com.zy.mall.common.ApiRestResponse;
import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.pojo.User;
import com.zy.mall.service.UerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/***
 * 用户控制器
 */
@Controller
public class UserController {
    @Autowired
    UerService uerService;

    @GetMapping("/test")
    @ResponseBody
    public User personPage() {
        return uerService.getUser();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws ImoocMallException {

        //用户名非空校验
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //密码非空校验
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不小于8位
        if (password.length()<8){
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        uerService.register(userName,password);
        return ApiRestResponse.success();
    }
}
