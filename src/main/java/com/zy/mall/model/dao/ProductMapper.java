package com.zy.mall.model.dao;

import com.zy.mall.model.pojo.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);
}