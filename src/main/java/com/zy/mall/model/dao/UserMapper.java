package com.zy.mall.model.dao;

import com.zy.mall.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据传入名字字段查询
     * @param userName
     * @return
     */
    User selectByName(String userName);

    User selectLogin(@Param("userName") String userName, @Param("password")String password);
}