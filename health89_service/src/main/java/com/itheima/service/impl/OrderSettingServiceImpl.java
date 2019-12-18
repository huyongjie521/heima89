package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {

        for (OrderSetting orderSetting : orderSettings) {
            //通过日期查询是否在数据库添加过该日期的数据
            Integer count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
            if (count>0){
                //如果当前日期已经添加过预约设置，就执行修改
                orderSettingMapper.updateNumberByOrderDate(orderSetting);
            }else{
                //执行新增
                orderSettingMapper.add(orderSetting);
            }

        }

    }

   /* @Override
    public List<Map> findOrderSettingByMonth(String date) {

        String dateBegin = date+"-01";
        String dateEnd = date+"-31";

        List<OrderSetting> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin,dateEnd);

        List<Map> maps = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map<String, Object> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            maps.add(map);
        }

        return maps;
    }*/

    @Override
    public List<Map> findOrderSettingByMonth(String date) {

        String dateBegin = date+"-01";
        String dateEnd = date+"-31";

        List<Map> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin,dateEnd);

        return orderSettings;
    }

    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        //通过日期查询是否在数据库添加过该日期的数据
        Integer count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0){
            //如果当前日期已经添加过预约设置，就执行修改
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        }else{
            //执行新增
            orderSettingMapper.add(orderSetting);
        }
    }
}
