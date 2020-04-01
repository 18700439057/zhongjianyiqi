package com.sibo.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VMaintainRecord;
import com.sibo.business.enums.MaintainClassfiy;
import com.sibo.business.enums.MaintainStatus;
import com.sibo.business.enums.WorkType;
import com.sibo.business.feign.WorkFlowFegin;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VMaintainRecordMapper;
import com.sibo.business.vo.VMaintainVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMaintain;
import com.sibo.business.mapper.VMaintainMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 维修主表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Service
@Transactional
public class VMaintainBiz extends BusinessBiz<VMaintainMapper,VMaintain> {

    @Autowired
    private VMaintainRecordMapper maintainRecordMapper;

    @Autowired
    private VMaintainMapper maintainMapper;

    @Autowired
    private WorkFlowFegin workFlowFegin;

    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    @Override
    public void insertSelective(VMaintain entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    /**
     * 维修申请查询
     * @param params
     * @return
     */
    public TableResultResponse<VMaintainVo> queryMaintainApplication(Map<String, Object> params) throws ParseException {
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        List resultList = null;
        //添加查询条件
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("devicename",params.get("devicename"));
        paramMmap.put("proposer",params.get("proposer"));

        paramMmap.put("department",params.get("department"));
        paramMmap.put("errorDescription",params.get("errorDescription"));
        paramMmap.put("status",params.get("status"));
        if(null != params.get("applicationStartTime") && null != params.get("applicationEndTime")){
            paramMmap.put("applicationStartTime",params.get("applicationStartTime"));
            paramMmap.put("applicationEndTime",params.get("applicationEndTime"));
        }
        //区分查询待办/已办
        List statusList = new ArrayList();
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            statusList.add(MaintainStatus.WAIT_SUBMIT.getCode());
            paramMmap.put("waitStatusList",statusList);
        }else{
            statusList.add(MaintainStatus.WAIT_SUBMIT.getCode());
            paramMmap.put("finishStatusList",statusList);

            paramMmap.put("crtUserId",BaseContextHandler.getUserID());
        }
        resultList = maintainMapper.selectMaintain(rb,paramMmap);
        //工作流獲取待辦已辦數據
        //1.使用當前登錄人名稱查詢是否有待辦事項
//        List<String> response = workFlowFegin.getMyApproveTasks(0, 10);
//        for (String sss:response) {
//            System.out.print(sss);
//        }
//        System.out.print(response+":::::::::================================");
        //###########################################

        return new TableResultResponse<VMaintainVo>(resultList.size(), resultList);

    }

    public void insertMaintain(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        maintainVo.setId(UUIDUtils.generateUuid());
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.WAIT_SUBMIT.getCode());
        entity.setDepartment(BaseContextHandler.getDepartID());
        super.insertSelective(entity);

        //測試工作流 可注釋########################################################
        //啟動流程
//        String instanceId = workFlowFegin.startInstance(maintainVo.getId());
//        System.out.print("流程id为==============:"+instanceId);
        //#測試工作流 可注釋########################################################
        //记录表数据保存
        saveRecord(maintainVo,MaintainStatus.WAIT_SUBMIT.getCode());
    }

    /**
     * 提交维修
     * @param maintainVo
     */
    public void submitMaintain(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.CONFIRMATION.getCode());
        super.updateSelectiveById(entity);

        //记录表数据保存
        saveRecord(maintainVo,MaintainStatus.CONFIRMATION.getCode());
    }

    /**
     * 运维部确认查询
     * @param params
     * @return
     */
    public TableResultResponse<VMaintainVo> queryMaintainConfirm(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        List resultList = null;
        //添加查询条件
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("devicename",params.get("devicename"));
        paramMmap.put("proposer",params.get("proposer"));
        paramMmap.put("applicationTime",params.get("applicationTime"));
        paramMmap.put("department",params.get("department"));
        paramMmap.put("waiterUseTime",params.get("waiterUseTime"));

        paramMmap.put("status",params.get("status"));

        if(null != params.get("applicationStartTime") && null != params.get("applicationEndTime")){
            paramMmap.put("applicationStartTime",params.get("applicationStartTime"));
            paramMmap.put("applicationEndTime",params.get("applicationEndTime"));
        }
        if(null != params.get("waiterStartTime") && null != params.get("waiterEndTime")){
            paramMmap.put("waiterStartTime",params.get("waiterStartTime"));
            paramMmap.put("waiterEndTime",params.get("waiterEndTime"));

        }

        //区分查询待办/已办
        List statusList = new ArrayList();
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            statusList.add(MaintainStatus.CONFIRMATION.getCode());
            statusList.add(MaintainStatus.MAINTENANCE.getCode());
            statusList.add(MaintainStatus.COMPLETE_MAINTENANCE_REJECT.getCode());
            paramMmap.put("waitStatusList",statusList);
        }else{
            statusList.add(MaintainStatus.CONFIRMATION.getCode());
            statusList.add(MaintainStatus.MAINTENANCE.getCode());
            paramMmap.put("finishStatusList",statusList);

            paramMmap.put("maintainConfirmPerson",BaseContextHandler.getUserID());
        }
        resultList = maintainMapper.selectMaintain(rb,paramMmap);
        return new TableResultResponse<VMaintainVo>(resultList.size(), resultList);

    }

    /**
     * 维修确认
     * @param maintainVo
     */
    public void MaintenanceConfirm(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.MAINTENANCE.getCode());
        entity.setMaintainConfirmPerson(BaseContextHandler.getUserID());
        entity.setMaintainConfirmTime(new Date());
        super.updateSelectiveById(entity);

        //记录表数据保存
        saveRecord(maintainVo,MaintainStatus.MAINTENANCE.getCode());
    }

    /**
     * 维修完成
     * @param maintainVo
     */
    public void MaintenanceCompleted(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.COMPLETE_MAINTENANCE.getCode());
        entity.setMaintainConfirmPerson(BaseContextHandler.getUserID());
        entity.setMaintainConfirmTime(new Date());
        super.updateSelectiveById(entity);

        //统计设备维修费用和次数
        VAssetsParameter vAssetsParameter = assetsParameterMapper.selectByPrimaryKey(maintainVo.getAssetId());
//        VAssetsParameter vAssetsParameter = new VAssetsParameter();
        if(MaintainClassfiy.CRUX.getCode().equals(maintainVo.getMaintainClassfiy())){

            //关键维修次数
            if(null != vAssetsParameter.getCruxMaintainNum()){
                vAssetsParameter.setCruxMaintainNum(vAssetsParameter.getCruxMaintainNum()+1);
            }else{
                vAssetsParameter.setCruxMaintainNum(1);
            }

        }else if(MaintainClassfiy.EVERYDAY.getCode().equals(maintainVo.getMaintainClassfiy())){
            //日常维修次数
            if(null != vAssetsParameter.getEveryDayMaintainNum()){
                vAssetsParameter.setEveryDayMaintainNum(vAssetsParameter.getEveryDayMaintainNum()+1);
            }else{
                vAssetsParameter.setEveryDayMaintainNum(1);
            }

        }else{
            //维修费用
            if(null != vAssetsParameter.getCharge()){
                vAssetsParameter.setCharge(vAssetsParameter.getCharge()+maintainVo.getCostEstimate());
            }else{
                vAssetsParameter.setCharge(0.00);
            }

        }
        vAssetsParameter.setId(maintainVo.getAssetId());
        assetsParameterMapper.updateByPrimaryKeySelective(vAssetsParameter);

        saveRecord(maintainVo,MaintainStatus.COMPLETE_MAINTENANCE.getCode());
    }

    /**
     * 申请人确认查询
     * @param params
     * @return
     */
    public TableResultResponse<VMaintainVo> proposterListPage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        List resultList = null;
        //添加查询条件
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("devicename",params.get("devicename"));
        paramMmap.put("proposer",params.get("proposer"));
        paramMmap.put("department",params.get("department"));
        paramMmap.put("waiterUseTime",params.get("waiterUseTime"));
        paramMmap.put("errorDescription",params.get("errorDescription"));
        if(null != params.get("applicationStartTime") && null != params.get("applicationEndTime")){
            paramMmap.put("applicationStartTime",params.get("applicationStartTime"));
            paramMmap.put("applicationEndTime",params.get("applicationEndTime"));
        }

        paramMmap.put("status",params.get("status"));
        if(null != params.get("waiterStartTime") && null != params.get("waiterEndTime")){
            paramMmap.put("waiterStartTime",params.get("waiterStartTime"));
            paramMmap.put("waiterEndTime",params.get("waiterEndTime"));
        }

        //区分查询待办/已办
        List statusList = new ArrayList();
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            statusList.add(MaintainStatus.COMPLETE_MAINTENANCE.getCode());
            paramMmap.put("waitStatusList",statusList);
        }else{
            statusList.add(MaintainStatus.COMPLETE_MAINTENANCE_CONFIRM.getCode());
            statusList.add(MaintainStatus.COMPLETE_MAINTENANCE_REJECT.getCode());
            paramMmap.put("waitStatusList",statusList);

            paramMmap.put("proposerConfirm",BaseContextHandler.getUserID());
        }
        resultList = maintainMapper.selectMaintain(rb,paramMmap);
        return new TableResultResponse<VMaintainVo>(resultList.size(), resultList);
    }

    /**
     * 申请人完成维修通过
     * @param maintainVo
     */
    public void MaintenancePass(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.COMPLETE_MAINTENANCE_CONFIRM.getCode());
        entity.setProposerConfirm(BaseContextHandler.getUserID());
        entity.setProposerConfirmTime(new Date());
        super.updateSelectiveById(entity);

        saveRecord(maintainVo,MaintainStatus.COMPLETE_MAINTENANCE_CONFIRM.getCode());
    }

    /**
     * 申请人完成维修拒绝
     * @param maintainVo
     */
    public void MaintenanceReject(VMaintainVo maintainVo){
        VMaintain entity = new VMaintain();
        BeanUtils.copyProperties(maintainVo,entity);
        entity.setStatus(MaintainStatus.COMPLETE_MAINTENANCE_REJECT.getCode());
        entity.setProposerConfirm(BaseContextHandler.getUserID());
        entity.setProposerConfirmTime(new Date());
        super.updateSelectiveById(entity);

        saveRecord(maintainVo,MaintainStatus.COMPLETE_MAINTENANCE_REJECT.getCode());
    }



    public void saveRecord(VMaintainVo entity,String status){
        //记录表数据保存
        VMaintainRecord maintainRecord = new VMaintainRecord();
        maintainRecord.setId(UUIDUtils.generateUuid());
        maintainRecord.setMaintainId(entity.getId());
        maintainRecord.setAssetId(entity.getAssetId());
        maintainRecord.setStatus(status);
        EntityUtils.setCreatAndUpdatInfo(maintainRecord);
        maintainRecordMapper.insertSelective(maintainRecord);
    }

    public TableResultResponse<VAssetsParameter> maintainListPage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        List resultList = null;
        //添加查询条件
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("unifiedCode",params.get("unifiedCode"));
        paramMmap.put("devicename",params.get("devicename"));
        paramMmap.put("unitType",params.get("unitType"));
        paramMmap.put("facitityStatus",params.get("facitityStatus"));
        resultList = assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);
        return new TableResultResponse<VAssetsParameter>(resultList.size(), resultList);
    }




}