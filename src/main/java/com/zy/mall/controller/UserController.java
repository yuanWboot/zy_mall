package com.zy.mall.controller;

import com.zy.mall.common.ApiRestResponse;
import com.zy.mall.common.Constant;
import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.pojo.User;
import com.zy.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/***
 * 用户控制器
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User personPage() {
        return userService.getUser();
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
        userService.register(userName,password);
        return ApiRestResponse.success();
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws ImoocMallException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws ImoocMallException {

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
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    /**
     * 更新用户签名
     * @param session
     * @param signature
     * @return
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session,String signature) throws ImoocMallException {
        //从session中获取当前用户
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            //空则未登录
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //将当前用户id获取，设置新的对象，把签名一并传进去
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateUserInformation(user);
        return ApiRestResponse.success(user);
    }

    /**
     * 用户退出功能
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws ImoocMallException {

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
        User user = userService.login(userName, password);
        if (userService.checkAdminRole(user)) {
            //是管理员，执行后续操作
            //保存用户信息时，不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.IMOOC_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else {
            return  ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }


    }
}
