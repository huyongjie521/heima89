package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;
    /**
     * 新增预约
     * @param map
     * @return
     */
    @Override
    public Order add(Map map) throws Exception {
        String telephone = (String) map.get("telephone");
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);

        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
        //查询所选择的预约日期是否已经提供了预约设置
        if(orderSetting==null){
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //检查用户所选择的预约日期是否已经约满
        if(orderSetting.getReservations()>=orderSetting.getNumber()){
            //如果预约满提示错误信息
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }

        //通过手机号检查当前用户是否是会员
        Member member = memberMapper.findByTelephone(telephone);
        if(member==null){
            //如果不是会员,注册会员
             member = new Member();
             member.setPhoneNumber(telephone);
             member.setSex((String)map.get("sex"));
             member.setIdCard((String)map.get("idCard"));
             member.setName((String)map.get("name"));
             member.setRegTime(new Date());
             //新增会员
            memberMapper.add(member);
        }else {
            //如果是会员，检查是否重复预约
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
            Order queryOrder = orderMapper.findByCondition(order);
            if(queryOrder!=null){
                //如果预约体检订单重复。提示错误信息
                throw new RuntimeException(MessageConstant.HAS_ORDERED);
            }
        }

        //新增预约体检订单
       Order order = new Order();
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String)map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        //执行新增
        orderMapper.add(order);
        //更新t_orderSetting中已预约数量
        orderSettingMapper.updateReservationsByOrderDate(date);
        return order ;
    }


    @Override
    public Map findById(Integer id) {
        Map map = orderMapper.findById(id);
        //转换日期格式，否则页面上的日期会有时分秒
        Date orderDate = (Date) map.get("orderDate");
        String date = new SimpleDateFormat("yy-MM-dd").format(orderDate);
        map.put("orderDate",date);
        //通过id查询order数据和member以及setmeal数据
        return orderMapper.findById(id);
    }
}
