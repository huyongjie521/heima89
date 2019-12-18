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
import org.springframework.security.core.parameters.P;

import java.util.List;

@Service(interfaceClass = CheckItemService.class )
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {

        checkItemMapper.add(checkItem);

    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
   /* @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        //定义分页查询的其实索引号，limit x，y中的x的值
        int firstResult = (currentPage-1)*pageSize;

        //查询总记录数
        Long count = checkItemMapper.findCount(queryString);

        //查询分页数据集合
        List<CheckItem> checkItemList = checkItemMapper.findByCondition(firstResult,pageSize,queryString);

        //将数据封装到PageResult

        return new PageResult(count,checkItemList);
    }*/

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);
        //条件查询所有数据
        List<CheckItem> checkItemList = checkItemMapper.findByCondition(queryString);
        //交给PageInfo包装
        PageInfo pageInfo = new PageInfo(checkItemList);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 通过id删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {

        //查询当前检查项是否被引用了
        Integer count = checkItemMapper.findCountByCheckItemId(id);
        if (count>0){
            //抛出异常，提示错误信息
            throw new RuntimeException(MessageConstant.CHECKITEM_IS_QUOTED);
        }

        //通过id删除检查项
        checkItemMapper.deleteById(id);


    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.findById(id);
    }

    /**
     * 编辑更新检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {

        checkItemMapper.edit(checkItem);

    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
//        int x = 1 / 0;
        return checkItemMapper.findCheckItemIdsByCheckGroupId(checkGroupId);
    }
}
