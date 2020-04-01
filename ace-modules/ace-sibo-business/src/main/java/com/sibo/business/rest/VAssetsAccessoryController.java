package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VAssetsAccessoryBiz;
import com.sibo.business.entity.VAssetsAccessory;
import com.sibo.business.vo.VAssetsAccessoryVo;
import com.sibo.business.vo.VAssetsParameterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("vAssetsAccessory")
@CheckClientToken
@CheckUserToken
public class VAssetsAccessoryController extends BaseController<VAssetsAccessoryBiz,VAssetsAccessory,String> {

    @Autowired
    private VAssetsAccessoryBiz assetsAccessoryBiz;



    @ApiOperation("随机资料新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsAccessoryVo> addSup(@RequestBody VAssetsAccessoryVo record) {
        String id = assetsAccessoryBiz.insertSelective(record);
        return new ObjectRestResponse<VAssetsAccessoryVo>().data(new VAssetsAccessoryVo(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation("更新单个对象")
    public ObjectRestResponse<VAssetsAccessoryVo> update(@RequestBody VAssetsAccessoryVo vo){
        VAssetsAccessoryVo entity =  assetsAccessoryBiz.updateAdditionalAsset(vo);
        return new ObjectRestResponse<VAssetsAccessoryVo>().data(entity);
    }

}