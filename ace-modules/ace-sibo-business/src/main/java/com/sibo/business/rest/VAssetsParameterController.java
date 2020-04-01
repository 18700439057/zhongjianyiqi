package com.sibo.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import com.sibo.business.biz.VAssetsParameterBiz;
import com.sibo.business.entity.VAssetsAccessory;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsUpdateRecords;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.utils.ExportUtils;
import com.sibo.business.vo.LimsVAssetsParameterVo;
import com.sibo.business.vo.VAssetsParameterVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("vAssetsParameter")
@CheckClientToken
@CheckUserToken
public class VAssetsParameterController extends BaseController<VAssetsParameterBiz,VAssetsParameter,String> {
    private Logger logger = LoggerFactory.getLogger(VAssetsParameterController.class);
    @Autowired
    private VAssetsParameterBiz assetsParameterBiz;

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> pageList(@RequestParam Map<String, Object> params){
        //查询列表数据
        return assetsParameterBiz.queryAssetsParameterPage(params);
    }

    @ApiOperation("待分配分页获取数据")
    @RequestMapping(value = "/equipmentPage",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> equipmentPage(@RequestParam Map<String, Object> params){
        //查询列表数据
        return assetsParameterBiz.equipmentPage(params);
    }

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/pageListAll",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameterVo> getAll(@RequestParam Map<String, Object> params){
        //查询列表数据
        return assetsParameterBiz.queryVAssetsParameterMapperAll(params);
    }

    /**
     * 创建编号
     * @param facitityCategory
     * @param measurementCheckClass
     * @return
     */
    @RequestMapping(value = "/createNum",method = RequestMethod.GET)
    public ObjectRestResponse<String> createNum(@RequestParam(defaultValue = "0") String facitityCategory,
                                                @RequestParam(defaultValue = "0") String measurementCheckClass,
                                                @RequestParam(defaultValue = "0") String assetsClass,
                                                @RequestParam(defaultValue = "0") String assetType,
                                                @RequestParam(defaultValue = "1970-01-01") String useTime,
                                                @RequestParam(defaultValue = "0") String unifiedCode){

        String str = assetsParameterBiz.getMaxNum(assetType,facitityCategory, measurementCheckClass, assetsClass,useTime,unifiedCode);
        return new ObjectRestResponse<String>().data(str);
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/addAssets",method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsParameterVo> addAssets(@RequestBody VAssetsParameterVo vo) {
        assetsParameterBiz.add(vo);
        return  new ObjectRestResponse<VAssetsParameterVo>().data(vo);
    }

    @ApiOperation("资产追加新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsParameterVo> addSup(@RequestBody VAssetsParameterVo record) {
        String id = assetsParameterBiz.insertAdditionalAsset(record);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation("更新单个对象")
    public ObjectRestResponse<VAssetsParameterVo> update(@RequestBody VAssetsParameterVo vo){
        VAssetsParameterVo entity =  assetsParameterBiz.updateAdditionalAsset(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(entity);
    }

    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查询单个对象")
    public ObjectRestResponse<VAssetsParameterVo> getById(@PathVariable String id) {
        VAssetsParameterVo vo = assetsParameterBiz.customSelectById(id);
        return new ObjectRestResponse<VAssetsParameterVo>().data(vo);
    }

    @RequestMapping(value = "/getFileById/{id}",method = RequestMethod.GET)
    @ApiOperation("查询单个对象的证书附件")
    public ObjectRestResponse<List> getFileById(@PathVariable String id) {
        List list = assetsParameterBiz.queryFileByBuisnessId(id);
        return new ObjectRestResponse<List>().data(list);
    }

    @RequestMapping(value = "/downDoc",method = RequestMethod.GET)
    public ObjectRestResponse<String> downDoc(@RequestParam(defaultValue = "0") String type,
                                                     @RequestParam(defaultValue = "0") String id,
                                                     HttpServletRequest request,
                                                     HttpServletResponse resp)  throws Exception {
        String path = assetsParameterBiz.exportExcel(type,id);
        //String fileName = "设备台账卡";
        //ExportUtils.down(path,fileName,request,resp);
        //return new ObjectRestResponse<String>().data(path);
        return new ObjectRestResponse<String>().data(path);
    }

    @ApiOperation("计量计划分页获取数据")
    @RequestMapping(value = "/pagePlayList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> pagePlayList(@RequestParam Map<String, Object> params){
        //查询列表数据
        return assetsParameterBiz.pagePlayList(params);
    }

    @ApiOperation("核查计划分页获取数据")
    @RequestMapping(value = "/pageInspectList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> pageInspectList(@RequestParam Map<String, Object> params){
        //查询列表数据
        return assetsParameterBiz.pageInspectList(params);
    }

    @ApiOperation("修改记录获取数据")
    @RequestMapping(value = "/pageUpdateRecordList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsUpdateRecords> pageUpdateRecordList(@RequestParam String assetsId){
        //查询列表数据
        return assetsParameterBiz.pageUpdateRecords(assetsId);
    }

    @ApiOperation("导出指定字段")
    @RequestMapping(value = "/exportAsset", method = RequestMethod.GET)
    public ObjectRestResponse<String> addSup(@RequestParam Map<String, Object> params,HttpServletResponse resp) throws Exception{
        String path = assetsParameterBiz.exportAssetsParameter(params,resp);
        logger.info("controlle返回路径："+path);
        return new ObjectRestResponse<String>().data(path);
    }


    @RequestMapping(value = "/copy/{id}",method = RequestMethod.GET)
    @ApiOperation("复制个对象")
    public ObjectRestResponse<VAssetsParameterVo> copy(@PathVariable String id){
        assetsParameterBiz.copyAssets(id);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo(id));
    }

    @RequestMapping(value = "/getAssets",method = RequestMethod.GET)
    @ApiOperation("lims接口调用")
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<List<LimsVAssetsParameterVo>> getAssets(@RequestParam(defaultValue = "")  String devicename, @RequestParam(defaultValue = "")  String unitType, @RequestParam(defaultValue = "")  String unifiedCode, @RequestParam(defaultValue = "")  String equipmentNumber){
        logger.info("lims请求接口开始，参数为：【devicename="+devicename+"】【unitType="+unitType+"】【unifiedCode="+unifiedCode+"】【equipmentNumber="+equipmentNumber+"】");
        List<LimsVAssetsParameterVo> list = assetsParameterBiz.limsQuery(devicename,unitType,unifiedCode,equipmentNumber);
        logger.info("lims请求接口结束，返回的数据为："+list.toString());
        return new ObjectRestResponse<List<LimsVAssetsParameterVo>>().data(list);
    }

    @RequestMapping(value = "/getByUnifiedCode/{unifiedCode}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查询单个对象")
    public ObjectRestResponse<VAssetsParameterVo> getVAssetsParameterByUnifiedCode(@PathVariable String unifiedCode) {
        VAssetsParameterVo vo = assetsParameterBiz.getVAssetsParameterByUnifiedCode(unifiedCode);
        return new ObjectRestResponse<VAssetsParameterVo>().data(vo);
    }



}