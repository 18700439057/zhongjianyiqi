package com.sibo.business.biz;

import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;

import com.sibo.business.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.annonation.QueryParmentType;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.config.StorageProperties;
import com.sibo.business.enums.AssetsStatus;
import com.sibo.business.enums.EquipmentSubordinate;
import com.sibo.business.enums.FileType;
import com.sibo.business.enums.MeasueementVerificationStatu;
import com.sibo.business.enums.MeasurementCheckClass;
import com.sibo.business.enums.MetrologicalReviewType;
import com.sibo.business.feign.DeptFeign;
import com.sibo.business.feign.DictFeign;
import com.sibo.business.feign.UserFeign;
import com.sibo.business.mapper.AppSystemMessageMapper;
import com.sibo.business.mapper.VAssetsAccessoryMapper;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VAssetsReceiveMainMapper;
import com.sibo.business.mapper.VAssetsReceiveMapper;
import com.sibo.business.mapper.VAssetsUpdateRecordsMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.mapper.VMeasurementCertificateMapper;
import com.sibo.business.mapper.VMeasurementVerificationPlanMapper;
import com.sibo.business.utils.Constant;
import com.sibo.business.utils.ExportExcelUtil;
import com.sibo.business.utils.ExportUtils;
import com.sibo.business.utils.MessagePush;
import com.sibo.business.vo.DictValue;
import com.sibo.business.vo.LimsVAssetsParameterVo;
import com.sibo.business.vo.StatisticsAssetsNumVo;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VAssetsReceiveVo;
import com.sibo.business.vo.VBusinessFileVo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:34
 */
@Service
//@Transactional
public class AppParameterBiz extends BusinessBiz<VAssetsParameterMapper,VAssetsParameter> {
    private Logger logger = LoggerFactory.getLogger(AppParameterBiz.class);
    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;
    
    @Autowired
    private AppSystemMessageMapper appSystemMessageMapper;

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Autowired
    private VAssetsAccessoryMapper assetsAccessoryMapper;

    @Autowired
    private StorageProperties storageProperties;
    
    @Autowired
    private VAssetsReceiveMapper assetsReceiveMapper;

    @Autowired
    private MergeCore mergeCor;

    @Autowired
    private VMeasurementCertificateMapper vMeasurementCertificateMapper;

    @Autowired
    private VAssetsUpdateRecordsMapper vAssetsUpdateRecordsMapper;
    
    
    @Autowired
    private VAssetsReceiveMainMapper vAssetsReceiveMainMapper;
    

    @Autowired
    private VInstrumentLibraryBiz vInstrumentLibraryBiz;


    @Autowired
    private VMeasurementVerificationPlanMapper vMeasurementVerificationPlanMapper;

    @Autowired
    private  UserFeign userFeign;

    @Autowired
    private DeptFeign deptFeign;

    @Autowired
    private VAssetsReceiveMainMapper assetsReceiveMainMapper;

    @Autowired
    private VMeteringMethodBiz meteringMethodBiz;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public void insertSelective(VAssetsParameter entity) {
        entity.setId(UUIDUtils.generateUuid());
        if(StringUtils.isNotEmpty(entity.getUnifiedCode())){
            entity.setUnifiedCodeOrder(entity.getUnifiedCode().substring(3,11));
        }

        //如果是附属设备增加，则需要给主设备追加总价
        if(StringUtils.isNotBlank(entity.getAssetType()) && entity.getAssetType().equals("2")){
            VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(entity.getParentId());
            assetsParameter.setTotalPrice(assetsParameter.getTotalPrice()+entity.getAfterTaxPrice());
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }

        super.insertSelective(entity);
    }

    @Override
    public void deleteById(Object id) {
        Example example = new Example(VAssetsReceiveMain.class);
        example.createCriteria().andEqualTo("assetsId",id);

        List<VAssetsReceiveMain> mainList = assetsReceiveMainMapper.selectByExample(example);

        for (VAssetsReceiveMain assetsReceiveMain:mainList) {
            assetsReceiveMainMapper.delete(assetsReceiveMain);
        }

        super.deleteById(id);
    }

    public TableResultResponse<VAssetsParameter> queryAssetsParameterPage(Map<String, Object> params){
        Query query = new Query(params);
        Example example = new Example(VAssetsParameter.class);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
              super.queryAll((String)params.get("searchAll"),example,VAssetsParameter.class);
              if(null != params.get("assetsStatus")){
                  example.createCriteria().andEqualTo("assetsStatus",params.get("assetsStatus")).andEqualTo("facitityStatus",params.get("facitityStatus"));
              }
        }else{
            this.query2criteria(query,example);
        }
        example.setOrderByClause("UNIFIED_CODE_ORDER DESC");
        List<VAssetsParameter> list = this.selectByExample(example);

        try {
            mergeCor.mergeResult(VAssetsParameter.class,list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }

    /**
     *
     * @param params
     * @return
     */
    public TableResultResponse<VAssetsParameter> equipmentPage(Map<String, Object> params){
        Query query = new Query(params);
        Example example = new Example(VAssetsParameter.class);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
            Map<String, Object> paramMmap = new HashMap<>();
            paramMmap.put("searchAll","%"+params.get("searchAll")+"%");
            paramMmap.put("assetsStatus",params.get("assetsStatus"));
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
            List<VAssetsParameter> list = assetsParameterMapper.selectEquipmentPageList(paramMmap);
            return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
        }else{
            this.query2criteria(query,example);
        }
        example.setOrderByClause("CRT_TIME desc,UNIFIED_CODE_ORDER ASC");
        List<VAssetsParameter> list = this.selectByExample(example);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }
    
    /**
    *
    * @param params
    * @return
    */
   public TableResultResponse<VAssetsParameter> equipmentPageDept(Map<String, Object> params){
       Query query = new Query(params);
       Example example = new Example(VAssetsParameter.class);
       Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
       if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
           Map<String, Object> paramMmap = new HashMap<>();
           paramMmap.put("searchAll","%"+params.get("searchAll")+"%");
           paramMmap.put("assetsStatus",params.get("assetsStatus"));
           paramMmap.put("facitityStatus",params.get("facitityStatus"));
           paramMmap.put("facilityDesignatedArea",params.get("facilityDesignatedArea"));
           List<VAssetsParameter> list = assetsParameterMapper.selectEquipmentPageList(paramMmap);
           return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
       }else{
           this.query2criteria(query,example);
       }
       example.setOrderByClause("CRT_TIME desc,UNIFIED_CODE_ORDER ASC");
       List<VAssetsParameter> list = this.selectByExample(example);
       
      /* List<Depart> deptList = deptFeign.getDeptAll();
       if(!CollectionUtils.isEmpty(deptList)){
           Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
          for (VAssetsParameter vAssetsParameter : list) {
        	  if(StringUtils.isNotBlank(vAssetsParameter.getEquipmentDepartment())) {
        		  vAssetsParameter.setEquipmentDepartment(map.get(vAssetsParameter.getEquipmentDepartment()).getName());
        	  }
		}
       }*/      
       
       
       return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
   }

    @Override
    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            Example.Criteria criteria = null;
//            if(null == query.get("parentId")){
//                criteria = example.createCriteria().andIsNull("parentId");
//            }else{
               criteria = example.createCriteria();
//            }
            Map map = new HashMap();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(StringUtils.isNotEmpty(entry.getValue()+"")){
                    if(entry.getKey().equals("assetsStatus") ||  entry.getKey().equals("facitityStatus") || entry.getKey().equals("facilityDesignatedArea") ){
                        criteria.andEqualTo(entry.getKey(),  entry.getValue().toString());
                    }else if(!entry.getKey().equals("flag") && !entry.getKey().equals("useTimeStart") && !entry.getKey().equals("useTimeEnd")){
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    }else if(entry.getKey().equals("useTimeStart")){
                        map.put("useTimeStart",entry.getValue().toString());
                    }else if( entry.getKey().equals("useTimeEnd")){
                        map.put("useTimeEnd",entry.getValue().toString());
                    }
                }

            }
            if(null != map.get("useTimeStart") && null != map.get("useTimeEnd")){
                criteria.andCondition(" USES_TIME >= to_date('"+map.get("useTimeStart")+"','yyyy-mm-dd') and USES_TIME <= to_date( '"+map.get("useTimeEnd")+"','yyyy-mm-dd')");
            }

        }
    }




    /**
     * 追加资产
     * @param vo
     */
    public String insertAdditionalAsset(VAssetsParameterVo vo) {
        if(checkUnicode(vo)){
            throw new BusinessException("统一编号已存在，请修改");
        }

        VAssetsParameter entity = new VAssetsParameter();
        org.springframework.beans.BeanUtils.copyProperties(vo,entity);
        entity.setUnifiedCodeOrder(vo.getUnifiedCode().substring(3,11));
        entity.setId(UUIDUtils.generateUuid());

        super.insertSelective(entity);

        //资产追加需要给主资产总价增加
        VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getParentId());
        assetsParameter.setTotalPrice(assetsParameter.getTotalPrice()+vo.getAfterTaxPrice());
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        //保存附件信息
        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(entity.getId());
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")));
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }
        return entity.getId();
    }

    @Transactional
    public VAssetsParameterVo add(VAssetsParameterVo vo){

        if(StringUtils.isEmpty(vo.getId())){
            if(checkUnicode(vo)){
                throw new BusinessException("统一编号已存在，请修改");
            }
            VAssetsParameter entity = new VAssetsParameter();
            org.springframework.beans.BeanUtils.copyProperties(vo,entity);
            entity.setUnifiedCodeOrder(vo.getUnifiedCode().substring(3,11));
            entity.setId(UUIDUtils.generateUuid());

            super.insertSelective(entity);
            vo.setId(entity.getId());
        }else{
            if(checkUpdateUnicode(vo)){
                throw new BusinessException("统一编号已存在，请修改");
            }
            VAssetsParameter vAssetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getId());
            org.springframework.beans.BeanUtils.copyProperties(vo,vAssetsParameter);
            vAssetsParameter.setUnifiedCodeOrder(vo.getUnifiedCode().substring(3,11));
            super.updateSelectiveById(vAssetsParameter);
        }

        vInstrumentLibraryBiz.distribute(vo);
        return vo;
    }

    private boolean checkUnicode(VAssetsParameterVo vo){
        Example example = new Example(VAssetsParameter.class);
        example.createCriteria().andEqualTo("unifiedCode",vo.getUnifiedCode());
        List list = assetsParameterMapper.selectByExample(example);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(list)){
            return true;
        }
        return false;
    }

    private boolean checkUpdateUnicode(VAssetsParameterVo vo){
        Example example = new Example(VAssetsParameter.class);
        example.createCriteria().andEqualTo("unifiedCode",vo.getUnifiedCode());
        List<VAssetsParameter> list = assetsParameterMapper.selectByExample(example);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(list)){
            VAssetsParameter assetsParameter = list.get(0);
            if(!assetsParameter.getId().equals(vo.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * 修改台账信息
     * @param vo
     * @return
     */
    public VAssetsParameterVo updateAdditionalAsset(VAssetsParameterVo vo) {
        if(checkUpdateUnicode(vo)){
            throw new BusinessException("统一编号已存在，请修改");
        }
        VAssetsParameter vAssetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getId());
        if(StringUtils.isNotEmpty(vo.getUnifiedCode())){
            if(vo.getUnifiedCode().length()>=12){
                vAssetsParameter.setUnifiedCodeOrder(vo.getUnifiedCode().substring(3,11));
            }else{
                vAssetsParameter.setUnifiedCodeOrder(vo.getUnifiedCode().substring(3,vo.getUnifiedCode().length()));
            }

        }
        if(StringUtils.isEmpty(vAssetsParameter.getUnifiedCode())){
            vAssetsParameter.setUnifiedCode(vo.getUnifiedCode());
        }
        assetsParameterMapper.updateByPrimaryKeySelective(vAssetsParameter);

        VAssetsParameter entity = new VAssetsParameter();


        if(null != vAssetsParameter){
            //不是暂存状态时才进行下面操作
            if(AssetsStatus.HOLD.getCode().equals(vo.getAssetsStatus())){
                return vo;
            }

            // 判断是否修改了有效期至，如果修改了说明计量已经完成，需要修改计量计划表状态为已完成
            if(null != vo.getMeasurementCheckClass() && Constant.ISNEETMEASURENT.equals(vo.getMeasurementCheckClass())
                    && null != vo.getMeasurementValidity() && null != vAssetsParameter.getMeasurementValidity() &&
                    null != vo.getPeriodDateStop() && null != vAssetsParameter.getPeriodDateStop()){
                if(vo.getMeasurementValidity().getTime()-vAssetsParameter.getMeasurementValidity().getTime()>0){
                    Example example = new Example(VMeasurementVerificationPlan.class);
                    example.createCriteria().andEqualTo("assetId",vAssetsParameter.getId()).andEqualTo("measueementVerificationStatu",MeasueementVerificationStatu.UNFINISHED.getCode())
                                .andEqualTo("type",MetrologicalReviewType.MEASURE.getCode());
                    List<VMeasurementVerificationPlan> list = vMeasurementVerificationPlanMapper.selectByExample(example);
                    if(!CollectionUtils.isEmpty(list)){
                        VMeasurementVerificationPlan vMeasurementVerificationPlan = list.get(0);
                        vMeasurementVerificationPlan.setMeasueementVerificationStatu(MeasueementVerificationStatu.FINISHED.getCode());
                        EntityUtils.setUpdatedInfo(vMeasurementVerificationPlan);
                        vMeasurementVerificationPlanMapper.updateByPrimaryKeySelective(vMeasurementVerificationPlan);
                    }
                }
                // 处理核查数据
                if(vo.getPeriodDateStop().getTime()-vAssetsParameter.getPeriodDateStop().getTime()>0){
                    Example example = new Example(VMeasurementVerificationPlan.class);
                    example.createCriteria().andEqualTo("assetId",vAssetsParameter.getId()).andEqualTo("measueementVerificationStatu",MeasueementVerificationStatu.UNFINISHED.getCode())
                            .andEqualTo("type",MetrologicalReviewType.INSPECT.getCode());;
                    List<VMeasurementVerificationPlan> list = vMeasurementVerificationPlanMapper.selectByExample(example);
                    if(!CollectionUtils.isEmpty(list)){
                        VMeasurementVerificationPlan vMeasurementVerificationPlan = list.get(0);
                        vMeasurementVerificationPlan.setMeasueementVerificationStatu(MeasueementVerificationStatu.FINISHED.getCode());
                        EntityUtils.setUpdatedInfo(vMeasurementVerificationPlan);
                        vMeasurementVerificationPlanMapper.updateByPrimaryKeySelective(vMeasurementVerificationPlan);
                    }
                }
            }

            // 只有主设备需要判断是否要给变更主表添加数据
            if(null!= vo.getAssetType() && vo.getAssetType().equals("1")){
                //判断是否修改负责人，如果是修改负责人则需要在变更主表增加修改记录
                if(!vAssetsParameter.getPrincipal().equals(vo.getPrincipal())){
                    vInstrumentLibraryBiz.distribute(vo);
                    org.springframework.beans.BeanUtils.copyProperties(vo,entity,new String[] { "assetsStatus"});
                }else{
                    org.springframework.beans.BeanUtils.copyProperties(vo,entity);
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        entity.setPeriodDate(sdf.parse(vo.getPeriodDate()==null?"":vo.getPeriodDate()));
                        entity.setPeriodDateStop(vo.getPeriodDateStop());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else if(null!= vo.getAssetType() && vo.getAssetType().equals("3")) {
                //如果是资产追加更新，则需要更新主设备总价信息
                VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getParentId());
                //主设备总价-追加设备的旧值
                assetsParameter.setTotalPrice(assetsParameter.getTotalPrice()-vAssetsParameter.getTotalPrice());
                float newPrice = assetsParameter.getTotalPrice()+vo.getTotalPrice();
                assetsParameter.setTotalPrice(newPrice);
                assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
                org.springframework.beans.BeanUtils.copyProperties(vo,entity);
            }else{
                org.springframework.beans.BeanUtils.copyProperties(vo,entity);
            }

        }
        else{
            throw new BusinessRuntimeException("台账数据异常，可能已被删除！");
        }

        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        //判断是否有修改原因
        if(StringUtils.isNotEmpty(vo.getDesc())){
            VAssetsUpdateRecords records = new VAssetsUpdateRecords();
            records.setId(UUIDUtils.generateUuid());
            EntityUtils.setCreatAndUpdatInfo(records);
            records.setDesc(vo.getDesc());
            records.setAssetsId(entity.getId());
            vAssetsUpdateRecordsMapper.insertSelective(records);
        }


        VBusinessFile file  = new VBusinessFile();
        Example example = new Example(VBusinessFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bussinessId", entity.getId());
        int i = businessFileMapper.deleteByExample(example);
        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                file  = new VBusinessFile();
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                file.setBussinessId(entity.getId());
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }


        return vo;
    }


    public String getMaxNum(String assetType,String facitityCategory,String measurementCheckClass,String assetsClass,String useTime,String unifiedCode){

        StringBuffer str = new StringBuffer();
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = Integer.valueOf(useTime.substring(0,4));
        int month = Integer.valueOf(useTime.substring(5,7));

        String newMonth = "";
        if(month<10){
            newMonth = "0"+month;
        }else{
            newMonth = month+"";
        }
        Map<String, Object> paramMmap = null;
        if(assetType.equals("1")){

            //查询数据库中当前编号的最大顺序号
            paramMmap = new HashMap<>();
            paramMmap.put("assetType",assetType);
            paramMmap.put("yearMonth",year+""+newMonth);
            paramMmap.put("assetsClass",assetsClass);
            String maxNum = assetsParameterMapper.getMaxNum(paramMmap);
            String resultMaxNum = "";
            if(null == maxNum){
                resultMaxNum = "01";
            }else if(Integer.valueOf(maxNum)==99){
                throw new BusinessException("当月编号已达上限，请联系管理员");
            }else{
                //resultMaxNum = (Integer.valueOf(maxNum.substring(maxNum.length()-1))+1)+"";
                resultMaxNum = (Integer.valueOf(maxNum)+1)+"";
            }

            if(resultMaxNum.length()<2){
                resultMaxNum  = "0"+resultMaxNum;
            }/*else if(resultMaxNum.length()<3){
                resultMaxNum  = "0"+resultMaxNum;
            }*/

            str.append(assetsClass).append(facitityCategory).append(measurementCheckClass).append(year).append(newMonth).append(resultMaxNum);
        }else{
            if("0".equals(unifiedCode)){
                throw new BusinessException("主设备编号为空，不能生成编号");
            }
            paramMmap = new HashMap<>();
            paramMmap.put("assetType",assetType);
            paramMmap.put("unifiedCode",unifiedCode+"-%");
            String maxNum = assetsParameterMapper.getOtherMaxNum(paramMmap);
            if(StringUtils.isNotEmpty(maxNum)){
                int resultMaxNum = Integer.valueOf(maxNum)+1;
                if(String.valueOf(resultMaxNum).length()<2 && String.valueOf(resultMaxNum).length()>1){
                    str.append("0"+resultMaxNum);
                }else if(String.valueOf(resultMaxNum).length() == 1){
                    str.append("00"+resultMaxNum);
                } else{
                    str.append(resultMaxNum);
                }

            }else{
                if(assetType.equals("2")){
                    str.append("001");
                }else{
                    str.append("500");
                }

            }

        }
        return str.toString();
    }

    public  String exportExcel(String type,String id) throws Exception {
//        String tmpFile = "classpath:static/template.xls";
//        File file = ResourceUtils.getFile(tmpFile);
//        String expFile = "D:/temp/";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String expFile =storageProperties.getDownPath();
        Map<String, Object> datas = new HashMap<>();
        VAssetsParameter model = assetsParameterMapper.selectByPrimaryKey(id);
        if(null != model){
            List<VAssetsParameter> listMerge = new ArrayList();
            listMerge.add(model);
            mergeCor.mergeResult(VAssetsParameter.class,listMerge);
            datas.put("devicename", model.getDevicename()==null?"":model.getDevicename());
            datas.put("unifiedCode", model.getUnifiedCode()==null?"":model.getUnifiedCode());
            datas.put("unitType", model.getUnitType()==null?"":model.getUnitType());
            datas.put("equipmentNumber", model.getEquipmentNumber()==null?"":model.getEquipmentNumber());
            datas.put("measurementRange", model.getMeasurementRange()==null?"":model.getMeasurementRange());
            datas.put("originalValue",model.getOriginalValue()==null?"":model.getOriginalValue());
            datas.put("grade", model.getGrade()==null?"":model.getGrade());
            datas.put("installedCapacity", model.getInstalledCapacity()==null?"":model.getInstalledCapacity());
            datas.put("facitityCategory", model.getFacitityCategory()==null?"":model.getFacitityCategory());
            datas.put("criticalEquipment", model.getCriticalEquipment()==null?"":model.getCriticalEquipment());
            datas.put("mainUses", model.getMainUses()==null?"":model.getMainUses());

            List<User> userList = userFeign.getAllUser();
            logger.info("--------------结束远程获取人员");
            if(!CollectionUtils.isEmpty(userList)){
                // 将userlist转换为Map
                Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
                //map中获取对应值
                listMerge.stream().map(v ->{
                    if(null != v.getPrincipal() && null != map.get(v.getPrincipal())){
                        v.setPrincipal(map.get(v.getPrincipal()).getName());
                    }
                    return v;
                }).collect(Collectors.toList());
            }
            datas.put("principal", model.getPrincipal()==null?"":model.getPrincipal());
            if(null != model.getDeliveryTime()){
                datas.put("deliveryTime",sdf1.format(model.getDeliveryTime()));
            }else{
                datas.put("deliveryTime","");
            }

            datas.put("manufacturer", model.getManufacturer()==null?"":model.getManufacturer());
            if(null != model.getUsesTime()){
                datas.put("usesTime", sdf1.format(model.getUsesTime()));
            }else{
                datas.put("usesTime", "");
            }

            if(!CollectionUtils.isEmpty(listMerge) && null != listMerge.get(0).getFacitityStatus()){
                datas.put("facitityStatus", listMerge.get(0).getFacitityStatus());
            }else{
                datas.put("facitityStatus", "");
            }
            if(!CollectionUtils.isEmpty(listMerge) && null != listMerge.get(0).getFacilityDesignatedArea()){
                datas.put("facilityDesignatedArea", listMerge.get(0).getFacilityDesignatedArea());
            }else{
                datas.put("facilityDesignatedArea", "");
            }

            datas.put("remark", model.getRemark()==null?"":model.getRemark());

            logger.info("--------------开始远程获取部门");
            List<Depart> deptList = deptFeign.getDeptAll();
            if(!CollectionUtils.isEmpty(deptList)){
                Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
                Depart dept = map.get(model.getEquipmentDepartment());
                if(null == dept){
                    throw new BusinessRuntimeException("当前数据部门获取失败");
                }
                model.setEquipmentDepartment(dept.getName());
                datas.put("equipmentDepartment",dept.getName());
            }
            logger.info("--------------结束远程获取部门");


            //查询随机资料和随机附件
            Example example = new Example(VAssetsAccessory.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("pkId",model.getId());
            List<VAssetsAccessory> assetsAccessoryList = assetsAccessoryMapper.selectByExample(example);

            List list = new ArrayList<>();
            List assetsAccessoryResultList = new ArrayList<>();
            for(VAssetsAccessory assetsAccessory : assetsAccessoryList){
                VAssetsAccessory entity = new VAssetsAccessory();
                entity.setName(assetsAccessory.getName());
                entity.setAmount(assetsAccessory.getAmount());
                entity.setRemark(assetsAccessory.getRemark());
                if("1".equals(assetsAccessory.getType())){
                    list.add(entity);
                }else{
                    assetsAccessoryResultList.add(entity);
                }
            }
            datas.put("randomFile", list);
            datas.put("assetsAccessoryResultList", assetsAccessoryResultList);
        }else{
            throw new BusinessRuntimeException("未查询到任何数据");
        }
        String filePath = "";
        if(type.equals(Constant.TZK)){
            datas.put("originalValue",model.getAfterTaxPrice()==null?"":new BigDecimal(model.getAfterTaxPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
            filePath = storageProperties.getTempletePath()+"仪器设备台帐卡.xls";
        }else if(type.equals(Constant.SQD)){
            filePath = storageProperties.getTempletePath()+"资产处置申请单.xls";
        }else if(type.equals(Constant.JLK)){
            datas.put("originalValue",model.getAfterTaxPrice()==null?"":new BigDecimal(model.getAfterTaxPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
            filePath = storageProperties.getTempletePath()+"投产记录卡.xls";
        }

        return  ExportUtils.export(filePath,expFile,datas);
    }

    public void queryAlls(String searchValue, Example example, Class obj) {
        //获取类属性
        Field[] field = obj.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            // key:得到属性名

            Column column = f.getAnnotation(Column.class);
            if(null != column){
                String key = column.name();
                Example.Criteria criteria = example.createCriteria();
//                criteria.andLike(key,"%"+searchValue+"%");
                criteria.andCondition("( upper("+key+") like upper('%"+searchValue+"%') or lower("+key+") like lower('%"+searchValue+"%')  or "+key+" like '%"+searchValue+"%') and MEASUREMENT_CHECK_CLASS = '1'");
                //criteria.andEqualTo("measurementCheckClass","1");
                example.or(criteria);
            }
        }
    }

    public void query2criterias(Query query, Example example, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map annotionParmets = new HashMap();
        for (Field field : fields) {
            QueryParmentType annotation = field.getAnnotation(QueryParmentType.class);
            if(null != annotation){
                if(StringUtils.isNotEmpty(annotation.key())){
                    annotionParmets.put(field.getName(),annotation.key());
                }
            }
        }
        if (query.entrySet().size() > 0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(StringUtils.isNotBlank(entry.getValue().toString())){
                    if(null != annotionParmets.get(entry.getKey())){
                        if("=".equals(annotionParmets.get(entry.getKey()))){
                            criteria.andEqualTo(entry.getKey(), entry.getValue().toString() );
                        }else{
                            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                        }
                    }else{
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    }
                    criteria.andEqualTo("measurementCheckClass","1");
                }
            }
        }
    }

    public TableResultResponse<VAssetsParameterVo> queryVAssetsParameterMapperAll(Map<String, Object> params){
        Query query = new Query(params);
        Example exampleMain = new Example(VAssetsParameter.class);

        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
            this.queryAlls((String)params.get("searchAll"),exampleMain,VAssetsParameter.class);
        }else{
            this.query2criterias(query,exampleMain,VAssetsParameter.class);
        }
        exampleMain.setOrderByClause("UNIFIED_CODE_ORDER asc");
        List<VAssetsParameter> list = this.selectByExample(exampleMain);
        try {
            mergeCor.mergeResult(VAssetsParameter.class,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<VAssetsParameterVo> resultList = new ArrayList<>();
        //如果是查询计量台账信息，需要查询证书信息
        if(StringUtils.isNotEmpty((String)params.get("measurementCheckClass")) && params.get("measurementCheckClass").equals(MeasurementCheckClass.NOT_NEED_TO_MEASURE.getCode())){

            for (VAssetsParameter vAssetsParameter : list) {
                VAssetsParameterVo vAssetsParameterVo = new VAssetsParameterVo();
                org.springframework.beans.BeanUtils.copyProperties(vAssetsParameter,vAssetsParameterVo);
                //查询记录证书信息
                Example example = new Example(VMeasurementCertificate.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("assetId",vAssetsParameter.getId());
                List<VMeasurementCertificate> vMeasurementCertificateList = vMeasurementCertificateMapper.selectByExample(example);
                vAssetsParameterVo.setvMeasurementCertificateVoList(vMeasurementCertificateList);
                resultList.add(vAssetsParameterVo);
            }
        }else{
            for (VAssetsParameter vAssetsParameter : list) {
                VAssetsParameterVo vAssetsParameterVo = new VAssetsParameterVo();
                org.springframework.beans.BeanUtils.copyProperties(vAssetsParameter,vAssetsParameterVo);
                resultList.add(vAssetsParameterVo);
            }
        }
        return new TableResultResponse<VAssetsParameterVo>(result.getTotal(), resultList);
    }


    public TableResultResponse<VAssetsParameter> queryDeviceList(Map<String, Object> params){
    	 
    	
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map paramMmap = new HashMap<>();
        
        List<VAssetsParameter> list = null;
        List<VAssetsParameter> listTemp = new ArrayList<>();
        if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
            paramMmap.put("searchAll","%"+params.get("searchAll")+"%");
            list = assetsParameterMapper.selectAssetsParamerByPlayAllLike(paramMmap);
        }else{
            list = assetsParameterMapper.selectAssetsParamerByPlay(paramMmap);
        }

        try {
            mergeCor.mergeResult(VAssetsParameter.class,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String status=params.get("status").toString();
        //当前日期
        Date date=new Date();
    	if(status.equals("1")) {
    		//查询即将过期
    		for(int i=0;i<list.size();i++) {
    			Date MeasurementValidity=list.get(i).getMeasurementValidity();
    			String dateStr = DateFormat.getDateInstance().format(MeasurementValidity);
    			dateStr=dateStr.replace("-", "/");
    			Date oldDate = new Date(dateStr);
                long num = (oldDate.getTime() - date.getTime()) / (1000 * 3600 * 24);
                if(num>0 && num <10) {
                	listTemp.add(list.get(i));
                }
    		}
    	}else {
    		//查询已过期
    		for(int i=0;i<list.size();i++) {
    			Date MeasurementValidity=list.get(i).getMeasurementValidity();
    			String dateStr = DateFormat.getDateInstance().format(MeasurementValidity);
    			dateStr=dateStr.replace("-", "/");
    			Date oldDate = new Date(dateStr);
                long num = (oldDate.getTime() - date.getTime()) / (1000 * 3600 * 24);
                if(num<0) {
                	listTemp.add(list.get(i));
                }
    		}
    	}
        return new TableResultResponse<>(listTemp.size(), listTemp);
    }

    public TableResultResponse<VAssetsParameter> pageInspectList(Map<String, Object> params){
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map paramMmap = new HashMap<>();
        if(!org.springframework.util.StringUtils.isEmpty(params.get("devicename"))){
            paramMmap.put("devicename",params.get("devicename"));
        }
        paramMmap.put("periodDate","PERIOD_DATE");
        paramMmap.put("measueementVerificationStatu", MeasueementVerificationStatu.UNFINISHED.getCode());
        paramMmap.put("type",MetrologicalReviewType.INSPECT.getCode());
        List<VAssetsParameter> list = null;
        if(StringUtils.isNotEmpty((String)params.get("searchAll"))){
            paramMmap.put("searchAll","%"+params.get("searchAll")+"%");
            list = assetsParameterMapper.selectAssetsParamerByPlayAllLike(paramMmap);
        }else{
            params.put("periodDate","PERIOD_DATE");
            params.put("measueementVerificationStatu", MeasueementVerificationStatu.UNFINISHED.getCode());
            params.put("type",MetrologicalReviewType.INSPECT.getCode());
            list = assetsParameterMapper.selectAssetsParamerByPlay(params);
        }

        try {
            mergeCor.mergeResult(VAssetsParameter.class,list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new TableResultResponse<>(result.getTotal(), list);
    }


    public TableResultResponse<VAssetsUpdateRecords> pageUpdateRecords(String assetId){
        Example example = new Example(VAssetsUpdateRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("assetsId",assetId);
        List<VAssetsUpdateRecords> list = vAssetsUpdateRecordsMapper.selectByExample(example);
        return new TableResultResponse<>(list.size(), list);
    }

    /**
     * 导出指定字段
     * @param params
     * @param resp
     * @return
     * @throws Exception
     */
    public String  exportAssetsParameter(Map<String, Object> params,HttpServletResponse resp) throws Exception {
        //获取查询条件
//        String[] queryFormArr = (JSON.parse((String)params.get("form"))).split(",");
        logger.info("--------------开始导出数据，正在查询");
        JSONObject  queryFormArr = JSON.parseObject((String)params.get("form"));
        Example example = new Example(VAssetsParameter.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(queryFormArr.getString("devicename"))){
            criteria.andEqualTo("devicename",queryFormArr.getString("devicename"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("unifiedCode"))){
            criteria.andEqualTo("unifiedCode",queryFormArr.getString("unifiedCode"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("unitType"))){
            criteria.andEqualTo("unitType",queryFormArr.getString("unitType"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("assetsClass"))){
            criteria.andEqualTo("assetsClass",queryFormArr.getString("assetsClass"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("facitityStatus"))){
            criteria.andEqualTo("facitityStatus",queryFormArr.getString("facitityStatus"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("equipmentDepartment"))){
            criteria.andEqualTo("equipmentDepartment",queryFormArr.getString("equipmentDepartment"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("facilityDesignatedArea"))){
            criteria.andEqualTo("facilityDesignatedArea",queryFormArr.getString("facilityDesignatedArea"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("criticalEquipment"))){
            criteria.andEqualTo("criticalEquipment",queryFormArr.getString("criticalEquipment"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("measurementCheckClass"))){
            criteria.andEqualTo("measurementCheckClass",queryFormArr.getString("measurementCheckClass"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("principal"))){
            criteria.andEqualTo("principal",queryFormArr.getString("principal"));
        }

        if(StringUtils.isNotEmpty(queryFormArr.getString("equipmentNumber"))){
            criteria.andEqualTo("equipmentNumber",queryFormArr.getString("equipmentNumber"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("equipmentSubordinate"))){
            criteria.andEqualTo("equipmentSubordinate",queryFormArr.getString("equipmentSubordinate"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("facitityCategory"))){
            criteria.andEqualTo("facitityCategory",queryFormArr.getString("facitityCategory"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("installedCapacity"))){
            criteria.andEqualTo("installedCapacity",queryFormArr.getString("installedCapacity"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("manufacturer"))){
            criteria.andEqualTo("manufacturer",queryFormArr.getString("manufacturer"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("agent"))){
            criteria.andEqualTo("agent",queryFormArr.getString("agent"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("dataManager"))){
            criteria.andEqualTo("dataManager",queryFormArr.getString("dataManager"));
        }
        if(StringUtils.isNotEmpty(queryFormArr.getString("useTime"))){
            criteria.andEqualTo("useTime",queryFormArr.getString("useTime"));
        }


        List<VAssetsParameter> allList = assetsParameterMapper.selectByExample(example);
        logger.info("--------------数据查询完成，共"+allList.size()+"条");
        //获取查询字段
        JSONObject  filedFormArr = JSON.parseObject((String)params.get("group"));
        JSONArray jsonArray = JSON.parseArray(filedFormArr.getString("resultGroup"));
        String[] filedHeaders = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            filedHeaders[i] =jsonObject.getString("name");
        }

        logger.info("--------------开始处理表头");
        long start = System.currentTimeMillis();
        //处理导出数据
        List<VAssetsParameter> list = new ArrayList<VAssetsParameter>();
        for (VAssetsParameter parameter:allList){
            VAssetsParameter parameterVo = new  VAssetsParameter();
            for (int i = 0; i < jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                VAssetsParameter newpParameter = new  VAssetsParameter();
                String value = "get"+(jsonObject.getString("value").substring(0, 1).toUpperCase())+jsonObject.getString("value").substring(1);
                Field[] fields = parameter.getClass().getDeclaredFields();
                for(Field field:fields){
                    PropertyDescriptor oldPd = new PropertyDescriptor(field.getName(), parameter.getClass());
                    PropertyDescriptor newPd = new PropertyDescriptor(field.getName(), parameterVo.getClass());
                    Method get = oldPd.getReadMethod();//获得读方法
                    String filedName = get.getName();
                    if(value.equals(filedName)){
                        Method  set = newPd.getWriteMethod();//获得写方法
                        Object aaa = get.invoke(parameter);
                        set.invoke(parameterVo,get.invoke(parameter));//
                    }
                }
            }
            list.add(parameterVo);
        }
        long end = System.currentTimeMillis()-start;
        logger.info("--------------处理表头完成总共用时:【"+end+"】mm");
        //合并字段数据
        mergeCor.mergeResult(VAssetsParameter.class, list);
        logger.info("--------------开始远程获取人员");
        //合并部门和人员
        List<User> userList = userFeign.getAllUser();
        logger.info("--------------结束远程获取人员");
        if(!CollectionUtils.isEmpty(userList)){
            // 将userlist转换为Map
            Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
            //map中获取对应值
            list.stream().map(v ->{
                if(null != v.getPrincipal() && null != map.get(v.getPrincipal())){
                    v.setPrincipal(map.get(v.getPrincipal()).getName());
                }
                if(null != v.getAgent() && null != map.get(v.getAgent())){
                    v.setAgent(map.get(v.getAgent()).getName());
                }
                if(null != v.getDataManager() && null != map.get(v.getDataManager())){
                    v.setDataManager(map.get(v.getDataManager()).getName());
                }

                return v;
            }).collect(Collectors.toList());
        }

        logger.info("--------------开始远程获取部门");
        List<Depart> deptList = deptFeign.getDeptAll();
        if(!CollectionUtils.isEmpty(deptList)){
            Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
            list.stream().map(v ->{
                if(null != v.getEquipmentDepartment() && null != map.get(v.getEquipmentDepartment())){
                    v.setEquipmentDepartment(map.get(v.getEquipmentDepartment()).getName());
                }
                return v;
            }).collect(Collectors.toList());
        }
        logger.info("--------------结束远程获取部门");

        logger.info("--------------开始生成文件");
        //导出数据
        ExportExcelUtil util = new ExportExcelUtil<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        String path = storageProperties.getDownPath()+"台账数据"+year+"年"+month+"月"+day+"日--"+new Random(100).nextInt()+".xlsx";
        logger.info("--------------文件名称：：："+path);
        String paths = path;
        util.exportExcel2007("台账数据",filedHeaders,list,new FileOutputStream(path),"yyyy-MM-dd HH:mm:ss",jsonArray);
        logger.info("--------------生成文件结束:"+paths);
        return paths;
    }




    /**
     * 首页统计信息查询
     * @param type
     * @return
     */
    public List<StatisticsAssetsNumVo>  queryDashboardAssetsCount(String type){
        String year = "";
        if(type.equals("1")){ // 如果是1时  统计年度设备数量
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR)+"";
        }
        List<StatisticsAssetsNumVo> countList = assetsParameterMapper.queryDashboardAssetsCount(String.valueOf(year));
        return countList;
    }

    /**
     * 查询年度设备采购费
     * @return
     */
    public Map equipmentPurchaseCost(){
        //1.查询年度设备采购费，并按照资产类别分类显示
        RowBounds rb = new RowBounds();
        Map paramMmap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        paramMmap.put("year",year);
        List<StatisticsAssetsNumVo> list = assetsParameterMapper.queryDashboardAssetsCategoryCount(year+"");
        Map map = new HashMap();
        map.put("practical",list);

        //2.查询采购费计划

        return map;
    }

    /**
     * 复制资产
     * @param id
     */
    public void copyAssets(String id){

        //1.复制资产主表数据
        //2.复制随机资料和随机图片
        VAssetsParameter  assetsParameter =  super.selectById(id);
        VAssetsParameter  assetsParameterNew =  new  VAssetsParameter();
        org.springframework.beans.BeanUtils.copyProperties(assetsParameter, assetsParameterNew);
        assetsParameterNew.setId(UUIDUtils.generateUuid());

//        String maxNum = assetsParameterMapper.getMaxNum("1");
//        String resultMaxNum = "";
//        if(null == maxNum){
//            resultMaxNum = "001";
//        }else{
//            resultMaxNum = (Integer.valueOf(maxNum.substring(maxNum.length()-2))+1)+"";
//        }
//
//        if(resultMaxNum.length()<2){
//            resultMaxNum  = "00"+resultMaxNum;
//        }else if(resultMaxNum.length()<3){
//            resultMaxNum  = "0"+resultMaxNum;
//        }

        assetsParameterNew.setUnifiedCode("");
//        assetsParameterNew.setAssetType("1");// 录入
        assetsParameterNew.setAssetsStatus(AssetsStatus.HOLD.getCode());//复制的为暂存状态
        EntityUtils.setCreateInfo(assetsParameter);
        super.insertSelective(assetsParameterNew);

        Example example1 = new Example(VAssetsAccessory.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("pkId",id);
        List<VAssetsAccessory> assetsAccessoryList = assetsAccessoryMapper.selectByExample(example1);
        assetsAccessoryList.stream().forEach(a -> {
            VAssetsAccessory vAssetsAccessory = new VAssetsAccessory();
            org.springframework.beans.BeanUtils.copyProperties(a,vAssetsAccessory);
            vAssetsAccessory.setId(UUIDUtils.generateUuid());
            vAssetsAccessory.setPkId(assetsParameterNew.getId());
            vAssetsAccessory.setAddTime(a.getAddTime());
            EntityUtils.setCreateInfo(vAssetsAccessory);

            assetsAccessoryMapper.insertSelective(vAssetsAccessory);
        });

        Example example = new Example(VBusinessFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bussinessId", id);
        List<VBusinessFile> list = businessFileMapper.selectByExample(example);
        list.stream().forEach(f -> {
            VBusinessFile vBusinessFile = new VBusinessFile();
            org.springframework.beans.BeanUtils.copyProperties(f,vBusinessFile);
            vBusinessFile.setId(UUIDUtils.generateUuid());
            vBusinessFile.setBussinessId(assetsParameterNew.getId());
            EntityUtils.setCreateInfo(vBusinessFile);
            businessFileMapper.insertSelective(vBusinessFile);
        });

    }

    @Autowired
    private DictFeign dictFeign;
    //处理导入数据人员
    public void dis(){
        // 查询部门、存放地、人员数据
        /**
         * -- 导入数据需要更新
         * update V_ASSETS_PARAMETER v set v.ASSETS_STATUS = '0';
         * update V_ASSETS_PARAMETER v set VERIFY_CURRENT_STATUS  = 'hg' where VERIFY_CURRENT_STATUS = '合格';
         * update V_ASSETS_PARAMETER v set VERIFY_CURRENT_STATUS  = '' where VERIFY_CURRENT_STATUS = '取消计量';
         * update V_ASSETS_PARAMETER v set VERIFY_CURRENT_STATUS  = 'fhyq' where VERIFY_CURRENT_STATUS = '符合要求';
         *
         *
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '0'  where v.MEASUREMENT_UNIT = '国家摩托车质量监督检验中心';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '37'  where v.MEASUREMENT_UNIT = '中国计量科学研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '2'  where v.MEASUREMENT_UNIT = '205所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '3'  where v.MEASUREMENT_UNIT = 'TUV实验室';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '4'  where v.MEASUREMENT_UNIT = '北京无线电计量测试研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '5'  where v.MEASUREMENT_UNIT = '广电计量';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '6'  where v.MEASUREMENT_UNIT = '广东产品质量监督检验研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '7'  where v.MEASUREMENT_UNIT = '广东省产品质量监督检验中心';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '8'  where v.MEASUREMENT_UNIT = '广东中准检测公司';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '9'  where v.MEASUREMENT_UNIT = '广州广电计量检测股份有限公司';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '10'  where v.MEASUREMENT_UNIT = '杭州远方检测校准技术有限公司';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '11'  where v.MEASUREMENT_UNIT = '华测检测';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '12'  where v.MEASUREMENT_UNIT = '摩托车检测技术研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '13'  where v.MEASUREMENT_UNIT = '陕西省测绘仪器计量监督检定中心';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '14'  where v.MEASUREMENT_UNIT = '陕西省计量科学研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '15'  where v.MEASUREMENT_UNIT = '陕西省计量科学研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '16'  where v.MEASUREMENT_UNIT = '陕西省计量研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '17'  where v.MEASUREMENT_UNIT = '陕西省计量院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '18'  where v.MEASUREMENT_UNIT = '陕西省气象计量检定所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '19'  where v.MEASUREMENT_UNIT = '陕西省气象局';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '天津市自行车计量技术中心';

         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '10'  where v.MEASUREMENT_UNIT = '天津市自行车计量中心';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '11'  where v.MEASUREMENT_UNIT = '威凯检测技术有限公司计量中心';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '12'  where v.MEASUREMENT_UNIT = '西安航天计量测试技术研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '13'  where v.MEASUREMENT_UNIT = '西安航天计量测试研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '14'  where v.MEASUREMENT_UNIT = '西安航天计量测试研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '15'  where v.MEASUREMENT_UNIT = '西安航天计量技术研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '16'  where v.MEASUREMENT_UNIT = '中国测试技术研究院';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '17'  where v.MEASUREMENT_UNIT = '中国电子科集团公司第二十研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '18'  where v.MEASUREMENT_UNIT = '中国电子科技集团第二十研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '19'  where v.MEASUREMENT_UNIT = '中国电子科技集团第二十研究所计量站校验室';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国电子科技集团公司等二十研究所计量站';

         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国电子科技集团公司第二十所计量站';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国电子科技集团公司第二十研究所';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国电子科技集团公司第二十研究所计量站';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国电子科技集团公司第二十研究所校准/检测实验室';
         UPDATE V_ASSETS_PARAMETER v set v.MEASUREMENT_UNIT = '20'  where v.MEASUREMENT_UNIT = '中国航天工业集团公司北京长城计量测试技术研究所';
         */
        // 修改经办人 资料负责人 负责人
        List<User> list =  userFeign.getAllUser();
        List<VAssetsParameter> list1 = assetsParameterMapper.selectAll();
        for (VAssetsParameter assetsParameter:list1) {
            for (User user:list ) {
                if(null != assetsParameter.getPrincipal() && user.getName().indexOf(assetsParameter.getPrincipal()) != -1){
                    assetsParameter.setPrincipal(user.getId());

                }
                if(null != assetsParameter.getDataManager() && user.getName().indexOf(assetsParameter.getDataManager())!= -1){
                    assetsParameter.setDataManager(user.getId());
                }

                if(null != assetsParameter.getAgent() && user.getName().indexOf(assetsParameter.getAgent())!= -1){
                    assetsParameter.setAgent(user.getId());
                }
            }
            assetsParameter.setUnifiedCodeOrder(assetsParameter.getUnifiedCode().substring(3));
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }


        // 更新核查截止日期
        /*List<VAssetsParameter> list3 = assetsParameterMapper.selectAll();
        for (VAssetsParameter paraemter :list3) {
            Calendar c = Calendar.getInstance();//获得一个日历的实例
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            if(null != paraemter.getPeriodDate() && null != paraemter.getPeriodCheckCycle()){
                try{
                    date = sdf.parse(sdf.format(paraemter.getPeriodDate()));//初始日期
                }catch(Exception e){
                }
                c.setTime(date);//设置日历时间
                c.add(Calendar.MONTH,paraemter.getPeriodCheckCycle().intValue());//在日历的月份上增加6个月

                paraemter.setPeriodDateStop(c.getTime());
                assetsParameterMapper.updateByPrimaryKeySelective(paraemter);
            }
        }*/
        List<VAssetsParameter> list4 = assetsParameterMapper.selectAll();
        for (VAssetsParameter paraemter :list4) {
            Calendar c = Calendar.getInstance();//获得一个日历的实例
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            if(null == paraemter.getMeasurementValidity() &&  paraemter.getMeasurementCycle()>0 && null != paraemter.getRecentMeasurementPlanTime()){

                c.setTime(paraemter.getRecentMeasurementPlanTime());//设置日历时间
                c.add(Calendar.MONTH,paraemter.getMeasurementCycle());//在日历的月份上增加6个月
                System.out.println(c.getTime());
                paraemter.setMeasurementValidity(c.getTime());
                assetsParameterMapper.updateByPrimaryKeySelective(paraemter);
            }
        }

//        list2.forEach(paraemter ->{
//            Calendar c = Calendar.getInstance();//获得一个日历的实例
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = null;
//            if(null != paraemter.getPeriodDate()){
//                try{
//                    date = sdf.parse(sdf.format(paraemter.getPeriodDate()));//初始日期
//                }catch(Exception e){
//                }
//                c.setTime(date);//设置日历时间
//                c.add(Calendar.MONTH,paraemter.getPeriodCheckCycle().intValue());//在日历的月份上增加6个月
//
//                paraemter.setPeriodDateStop(c.getTime());limsQuery
//                assetsParameterMapper.updateByPrimaryKeySelective(paraemter);
//            }
//
//        });
//
       List<VAssetsParameter> list2 = assetsParameterMapper.selectAll();
        List<DictValue> dictValuesList = dictFeign.getDictValuesAll();


        for (VAssetsParameter assetsParameter:list2) {
            for (DictValue dictValue:dictValuesList ) {
                if(null != assetsParameter.getFacilityDesignatedArea() && dictValue.getLabelDefault().equals(assetsParameter.getFacilityDesignatedArea().trim())){
                    assetsParameter.setFacilityDesignatedArea(dictValue.getValue());
                }
                if(null != assetsParameter.getMeasurementUnit() && dictValue.getLabelDefault().equals(assetsParameter.getMeasurementUnit().trim())){
                    assetsParameter.setMeasurementUnit(dictValue.getValue());
                }
            }
            assetsParameter.setUnifiedCodeOrder(assetsParameter.getUnifiedCode().substring(3));
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }

    }


    public static void main(String []args){
        Calendar c = Calendar.getInstance();//获得一个日历的实例

        Date date = null;
        try{
            date = sdf.parse("2016-12-31");//初始日期
        }catch(Exception e){
        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,3);//在日历的月份上增加6个月
        System.out.println(sdf.format(c.getTime()));//得到6个月后的日期
    }

    public List<LimsVAssetsParameterVo>  limsQuery(String devicename, String unitType, String unifiedCode,String equipmentNumber){
        List<LimsVAssetsParameterVo> resultList = new ArrayList<>();
        Example example = new Example(VAssetsParameter.class);
        /*Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(devicename)){
            criteria.andLike("devicename",devicename+"%");
        }

        if(StringUtils.isNotEmpty(unitType)){
            criteria.andLike("unitType",unitType+"%");
        }

        if(StringUtils.isNotEmpty(unifiedCode)){
            criteria.andLike("unifiedCode",unifiedCode+"%");
        }*/

        //criteria.andEqualTo("facitityStatus","0").andEqualTo("facitityCategory","1");


        Example.Criteria criteria1 = example.createCriteria();
        if(StringUtils.isNotEmpty(devicename)){
            criteria1.andLike("devicename","%"+devicename+"%");
        }

        if(StringUtils.isNotEmpty(unitType)){
            criteria1.andLike("unitType","%"+unitType+"%");
        }

        if(StringUtils.isNotEmpty(unifiedCode)){
            criteria1.andLike("unifiedCode","%"+unifiedCode+"%");
        }

        if(StringUtils.isNotEmpty(equipmentNumber)){
            criteria1.andLike("equipmentNumber","%"+equipmentNumber+"%");
        }
        criteria1.andEqualTo("facitityStatus","0").andEqualTo("facitityCategory","1");

        Example.Criteria criteria2 = example.createCriteria();
        if(StringUtils.isNotEmpty(devicename)){
            criteria2.andLike("devicename",devicename+"%");
        }

        if(StringUtils.isNotEmpty(unitType)){
            criteria2.andLike("unitType",unitType+"%");
        }

        if(StringUtils.isNotEmpty(unifiedCode)){
            criteria2.andLike("unifiedCode",unifiedCode+"%");
        }

        if(StringUtils.isNotEmpty(equipmentNumber)){
            criteria1.andLike("equipmentNumber","%"+equipmentNumber+"%");
        }
        criteria2.andEqualTo("facitityStatus","0").andEqualTo("facitityCategory","2");

        Example.Criteria criteria3 = example.createCriteria();
        if(StringUtils.isNotEmpty(devicename)){
            criteria3.andLike("devicename",devicename+"%");
        }

        if(StringUtils.isNotEmpty(unitType)){
            criteria3.andLike("unitType",unitType+"%");
        }

        if(StringUtils.isNotEmpty(unifiedCode)){
            criteria3.andLike("unifiedCode",unifiedCode+"%");
        }

        if(StringUtils.isNotEmpty(equipmentNumber)){
            criteria1.andLike("equipmentNumber","%"+equipmentNumber+"%");
        }
        criteria3.andEqualTo("facitityStatus","0").andEqualTo("facitityCategory","3");
//        example.or(criteria);
        example.or(criteria1);
        example.or(criteria2);
        example.or(criteria3);
        logger.info("lims模糊查询开始");
        List<VAssetsParameter> list = assetsParameterMapper.selectByExample(example);
        logger.info("lims模糊查询结束");
        list.stream().forEach(v ->{
            LimsVAssetsParameterVo vo = new LimsVAssetsParameterVo();
            vo.setUnifiedCode(v.getUnifiedCode());
            vo.setDevicename(v.getDevicename());
            vo.setId(v.getId());
            vo.setUnitType(v.getUnitType());
            vo.setEquipmentNumber(v.getEquipmentNumber());
            if(null != v.getMeasurementValidity()){
                vo.setPeriodDate(sdf.format(v.getMeasurementValidity()));
            }else{
                vo.setPeriodDate("");
            }
            resultList.add(vo);
        });
        return resultList;
    }

    public List<VAssetsParameter> queryYearMeasurementCost(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<VAssetsParameter> list = assetsParameterMapper.queryYearMeasurementCost(year+"");
        return list;
    }

    public VAssetsParameter customSelectById(Object id) {
        
        try {
        	VAssetsParameter model = mapper.selectByPrimaryKey(id);
            List<User> userList = userFeign.getAllUser();
            logger.info("--------------结束远程获取人员");
            if(!CollectionUtils.isEmpty(userList)){
                // 将userlist转换为Map
                Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
                //map中获取对应值
                if(null != model.getPrincipal() && null != map.get(model.getPrincipal())){
                    model.setPrincipal(map.get(model.getPrincipal()).getName());
                }else{
                    model.setPrincipal("");
                }

                if(null != model.getAgent() && null != map.get(model.getAgent())){
                    model.setAgent(map.get(model.getAgent()).getName());
                }else{
                    model.setAgent("");
                }

                if(null != model.getDataManager() && null != map.get(model.getDataManager())){
                    model.setDataManager(map.get(model.getDataManager()).getName());
                }else{
                    model.setDataManager("");
                }

            }

            logger.info("--------------开始远程获取部门");
            List<Depart> deptList = deptFeign.getDeptAll();
            if(!CollectionUtils.isEmpty(deptList)){
                Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
                Depart dept = map.get(model.getEquipmentDepartment());
                if(null == dept){
                    throw new BusinessRuntimeException("当前数据部门获取失败");
                }
                model.setEquipmentDepartment(dept.getName());
            }
            logger.info("--------------结束远程获取部门");
            List<VAssetsParameter> listMerge = new ArrayList();
            listMerge.add(model);
            mergeCor.mergeResult(VAssetsParameter.class,listMerge);

            List<VMeteringMethod> methodList =  meteringMethodBiz.findList();
            Map<String,String> map = methodList.stream().collect(Collectors.toMap(dept -> dept.getId(),dept -> dept.getMethodName()));
            model.setMeasurementGistName(map.get(model.getMeasurementGistName()));
            model.setMeasurementGistCode(map.get(model.getMeasurementGistCode()));
            return model;
    	} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ExecutionException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        return null;
    }
    
    public VAssetsParameter customSelectByCode(Object code) {
    	Example example = new Example(VAssetsParameter.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("unifiedCode",code);
        List<VAssetsParameter> assetsParameter2 = assetsParameterMapper.selectByExample(criteria1);
        
        try {
            mergeCor.mergeResult(VAssetsParameter.class,assetsParameter2);
    	} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ExecutionException e) {
    		e.printStackTrace();
    	}
        return assetsParameter2.get(0);
    }

    public VAssetsParameterVo getVAssetsParameterByUnifiedCode(String unifiedCode){
        Example example = new Example(VAssetsParameter.class);
        example.createCriteria().andEqualTo("unifiedCode",unifiedCode);
        List<VAssetsParameter> list = assetsParameterMapper.selectByExample(example);
        VAssetsParameterVo vo = new VAssetsParameterVo();
         if(!CollectionUtils.isEmpty(list)){
             List<User> userList = userFeign.getAllUser();
             logger.info("--------------结束远程获取人员");
             if(!CollectionUtils.isEmpty(userList)){
                 // 将userlist转换为Map
                 Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
                 //map中获取对应值
                 list.stream().map(v ->{
                     if(null != v.getPrincipal() && null != map.get(v.getPrincipal())){
                         v.setPrincipal(map.get(v.getPrincipal()).getName());
                     }else{
                         v.setPrincipal("");
                     }

                     if(null != v.getAgent() && null != map.get(v.getAgent())){
                         v.setAgent(map.get(v.getAgent()).getName());
                     }else{
                         v.setAgent("");
                     }

                     if(null != v.getDataManager() && null != map.get(v.getDataManager())){
                         v.setDataManager(map.get(v.getDataManager()).getName());
                     }else{
                         v.setDataManager("");
                     }
                     return v;
                 }).collect(Collectors.toList());
             }

             logger.info("--------------开始远程获取部门");
             List<Depart> deptList = deptFeign.getDeptAll();
             if(!CollectionUtils.isEmpty(deptList)){
                 Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
                 Depart dept = map.get(list.get(0).getEquipmentDepartment());
                 if(null == dept){
                     throw new BusinessRuntimeException("当前数据部门获取失败");
                 }
                 list.get(0).setEquipmentDepartment(dept.getName());
             }
             logger.info("--------------结束远程获取部门");
             try {
                 mergeCor.mergeOne(VAssetsParameter.class,list.get(0));
             } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ExecutionException e) {
                 e.printStackTrace();
             }
             org.springframework.beans.BeanUtils.copyProperties(list.get(0),vo);
        }
        return vo;
    }
    
    /**
     * 待确认查询
     * @param vo
     */
    public TableResultResponse<VAssetsParameter> queryWaitCommit(Map<String, Object> params,String id){
        //查询条件：变更主表负责人是登录账号，状态是待确认，id为变更主表id
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("principal",id);
        paramMmap.put("assetsStatus",AssetsStatus.CONFIRM_CHANGE.getCode());
        List<VAssetsParameter> list =assetsParameterMapper.selectChangePageList(paramMmap);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }
    
    /**
     * 设备归还确认查询
     * @param vo
     */
    public TableResultResponse<VAssetsReceiveVo> queryGiveBackAffirm(Map<String, Object> params,String id){
        //1.查询条件 存放地为仪器库，负责人为空，部门为当前登录人的部门（设备部）,归还主表有一条数据为归还待确认的数据
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        paramMmap.put("mainPrincipal",id);
        paramMmap.put("crtUserId",id);
        //paramMmap.put("facilityDesignatedArea",DesignatedArea.YQK.getCode());
        paramMmap.put("equipmentDepartment",Constant.SBYWB);
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.RETURN.getCode());
        paramMmap.put("assetStatusList",assetStatusList);

        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }
    
    //查询领取、归还、拒绝详情通过主表id
    public VAssetsParameter customSelectByMainId(Object id) {
        try {
        	VAssetsReceiveMain assetsReceiveMain = vAssetsReceiveMainMapper.selectByPrimaryKey(id);
        	if(org.springframework.util.StringUtils.isEmpty(assetsReceiveMain)) {
        		
        	}else {
	            VAssetsParameter model = assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
                List<User> userList = userFeign.getAllUser();
                logger.info("--------------结束远程获取人员");
                if(!CollectionUtils.isEmpty(userList)){
                    // 将userlist转换为Map
                    Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
                    //map中获取对应值
                        if(null != model.getPrincipal() && null != map.get(model.getPrincipal())){
                            model.setPrincipal(map.get(model.getPrincipal()).getName());
                        }else{
                            model.setPrincipal("");
                        }

                    if(null != model.getAgent() && null != map.get(model.getAgent())){
                        model.setAgent(map.get(model.getAgent()).getName());
                    }else{
                        model.setAgent("");
                    }

                    if(null != model.getDataManager() && null != map.get(model.getDataManager())){
                        model.setDataManager(map.get(model.getDataManager()).getName());
                    }else{
                        model.setDataManager("");
                    }

                }

                logger.info("--------------开始远程获取部门");
                List<Depart> deptList = deptFeign.getDeptAll();
                if(!CollectionUtils.isEmpty(deptList)){
                    Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
                    Depart dept = map.get(model.getEquipmentDepartment());
                    if(null == dept){
                        throw new BusinessRuntimeException("当前数据部门获取失败");
                    }
                    model.setEquipmentDepartment(dept.getName());
                }
                logger.info("--------------结束远程获取部门");
	            List<VAssetsParameter> listMerge = new ArrayList();
	            listMerge.add(model);
	            mergeCor.mergeResult(VAssetsParameter.class,listMerge);
	            return model;
        	}
    	} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ExecutionException e) {
    		e.printStackTrace();
    	}
        
        return null;
    }
    
    //查询拒绝原因通过主表id
    public VAssetsReceiveMain customSelectYuanYinByMainId(Object id) {
        	VAssetsReceiveMain assetsReceiveMain = vAssetsReceiveMainMapper.selectByPrimaryKey(id);
        	if(org.springframework.util.StringUtils.isEmpty(assetsReceiveMain)) {
        		return null;
        	}else {
	            return assetsReceiveMain;
        	}
    }
    
    /**
     * 查询拒绝待处理数据
     * @param params
     * @return
     */
    public TableResultResponse<VAssetsReceiveVo> queryRejectData(Map<String, Object> params,String id){
        // 查询条件：设备主表和变更主表状态都是：拒绝
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        Map<String, Object> paramMmap = new HashMap<>();
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.REJECT.getCode());
        paramMmap.put("assetStatusList",assetStatusList);
        paramMmap.put("crtUserId",id);
        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);

    }
    
    /**
     * 确认
     * @param vo
     */
    public void affirm(VAssetsParameterVo vo,String uname){
        // 修改变更主表状态为已确认 此处vo里面的id为变更主表的id
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsReceiveMain){
            assetsReceiveMain.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            EntityUtils.setUpdatedInfo(assetsReceiveMain);
            assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

            // 修改设备主表状态为已领用，负责人为当前账号
            VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
            if(null != assetsParameter){
                    assetsParameter.setPrincipal(vo.getPrincipal());
                    if(EquipmentSubordinate.FOLLOW_THE_PERSONNEL.getCode().equals(assetsParameter.getEquipmentSubordinate())){
                        assetsParameter.setEquipmentDepartment(vo.getEquipmentDepartment());
                    }
                    assetsParameter.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
                    EntityUtils.setUpdatedInfo(assetsParameter);
                    assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
            }else{
                throw new BusinessRuntimeException("设备数据不存在，请检查！");
            }

            // 变更记录表增加一条确认数据
            VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
            vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
            vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
            vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
            EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
            vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            vAssetsReceiveRecords.setRecipient(vo.getPrincipal());
            assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
          MessagePush.sendPush(assetsReceiveMain.getCrtUserId(), "您给【"+uname+"】配发的设备已领取，请知悉！");
          saveMessage("您给【"+uname+"】配发的设备已领取，请知悉！",assetsReceiveMain.getCrtUserId());
        }else{
            throw new BusinessRuntimeException("数据不存在，请执行查询后查看数据是否存在！");
        }
    }
    
    public void saveMessage(String content,String uid) {
   	 AppSystemMessage appSystemMessage=new AppSystemMessage();
        appSystemMessage.setId(UUIDUtils.generateUuid());
        appSystemMessage.setTitle("通知");
        appSystemMessage.setContent(content);
        appSystemMessage.setStatus("0");
        appSystemMessage.setUserId(uid);
        EntityUtils.setCreatAndUpdatInfo(appSystemMessage);
        appSystemMessageMapper.insert(appSystemMessage);
   }
    
    /**
     * 拒绝
     * @param vo
     */
    public void reject(VAssetsParameterVo vo,String uname){
        // 修改主表状态为拒绝 保存拒绝原因
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsReceiveMain){
            assetsReceiveMain.setAssetsStatus(AssetsStatus.REJECT.getCode());
            assetsReceiveMain.setRemark(vo.getRemark());
            EntityUtils.setUpdatedInfo(assetsReceiveMain);
            assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

            // 修改资产主表状态为拒绝
            VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
            if(null != assetsParameter){
                assetsParameter.setAssetsStatus(AssetsStatus.REJECT.getCode());
                EntityUtils.setUpdatedInfo(assetsParameter);
                assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
            }

            // 记录表保存一条拒绝数据
            VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
            vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
            vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
            vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
            EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
            vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.REJECT.getCode());
            vAssetsReceiveRecords.setRecipient(vo.getInputer());
            assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
          MessagePush.sendPush(assetsReceiveMain.getCrtUserId(), "您给【"+uname+"】配发的设备已拒绝，请确认！");
          saveMessage("您给【"+uname+"】配发的设备已拒绝，请确认！",assetsReceiveMain.getCrtUserId());
        }
    }
    
    /**
     * 设备拒绝归库
     * @param assetsId
     */
    public void cancel(String assetsId){
        /*
        1.修改设备主表状态为录入
        2.修改变更主表状态为已完成
        3.变更记录表新增一条数据为归库
         */
        VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(assetsId);
        if(null != assetsParameter){
            assetsParameter.setAssetsStatus(AssetsStatus.ENTERING.getCode());
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
            Example example = new Example(VAssetsReceiveMain.class);
            example.createCriteria().andEqualTo("assetsId",assetsParameter.getId()).andEqualTo("assetsStatus",AssetsStatus.REJECT.getCode());
            List<VAssetsReceiveMain> mainList = assetsReceiveMainMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(mainList)) {
                VAssetsReceiveMain vAssetsReceiveMain = mainList.get(0);
                vAssetsReceiveMain.setAssetsStatus(AssetsStatus.IN_STORAGE.getCode());
                assetsReceiveMainMapper.updateByPrimaryKeySelective(vAssetsReceiveMain);
                //变更记录表增加一条记录
                VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
                vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
                vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
                vAssetsReceiveRecords.setAssetsReceiveMainId(vAssetsReceiveMain.getId());
                EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.IN_STORAGE.getCode());
//                vAssetsReceiveRecords.setRecipient(vo.getPrincipal());
                assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
            }
        }
    }
    
    /**
     * 设备归还查询
     * @param vo
     */
    public TableResultResponse<VAssetsParameter> queryGiveBack(Map<String, Object> params,String uid){
        //查询条件为： 查询设备主表负责人是当前登录人即可
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        paramMmap.put("principal",uid);
        List statusList = new ArrayList();
        statusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
        statusList.add(AssetsStatus.ENTERING.getCode());
        paramMmap.put("assetStatusList",statusList);

        List<VAssetsParameter> list =assetsParameterMapper.selectAssetsParameterAll(paramMmap);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }
    
    /**
     * 设备归还
     * @param vo
     */
    public void giveBack(VAssetsParameterVo vo,String uname){
        //1.修改设备负责人为空，存放地为仪器库，部门为空，状态为归还待确认
        VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsParameter){
            assetsParameter.setPrincipal("");
            assetsParameter.setEquipmentDepartment(Constant.SBYWB);
//            assetsParameter.setFacilityDesignatedArea(DesignatedArea.YQK.getCode());
            assetsParameter.setAssetsStatus(AssetsStatus.RETURN.getCode());
            assetsParameter.setRemark(vo.getRemark());
            //EntityUtils.setUpdatedInfo(assetsParameter);
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }
        //2.归还主表新增一条信息，状态为归还待确认
        VAssetsReceiveMain vAssetsReceiveMain = new VAssetsReceiveMain();
        vAssetsReceiveMain.setId(UUIDUtils.generateUuid());
        EntityUtils.setUpdatedInfo(vAssetsReceiveMain);
        //创建人为配发人
        if(null != assetsParameter.getHandingOutPeople()){
            vAssetsReceiveMain.setCrtUserId(assetsParameter.getHandingOutPeople());
            vAssetsReceiveMain.setCrtTime(new Date());
            List<User> userList = userFeign.getAllUser();
            logger.info("--------------结束远程获取人员");
            if(!CollectionUtils.isEmpty(userList)){
                // 将userlist转换为Map
                Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
                logger.info("--------------获取人员名称："+map.get(assetsParameter.getHandingOutPeople()).getName());
                vAssetsReceiveMain.setCrtUserName(map.get(assetsParameter.getHandingOutPeople()).getName());
            }
        }
        
        vAssetsReceiveMain.setAssetsId(assetsParameter.getId());
        vAssetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN.getCode());
        vAssetsReceiveMain.setReturnPeople(vo.getInputer());
        vAssetsReceiveMain.setRecipientTime(new Date());
        assetsReceiveMainMapper.insertSelective(vAssetsReceiveMain);

        //3.明细记录表记录一条归还信息
        VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
        vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
        vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
        vAssetsReceiveRecords.setAssetsReceiveMainId(vAssetsReceiveMain.getId());
        EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
        vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.RETURN.getCode());
        vAssetsReceiveRecords.setRecipient(vo.getPrincipal());
        assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
        MessagePush.sendPush(assetsParameter.getHandingOutPeople(), "您给【"+uname+"】配发的设备已归还，请确认！");
        saveMessage("您给【"+uname+"】配发的设备已归还，请确认！",assetsParameter.getHandingOutPeople());
    }
    
    //查询消息
    public TableResultResponse<AppSystemMessage> queryMessageList(Map<String, Object> params){
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map paramMmap = new HashMap<>();
        List<AppSystemMessage> list = null;
       list = appSystemMessageMapper.selectAppSystemMessageByStatus(paramMmap);
        return new TableResultResponse<>(list.size(), list);
    }
    
}