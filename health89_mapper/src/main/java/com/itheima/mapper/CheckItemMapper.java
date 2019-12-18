package com.itheima.mapper;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {
    void add(CheckItem checkItem);

    List<CheckItem> findByCondition(@Param("queryString") String queryString);

    Integer findCountByCheckItemId(@Param("id")Integer id);

    void deleteById(@Param("id")Integer id);

    CheckItem findById(@Param("id")Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("checkGroupId")Integer checkGroupId);

    List<CheckItem> findByCheckGroupId(@Param("id")Integer id);

    /***
     * 只要参数不是pojo\Map都使用@Param
     *
     * @param queryString
     * @return
     */
//    Long findCount(@Param("queryString") String queryString);

//    List<CheckItem> findByCondition(@Param("firstResult")int firstResult, @Param("pageSize")Integer pageSize, @Param("queryString")String queryString);

}
