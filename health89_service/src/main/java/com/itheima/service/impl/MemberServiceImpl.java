package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Member findById(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }


    @Override
    public Map getMemberReport() {

        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //将日期往前推送12个
        calendar.add(Calendar.MONTH,-12);

        //定义存储会员数量的集合
        List<Integer> memberCounts = new ArrayList<>();

        //定义存储过去12个月的集合
        List<String> months = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {

            //获取日期
            Date time = calendar.getTime();
            String orderDate = new SimpleDateFormat("yyyy-MM").format(time);

            //定义每个月查询的起始日期
            String dateBegin = orderDate+"-01";

            //定义每个月查询的结束日期
            String dateEnd = orderDate+"-31";

            //查询每个月的会员数量
            int count = memberMapper.findMemberCountByMonth(dateBegin,dateEnd);

            memberCounts.add(count);
            months.add(orderDate);
            calendar.add(Calendar.MONTH,1);
        }

        Map<String, List> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCounts",memberCounts);

        return map;
    }


}
