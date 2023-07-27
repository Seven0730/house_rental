package com.superCode.dao;

import java.util.List;
import java.util.Map;


import com.superCode.entity.Landlord;

public interface LandlordDAO {

    void insert(Landlord landlord) ;

    void update(Landlord landlord) ;

    void delete(String landlordid);

    List<Landlord> getAll(Map<String, Object> params) ;

    int getRowSize(Map<String, Object> params) ;




    // 按照Admin类里面的值精确查询 调用entity包landlord.xml里的getLandlordByCond配置
    List<Landlord> getByCond(Landlord landlord) ;

    // 按照Landlord类里面的值模糊查询 调用entity包landlord.xml里的getLandlordByLike配置
    List<Landlord> getByLike(Landlord landlord) ;

    // 按主键查询表返回单一的Landlord实例 调用entity包landlord.xml里的getLandlordById配置
    Landlord getById(String landlordid) ;


}
