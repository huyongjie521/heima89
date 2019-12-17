package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("setmeal")
public class SetmealController {


    @Reference
    private SetMealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile")MultipartFile file){
        try {
            //获取上传的文件名称
            String originalFilename = file.getOriginalFilename();
            //获取后缀名称
            int index = originalFilename.indexOf(".");
            String suffix = originalFilename.substring(index);
            //获取一个唯一的文件名
            String fileName = UUID.randomUUID().toString() + suffix;
            //将文件上传到七牛云
            QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
            //将上传到七牛云文件保存一份到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            //提示上传成功
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            //提示上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增套餐
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestParam("checkgroupIds")Integer[] checkgroupIds,@RequestBody Setmeal setmeal){
        try {
            setmealService.add(checkgroupIds,setmeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     *
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }

    }
}
