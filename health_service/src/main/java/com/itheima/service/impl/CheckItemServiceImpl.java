package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    //新增
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    //分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
//        //定义分页查询的其实索引号，limit x，y中的x值
//        int firstResult=(currentPage-1)*pageSize;
//        //查询总记录数
//       Long count = checkItemMapper.findCount(queryString);
//        //查询分页数据集合
//        List<PageResult> checkItemList = checkItemMapper.findByCondition(firstResult,pageSize,queryString);
//        //将数据封装到PageResult
//        return new PageResult(count,checkItemList);
        //分页插件查询
        PageHelper.startPage(currentPage,pageSize);
        //条件查询所有数据
        List<PageResult> checkItemList = checkItemMapper.findByCondition(queryString);
        //交给PageInfo封装
        PageInfo pageInfo = new PageInfo(checkItemList);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }


    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
       Integer count = checkItemMapper.findCountByCheckItemId(id);
       if (count>0){
           //抛出异常，提示错误信息
           throw new RuntimeException(MessageConstant.CHECKITEM_IS_QUOTED);
       }
        checkItemMapper.deleteById(id);
    }

    /**
     * 回显数据的查找
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {

        return checkItemMapper.findById(id);
    }

    /**
     * 编辑数据
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
      checkItemMapper.edit(checkItem);
    }

    /**
     * 查询所有检查项信息
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }

    /**
     * 通过查询组的id 来查询关联的检查项
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkItemMapper.findCheckItemIdsByCheckGroupId(id);
    }
}
