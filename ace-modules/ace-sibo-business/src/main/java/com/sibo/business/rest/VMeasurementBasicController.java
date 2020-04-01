package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMeasurementBasicBiz;
import com.sibo.business.entity.VMeasurementBasic;
import com.sibo.business.vo.VMeasurementBasicVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-06 18:27:28
 */
@RestController
@RequestMapping("vMeasurementBasic")
@CheckClientToken
@CheckUserToken
public class VMeasurementBasicController extends BaseController<VMeasurementBasicBiz,VMeasurementBasic,String> {

    @Autowired
    private VMeasurementBasicBiz vMeasurementBasicBiz;


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增单个对象")
    public ObjectRestResponse<VMeasurementBasicVo> add(@RequestBody VMeasurementBasicVo vo){
        vMeasurementBasicBiz.insert(vo);
        return new ObjectRestResponse<VMeasurementBasicVo>().data(vo);
    }

}