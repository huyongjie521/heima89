package com.itheima.mapper;

import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupMapper {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param("checkItemIds") Integer[] checkItemIds,@Param("id") Integer id);

    List<CheckGroup> findByCondition(@Param("queryString")String queryString);

    void deleteAssociation(@Param("id")Integer id);

    void edit(CheckGroup checkGroup);

    Integer findCountById(@Param("id")Integer id);

    void deleteById(@Param("id")Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findBySetmealId(@Param("id")Integer id);

//    void setCheckGroupAndCheckItem(@Param("checkItemId") Integer checkItemId,@Param("id") Integer id);
}
