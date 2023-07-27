package com.superCode.dao;

import com.superCode.entity.Orders;

import java.util.List;
import java.util.Map;

public interface OrdersDAO {
    void insert(Orders orders);

    void update(Orders orders);

    void delete(String ordersid);

    List<Orders> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);

    int getOrdersNumber(int housing_infoid) ;

    List<Orders> getByCond(Orders orders);

    List<Orders> getByLike(Orders orders);

    Orders getById(String ordersid);

    List orderMoneyTotal(Map<String, Object> params) ;
    List getcontract_infonumber(Map<String, Object> params) ;


}
