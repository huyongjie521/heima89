package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Integer[] checkGroupIds, Setmeal setmeal);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    Map getSetmealReport();
}
