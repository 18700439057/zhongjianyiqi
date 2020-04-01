package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import com.sibo.business.biz.VAssetsChangeRecordsBiz;
import com.sibo.business.entity.VAssetsChangeRecords;
import com.sibo.business.vo.VAssetsChangeRecordsVo;
import com.sibo.business.vo.VAssetsParameterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Map;

@RestController
@RequestMapping("vAssetsChangeRecords")
@CheckClientToken
@CheckUserToken
public class VAssetsChangeRecordsController extends BaseController<VAssetsChangeRecordsBiz,VAssetsChangeRecords,String> {

    @Autowired
    private VAssetsChangeRecordsBiz assetsChangeRecordsBiz;


    @ApiOperation("资产更新新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsChangeRecordsVo> addAssetsChange(@RequestBody VAssetsChangeRecordsVo record) {
        assetsChangeRecordsBiz.insertAssetsChange(record);
        return new ObjectRestResponse<VAssetsChangeRecordsVo>().data(new VAssetsChangeRecordsVo());
    }


    @ApiOperation("变更分页获取数据")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<VAssetsChangeRecords> queryPage(@RequestParam Map<String, Object> params){
        return assetsChangeRecordsBiz.queryAssetsChangePage(params);
    }

    @ApiOperation("变更记录分页获取全部数据")
    @RequestMapping(value = "/queryAssetsChangePageAll",method = RequestMethod.GET)
    public TableResultResponse<VAssetsChangeRecords> queryAssetsChangePageAll(@RequestParam Map<String, Object> params){
        return assetsChangeRecordsBiz.queryAssetsChangePageAll(params);
    }

    @ApiOperation("变更确认分页获取数据")
    @RequestMapping(value = "/queryChangeConfirmPage",method = RequestMethod.GET)
    public TableResultResponse<VAssetsChangeRecords> queryChangeConfirmPage(@RequestParam Map<String, Object> params){
        return assetsChangeRecordsBiz.queryChangeConfirmPage(params);
    }

    @ApiOperation("资产更新编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ObjectRestResponse<Integer> updateAssetsChange(@RequestBody VAssetsChangeRecordsVo record) {
        int num = assetsChangeRecordsBiz.updateAssetsChange(record);
        return new ObjectRestResponse<Integer>().data(num);
    }

    @ApiOperation("资产更新提交")
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsChangeRecordsVo> assetsChangeSave(@RequestBody VAssetsChangeRecordsVo record) {
        assetsChangeRecordsBiz.assetsChangeSave(record);
        return new ObjectRestResponse<VAssetsChangeRecordsVo>().data(new VAssetsChangeRecordsVo());
    }


    @ApiOperation("资产变更确认")
    @RequestMapping(value = "/affirm", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsChangeRecordsVo> assetsChangeAffirm(@RequestBody VAssetsChangeRecordsVo record) {
        assetsChangeRecordsBiz.assetsChangeAffirm(record);
        return new ObjectRestResponse<VAssetsChangeRecordsVo>().data(new VAssetsChangeRecordsVo());
    }

    @ApiOperation("资产变更拒绝")
    @RequestMapping(value = "/reject", method = RequestMethod.PUT)
    public ObjectRestResponse<VAssetsChangeRecordsVo> assetsChangeReject(@RequestBody VAssetsChangeRecordsVo record) {
        assetsChangeRecordsBiz.assetsChangeReject(record);
        return new ObjectRestResponse<VAssetsChangeRecordsVo>().data(new VAssetsChangeRecordsVo());
    }

    @ApiOperation("资产变更删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse<VAssetsChangeRecordsVo> deleteAssetsChange(@PathVariable String  id) {
        assetsChangeRecordsBiz.deleteAssetsChange(id);
        return new ObjectRestResponse<VAssetsChangeRecordsVo>().data(new VAssetsChangeRecordsVo());
    }




}