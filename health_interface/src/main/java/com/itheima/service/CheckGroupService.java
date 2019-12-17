package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void checkGroupAdd(Integer[] checkItemIds, CheckGroup checkGroup);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);


    void edit(Integer[] checkitemIds, CheckGroup checkGroup);

    void deleteById(Integer id);

    List<CheckGroup> findAll();
}
