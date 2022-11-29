package com.zy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.mall.exception.ImoocMallException;
import com.zy.mall.exception.ImoocMallExceptionEnum;
import com.zy.mall.model.dao.CategoryMapper;
import com.zy.mall.model.pojo.Category;
import com.zy.mall.model.request.AddCategoryReq;
import com.zy.mall.model.vo.CategoryVo;
import com.zy.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品目录分类实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        //利用框架的工具将addCategoryReq与category的对字段赋值
        BeanUtils.copyProperties(addCategoryReq, category);
        //查询名字是否存在
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            //不为空说明已存在，返回不允许重复异常
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //没有任何问题则插入数据表
        int count = categoryMapper.insertSelective(category);
        //返回0说明插入失败，抛出异常
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }

    }

    @Override
    public void delete(Integer id) {
        //根据id查找对象
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //查不到记录，无法删除，删除失败
        if (categoryOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }

    }

    //分页列表中有categoryList
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    public List<CategoryVo> listCategoryForCustomer() {
        ArrayList<CategoryVo> categoryVoList = new ArrayList<>();
        recursivelyFindCategories(categoryVoList,0);
        return categoryVoList;
    }

    private void recursivelyFindCategories(List<CategoryVo> categoryVoList, Integer parentId) {
        //递归获取所有子类类别，并组合成为一个“目录树”
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                categoryVoList.add(categoryVo);
                //子目录
                recursivelyFindCategories(categoryVo.getChildCategory(),categoryVo.getId());
            }
        }
    }
}
