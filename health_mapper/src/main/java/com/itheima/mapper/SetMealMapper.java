package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealMapper {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("checkgroupIds")Integer[] checkgroupIds,@Param("id") Integer id);

    List<Setmeal> findPage(@Param("queryString")String queryString);

    List<Setmeal> findSetmealAll();

    Setmeal findById(@Param("id") Integer id);
}
