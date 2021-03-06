package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.util.DateUtils;
import com.itheima.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    /**
     * 新增预约数据
     * @param file
     * @return
     */
    @RequestMapping("importOrderSettings")
    public Result importOrderSettings(@RequestParam("excelFile") MultipartFile file){

        try {
            //读取excel，list中包含的每一行数据
            List<String[]> strings = POIUtils.readExcel(file);
            //判断集合是否为空，防止上传一个空的excel
            if(strings!=null){
                //定义一个List用来存放orderSetting
                ArrayList<OrderSetting> orderSettings = new ArrayList<>();
                //遍历模板中的每一行
                for (String[] string : strings) {
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(DateUtils.parseString2Date(string[0],"yyyy/MM/dd"));
                    orderSetting.setNumber(Integer.parseInt(string[1]));
                    orderSettings.add(orderSetting);
                }
                //将预约数据保存到数据库
                orderSettingService.importOrderSettings(orderSettings);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 查询当月预约数据
     * @param date
     * @return
     */
    @RequestMapping("findOrderSettingByMonth")
    public Result findOrderSettingByMonth(@RequestParam("date") String date){
        try {
            List<Map> list = orderSettingService.findOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据日期更新预约数量
     * @param orderSetting
     * @return
     */
    @RequestMapping("updateNumberByOrderDate")
    public Result updateNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.updateNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ORDERSETTING_FAIL);

        }

    }
}
