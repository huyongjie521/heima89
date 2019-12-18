package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {


    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

   /* reportDate:null,//报告日期
    todayNewMember :0,//今日新增会员数
    totalMember :0,//总会员数
    thisWeekNewMember :0,//本周新增会员数
    thisMonthNewMember :0,//本月新增会员数
    todayOrderNumber :0,//今日预约数
    todayVisitsNumber :0,//今日到诊数
    thisWeekOrderNumber :0,//本周预约数
    thisMonthOrderNumber :0,//本月预约数
    thisWeekVisitsNumber :0,//本周到诊数
    thisMonthVisitsNumber :0,//本月到诊数
    hotSetmeal :[//显示4个热门套餐
        {name:"粉红珍爱(女)升级TM12项筛查体检套餐",setmeal_count:5,proportion:0.4545},
        {name:"阳光爸妈升级肿瘤12项筛查体检套餐",setmeal_count:2,proportion:0.1818},
        {name:"珍爱高端升级肿瘤12项筛查",setmeal_count:2,proportion:0.1818},
        {name:"孕前检查套餐",setmeal_count:1,proportion:0.0909}
    ]*/

    @Override
    public Map getBusinessReport() throws Exception {

        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String thisWeekModay = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        Integer todayNewMember = memberMapper.findTodayNewMember(today);

        Integer totalMember = memberMapper.findTotalMember();

        Integer thisWeekNewMember = memberMapper.findNewMemberCountAfterDate(thisWeekModay);
        Integer thisMonthNewMember = memberMapper.findNewMemberCountAfterDate(firstDay4ThisMonth);

        Integer todayOrderNumber = orderMapper.findTodayOrderNumber(today);

        Integer todayVisitsNumber = orderMapper.findTodayVisitsNumber(today);

        Integer thisWeekOrderNumber = orderMapper.findOrderNumberAfterDate(thisWeekModay);
        Integer thisMonthOrderNumber = orderMapper.findOrderNumberAfterDate(firstDay4ThisMonth);

        Integer thisWeekVisitsNumber = orderMapper.findVisitsNumberAfterDate(thisWeekModay);
        Integer thisMonthVisitsNumber = orderMapper.findVisitsNumberAfterDate(firstDay4ThisMonth);

        List<Map> hotSetmeal = orderMapper.findHotSetmeal();

        Map result = new HashMap();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
}
