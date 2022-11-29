package com.zy.mall.service.impl;

import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.dao.CategoryMapper;
import com.zy.mall.model.pojo.Category;
import com.zy.mall.model.request.AddCategoryReq;
import com.zy.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品目录分类实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
        //利用框架的工具将addCategoryReq与category的对字段赋值
        BeanUtils.copyProperties(addCategoryReq,category);
        //查询名字是否存在
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            //不为空说明已存在，返回不允许重复异常
         throw  new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //没有任何问题则插入数据表
        int count = categoryMapper.insertSelective(category);
        //返回0说明插入失败，抛出异常
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }

    }
}
