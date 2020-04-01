package com.sibo.business.biz;

//import com.codingapi.ribbon.loadbalancer.LcnLoadBalancerRule;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.config.StorageProperties;
import com.sibo.business.entity.*;
import com.sibo.business.enums.CertificateType;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.*;
import com.sibo.business.utils.ExportUtils;
import com.sibo.business.utils.ImportExcelUtils;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VBusinessFileVo;
import com.sibo.business.vo.VMeasurementCertificateVo;
import com.sibo.business.vo.VSourceRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 计量证书表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Service
@Transactional
public class VMeasurementCertificateBiz extends BusinessBiz<VMeasurementCertificateMapper,VMeasurementCertificate> {

    private Logger logger = LoggerFactory.getLogger(VMeasurementCertificateBiz.class);

    @Autowired
    private VMeasurementCertificateMapper vMeasurementCertificateMapper;

    @Autowired
    private VAssetsParameterMapper vAssetsParameterMapper;

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Autowired
    private VSourceRecordMapper vSourceRecordMapper;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private VMeteringMethodMapper vMeteringMethodMapper;

    @Autowired
    private VPlannedApprovedProjectsMapper vPlannedApprovedProjectsMapper;

    @Autowired
    private VStandardMapper vStandardMapper;


    @Autowired
    private MergeCore mergeCor;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 新增证书
     * @param vMeasurementCertificateVo
     * @throws Exception
     */
    public VMeasurementCertificate addVMeasurementCertificate(VMeasurementCertificateVo vMeasurementCertificateVo) throws Exception{
        VMeasurementCertificate model = new VMeasurementCertificate();
        BeanUtils.copyProperties(vMeasurementCertificateVo,model);
        model.setId(UUIDUtils.generateUuid());
        vMeasurementCertificateMapper.insertSelective(model);
        VBusinessFile file  = null;
        //外部计量只需要把计量信息保存
        if(CertificateType.EXTERTOR.getCode().equals(vMeasurementCertificateVo.getCertificateType())){
            //保存附件信息
            if(null != vMeasurementCertificateVo.getImageList() && vMeasurementCertificateVo.getImageList().size()>0){
                for (VBusinessFileVo fileVo:vMeasurementCertificateVo.getImageList()) {
                    file  = new VBusinessFile();
                    file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                    file.setBussinessId(model.getId());
                    file.setFilePath(fileVo.getFilePath());
                    file.setId(UUIDUtils.generateUuid());
                    file.setType(FileType.MEASUREMENT_PLAN.getCode());
                    businessFileMapper.insertSelective(file);
                }
            }
            return model;
        }
        //保存附件信息

        if(null != vMeasurementCertificateVo.getImageList() && vMeasurementCertificateVo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vMeasurementCertificateVo.getImageList()) {
                //查询模板类型
                file = businessFileMapper.selectByPrimaryKey(vMeasurementCertificateVo.getCertificateTempleted());

                //解析原始记录
                Map map =  ImportExcelUtils.importExcel(fileVo.getFilePath(),file.getTempletedCode());

                List<VSourceRecord> list = (List<VSourceRecord>)map.get("list");
                if(!CollectionUtils.isEmpty(list)){
                    for (VSourceRecord record:list) {
                        record.setMeasurementCertificateId(model.getId());
                        EntityUtils.setCreateInfo(record);
                        vSourceRecordMapper.insertSelective(record);
                    }

                }else{
                    throw new BusinessRuntimeException("原始记录解析失败,请检查数据是否正确");
                }
                //保存温度，湿度
                VMeasurementCertificate sourceModel = (VMeasurementCertificate)map.get("vMeasurementCertificate");
                if(!StringUtils.isEmpty(sourceModel)){
                    VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(model.getId());
                    vMeasurementCertificate.setHumidity(sourceModel.getHumidity());
                    vMeasurementCertificate.setTemperature(sourceModel.getTemperature());
                    vMeasurementCertificateMapper.updateByPrimaryKeySelective(vMeasurementCertificate);
                }else{
                    throw new BusinessRuntimeException("原始记录解析失败,请检查温度湿度是否正确");
                }


                //保存计量器 有一个或者多个情况
                VStandard vStandard = (VStandard)map.get("vStandard");
                List<VStandard> vStandardList = (List)map.get("vStandardList");
                if(!StringUtils.isEmpty(vStandard)){
                    vStandard.setMeasurementCertificateId(model.getId());
                    EntityUtils.setCreateInfo(vStandard);
                    vStandardMapper.insertSelective(vStandard);
                }else if(!CollectionUtils.isEmpty(vStandardList)){
                    for (VStandard vStandardModel: vStandardList){
                        vStandardModel.setMeasurementCertificateId(model.getId());
                        EntityUtils.setCreateInfo(vStandardModel);
                        vStandardMapper.insertSelective(vStandardModel);
                    }
                }else{
                    throw new BusinessRuntimeException("原始记录解析失败,请检查计量标准器数据是否正确");
                }

                file  = new VBusinessFile();
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                file.setBussinessId(model.getId());
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.MEASUREMENT_PLAN.getCode());
                businessFileMapper.insertSelective(file);

                //更新台账信息表关联字段
                VAssetsParameter vAssetsParameter = vAssetsParameterMapper.selectByPrimaryKey(vMeasurementCertificateVo.getAssetId());
                VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(model.getId());
                this.update(vAssetsParameter,vMeasurementCertificate,model,map);

            }
        }
        return model;
    }

    /**
     * 更新
     * @param vAssetsParameter
     * @param vMeasurementCertificate
     * @param model
     * @param map
     */
    public void update(VAssetsParameter vAssetsParameter,VMeasurementCertificate vMeasurementCertificate,VMeasurementCertificate model,Map map) throws Exception{
        map = this.checkDate(map);
        if(null != vAssetsParameter){
            vAssetsParameter.setCredentialFileId(model.getId());
            String recentMeasurementPlanTime = (String)map.get("recentMeasurementPlanTime");
            if(!StringUtils.isEmpty(recentMeasurementPlanTime)){
                vAssetsParameter.setRecentMeasurementPlanTime(sdf.parse(recentMeasurementPlanTime));
                vMeasurementCertificate.setCertificateStartTime(sdf.parse(recentMeasurementPlanTime));
            }


            String measurementValidity = (String)map.get("measurementValidity");
            if(!StringUtils.isEmpty(recentMeasurementPlanTime)){
                vAssetsParameter.setMeasurementValidity(sdf.parse(measurementValidity));
                vMeasurementCertificate.setCertificateStartEndTime(sdf.parse(measurementValidity));
            }

            vAssetsParameter.setMeasurementResult(model.getDetectionResult());
            vAssetsParameter.setCertificateNumber(model.getCertificateNumber());
            vAssetsParameter.setMeasurementGistName(model.getDetectionPrinciple());
            int cycle = this.verificationPeriod(recentMeasurementPlanTime,measurementValidity);
            vAssetsParameter.setMeasurementCycle(cycle);
            vMeasurementCertificateMapper.updateByPrimaryKeySelective(vMeasurementCertificate);
            vAssetsParameterMapper.updateByPrimaryKeySelective(vAssetsParameter);
        }

        //判断项目id是否不为空，如果不为空则需要更新项目信息
        if(!StringUtils.isEmpty(model.getProjectId())){
            VPlannedApprovedProjects vPlannedApprovedProjects = vPlannedApprovedProjectsMapper.selectByPrimaryKey(model.getProjectId());
            if(null != vPlannedApprovedProjects){
                String recentMeasurementPlanTime = (String)map.get("recentMeasurementPlanTime");
                if(!StringUtils.isEmpty(recentMeasurementPlanTime)){
                    vPlannedApprovedProjects.setLatestCalibrationDate(sdf.parse(recentMeasurementPlanTime));
                }


                String measurementValidity = (String)map.get("measurementValidity");
                if(!StringUtils.isEmpty(measurementValidity)){
                    vPlannedApprovedProjects.setValidityMeasurement(sdf.parse(measurementValidity));
                }

                vPlannedApprovedProjects.setVerificationGistName(model.getDetectionPrinciple());
                vPlannedApprovedProjects.setCalibrationResult(model.getDetectionResult());
                vPlannedApprovedProjects.setCertificateNumber(model.getCertificateNumber());

                //计算计量周期
                int cycle = this.verificationPeriod(recentMeasurementPlanTime,measurementValidity);
                vPlannedApprovedProjects.setVerificationPeriod(cycle);
                vPlannedApprovedProjectsMapper.updateByPrimaryKeySelective(vPlannedApprovedProjects);
            }
        }
    }

    public Integer verificationPeriod(String startCal,String endCal){
        try {
            Date startDate = sdf.parse(startCal);
            Date endDate = sdf.parse(endCal);
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            if (endCalendar.compareTo(startCalendar) < 0) {
                System.out.println("后一时次的日期小于前一时次的日期，请重新输入。");
                return 0;
            }

            int day = endCalendar.get(Calendar.DAY_OF_MONTH)
                    - startCalendar.get(Calendar.DAY_OF_MONTH);
            int month = endCalendar.get(Calendar.MONTH)
                    - startCalendar.get(Calendar.MONTH);
            int year = endCalendar.get(Calendar.YEAR)
                    - startCalendar.get(Calendar.YEAR);

            if (day < 0) {
                month--;
            }

            if (month < 0) {
                month += 12;
                year--;
            }
            System.out.println("两者相差年月为：" + year + "年" + month + "个月");
            return (year*12+month);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public Map checkDate(Map map){
        String recentMeasurementPlanTime = (String)map.get("recentMeasurementPlanTime");
        if(null != recentMeasurementPlanTime){
            logger.info("<<<<<<<<<<解析到的计量日期<<<<<<<<<<<<<<<"+recentMeasurementPlanTime);
            try {
                recentMeasurementPlanTime = recentMeasurementPlanTime.replace("年","-").replace("月","-").replace("日","");
                map.put("recentMeasurementPlanTime",recentMeasurementPlanTime);
            }catch (Exception e){
                throw new BusinessRuntimeException("计量日期格式错误，标准格式为yyyy年mm月dd日");
            }
        }else{
            throw new BusinessRuntimeException("原始记录解析失败,请检查计量日期错误");
        }

        String measurementValidity = (String)map.get("measurementValidity");
        if(null != measurementValidity){
            logger.info("<<<<<<<<<<解析到的有效期至<<<<<<<<<<<<<<<"+measurementValidity);
            try{
                measurementValidity = measurementValidity.replace("年","-").replace("月","-").replace("日","");
                map.put("measurementValidity",measurementValidity);
            }catch (Exception e){
                throw new BusinessRuntimeException("有效期至格式错误，标准格式为yyyy年mm月dd日");
            }
        }else{
            throw new BusinessRuntimeException("原始记录解析失败,请检查有效期至错误");
        }
        return map;
    }

    /**
     * 获取证书信息
     * @param assetId
     * @return
     */
    public VMeasurementCertificateVo getVMeasurementCertificateByAssetId(String assetId,String projectId,String certificateType){
        //台账查询证书
        Example example = new Example(VMeasurementCertificate.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("assetId",assetId);
        if(!StringUtils.isEmpty(projectId) && !projectId.equals("undefined")){
            criteria.andEqualTo("projectId",projectId);
        }else{
            criteria.andEqualTo("projectId","null");
        }

        criteria.andEqualTo("certificateType",certificateType);

        List<VMeasurementCertificate> list = vMeasurementCertificateMapper.selectByExample(example);
        VMeasurementCertificateVo vo = new VMeasurementCertificateVo();
        if(!CollectionUtils.isEmpty(list)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BeanUtils.copyProperties(list.get(0),vo);
//            List<String> timeList = new ArrayList();
//            timeList.add(sdf.format(list.get(0).getCertificateStartTime()));
//            timeList.add(sdf.format(list.get(0).getCertificateStartEndTime()));
//            vo.setCertificateTimeRange(timeList);

            //证书id查询附件信息
            example = new Example(VBusinessFile.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("bussinessId",list.get(0).getId());
            List fileList = businessFileMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(fileList)){
                vo.setImageList(fileList);
            }
            return vo;
        }
        return null;

    }



    public List<VMeasurementCertificateVo> findVMeasurementCertificateByAssetId(String assetIds){
        List<VMeasurementCertificateVo> resultList = new ArrayList<>();
        String[] idArr =  new String[assetIds.length()];
        if(assetIds.length()>1){
            idArr = assetIds.split(",");
        }else{
            idArr[0] = assetIds;
        }
        for (String assetId : idArr) {
            //台账查询证书
            Example example = new Example(VMeasurementCertificate.class);
            Example.Criteria criteria = example.createCriteria();

            criteria.andEqualTo("assetId",assetId);

            List<VMeasurementCertificate> list = vMeasurementCertificateMapper.selectByExample(example);

            if(!CollectionUtils.isEmpty(list)){
                for (VMeasurementCertificate vMeasurementCertificate : list) {
                    VMeasurementCertificateVo vo = new VMeasurementCertificateVo();
                    BeanUtils.copyProperties(vMeasurementCertificate,vo);
                    resultList.add(vo);
                }
            }
        }

        return resultList;

    }

    public void updateVMeasurementCertificate(VMeasurementCertificateVo vMeasurementCertificateVo) throws Exception{
        VMeasurementCertificate entity = vMeasurementCertificateMapper.selectByPrimaryKey(vMeasurementCertificateVo);
        BeanUtils.copyProperties(vMeasurementCertificateVo,entity);
        vMeasurementCertificateMapper.updateByPrimaryKeySelective(entity);
        VBusinessFile file  = null;
        //删除附件信息
        Example example = new Example(VBusinessFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bussinessId",entity.getId());
        List<VBusinessFile> fileList = businessFileMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(fileList)){
            for (VBusinessFile vBusinessFile : fileList) {
                businessFileMapper.delete(vBusinessFile);
            }
        }
        //外部计量只需要把计量信息保存
        if(CertificateType.EXTERTOR.getCode().equals(vMeasurementCertificateVo.getCertificateType())){
            //保存附件信息
            if(null != vMeasurementCertificateVo.getImageList() && vMeasurementCertificateVo.getImageList().size()>0){
                for (VBusinessFileVo fileVo:vMeasurementCertificateVo.getImageList()) {
                    file  = new VBusinessFile();
                    file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                    file.setBussinessId(entity.getId());
                    file.setFilePath(fileVo.getFilePath());
                    file.setId(UUIDUtils.generateUuid());
                    file.setType(FileType.MEASUREMENT_PLAN.getCode());
                    businessFileMapper.insertSelective(file);
                }
            }
        }else{
//删除原始记录信息
            Example example1 = new Example(VSourceRecord.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("measurementCertificateId",entity.getId());
            List<VSourceRecord> VSourceRecordList = vSourceRecordMapper.selectByExample(example1);
            if(!CollectionUtils.isEmpty(VSourceRecordList)){
                for (VSourceRecord vBusinessFile : VSourceRecordList) {
                    vSourceRecordMapper.delete(vBusinessFile);
                }
            }

            //删除计量器信息
            Example example2 = new Example(VSourceRecord.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria1.andEqualTo("measurementCertificateId",entity.getId());
            List<VStandard> vStandardList = vStandardMapper.selectByExample(example2);
            if(!CollectionUtils.isEmpty(vStandardList)){
                for (VStandard vStandard : vStandardList) {
                    vStandardMapper.delete(vStandard);
                }
            }

            //保存附件信息
            if(null != vMeasurementCertificateVo.getImageList() && vMeasurementCertificateVo.getImageList().size()>0){
                for (VBusinessFileVo fileVo:vMeasurementCertificateVo.getImageList()) {
                    //查询模板类型
                    file = businessFileMapper.selectByPrimaryKey(vMeasurementCertificateVo.getCertificateTempleted());
                    //解析原始记录
                    Map map = ImportExcelUtils.importExcel(fileVo.getFilePath(), file.getTempletedCode());
                    List<VSourceRecord> list = (List<VSourceRecord>) map.get("list");
                    if (!CollectionUtils.isEmpty(list)) {
                        for (VSourceRecord record : list) {
                            record.setMeasurementCertificateId(entity.getId());
                            EntityUtils.setCreateInfo(record);
                            vSourceRecordMapper.insertSelective(record);
                        }
                    } else {
                        throw new BusinessRuntimeException("原始记录解析失败,请检查数据是否正确");
                    }

                    //保存温度，湿度
                    VMeasurementCertificate sourceModel = (VMeasurementCertificate) map.get("vMeasurementCertificate");
                    if (!StringUtils.isEmpty(sourceModel)) {
                        VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(entity.getId());
                        vMeasurementCertificate.setHumidity(sourceModel.getHumidity());
                        vMeasurementCertificate.setTemperature(sourceModel.getTemperature());
                        vMeasurementCertificateMapper.updateByPrimaryKeySelective(vMeasurementCertificate);
                    } else {
                        throw new BusinessRuntimeException("原始记录解析失败,请检查温度湿度是否正确");
                    }

                    VStandard vStandard = (VStandard) map.get("vStandard");
                    List<VStandard> vStandardLists = (List) map.get("vStandardList");
                    if (!StringUtils.isEmpty(vStandard)) {
                        vStandard.setMeasurementCertificateId(entity.getId());
                        vStandardMapper.insertSelective(vStandard);
                    } else if (!CollectionUtils.isEmpty(vStandardLists)) {
                        for (VStandard vStandardModel : vStandardLists) {
                            vStandardModel.setMeasurementCertificateId(entity.getId());
                            EntityUtils.setCreateInfo(vStandardModel);
                            vStandardMapper.insertSelective(vStandardModel);
                        }
                    } else {
                        throw new BusinessRuntimeException("原始记录解析失败,请检查计量标准器是否正确");
                    }

                    file = new VBusinessFile();
                    file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/") + 1));
                    file.setBussinessId(entity.getId());
                    file.setFilePath(fileVo.getFilePath());
                    file.setType(FileType.MEASUREMENT_PLAN.getCode());
                    file.setId(UUIDUtils.generateUuid());
                    businessFileMapper.insertSelective(file);

                    //更新台账信息表关联字段
                    VAssetsParameter vAssetsParameter = vAssetsParameterMapper.selectByPrimaryKey(vMeasurementCertificateVo.getAssetId());
                    VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(entity.getId());
                    this.update(vAssetsParameter, vMeasurementCertificate, entity, map);
                }
        }

        }

    }

    /**
     * 生成证书
     */
    public VBusinessFile createMeasurement(String id) throws Exception{
        /**
         * 1、获取证书模板
         * 2、查询证书所需要的数据
         *   2.1、查询台账信息表
         *   2.2、查询记录项目表
         *   2.3、查询原始记录表
         * 3、导出下载证书
         */
        Map<String, Object> datas = new HashMap<>();
        //查询证书
        VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(id);
        if(null != vMeasurementCertificate) {
            List<VMeasurementCertificate> list = new ArrayList();
            list.add(vMeasurementCertificate);
            mergeCor.mergeResult(VMeasurementCertificate.class, list);
            datas.put("facilityDesignatedArea", list.get(0).getFacilityDesignatedArea());//地  点

            datas.put("detectionResult", list.get(0).getDetectionResult());//检定结论
            datas.put("temperature", vMeasurementCertificate.getTemperature());//温度
            datas.put("humidity", vMeasurementCertificate.getHumidity());//湿度
            datas.put("remark", vMeasurementCertificate.getRemark());//备注
            //查询检测方法
            VMeteringMethod vMeteringMethod = vMeteringMethodMapper.selectByPrimaryKey(vMeasurementCertificate.getDetectionPrinciple());
            if (null != vMeteringMethod) {
                datas.put("methodName", vMeteringMethod.getMethodName());//检定依据
            }

            //查询计量标准器
            Example example = new Example(VStandard.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("measurementCertificateId", vMeasurementCertificate.getId());
            List<VStandard> vStandardList = vStandardMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(vStandardList)) {
                datas.put("list", vStandardList);
            } else {
                vStandardList = new ArrayList();
                datas.put("list", vStandardList);
                logger.info("<<<<<<<<<<计量标准器为空<<<<<<<<<<<<<<<");

            }

            //查询台账信息
            VAssetsParameter vAssetsParameter = vAssetsParameterMapper.selectByPrimaryKey(vMeasurementCertificate.getAssetId());
            if (null != vAssetsParameter) {
                datas.put("certificateNumber", vAssetsParameter.getCertificateNumber());//证书编号
                datas.put("testUnit", vAssetsParameter.getMeasurementUnit());//委托单位
                datas.put("devicename", vAssetsParameter.getDevicename());//计量器具名称
                datas.put("unitType", vAssetsParameter.getUnitType());//型 号 / 规 格
                datas.put("equipmentNumber", vAssetsParameter.getEquipmentNumber());//出厂编号
                datas.put("manufacturer", vAssetsParameter.getManufacturer());//制造单位

                datas.put("recentMeasurementPlanTime", sdf1.format(vAssetsParameter.getRecentMeasurementPlanTime()));//检 定 日 期
                datas.put("measurementValidity", sdf1.format(vAssetsParameter.getMeasurementValidity()));//有效期至

                Map map = null;
                //查询原始记录
                if (null != vMeasurementCertificate) {
                    Example example1 = new Example(VSourceRecord.class);
                    Example.Criteria criteria1 = example1.createCriteria();
                    criteria1.andEqualTo("measurementCertificateId", vMeasurementCertificate.getId());
                    List<VSourceRecord> sourceList = vSourceRecordMapper.selectByExample(example1);
                    if (!CollectionUtils.isEmpty(sourceList)) {
                        VBusinessFile files = businessFileMapper.selectByPrimaryKey(vMeasurementCertificate.getCertificateTempleted());
                        if (null == files) {
                            logger.info("》》》》》》》》查询模板code为空》》》》》》》");
                            throw new BusinessRuntimeException("查询模板code为空");
                        }
                        datas = ImportExcelUtils.exportExcel(sourceList, files.getTempletedCode(), datas);
                    } else {
                        sourceList = new ArrayList();
                        datas.put("sourceList", sourceList);
                    }
                } else {
                    List<VSourceRecord> sourceList = new ArrayList();
                    datas.put("sourceList", sourceList);
                }

                String expFile = storageProperties.getDownPath();
                VBusinessFile businessFile = new VBusinessFile();
                if (!StringUtils.isEmpty(datas.get("filePath"))) {
                    String filePath = storageProperties.getTempletePath() + datas.get("filePath");
                    String path = ExportUtils.export(filePath, expFile, datas);
                    logger.info("证书生成成功：》》》》》》》》》》》》》》》" + filePath);
                    businessFile.setFilePath(path);
                    businessFile.setFileName(vAssetsParameter.getDevicename() + "检测报告");
                } else {
                    logger.info("》》》》》》》》模板未找到》》》》》》》");
                    throw new BusinessRuntimeException("模板未找到");
                }

                return businessFile;
            }

        }
        return null;
    }

    /**
     * 台账id查询合格证数据
     * @param id
     * @return
     */
    public List<VAssetsParameterVo> findCertificateData(String ids){
        List<VAssetsParameterVo> resultList = new ArrayList<>();
        String[] idArr =  new String[ids.length()];
        if(ids.length()>1){
            idArr = ids.split(",");
        }else{
            idArr[0] = ids;
        }
        for (String id : idArr) {
            VAssetsParameter vAssetsParameter = vAssetsParameterMapper.selectByPrimaryKey(id);
            if(null != vAssetsParameter){
                try {
                    mergeCor.mergeOne(VAssetsParameter.class,vAssetsParameter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                VAssetsParameterVo vo = new VAssetsParameterVo();
                vo.setTestUnit(vAssetsParameter.getMeasurementUnit());
                vo.setCertificateNumber(vAssetsParameter.getEquipmentNumber());
                vo.setMeasurementValidity(vAssetsParameter.getMeasurementValidity());
                vo.setRecentMeasurementPlanTime(vAssetsParameter.getRecentMeasurementPlanTime());
                resultList.add(vo);

//                Example example = new Example(VMeasurementCertificate.class);
//                Example.Criteria criteria = example.createCriteria();
//                criteria.andEqualTo("assetId",id);
//                List<VMeasurementCertificate> list = vMeasurementCertificateMapper.selectByExample(example);
//                VAssetsParameterVo vo = null;
//                for (VMeasurementCertificate vMeasurementCertificate : list) {
//                    vo = new VAssetsParameterVo();
//                    vo.setTestUnit(vAssetsParameter.getMeasurementUnit());
//                    vo.setCertificateNumber(vMeasurementCertificate.getCertificateNumber());
//                    if(null != vMeasurementCertificate.getCertificateStartTime()){
//                        vo.setCertificateStartTime(sdf1.format(vMeasurementCertificate.getCertificateStartTime()));
//                    }
//                    if(null != vMeasurementCertificate.getCertificateStartEndTime()){
//                        vo.setCertificateStartEndTime(sdf1.format(vMeasurementCertificate.getCertificateStartEndTime()));
//                    }
//                    resultList.add(vo);
//                }

            }
        }

        return resultList;
    }

    /**
     * 证书id查询合格证数据
     * @param ids
     * @return
     */
    public List<VAssetsParameterVo> findCertificateDataByCertificateId(String ids){
        List<VAssetsParameterVo> resultList = new ArrayList<>();
        String[] idArr =  new String[ids.length()];
        if(ids.length()>1){
            idArr = ids.split(",");
        }else{
            idArr[0] = ids;
        }
        for (String id : idArr) {
            //查询证书
            VMeasurementCertificate vMeasurementCertificate = vMeasurementCertificateMapper.selectByPrimaryKey(id);
            VAssetsParameterVo vo = null;
            if (null != vMeasurementCertificate) {
                vo = new VAssetsParameterVo();
                //查询台账
                VAssetsParameter vAssetsParameter = vAssetsParameterMapper.selectByPrimaryKey(vMeasurementCertificate.getAssetId());
                if(null != vAssetsParameter){
                    vo.setTestUnit(vAssetsParameter.getMeasurementUnit());
                }
                vo.setCertificateNumber(vAssetsParameter.getEquipmentNumber());
                if (null != vMeasurementCertificate.getCertificateStartTime()) {
                    vo.setCertificateStartTime(sdf1.format(vMeasurementCertificate.getCertificateStartTime()));
                }
                if (null != vMeasurementCertificate.getCertificateStartEndTime()) {
                    vo.setCertificateStartEndTime(sdf1.format(vMeasurementCertificate.getCertificateStartEndTime()));
                }
                resultList.add(vo);
            }
        }
        return resultList;
    }

    /**
     * 查询最大编号
     * @return
     */
    public String createCertificateNumber(){
        StringBuilder resultNum = new StringBuilder();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String date = sdf.format(d);

        synchronized (this){
            String num = vMeasurementCertificateMapper.getCertificateMaxNum();
            if( null == num || Integer.valueOf(num) == 0){
                resultNum = resultNum.append(date).append("001");
            }else{
                resultNum =  resultNum.append(date).append((Integer.valueOf(num)+1)+"");
            }
        }
        //201806
        if(resultNum.length() == 7){
            resultNum = resultNum.insert(6,"00");
        }else if(resultNum.length() ==8){
            resultNum = resultNum.insert(6,"0");
        }

        return resultNum.toString();
    }
}