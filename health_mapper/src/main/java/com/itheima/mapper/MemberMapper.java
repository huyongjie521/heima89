package com.itheima.mapper;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    Member findByTelephone(@Param("telephone") String telephone);

    void add(Member member);
}
