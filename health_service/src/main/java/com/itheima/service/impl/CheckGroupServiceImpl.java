package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
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
    public void checkGroupAdd(Integer[] checkItemIds, CheckGroup checkGroup) {
        //新增检查组
        checkGroupMapper.checkGroupAdd(checkGroup);
        //添加检查组和检查项的中间表
        setCheckGroupAndCheckItem(checkItemIds,checkGroup);
    }

    private void setCheckGroupAndCheckItem(Integer[] checkItemIds, CheckGroup checkGroup) {

        checkGroupMapper.setCheckGroupAndCheckItem(checkItemIds,checkGroup.getId());
    }

   /* private void setCheckGroupAndCheckItem(Integer[] checkItemIds, CheckGroup checkGroup) {
        if(checkItemIds!=null && checkItemIds.length>0){
            for (Integer checkItemId : checkItemIds) {
                checkGroupMapper.setCheckGroupAndCheckItem(checkItemId,checkGroup.getId());
            }

        }*/




    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckGroup> checkGroups = checkGroupMapper.findPage(queryString);
        PageInfo pageInfo = new PageInfo(checkGroups);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }



    /**
     * 数据回显
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupMapper.findById(id);
    }



    /**
     * 编辑更新
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //删除中间表关系
      checkGroupMapper.deleteAssociation(checkGroup.getId());
      //更新中间表关系
        checkGroupMapper.setCheckGroupAndCheckItem(checkitemIds,checkGroup.getId());
        //更新检查组信息
        checkGroupMapper.edit(checkGroup);
    }



    /**
     * 删除检查组
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //查询当前检查项有没有被套餐引用
        Integer count = checkGroupMapper.findCountById(id);
        if(count>0){
            throw new RuntimeException(MessageConstant.CHECKGROUP_IS_QUOTED);
        }
        //通过id检查项和检查组中间表关系
        checkGroupMapper.deleteAssociation(id);
        //通过id删除检查组
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

