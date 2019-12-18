package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

public interface MemberService {
    Member findById(String telephone);

    void add(Member member);

    Map getMemberReport();
}
