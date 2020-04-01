package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import com.sibo.business.biz.VSupAppraiseBiz;
import com.sibo.business.entity.VSupAppraise;
import com.sibo.business.vo.VSupAppraiseVo;
import com.sibo.business.vo.VSupSupplierVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vSupAppraise")
@CheckClientToken
@CheckUserToken
public class VSupAppraiseController extends BaseController<VSupAppraiseBiz,VSupAppraise,String> {

    @Autowired
    private VSupAppraiseBiz supAppraiseBiz;


    @ApiOperation("供应商评价新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VSupAppraiseVo> addSup(@RequestBody VSupAppraiseVo record) {
        String id = supAppraiseBiz.insertSelective(record);
        return new ObjectRestResponse<VSupAppraiseVo>().data(new VSupAppraiseVo(id));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据父id获取评价表")
    public TableResultResponse<VSupAppraise> page(@RequestParam(defaultValue = "10") int limit,
                                             @RequestParam(defaultValue = "1") int offset,String name, @RequestParam(defaultValue = "0") String supplierId) {
        Example example = new Example(VSupAppraise.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("supplierId", supplierId);
        if(StringUtils.isNotBlank(name)){
            criteria.andLike("name", "%" + name + "%");
        }
        List<VSupAppraise> elements = baseBiz.selectByExample(example);
        return new TableResultResponse<VSupAppraise>(elements.size(), elements);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("更新单个对象")
    public ObjectRestResponse<VSupAppraiseVo> update(@RequestBody VSupAppraiseVo vo){
        VSupAppraiseVo entity =  supAppraiseBiz.updateById(vo);
        return new ObjectRestResponse<VSupAppraiseVo>().data(entity);
    }

}