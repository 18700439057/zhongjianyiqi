package com.sibo.business.biz;

import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.enums.FileType;
import com.sibo.business.enums.MeasueementVerificationStatu;
import com.sibo.business.enums.MetrologicalReviewType;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.utils.ExportExcelUtil;
import com.sibo.business.vo.VAssetsParamenterVerificationExportVo;
import com.sibo.business.vo.VAssetsParameterMeteringExportVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMeasurementVerificationPlan;
import com.sibo.business.mapper.VMeasurementVerificationPlanMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 计量/核查计划表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Service
@Transactional
@Configuration
public class VMeasurementVerificationPlanBiz extends BusinessBiz<VMeasurementVerificationPlanMapper,VMeasurementVerificationPlan> {

    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    @Autowired
    private VMeasurementVerificationPlanMapper vMeasurementVerificationPlanMapper;

    @Autowired
    private VBusinessFileMapper vBusinessFileMapper;

    @Autowired
    private MergeCore mergeCor;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//    @Autowired
//    private StorageProperties storageProperties;

    @Value("${filePath}")
    private String filePath;

    /**
     * 1.查询出当年的全部需要计量的信息向计量计划表添加数据
     * 2，按照月查出每月数据并生成excel
     * 3.使用当年全部数据生成一个全年excel
     */
    public void  createMeasurementpaln() throws  BusinessRuntimeException{

        //获取当年年份
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        //删除已生成附件
        //获取当年年份
        Example example1 = new Example(VBusinessFile.class);
        example1.createCriteria().andEqualTo("type",FileType.YEAR_PLAY.getCode()).andCondition("  TO_NUMBER (TO_CHAR(CRT_TIME, 'YYYY')) ='" +year+"'");
        vBusinessFileMapper.deleteByExample(example1);

        //根据计量有效期查询当年数据
        RowBounds rb = new RowBounds();
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("year",year);
        paramMmap.put("measurementCheckClass","1");
        paramMmap.put("notmeasueementStatus","tzjl");
        paramMmap.put("notmeasueementStatus1","syqjl");
        paramMmap.put("facitityStatus","0");
        List<VAssetsParameter> yearAllList =  assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);

        //和计量计划表做数据关联
        if(!CollectionUtils.isEmpty(yearAllList)){
            VMeasurementVerificationPlan vMeasurementVerificationPlan = null;
            VAssetsParameterMeteringExportVo vAssetsParameterExport = null;
            ExportExcelUtil<VAssetsParameterMeteringExportVo> util = new ExportExcelUtil<VAssetsParameterMeteringExportVo>();
            List<VAssetsParameterMeteringExportVo> exportExcelList = new ArrayList();
            // 删除当年生成的计划
            Example example = new Example(VMeasurementVerificationPlan.class);
            example.createCriteria().andCondition("  TO_NUMBER (TO_CHAR(CRT_TIME, 'YYYY')) ='" +year+"'");
            vMeasurementVerificationPlanMapper.deleteByExample(example);

            for (VAssetsParameter vAssetsParameter:yearAllList ) {
                //合并字典
                    try {
                        mergeCor.mergeOne(VAssetsParameter.class,vAssetsParameter);
                    }catch (Exception e) {
                        throw new BusinessRuntimeException(e.getMessage());
                    }

                vMeasurementVerificationPlan = new VMeasurementVerificationPlan();
                vAssetsParameterExport = new VAssetsParameterMeteringExportVo();
                //复制数据
                org.springframework.beans.BeanUtils.copyProperties(vAssetsParameter,vAssetsParameterExport);
                if(null != vAssetsParameter.getMeasurementValidity()){
                    vAssetsParameterExport.setMeasurementValidity(sdf.format(vAssetsParameter.getMeasurementValidity()));
                }

                if(null != vAssetsParameter.getRecentMeasurementPlanTime()){
                    vAssetsParameterExport.setRecentMeasurementPlanTime(sdf.format(vAssetsParameter.getRecentMeasurementPlanTime()));
                }

                exportExcelList.add(vAssetsParameterExport);

                //计量表增加数据
                vMeasurementVerificationPlan.setId(UUIDUtils.generateUuid());
                vMeasurementVerificationPlan.setAssetId(vAssetsParameter.getId());
                vMeasurementVerificationPlan.setMeasurementValidity(vAssetsParameter.getMeasurementValidity());
                vMeasurementVerificationPlan.setMeasueementVerificationStatu(MeasueementVerificationStatu.UNFINISHED.getCode());
                vMeasurementVerificationPlan.setType(MetrologicalReviewType.MEASURE.getCode());
                EntityUtils.setCreateInfo(vMeasurementVerificationPlan);
                vMeasurementVerificationPlanMapper.insertSelective(vMeasurementVerificationPlan);
            }

            //生成全年excel
            String[] columnNames = {"设备名称" ,"范围","型号","编号","精度","计量周期","生产厂家","检定单位","计划时间","检定时间"};
            String fileName = year+"计量计划.xls";
            try {
                util.exportExcel("计量计划", columnNames, exportExcelList, new FileOutputStream(filePath+fileName), ExportExcelUtil.EXCEL_FILE_2003);
            } catch (FileNotFoundException e) {
                throw new BusinessRuntimeException(e.getMessage());
            }

            //保存全年计划文件路径
            saveFilePath(fileName,filePath+fileName,FileType.YEAR_PLAY.getCode());
            //按照月份查询每月数据
            for (int i=1;i<=12;i++){
                paramMmap = new HashMap<>();
                if(i<10){
                    paramMmap.put("month",year+"0"+i);
                }else{
                    paramMmap.put("month",year+""+i);
                }

                List<VAssetsParameter> monthList =  assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);
                exportExcelList = new ArrayList();
                if(!CollectionUtils.isEmpty(monthList)){
                    for (VAssetsParameter assetsParameter: monthList){
                        //合并字典
                        try {
                            mergeCor.mergeOne(VAssetsParameter.class,assetsParameter);
                        } catch (Exception e) {
                            throw new BusinessRuntimeException(e.getMessage());
                        }
                        vAssetsParameterExport = new VAssetsParameterMeteringExportVo();
                        org.springframework.beans.BeanUtils.copyProperties(assetsParameter,vAssetsParameterExport);
                        if(null != assetsParameter.getMeasurementValidity()){
                            vAssetsParameterExport.setMeasurementValidity(sdf.format(assetsParameter.getMeasurementValidity()));
                        }

                        if(null != assetsParameter.getRecentMeasurementPlanTime()){
                            vAssetsParameterExport.setRecentMeasurementPlanTime(sdf.format(assetsParameter.getRecentMeasurementPlanTime()));
                        }

                        exportExcelList.add(vAssetsParameterExport);
                    }
                    fileName = year+"年"+i+"月计量计划.xls";
                    try {
                        util.exportExcel("计量计划", columnNames, exportExcelList, new FileOutputStream(filePath+fileName), ExportExcelUtil.EXCEL_FILE_2003);
                    } catch (FileNotFoundException e) {
                        throw new BusinessRuntimeException(e.getMessage());
                    }
                    //保存月度计划文件路径
                    saveFilePath(fileName,filePath+fileName,FileType.YEAR_PLAY.getCode());
                }

            }
        }

    }

    /**
     * 核查计划
     * @throws Exception
     */
    public void  createInspectPaln() throws BusinessRuntimeException{



        //获取当年年份
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);

        //删除已生成附件
        //获取当年年份
        Example example1 = new Example(VBusinessFile.class);
        example1.createCriteria().andEqualTo("type",FileType.YEAR_CHECK.getCode()).andCondition("  TO_NUMBER (TO_CHAR(CRT_TIME, 'YYYY')) ='" +year+"'");
        vBusinessFileMapper.deleteByExample(example1);

        //根据核查日期至查询当年数据
        RowBounds rb = new RowBounds();
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("periodDateYear",year);
        paramMmap.put("isReviewed","1");
        List<VAssetsParameter> yearAllList =  assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);

        //和计量计划表做数据关联
        if(!CollectionUtils.isEmpty(yearAllList)){
            VMeasurementVerificationPlan vMeasurementVerificationPlan = null;
            VAssetsParamenterVerificationExportVo vAssetsParameterExport = null;
            ExportExcelUtil<VAssetsParamenterVerificationExportVo> util = new ExportExcelUtil<VAssetsParamenterVerificationExportVo>();
            List<VAssetsParamenterVerificationExportVo> exportExcelList = new ArrayList();
            for (VAssetsParameter vAssetsParameter:yearAllList ) {
                // 删除当年生成的计划
                Example example = new Example(VMeasurementVerificationPlan.class);
                example.createCriteria().andEqualTo("assetId",vAssetsParameter.getId());
                vMeasurementVerificationPlanMapper.deleteByExample(example);

                //合并字典
                try {
                    mergeCor.mergeOne(VAssetsParameter.class,vAssetsParameter);
                }catch (Exception e) {
                    throw new BusinessRuntimeException(e.getMessage());
                }

                vMeasurementVerificationPlan = new VMeasurementVerificationPlan();
                vAssetsParameterExport = new VAssetsParamenterVerificationExportVo();
                org.springframework.beans.BeanUtils.copyProperties(vAssetsParameter,vAssetsParameterExport);

                if(null != vAssetsParameter.getPeriodDateStop()){
                    vAssetsParameterExport.setPeriodDateStop(sdf.format(vAssetsParameter.getPeriodDateStop()));
                }
                exportExcelList.add(vAssetsParameterExport);

                vMeasurementVerificationPlan.setId(UUIDUtils.generateUuid());
                vMeasurementVerificationPlan.setAssetId(vAssetsParameter.getId());
                vMeasurementVerificationPlan.setMeasurementValidity(vAssetsParameter.getPeriodDateStop());
                vMeasurementVerificationPlan.setMeasueementVerificationStatu(MeasueementVerificationStatu.UNFINISHED.getCode());
                vMeasurementVerificationPlan.setType(MetrologicalReviewType.INSPECT.getCode());
                EntityUtils.setCreateInfo(vMeasurementVerificationPlan);
                vMeasurementVerificationPlanMapper.insertSelective(vMeasurementVerificationPlan);
            }

            //生成全年excel
            String[] columnNames = { "仪器设备名称" ,"型号","编号","计划日期","责任部门","核查日期","结果"};
            String fileName = year+"核查计划.xls";
            try {
                util.exportExcel("核查计划", columnNames, exportExcelList, new FileOutputStream(filePath+fileName), ExportExcelUtil.EXCEL_FILE_2003);
            } catch (FileNotFoundException e) {
                throw new BusinessRuntimeException(e.getMessage());
            }

            //保存全年计划文件路径
            saveFilePath(fileName,filePath+fileName,FileType.YEAR_CHECK.getCode());
            //按照月份查询每月数据
            for (int i=1;i<=12;i++){
                paramMmap = new HashMap<>();
                if(i<10){
                    paramMmap.put("periodDateMonth",year+"0"+i);
                }else{
                    paramMmap.put("periodDateMonth",year+""+i);
                }
                List<VAssetsParameter> monthList =  assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);
                exportExcelList = new ArrayList();
                if(!CollectionUtils.isEmpty(monthList)){
                    for (VAssetsParameter assetsParameter: monthList){
                        //合并字典
                        try {
                            mergeCor.mergeOne(VAssetsParameter.class,assetsParameter);
                        } catch (Exception e) {
                            throw new BusinessRuntimeException(e.getMessage());
                        }

                        vAssetsParameterExport = new VAssetsParamenterVerificationExportVo();
                        org.springframework.beans.BeanUtils.copyProperties(assetsParameter,vAssetsParameterExport);
                        if(null != assetsParameter.getPeriodDateStop()){
                            vAssetsParameterExport.setPeriodDateStop(sdf.format(assetsParameter.getPeriodDateStop()));
                        }
                        exportExcelList.add(vAssetsParameterExport);
                    }
                    fileName = year+"年"+i+"月核查计划.xls";
                    try {
                        util.exportExcel("核查计划", columnNames, exportExcelList, new FileOutputStream(filePath+fileName), ExportExcelUtil.EXCEL_FILE_2003);
                    } catch (FileNotFoundException e) {
                        throw new BusinessRuntimeException(e.getMessage());
                    }
                    //保存月度计划文件路径
                    saveFilePath(fileName,filePath+fileName,FileType.YEAR_CHECK.getCode());
                }

            }
        }

    }

    public void saveFilePath(String fileName,String filePath,String type){


        VBusinessFile vBusinessFile = new VBusinessFile();
        vBusinessFile.setId(UUIDUtils.generateUuid());
        vBusinessFile.setBussinessId("/");
        vBusinessFile.setFileName(fileName);
        vBusinessFile.setFilePath(filePath);
        vBusinessFile.setType(type);
        EntityUtils.setCreateInfo(vBusinessFile);
        vBusinessFileMapper.insertSelective(vBusinessFile);
    }

    /**
     * 首页统计年度数据
     */
    public List<VMeasurementVerificationPlan>  queryYearData(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return vMeasurementVerificationPlanMapper.queryDashboardVerificationPlanCount(year+"");
        // 1.按照年度+月度日期查询出每个月的计量数量(包括每个月的计量、核查计划和已完成的数量)
        // 2.区分计量和核查
        // 3.处理计量数据，按照月份分类 获取每个月的计划数量
        // 4.统计每个月计划数据中已完成的数量


        // 5.处理核查数据，，按照月份分类 获取每个月的计划数量
        // 4.统计每个月计划数据中已完成的数量

    }
 }