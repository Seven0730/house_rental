package com.superCode.dao;

import com.superCode.entity.Housing_info;
import java.util.List;
import java.util.Map;



public interface Housing_infoDAO {
    void insert(Housing_info housing_info) ;

    void update(Housing_info housing_info);

    void delete(String housing_infoid) ;

    List<Housing_info> getAll(Map<String, Object> params)  ;

    int getRowSize(Map<String, Object> params) ;






    // 按照Housing_info类里面的值精确查询 调用entity包housing_info.xml里的getHousing_infoByCond配置
    List<Housing_info> getByCond(Housing_info housing_info) ;

    // 按照Housing_info类里面的值模糊查询 调用entity包housing_info.xml里的getHousing_infoByLike配置
    List<Housing_info> getByLike(Housing_info housing_info) ;

    // 按主键查询表返回单一的Housing_info实例 调用entity包housing_info.xml里的getHousing_infoById配置
    Housing_info getById(String housing_infoid);


}
