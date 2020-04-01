package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMeasurementVerificationPlanBiz;
import com.sibo.business.entity.VMeasurementVerificationPlan;
import com.sibo.business.vo.VMaintainVo;
import com.sibo.business.vo.VMeasurementVerificationPlanVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.text.ParseException;
import java.util.Map;

/**
 * 计量/核查计划表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@RestController
@RequestMapping("vMeasurementVerificationPlan")
@CheckClientToken
@CheckUserToken
public class VMeasurementVerificationPlanController extends BaseController<VMeasurementVerificationPlanBiz,VMeasurementVerificationPlan,String> {

    @Autowired
    private VMeasurementVerificationPlanBiz measurementVerificationPlanBiz;

    @ApiOperation("生成计划")
    @RequestMapping(value = "/createMeasurementpaln",method = RequestMethod.GET)
    public TableResultResponse<VMeasurementVerificationPlanVo> createMeasurementpaln() throws BusinessRuntimeException {
        measurementVerificationPlanBiz.createMeasurementpaln();
        return new TableResultResponse<VMeasurementVerificationPlanVo>();
    }

    @ApiOperation("生成计划")
    @RequestMapping(value = "/createInspectPaln",method = RequestMethod.GET)
    public TableResultResponse<VMeasurementVerificationPlanVo> createInspectPaln() throws BusinessRuntimeException {
        measurementVerificationPlanBiz.createInspectPaln();
        return new TableResultResponse<VMeasurementVerificationPlanVo>();
    }

}