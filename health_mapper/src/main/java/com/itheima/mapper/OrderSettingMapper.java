package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {
    Integer findCountByOrderDate(@Param("orderDate") Date orderDate);

    void updateNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findOrderSettingByMonth(@Param("dateBegin") String dateBegin,@Param("dateEnd") String dateEnd);

    OrderSetting findByOrderDate(@Param("date")Date date);

    void updateReservationsByOrderDate(@Param("date")Date date);
}
