package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 注册会员
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }

    /**
     * 新增会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }
}
