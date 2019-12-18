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
import com.itheima.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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

   /*
    1、检查用户所选择的预约日期是否已经提供了预约设置，如果没有设置则无法进行预约
    2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
    3、通过手机号检查当前用户是否为会员
        不是会员：自动完成注册，
        是会员：检查用户是否重复预约（通过member_id、orderDate、setmeal_id，查询同一个会员在当天是不是预约了同一个套餐），如果是重复预约则无法完成再次预约
    4、添加预约
        需要手动设置预约状态、预约类型、会员编号、预约日期、套餐编号
    5、预约成功，更新当日的已预约人数
*/

    @Override
    public Order add(Map map) throws Exception {

        String telephone = (String) map.get("telephone");
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);

        //查用户所选择的预约日期是否已经提供了预约设置
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
        if (orderSetting==null){
            //如果不提供预约设置，提示错误信息
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //检查用户所选择的预约日期是否已经约满
        if (orderSetting.getReservations()>=orderSetting.getNumber()){
            //如果预约已满，提示错误信息
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }
        //通过手机号检查当前用户是否为会员
        Member member =memberMapper.findByTelephone(telephone);
        if (member==null){
            //如果不是会员，注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setSex((String)map.get("sex"));
            member.setIdCard((String)map.get("idCard"));
            member.setName((String)map.get("name"));
            member.setRegTime(new Date());

            //新增为会员
            memberMapper.add(member);

        }else{

            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
            //如果是会员，检查是否重复预约
            Order queryOrder = orderMapper.findByCondition(order);
            if (queryOrder!=null){
                //如果预约体检订单重复，提示错误信息
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
        //更新t_ordersetting中已预约数量
        orderSettingMapper.updateReservationsByOrderDate(date);

        return order;
    }

    @Override
    public Map findById(Integer id) {
        Map map = orderMapper.findById(id);
        Date orderDate = (Date) map.get("orderDate");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
        map.put("orderDate",date);

        return map;
    }
}
