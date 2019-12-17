package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送验证码到手机
     * @param telephone
     * @return
     */
    @RequestMapping("send2telephone")
    public Result send2telephone(@RequestParam("telephone")String telephone){
        try {
            //获取code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //发送验证码到手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            //设置一个key用来保存验证码
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
            //将验证码保存到redis中
            jedisPool.getResource().setex(key,10*60,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    @RequestMapping("send2Login")
    public Result send2Login(@RequestParam("telephone")String telephone){
        try {
            //获取code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //发送验证码到手机
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            //设置一个key用来保存验证码
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
            //将验证码保存到redis中
            jedisPool.getResource().setex(key,10*60,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }
}
