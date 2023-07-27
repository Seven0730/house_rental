package com.superCode.service;

import com.superCode.dao.Housing_infoDAO;
import com.superCode.entity.Housing_info;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("housing_info")
public class Housing_infoService {

    @Resource
    private Housing_infoDAO housing_infoDao;

    // 插入数据 调用dAO里的insertHousing_info配置
    public void insert(Housing_info housing_info) {
          housing_infoDao.insert(  housing_info);
    }

    // 更新数据 调用dAO里的updateHousing_info配置
    public void update(Housing_info housing_info) {
        housing_infoDao.update(  housing_info);
    }

    // 删除数据 调用dAO里的deleteHousing_info配置
    public void delete(String housing_infoid) {
        housing_infoDao.delete(  housing_infoid);
    }

    // 查询全部数据 调用dAO里的getAllHousing_info配置
    public List<Housing_info> getAll(Map<String, Object> params) {
        return housing_infoDao.getAll( params);
    }

    // 查询总行数 调用dAO里的配置
    public int getRowSize(Map<String, Object> params) {
        return housing_infoDao.getRowSize( params);
    }







    // 按主键查询表返回单一的Housing_info实例 调用dAO里的getHousing_infoById配置
    public Housing_info getById(String housing_infoid) {
        return housing_infoDao.getById( housing_infoid);
    }




}
