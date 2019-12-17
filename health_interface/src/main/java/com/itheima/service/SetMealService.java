package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetMealService {
    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> findSetmealAll();

    Setmeal findById(Integer id);
}
