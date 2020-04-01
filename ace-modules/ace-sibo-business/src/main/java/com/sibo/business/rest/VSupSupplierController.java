package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.sibo.business.biz.VSupSupplierBiz;
import com.sibo.business.entity.VSupSupplier;
import com.sibo.business.vo.VSupSupplierVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("vSupSupplier")
@CheckClientToken
@CheckUserToken
@Api(tags = "供应商管理")
public class VSupSupplierController extends BaseController<VSupSupplierBiz,VSupSupplier,String> {

    @Autowired
    private VSupSupplierBiz supSupplierBiz;

    @ApiOperation("供应商新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VSupSupplierVo> addSup(@RequestBody VSupSupplierVo record) {
        String id = supSupplierBiz.insertSelective(record);
        return new ObjectRestResponse<VSupSupplierVo>().data(new VSupSupplierVo(id));
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("更新单个对象")
    public ObjectRestResponse<VSupSupplierVo> update(@RequestBody VSupSupplierVo vo){
        VSupSupplierVo entity =  supSupplierBiz.updateById(vo);
        return new ObjectRestResponse<VSupSupplierVo>().data(entity);
    }

}