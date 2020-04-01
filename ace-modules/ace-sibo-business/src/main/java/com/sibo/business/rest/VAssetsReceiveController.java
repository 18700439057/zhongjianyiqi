package com.sibo.business.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VAssetsReceiveBiz;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsReceiveRecords;
import com.sibo.business.vo.VAssetsReceiveVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("vAssetsReceive")
@CheckClientToken
@CheckUserToken
public class VAssetsReceiveController extends BaseController<VAssetsReceiveBiz,VAssetsReceiveRecords,String> {

    @Autowired
    private VAssetsReceiveBiz assetsReceiveBiz;

    @ApiOperation("资产派发分页获取数据")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryPage(@RequestParam Map<String, Object> params){
        return assetsReceiveBiz.queryAssetsChangePage(params);
    }

    @ApiOperation("统计资产记录分页获取数据")
    @RequestMapping(value = "/queryReceiveRecordAll",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryReceiveRecordAll(@RequestParam Map<String, Object> params){
        return assetsReceiveBiz.queryReceiveRecordAll(params);
    }

    @ApiOperation("资产归还分页获取数据")
    @RequestMapping(value = "/returnPageList",method = RequestMethod.GET)
    public TableResultResponse<VAssetsParameter> queryReturnPage(@RequestParam Map<String, Object> params){
        return assetsReceiveBiz.queryAssetsReturnPage(params);
    }

    @ApiOperation("资产归还确认分页获取数据")
    @RequestMapping(value = "/queryAssetsReturnConfirmPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryAssetsReturnConfirmPage(@RequestParam Map<String, Object> params){
        return assetsReceiveBiz.queryAssetsReturnConfirmPage(params);
    }


    @ApiOperation("获取当前领取人和时间")
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public ObjectRestResponse<VAssetsReceiveVo> getCurrentUser() {
        VAssetsReceiveVo vo = new VAssetsReceiveVo();
        vo.setRecipient(BaseContextHandler.getUserID());
        vo.setRecipientTime(new Date());
        return new ObjectRestResponse<VAssetsReceiveVo>().data(vo);
    }

    @ApiOperation("领用")
    @RequestMapping(value = "/receive", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsReceiveVo> receive(@RequestBody VAssetsReceiveVo vo) {
        assetsReceiveBiz.receive(vo);
        return new ObjectRestResponse<VAssetsReceiveVo>().data(new VAssetsReceiveVo());
    }


    @ApiOperation("归还")
    @RequestMapping(value = "/assetsReturn", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsReceiveVo> assetsReturn(@RequestBody VAssetsReceiveVo vo) {
        assetsReceiveBiz.assetsReturn(vo);
        return new ObjectRestResponse<VAssetsReceiveVo>().data(new VAssetsReceiveVo());
    }

    @ApiOperation("归还确认")
    @RequestMapping(value = "/assetsReturnConfirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsReceiveVo> assetsReturnConfirm(@RequestBody VAssetsReceiveVo vo) {
        assetsReceiveBiz.assetsReturnConfirm(vo);
        return new ObjectRestResponse<VAssetsReceiveVo>().data(new VAssetsReceiveVo());
    }



}