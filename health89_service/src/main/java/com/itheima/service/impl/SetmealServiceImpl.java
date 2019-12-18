package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.RedisConstant;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Integer[] checkGroupIds, Setmeal setmeal) {

        //新增套餐
        setmealMapper.add(setmeal);
        //设置套餐和检查组中间表关系
        setSetmealAndCheckGroup(checkGroupIds,setmeal.getId());

        //将保存到数据库的图片保存一份到redis中
        if (setmeal.getImg()!=null){
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        }

    }

    private void setSetmealAndCheckGroup(Integer[] checkGroupIds, Integer id) {

        if (checkGroupIds!=null && checkGroupIds.length>0){
            setmealMapper.setSetmealAndCheckGroup(checkGroupIds,id);
        }

    }

    @Override
    public List<Setmeal> findAll() {
        return setmealMapper.findAll();
    }

   /* @Override
    public Setmeal findById(Integer id) {

        return setmealMapper.findById(id);
    }*/

    @Override
    public Setmeal findById(Integer id) {

       Setmeal setmeal =  setmealMapper.findById(id);
       //通过套餐的id查询检查组
        List<CheckGroup> checkGroups = checkGroupMapper.findBySetmealId(setmeal.getId());
        setmeal.setCheckGroups(checkGroups);
        for (CheckGroup checkGroup : checkGroups) {
            List<CheckItem> checkItems = checkItemMapper.findByCheckGroupId(checkGroup.getId());
            checkGroup.setCheckItems(checkItems);
        }


        return  setmeal;
    }

    @Override
    public Map getSetmealReport() {

        List<Map> setmealCounts = setmealMapper.getSetmealReport();

        //定义一个集合封装所有的套餐名称
        List<String> setmealNames = new ArrayList<>();
        for (Map map : setmealCounts) {
            String name = (String) map.get("name");
            setmealNames.add(name);
        }

        //将数据封装到map中
        Map map = new HashMap<>();
        map.put("setmealNames",setmealNames);
        map.put("setmealCounts",setmealCounts);

        return map;
    }
}
