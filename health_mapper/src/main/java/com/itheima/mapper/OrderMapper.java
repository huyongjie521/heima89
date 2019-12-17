package com.itheima.mapper;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface OrderMapper {
    Order findByCondition(Order order);

    void add(Order order);

    Map findById(@Param("id") Integer id);
}
