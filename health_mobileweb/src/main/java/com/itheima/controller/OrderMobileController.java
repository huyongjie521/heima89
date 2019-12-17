package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderMobileController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 新增预约
     * @param map
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody Map map) {
        try {
            //获取手机号
            String telephone = (String) map.get("telephone");
            //获取用户提交的验证码
            String code = (String) map.get("validateCode");
            //设置key
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
            //获取redis中的验证码
            String redisCode = jedisPool.getResource().get(key);
            //验证码校验
            if (redisCode == null || !redisCode.equals(code)) {
                //提示错误信息
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            } else {
                //执行新增预约订单
                Order order = orderService.add(map);
                //发送短信通知
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, ValidateCodeUtils.generateValidateCode(4).toString());
                return new Result(true, MessageConstant.ADD_ORDER_SUCCESS, order);
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ORDER_FAIL);
        }
    }


    /**
     * 查询预约信息
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
