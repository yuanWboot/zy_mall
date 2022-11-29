package com.zy.mall.service;

import com.github.pagehelper.PageInfo;
import com.zy.mall.model.request.AddCategoryReq;

/**
 * 商品分类目录Service
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
