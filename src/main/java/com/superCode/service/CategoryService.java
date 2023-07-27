package com.superCode.service;

import com.superCode.dao.CategoryDAO;
import com.superCode.entity.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("category")

public class CategoryService {
    @Resource
    private CategoryDAO categoryDao;

    // 插入数据 调用entity包category.xml里的insertCategory配置
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    // 更新数据 调用entity包category.xml里的updateCategory配置
    public void update(Category category) {
        categoryDao.update(category);
    }

    // 删除数据 调用entity包category.xml里的deleteCategory配置
    public void delete(String categoryid) {
        categoryDao.delete(categoryid);
    }

    // 查询全部数据 调用entity包category.xml里的getAllCategory配置
    public List<Category> getAll(Map<String, Object> params) {
        return categoryDao.getAll(params);
    }

    // 查询总行数 调用entity包Category.xml里的配置
    public int getRowSize(Map<String, Object> params) {
        return categoryDao.getRowSize(params);
    }


    // 按照Admin类里面的值精确查询 调用entity包category.xml里的getCategoryByCond配置
    public List<Category> getByCond(Category category) {
        return categoryDao.getByCond(category);
    }

    // 按照Category类里面的值模糊查询 调用entity包category.xml里的getCategoryByLike配置
    public List<Category> getByLike(Category category) {
        return categoryDao.getByLike(category);
    }

    // 按主键查询表返回单一的Category实例 调用entity包category.xml里的getCategoryById配置
    public Category getById(String categoryid) {
        return categoryDao.getById(categoryid);
    }

}
