package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//默认当前类中的所有的方法的返回值响应为json
@RequestMapping("checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkitemService;

    /**
     *      新增选项
     * @param checkItem
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkitemService.add(checkItem);
            //新增成功
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }


    /**
     *      分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkitemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    /**
     * 通过id删除检查项
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            checkitemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 通过id查询数据，然后在回显数据
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            CheckItem checkItem =  checkitemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 编辑数据
     * @param checkItem
     * @return
     */
    @RequestMapping("edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkitemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }


    /**
     * 查询所有检查项信息
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<CheckItem> list = checkitemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    /**
     * 通过检查组的id查询关联的检查项
     * @param id
     * @return
     */
    @RequestMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer id){
        try {
            List<Integer> checkItemIds  = checkitemService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
