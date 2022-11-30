package com.zy.mall.service;


import com.zy.mall.model.pojo.Product;
import com.zy.mall.model.request.AddProductReq;

/**
 * ProductService 商品
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);
}
