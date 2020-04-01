package com.sibo.business.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.DashboardBiz;
import com.sibo.business.biz.VInstrumentLibraryBiz;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsReceiveRecords;
import com.sibo.business.enums.Dashboard;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VAssetsReceiveVo;
import com.sibo.business.vo.VInstrumentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Map;

/**
 * 仪器库管理
 */
@RestController
@RequestMapping("vInstrumentLibrary")
@CheckClientToken
@CheckUserToken
public class VInstrumentLibraryController extends BaseController<VInstrumentLibraryBiz,VAssetsReceiveRecords,String> {

    @Autowired
    private VInstrumentLibraryBiz instrumentLibraryBiz;

    @Autowired
    private DashboardBiz dashboardBiz;

    @ApiOperation("仪器台账分页获取数据")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<VInstrumentVo> queryPage(@RequestParam Map<String, Object> params){
        return instrumentLibraryBiz.queryAssetsChangePage(params);
    }

    @ApiOperation("编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ObjectRestResponse<VInstrumentVo> updateInstrument(@RequestBody VInstrumentVo instrumentVo) {
        instrumentLibraryBiz.updateInstrument(instrumentVo);
        return new ObjectRestResponse<VInstrumentVo>().data(new VInstrumentVo());
    }

    @ApiOperation("借出查询")
    @RequestMapping(value = "/queryLoanPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryLoanPage(@RequestParam Map<String, Object> params){
        return instrumentLibraryBiz.queryLoanPage(params);
    }

    @ApiOperation("借出确认查询")
    @RequestMapping(value = "/queryLoanConfirmPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryLoanConfirmPage(@RequestParam Map<String, Object> params){
        return instrumentLibraryBiz.queryLoanConfirmPage(params);
    }

    @ApiOperation("仪器归还")
    @RequestMapping(value = "/queryReturnPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryReturnPage(@RequestParam Map<String, Object> params){
        return instrumentLibraryBiz.queryReturnPage(params);
    }

    @ApiOperation("仪器归还确认查询")
    @RequestMapping(value = "/queryReturnConfirmPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryReturnConfirmPage(@RequestParam Map<String, Object> params){
        return instrumentLibraryBiz.queryReturnConfirmPage(params);
    }

    @ApiOperation("借出")
    @RequestMapping(value = "/loan", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> loan(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.loan(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("借出确认")
    @RequestMapping(value = "/loanConfirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> loanConfirm(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.loanConfirm(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("借出拒绝")
    @RequestMapping(value = "/loanReject", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> loanReject(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.loanReject(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("仪器归还")
    @RequestMapping(value = "/returnInstrument", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> returnInstrument(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.returnInstrument(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("仪器归还提交")
    @RequestMapping(value = "/returnConfirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> returnConfirm(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.returnConfirm(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("仪器归还拒绝")
    @RequestMapping(value = "/rejectInstrument", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> rejectInstrument(@RequestBody VAssetsReceiveVo vo) {
        instrumentLibraryBiz.rejectInstrument(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    //================================================2019.1.11 修改为以下新流程==========================================================================
    @ApiOperation("设备派发")
    @RequestMapping(value = "/distribute", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> distribute(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.distribute(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("设备归库")
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public ObjectRestResponse<VAssetsParameterVo> cancel(@RequestParam String asstesId) {
        instrumentLibraryBiz.cancel(asstesId);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("待确认查询")
    @RequestMapping(value = "/queryWaitCommit", method = RequestMethod.GET)
    public TableResultResponse<VAssetsParameter> queryWaitCommit(@RequestParam Map<String, Object> params) {
        return instrumentLibraryBiz.queryWaitCommit(params);
    }

    @ApiOperation("待确认查询详细信息")
    @RequestMapping(value = "/queryWaitCommitById", method = RequestMethod.GET)
    public ObjectRestResponse<VAssetsParameter> queryWaitCommitById(@RequestParam String id) {
        VAssetsParameter vAssetsParameter = instrumentLibraryBiz.queryWaitCommitById(id);
        return new ObjectRestResponse<VAssetsParameter>().data(vAssetsParameter)  ;
    }

    @ApiOperation("确认")
    @RequestMapping(value = "/affirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> affirm(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.affirm(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("待归还查询")
    @RequestMapping(value = "/queryGiveBack", method = RequestMethod.GET)
    public TableResultResponse<VAssetsParameter> queryGiveBack(@RequestParam Map<String, Object> params) {
        return instrumentLibraryBiz.queryGiveBack(params);
    }

    @ApiOperation("设备归还")
    @RequestMapping(value = "/giveBack", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> giveBack(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.giveBack(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("设备归还确认查询")
    @RequestMapping(value = "/queryGiveBackAffirm", method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryGiveBackAffirm(@RequestParam Map<String, Object> params) {
        return instrumentLibraryBiz.queryGiveBackAffirm(params);
    }

    @ApiOperation("设备归还确认")
    @RequestMapping(value = "/giveBackAffirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> giveBackAffirm(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.giveBackAffirm(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("设备归还确认拒绝")
    @RequestMapping(value = "/giveBackReject", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> giveBackReject(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.giveBackReject(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("错误数据驳回")
    @RequestMapping(value = "/reject", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsParameterVo> reject(@RequestBody VAssetsParameterVo vo) {
        instrumentLibraryBiz.reject(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }

    @ApiOperation("驳回数据查询")
    @RequestMapping(value = "/queryRejectData", method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryRejectData(@RequestParam Map<String, Object> params) {
        return instrumentLibraryBiz.queryRejectData(params);
    }

    @ApiOperation("我的资产")
    @RequestMapping(value = "/queryMineAssets", method = RequestMethod.GET)
    public TableResultResponse<VAssetsParameter> queryMineAssets(@RequestParam Map<String, Object> params) {
        return instrumentLibraryBiz.queryMineAssets(params);
    }

    @ApiOperation("查询代办数量")
    @RequestMapping(value = "/queryWaitCount", method = RequestMethod.GET)
    public ObjectRestResponse<Integer> queryWaitCount(@RequestParam String type){
        int count = dashboardBiz.queryCount(type,BaseContextHandler.getUserID());
        return new ObjectRestResponse<Integer>().data(count);
    }


}