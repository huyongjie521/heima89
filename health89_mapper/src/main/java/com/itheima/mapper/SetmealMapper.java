package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("checkGroupIds")Integer[] checkGroupIds, @Param("id")Integer id);

    List<Setmeal> findAll();

    Setmeal findById(@Param("id")Integer id);

    List<Map> getSetmealReport();
}
