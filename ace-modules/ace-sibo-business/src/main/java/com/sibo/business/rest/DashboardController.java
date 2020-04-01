package com.sibo.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.netflix.discovery.converters.Auto;
import com.sibo.business.biz.*;
import com.sibo.business.entity.*;
import com.sibo.business.vo.StatisticsAssetsNumVo;
import com.sibo.business.vo.VMaintenanceScheduleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 首页信息查询
 */
@RestController
@RequestMapping("dashboard")
//@CheckClientToken
@CheckUserToken
public class DashboardController {
    @Autowired
    private DashboardBiz dashboardBiz;

    @Autowired
    private VAssetsParameterBiz vAssetsParameterBiz;

    @Autowired
    private VCostRecordBiz vCostRecordBiz;

    @Autowired
    private VMaintenanceScheduleBiz vMaintenanceScheduleBiz;

    @Autowired
    private VMeasurementVerificationPlanBiz vMeasurementVerificationPlanBiz;

    @Autowired
    private VAssetsReceiveBiz assetsReceiveBiz;

    @Autowired
    private VInvestmentBiz vInvestmentBiz;
    @ApiOperation("首页获取数据")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ObjectRestResponse<Integer> queryPage(@RequestParam String type,@RequestParam String id){
        int count = dashboardBiz.queryCount(type,id);
        return new ObjectRestResponse<Integer>().data(count);
    }

    @ApiOperation("首页设备总数量")
    @RequestMapping(value = "/queryDashboardAssetsCount",method = RequestMethod.GET)
    public ObjectRestResponse<List<StatisticsAssetsNumVo> > queryDashboardAssetsCount(@RequestParam String type){
        List<StatisticsAssetsNumVo>  count = vAssetsParameterBiz.queryDashboardAssetsCount(type);
        return new ObjectRestResponse<List<StatisticsAssetsNumVo> >().data(count);
    }

    @ApiOperation("首页设备采购费")
    @RequestMapping(value = "/queryEquipmentPurchaseCost",method = RequestMethod.GET)
    public ObjectRestResponse<Map> equipmentPurchaseCost(){
        Map count = vAssetsParameterBiz.equipmentPurchaseCost();
        return new ObjectRestResponse<Map>().data(count);
    }

    @ApiOperation("首页维修耗材费")
    @RequestMapping(value = "/queryCurrentYearCost",method = RequestMethod.GET)
    public ObjectRestResponse<List<VCostRecord>> queryCurrentYearCost(){
        List count = vCostRecordBiz.queryCurrentYearCost();
        return new ObjectRestResponse<List<VCostRecord>>().data(count);
    }

    @ApiOperation("首页维保计划")
    @RequestMapping(value = "/queryYearSchedule",method = RequestMethod.GET)
    public ObjectRestResponse<List<VMaintenanceScheduleVo>> queryYearSchedule(){
        List count = vMaintenanceScheduleBiz.queryYearSchedule();
        return new ObjectRestResponse<List<VMaintenanceScheduleVo>>().data(count);
    }

    @ApiOperation("首页计量数据")
    @RequestMapping(value = "/queryYearData",method = RequestMethod.GET)
    public ObjectRestResponse<List<VMeasurementVerificationPlan>> queryYearData(){
        List count = vMeasurementVerificationPlanBiz.queryYearData();
        return new ObjectRestResponse<List<VMeasurementVerificationPlan>>().data(count);
    }

    @ApiOperation("年度计量费用")
    @RequestMapping(value = "/queryYearMeasurementCost",method = RequestMethod.GET)
    public ObjectRestResponse<List<VAssetsParameter>> queryYearMeasurementCost(){
        List<VAssetsParameter> list = vAssetsParameterBiz.queryYearMeasurementCost();
        return new ObjectRestResponse<List<VAssetsParameter>>().data(list);
    }

    @ApiOperation("投资计划")
    @RequestMapping(value = "/queryYearIncesetPlan",method = RequestMethod.GET)
    public ObjectRestResponse<List<VInvestment>> queryYearIncesetPlan(){
        List<VInvestment> list =vInvestmentBiz.queryYearInvestmentCost();
        return new ObjectRestResponse<List<VInvestment>>().data(list);
    }

    @ApiOperation("系统消息")
    @RequestMapping(value = "/queryAutoCommit",method = RequestMethod.GET)
    public ObjectRestResponse<List<VAssetsReceiveRecords>> queryAutoCommit(){
        List<VAssetsReceiveRecords> list =assetsReceiveBiz.queryAutoCommit();
        return new ObjectRestResponse<List<VAssetsReceiveRecords>>().data(list);
    }


}
