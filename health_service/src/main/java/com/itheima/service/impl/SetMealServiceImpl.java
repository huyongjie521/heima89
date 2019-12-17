package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.SetMealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private JedisPool jedisPool;
    /**
     * 添加套餐
     * @param checkgroupIds
     * @param setmeal
     */
    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        //新增套餐
        setMealMapper.add(setmeal);
        //设置套餐和检查组的中间表关系
        setSetmealAndCheckGroup(checkgroupIds, setmeal.getId());
        //将保存到数据库的图片保存一份到readis中
        if(setmeal.getImg()!=null){
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        }
    }

    private void setSetmealAndCheckGroup(Integer[] checkgroupIds, Integer id) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            setMealMapper.setSetmealAndCheckGroup(checkgroupIds, id);
        }
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //定义分页信息
        PageHelper.startPage(currentPage,pageSize);
        //查询分页数据
        List<Setmeal> list = setMealMapper.findPage(queryString);
        //封装到pageInfo中
        PageInfo pageInfo = new PageInfo(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 查询套餐列表数据
     * @return
     */
    @Override
    public List<Setmeal> findSetmealAll() {
        return setMealMapper.findSetmealAll();
    }

    /**
     * 通过id查询套餐和检查组以及检查项数据
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setMealMapper.findById(id);
    }
}