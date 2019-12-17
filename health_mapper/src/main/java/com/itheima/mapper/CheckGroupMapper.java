package com.itheima.mapper;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupMapper {

//    void setCheckGroupAndCheckItem(@Param("checkItemId") Integer checkItemId, @Param("id")Integer id);

    void checkGroupAdd(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param("checkItemIds")Integer[] checkItemIds,@Param("id") Integer id);

    List<CheckGroup> findPage(@Param("queryString") String queryString);

    CheckGroup findById(@Param("id") Integer id);

    void deleteAssociation(@Param("id") Integer id);

    void edit(CheckGroup checkGroup);

    Integer findCountById(@Param("id") Integer id);

    void deleteById(@Param("id") Integer id);

    List<CheckGroup> findAll();
}
