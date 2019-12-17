package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增数据
     * @param checkItemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping("checkGroupAdd")
    public Result checkGroupAdd(@RequestParam(value = "checkItemIds" )Integer [] checkItemIds, @RequestBody CheckGroup checkGroup){
        try {
            checkGroupService.checkGroupAdd(checkItemIds,checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @RequestMapping("findBbyId")
    public Result findBbyId(@RequestParam("id") Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 编辑更新
     * @param checkitemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping("edit")
    public Result edit(@RequestParam("checkItemIds")Integer[] checkitemIds,@RequestBody CheckGroup checkGroup ){
        try {
            checkGroupService.edit(checkitemIds,checkGroup);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch ( RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }


    /**
     * 查询所有检查组信息
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
