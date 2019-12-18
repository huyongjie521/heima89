package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {


    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {

        //新增检查组
        checkGroupMapper.add(checkGroup);
        //添加检查组和检查项的中间表数据
        setCheckGroupAndCheckItem(checkItemIds,checkGroup);

    }

    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, CheckGroup checkGroup) {
        if (checkItemIds!=null && checkItemIds.length>0){
            checkGroupMapper.setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());
        }

    }

   /* private void setCheckGroupAndCheckItem(Integer[] checkItemIds, CheckGroup checkGroup) {
        if (checkItemIds!=null && checkItemIds.length>0){
            for (Integer checkItemId : checkItemIds) {
                checkGroupMapper.setCheckGroupAndCheckItem(checkItemId,checkGroup.getId());
            }
        }
    }*/


    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);

        List<CheckGroup> checkGroupList = checkGroupMapper.findByCondition(queryString);

        PageInfo pageInfo = new PageInfo(checkGroupList);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {

        //删除中间表数据
        checkGroupMapper.deleteAssociation(checkGroup.getId());

        //重新添加中间表数据
        checkGroupMapper.setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());

        //更新检查组数据
        checkGroupMapper.edit(checkGroup);

    }

    @Override
    public void deleteById(Integer id) {

        //通过检查组的id统计当前检查组有没有被套餐引用过
        Integer count = checkGroupMapper.findCountById(id);
        if (count>0){
            //如果被某一个套餐引用，就抛出异常，不允许删除
            throw new RuntimeException(MessageConstant.CHECKGROUP_IS_QUOTED);
        }
        //删除检查组和检查项中间表数据
        checkGroupMapper.deleteAssociation(id);

        //删除检查组数据
        checkGroupMapper.deleteById(id);

    }

    /**
     * 查询所有检查组信息
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }
}
