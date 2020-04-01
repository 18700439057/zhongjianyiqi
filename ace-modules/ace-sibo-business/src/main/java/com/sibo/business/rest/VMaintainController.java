package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMaintainBiz;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VMaintain;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VMaintainVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("vMaintain")
@CheckClientToken
@CheckUserToken
public class VMaintainController extends BaseController<VMaintainBiz,VMaintain,String> {

    @Autowired
    private VMaintainBiz maintainBiz;

    @ApiOperation("维修申请分页获取数据")
    @RequestMapping(value = "/queryMaintainApplication",method = RequestMethod.GET)
    public TableResultResponse<VMaintainVo> queryMaintainApplication(@RequestParam Map<String, Object> params) throws ParseException {
        return maintainBiz.queryMaintainApplication(params);
    }

    @ApiOperation("运维部确认分页获取数据")
    @RequestMapping(value = "/queryMaintainConfirm",method = RequestMethod.GET)
    public TableResultResponse<VMaintainVo> queryMaintainConfirm(@RequestParam Map<String, Object> params){
        return maintainBiz.queryMaintainConfirm(params);
    }


    @ApiOperation("申请人确认分页获取数据")
    @RequestMapping(value = "/proposterListPage",method = RequestMethod.GET)
    public TableResultResponse<VMaintainVo> proposterListPage(@RequestParam Map<String, Object> params){
        return maintainBiz.proposterListPage(params);
    }

    @ApiOperation("维修保养分页数据获取")
    @RequestMapping(value = "/maintainListPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsParameter> maintainListPage(@RequestParam Map<String, Object> params){
        return maintainBiz.maintainListPage(params);
    }

    @ApiOperation("新增维修")
    @RequestMapping(value = "/insertMaintain",method = RequestMethod.POST)
    public ObjectRestResponse<VMaintainVo> insertMaintain(@RequestBody VMaintainVo vo){
        maintainBiz.insertMaintain(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }

    @ApiOperation("维修申请提交")
    @RequestMapping(value = "/submitMaintain",method = RequestMethod.PUT)
    public ObjectRestResponse<VMaintainVo> submitMaintain(@RequestBody VMaintainVo vo){
        maintainBiz.submitMaintain(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }

    @ApiOperation("维修确认")
    @RequestMapping(value = "/maintenanceConfirm",method = RequestMethod.PUT)
    public ObjectRestResponse<VMaintainVo> MaintenanceConfirm(@RequestBody VMaintainVo vo){
        maintainBiz.MaintenanceConfirm(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }

    @ApiOperation("维修完成")
    @RequestMapping(value = "/maintenanceCompleted",method = RequestMethod.PUT)
    public ObjectRestResponse<VMaintainVo> MaintenanceCompleted(@RequestBody VMaintainVo vo){
        maintainBiz.MaintenanceCompleted(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }

    @ApiOperation("申请人完成维修通过")
    @RequestMapping(value = "/maintenancePass",method = RequestMethod.PUT)
    public ObjectRestResponse<VMaintainVo> MaintenancePass(@RequestBody VMaintainVo vo){
        maintainBiz.MaintenancePass(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }

    @ApiOperation("申请人完成维修拒绝")
    @RequestMapping(value = "/maintenanceReject",method = RequestMethod.PUT)
    public ObjectRestResponse<VMaintainVo> MaintenanceReject(@RequestBody VMaintainVo vo){
        maintainBiz.MaintenanceReject(vo);
        return new ObjectRestResponse<VMaintainVo>().data(new VMaintainVo());
    }


}