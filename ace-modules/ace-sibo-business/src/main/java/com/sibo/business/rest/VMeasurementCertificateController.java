package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMeasurementCertificateBiz;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.entity.VMeasurementCertificate;
import com.sibo.business.utils.ExportUtils;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VMeasurementCertificateVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计量证书表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@RestController
@RequestMapping("vMeasurementCertificate")
@CheckClientToken
@CheckUserToken
public class VMeasurementCertificateController extends BaseController<VMeasurementCertificateBiz,VMeasurementCertificate,String> {

    @Autowired
    private VMeasurementCertificateBiz vMeasurementCertificateBiz;

    @RequestMapping(value = "/addVMeasurementCertificate",method = RequestMethod.POST)
    public ObjectRestResponse<VMeasurementCertificate> addVMeasurementCertificate (@RequestBody VMeasurementCertificateVo vo) throws Exception{
        VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateBiz.addVMeasurementCertificate(vo);
        return new ObjectRestResponse<VMeasurementCertificate>().data(vMeasurementCertificate);
    }

    @RequestMapping(value = "/getVMeasurementCertificateByAddetId/{assetId}/{projectId}/{certificateType}",method = RequestMethod.GET)
    public ObjectRestResponse<VMeasurementCertificateVo> getVMeasurementCertificateByAddetId (@PathVariable String assetId,@PathVariable String projectId,@PathVariable String certificateType){
        VMeasurementCertificateVo vo =  vMeasurementCertificateBiz.getVMeasurementCertificateByAssetId(assetId,projectId,certificateType);
        return new ObjectRestResponse<VMeasurementCertificateVo>().data(vo);
    }

    @RequestMapping(value = "/findVMeasurementCertificateByAssetId/{assetIds}",method = RequestMethod.GET)
    public ObjectRestResponse<List<VMeasurementCertificateVo>> findVMeasurementCertificateByAssetId (@PathVariable String assetIds){
        List<VMeasurementCertificateVo> vo =  vMeasurementCertificateBiz.findVMeasurementCertificateByAssetId(assetIds);
        return new ObjectRestResponse<List<VMeasurementCertificateVo>>().data(vo);
    }

    @RequestMapping(value = "/updateVMeasurementCertificate",method = RequestMethod.PUT)
    public ObjectRestResponse<VMeasurementCertificateVo> updateVMeasurementCertificate (@RequestBody VMeasurementCertificateVo vo) throws Exception{
        vMeasurementCertificateBiz.updateVMeasurementCertificate(vo);
        return new ObjectRestResponse<VMeasurementCertificateVo>().data(vo);
    }

    /**
     * 生成证书
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createMeasurement/{id}",method = RequestMethod.GET)
    public ObjectRestResponse<VBusinessFile> createMeasurement (@PathVariable String id,HttpServletRequest request,
                                                                            HttpServletResponse resp) throws Exception{
        VBusinessFile businessFile = vMeasurementCertificateBiz.createMeasurement(id);
//        if(null != businessFile){
//            ExportUtils.down(businessFile.getFilePath(),businessFile.getFileName(),request,resp);
//        }
        return new ObjectRestResponse<VBusinessFile>().data(businessFile);
    }

    /**
     * 台账id生成合格证书
     * @param id
     * @return
     */
    @RequestMapping(value = "/findCertificateData/{ids}",method = RequestMethod.GET)
    public ObjectRestResponse<List<VAssetsParameterVo>> findCertificateData (@PathVariable String ids){
        List<VAssetsParameterVo> vo =  vMeasurementCertificateBiz.findCertificateData(ids);
        return new ObjectRestResponse<List<VAssetsParameterVo>>().data(vo);
    }

    /**
     *证书id查询合格证数据
     * @param ids
     * @return
     */
    @RequestMapping(value = "/findCertificateDataByCertificateId/{ids}",method = RequestMethod.GET)
    public ObjectRestResponse<List<VAssetsParameterVo>> findCertificateDataByCertificateId (@PathVariable String ids){
        List<VAssetsParameterVo> vo =  vMeasurementCertificateBiz.findCertificateDataByCertificateId(ids);
        return new ObjectRestResponse<List<VAssetsParameterVo>>().data(vo);
    }

    /**
     * 获取证书编号
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ObjectRestResponse<String> createCertificateNumber (){
       String vo =  vMeasurementCertificateBiz.createCertificateNumber();
        return new ObjectRestResponse<String>().data(vo);
    }







}