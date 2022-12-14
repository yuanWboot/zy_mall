package com.zy.mall.controller;

import com.github.pagehelper.PageInfo;
import com.zy.mall.common.ApiRestResponse;
import com.zy.mall.common.Constant;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.pojo.User;
import com.zy.mall.model.request.AddCategoryReq;
import com.zy.mall.model.vo.CategoryVo;
import com.zy.mall.service.CategoryService;
import com.zy.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 商品目录controller
 */
@Controller
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @ApiOperation("后台添加目录")
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session,
                                       @Valid @RequestBody AddCategoryReq addCategoryReq) {
        //四个参数都不能为空，否则返回异常
        // @Valid有这个注解不需要以下校验
//        if (addCategoryReq.getName() == null ||
//                addCategoryReq.getType() == null ||
//                addCategoryReq.getParentId() == null ||
//                addCategoryReq.getOrderNum() == null
//        ) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.PARAM_NOT_NULL);
//        }
        //获取当前用户
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        //为空说明未登录。提示未登录
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //管理员校验
        boolean adminRole = userService.checkAdminRole(currentUser);
        //是管理员
        if (adminRole) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else {
            //不是管理员
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }


    @ApiOperation("后台删除目录")
    @PostMapping("/admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台目录列表")
    @PostMapping("/admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台目录列表")
    @PostMapping("/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVo> categoryVos = categoryService.listCategoryForCustomer();
        return ApiRestResponse.success(categoryVos);
    }
}
