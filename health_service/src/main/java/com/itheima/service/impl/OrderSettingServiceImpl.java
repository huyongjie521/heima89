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

    /**
     * 新增预约数据
     * @param orderSettings
     */
    @Override
    public void importOrderSettings(ArrayList<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            //检查当前预约日期是否在数据库中设置过
          Integer count =  orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
          if(count>0){
              //已存在更新预约数量
              orderSettingMapper.updateNumberByOrderDate(orderSetting);
          }else {
              //不存在就新增
              orderSettingMapper.add(orderSetting);
          }
        }
    }


    /**
     *  获取预约设置数据
     * @param date
     * @return
     */
    @Override
    public List<Map> findOrderSettingByMonth(String date) {
        //定义月份第一天
        String dateBegin = date + "-01";
        //定义月份最后天
        String dateEnd = date + "-31";
        //同当前月份的第一天和最后一天查询当前月所有的预约设置数据
        List<OrderSetting> orderSettings = orderSettingMapper.findOrderSettingByMonth(dateBegin,dateEnd);

        List list = new ArrayList();

        //遍历每一个OrderSetting，将数据封装到map中
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());

            //将所有map封装到List
            list.add(map);
        }
        return list;
    }

    /**
     * 根据日期更新预约数量
     * @param orderSetting
     */
    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        //根据日期统计之前是否设置过预约数据
        int count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if(count>0){
            //如果设置过预约数据，就执行更新
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        }else {
            //如果之前就没有设置过，就新增
            orderSettingMapper.add(orderSetting);
        }

    }
}

