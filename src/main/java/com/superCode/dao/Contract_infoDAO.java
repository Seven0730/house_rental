package com.superCode.dao;

import com.superCode.entity.Contract_info;

import java.util.List;
import java.util.Map;


public interface Contract_infoDAO {
    void insert(Contract_info contract_info) ;

    void update(Contract_info contract_info);

    void delete(String contract_infoid) ;

    List<Contract_info> getAll(Map<String, Object> params)  ;

    int getRowSize(Map<String, Object> params) ;






    // 按照Contract_info类里面的值精确查询 调用entity包contract_info.xml里的getContract_infoByCond配置
    List<Contract_info> getByCond(Contract_info contract_info) ;

    // 按照Contract_info类里面的值模糊查询 调用entity包contract_info.xml里的getContract_infoByLike配置
    List<Contract_info> getByLike(Contract_info contract_info) ;

    // 按主键查询表返回单一的Contract_info实例 调用entity包contract_info.xml里的getContract_infoById配置
    Contract_info getById(String contract_infoid);


}
