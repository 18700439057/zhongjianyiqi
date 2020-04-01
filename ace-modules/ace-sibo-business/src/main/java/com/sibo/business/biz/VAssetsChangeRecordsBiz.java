package com.sibo.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VAssetsChange;
import com.sibo.business.entity.VAssetsChangeRecords;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.enums.AssetsStatus;
import com.sibo.business.enums.WorkType;
import com.sibo.business.mapper.VAssetsChangeMapper;
import com.sibo.business.mapper.VAssetsChangeRecordsMapper;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.vo.VAssetsChangeRecordsVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 资产变更记录
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-22 14:22:08
 */
@Service
@Transactional
public class VAssetsChangeRecordsBiz extends BusinessBiz<VAssetsChangeRecordsMapper,VAssetsChangeRecords> {

    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    @Autowired
    private VAssetsChangeRecordsMapper assetsChangeRecordsMapper;

    @Autowired
    private VAssetsChangeMapper assetsChangeMapper;


    @Override
    public void insertSelective(VAssetsChangeRecords entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public void  insertAssetsChange(VAssetsChangeRecordsVo record){
        //更新台账信息表
        VAssetsParameter assetsParameter = new VAssetsParameter();
        BeanUtils.copyProperties(record,assetsParameter);
        assetsParameter.setAssetsStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
        assetsParameter.setUpdTime(new Date());
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        Map map = new HashMap();
        map.put("id",record.getId());
        map.put("assetsStatus",AssetsStatus.CONFIRM_CHANGE.getCode());
        Integer version = assetsChangeRecordsMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //插入变更主表
        VAssetsChange assetsChange = new VAssetsChange();
        assetsChange.setId(UUIDUtils.generateUuid());
        assetsChange.setAssetsId(record.getId());
        assetsChange.setAssetsStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
        EntityUtils.setCreatAndUpdatInfo(assetsChange);
        assetsChangeMapper.insertSelective(assetsChange);

        //插入变更记录表
        this.insertRecord(record.getId(),assetsChange.getId(),assetsChange.getAssetsStatus(),version+"");

    }

    /**
     * 插入变更记录表
     * @param assetId
     * @param changeId
     * @param status
     * @param version
     */
    public void insertRecord(String assetId,String changeId,String status,String version){
        VAssetsChangeRecords assetsChangeRecords = new VAssetsChangeRecords();
        assetsChangeRecords.setAssetsStatus(status);
        assetsChangeRecords.setAssetsId(assetId);
        assetsChangeRecords.setAssetsChangeMainId(changeId);
        assetsChangeRecords.setId(UUIDUtils.generateUuid());
        assetsChangeRecords.setVersion(version+"");
        super.insertSelective(assetsChangeRecords);
    }

    /**
     * 统计查询变更记录
     * @param params
     * @return
     */
    public TableResultResponse<VAssetsChangeRecords> queryAssetsChangePageAll(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        Map paramMmap = new HashMap<>();
        if(!StringUtils.isEmpty(params.get("devicename"))){
            paramMmap.put("devicename",params.get("devicename"));
        }

        if(!StringUtils.isEmpty(params.get("unifiedCode"))){
            paramMmap.put("unifiedCode",params.get("unifiedCode"));
        }

        if(!StringUtils.isEmpty(params.get("unitType"))){
            paramMmap.put("unitType",params.get("unitType"));
        }

        if(!StringUtils.isEmpty(params.get("assetsClass"))){
            paramMmap.put("assetsClass",params.get("assetsClass"));
        }

        if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
            paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
        }

        if(!StringUtils.isEmpty(params.get("equipmentDepartment"))){
            paramMmap.put("equipmentDepartment",params.get("equipmentDepartment"));
        }

        if(!StringUtils.isEmpty(params.get("facitityStatus"))){
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
        }


        List<VAssetsChangeRecords> list = assetsChangeRecordsMapper.selectRecordAll(rb,paramMmap);
        return new TableResultResponse<VAssetsChangeRecords>(list.size(), list);
    }


    public TableResultResponse<VAssetsChangeRecords> queryAssetsChangePage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        Map paramMmap = new HashMap<>();
        paramMmap.put("devicename",params.get("devicename"));
        List<VAssetsChangeRecords> list = null;
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            //待办工作
            List recordStatusList = new ArrayList();
            recordStatusList.add(AssetsStatus.CONFIRM_CHANGE.getCode());
            recordStatusList.add(AssetsStatus.REJECT.getCode());
            paramMmap.put("recordStatusList",recordStatusList);

            List statusList = new ArrayList();
            statusList.add(AssetsStatus.CONFIRM_CHANGE.getCode());
            statusList.add(AssetsStatus.REJECT.getCode());
            paramMmap.put("assetStatusList",statusList);
            list = assetsChangeRecordsMapper.selectall(rb,paramMmap);
        }else{

            List recordStatusList = new ArrayList();
            recordStatusList.add(AssetsStatus.CHANGE_FINISH.getCode());
            recordStatusList.add(AssetsStatus.REJECT.getCode());
            recordStatusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            paramMmap.put("recordStatusList",recordStatusList);

            paramMmap.put("crtUserId",BaseContextHandler.getUserID());
            list = assetsChangeRecordsMapper.selectall(rb,paramMmap);
        }

        return new TableResultResponse<VAssetsChangeRecords>(list.size(), list);

    }

    /**
     * 查询变更确认分页数据
     * @return
     */
    public TableResultResponse<VAssetsChangeRecords> queryChangeConfirmPage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("offset").toString()), Integer.parseInt(params.get("limit").toString()));
        Map paramMmap = new HashMap<>();
        paramMmap.put("devicename",params.get("devicename"));
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            //待办工作
//            paramMmap.put("isBacklog","1");//判断是否待办，如果是待办需要添加查询条件

            List recordStatusList = new ArrayList();
            recordStatusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            paramMmap.put("recordStatusList",recordStatusList);

            List statusList = new ArrayList();
            statusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            paramMmap.put("assetStatusList",statusList);
        }else{

            List recordStatusList = new ArrayList();
            recordStatusList.add(AssetsStatus.CHANGE_FINISH.getCode());
            recordStatusList.add(AssetsStatus.REJECT.getCode());

            paramMmap.put("updUserId",BaseContextHandler.getUserID());
            paramMmap.put("recordStatusList",recordStatusList);
        }

        List<VAssetsChangeRecords> list = assetsChangeRecordsMapper.selectall(rb,paramMmap);
        return new TableResultResponse<VAssetsChangeRecords>(list.size(), list);

    }

    /**
     * 变更编辑
     * @param record
     * @return
     */
    public  int  updateAssetsChange(VAssetsChangeRecordsVo record){
        //更新台账信息表
        VAssetsParameter assetsParameter = new VAssetsParameter();
        BeanUtils.copyProperties(record,assetsParameter);
        assetsParameter.setUpdTime(new Date());
        return assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
    }

    /**
     * 变更提交
     * @param record
     * @return
     */
    public  void  assetsChangeSave(VAssetsChangeRecordsVo record){
        //更新台账信息表
        VAssetsParameter assetsParameter = new VAssetsParameter();
        assetsParameter.setId(record.getAssetsId());
        assetsParameter.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
        assetsParameter.setUpdTime(new Date());
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        Map map = new HashMap();
        map.put("id",record.getAssetsId());
        map.put("assetsStatus",AssetsStatus.CHANGE_CONFIRMATION.getCode());
        Integer version = assetsChangeRecordsMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //更新变更主表
        VAssetsChange assetsChange = assetsChangeMapper.selectByPrimaryKey(record.getId());
        assetsChange.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
        assetsChangeMapper.updateByPrimaryKey(assetsChange);

        //插入变更记录表
        this.insertRecord(record.getAssetsId(),assetsChange.getId(),assetsChange.getAssetsStatus(),version+"");
    }

    /**
     *变更确认
     * @param record
     */
    public void  assetsChangeAffirm(VAssetsChangeRecordsVo record){
        //更新台账信息表
        VAssetsParameter assetsParameter = new VAssetsParameter();
        BeanUtils.copyProperties(record,assetsParameter);
        assetsParameter.setAssetsStatus(AssetsStatus.CHANGE_FINISH.getCode());
        assetsParameter.setUpdTime(new Date());
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        Map map = new HashMap();
        map.put("id",record.getId());
        map.put("assetsStatus",AssetsStatus.CHANGE_FINISH.getCode());
        Integer version = assetsChangeRecordsMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //更新变更主表
        VAssetsChange assetsChange = assetsChangeMapper.selectByPrimaryKey(record.getChangeMainId());
        assetsChange.setAssetsStatus(AssetsStatus.CHANGE_FINISH.getCode());
        EntityUtils.setUpdatedInfo(assetsChange);
        assetsChangeMapper.updateByPrimaryKey(assetsChange);

        //插入变更记录表
        this.insertRecord(assetsChange.getAssetsId(),assetsChange.getId(),assetsChange.getAssetsStatus(),version+"");

    }

    /**
     * 驳回
     * @param record
     */
    public void  assetsChangeReject(VAssetsChangeRecordsVo record){
        //更新台账信息表
        VAssetsParameter assetsParameter = new VAssetsParameter();
        BeanUtils.copyProperties(record,assetsParameter);
        assetsParameter.setAssetsStatus(AssetsStatus.REJECT.getCode());
        assetsParameter.setUpdTime(new Date());
        assetsParameter.setDismissReason(record.getDismissReason());
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        Map map = new HashMap();
        map.put("id",record.getId());
        map.put("assetsStatus",AssetsStatus.REJECT.getCode());
        Integer version = assetsChangeRecordsMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //更新变更主表
        VAssetsChange assetsChange = assetsChangeMapper.selectByPrimaryKey(record.getChangeMainId());
        assetsChange.setAssetsStatus(AssetsStatus.REJECT.getCode());
        EntityUtils.setUpdatedInfo(assetsChange);
        assetsChangeMapper.updateByPrimaryKey(assetsChange);

        //插入变更记录表
        this.insertRecord(assetsChange.getAssetsId(),assetsChange.getId(),assetsChange.getAssetsStatus(),version+"");

    }

    /**
     * 删除
     * @param id
     */
    public  void  deleteAssetsChange(String id){
        VAssetsChangeRecords model = super.selectById(id);

        if(null != model){
            //删除变更记录
            //super.deleteById(id);
            assetsChangeMapper.deleteByPrimaryKey(id);

            Example example = new Example(VAssetsChangeRecords.class);
            example.createCriteria().andEqualTo("assetsChangeMainId",id);
            assetsChangeRecordsMapper.deleteByExample(example);

            //更新资产表数据
            VAssetsParameter assetsParameter = new VAssetsParameter();
            assetsParameter.setId(model.getAssetsId());
            assetsParameter.setAssetsStatus(AssetsStatus.ENTERING.getCode());
            assetsParameter.setUpdTime(new Date());
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }
    }




}