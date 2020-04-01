package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import com.sibo.business.biz.VMeteringMethodBiz;
import com.sibo.business.entity.VMeteringMethod;
import com.sibo.business.vo.VMeteringMethodVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-07 14:39:47
 */
@RestController
@RequestMapping("vMeteringMethod")
@CheckClientToken
@CheckUserToken
public class VMeteringMethodController extends BaseController<VMeteringMethodBiz,VMeteringMethod,String> {


    @Autowired
    private VMeteringMethodBiz vMeteringMethodBiz;

    @RequestMapping(value = "/queryVMeteringMethod",method = RequestMethod.GET)
    public TableResultResponse<VMeteringMethod> queryAllMethod() {
        return vMeteringMethodBiz.queryAll();
    }

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public List<VMeteringMethod> list(){
        return vMeteringMethodBiz.findList();
    }





//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    public  ObjectRestResponse<VMeteringMethodVo> addMethod(@RequestBody VMeteringMethodVo vo){
//        vMeteringMethodBiz.addMethod(vo);
//        return new ObjectRestResponse<VMeteringMethodVo>().data(vo);
//    }

}