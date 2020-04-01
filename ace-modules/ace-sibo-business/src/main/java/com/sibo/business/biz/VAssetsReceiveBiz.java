package com.sibo.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsReceiveRecords;
import com.sibo.business.entity.VAssetsReceiveMain;
import com.sibo.business.enums.AssetsStatus;
import com.sibo.business.enums.AssetsType;
import com.sibo.business.enums.WorkType;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VAssetsReceiveMainMapper;
import com.sibo.business.mapper.VAssetsReceiveMapper;
import com.sibo.business.vo.VAssetsReceiveVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 资产领用记录
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-22 14:27:58
 */
@Service
@Transactional
public class VAssetsReceiveBiz extends BusinessBiz<VAssetsReceiveMapper,VAssetsReceiveRecords> {

    @Autowired
    private VAssetsReceiveMapper assetsReceiveMapper;

    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    @Autowired
    private MergeCore mergeCor;

    @Autowired
    private VAssetsReceiveMainMapper assetsReceiveMainMapper;

    public TableResultResponse<VAssetsReceiveVo> queryAssetsChangePage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        VAssetsReceiveVo vo = new VAssetsReceiveVo();
        vo.setDevicename(params.get("devicename")+"");
        vo.setPrincipal(BaseContextHandler.getUserID());//传入当前登录人id
        List<VAssetsReceiveVo> list = assetsReceiveMapper.selectall(rb,vo);
        return new TableResultResponse<VAssetsReceiveVo>(list.size(), list);
    }

    public TableResultResponse<VAssetsReceiveVo> queryReceiveRecordAll(Map<String, Object> params){
        Query query = new Query(params);
        Example example = new Example(VAssetsParameter.class);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map<String, Object> paramMmap = new HashMap<>();
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

        if(!StringUtils.isEmpty(params.get("type"))){
            paramMmap.put("type",params.get("type"));
        }
        List<VAssetsReceiveVo> list = null;
        if(org.apache.commons.lang.StringUtils.isNotEmpty((String)params.get("searchAll"))){
            paramMmap.put("searchAll","%"+params.get("searchAll")+"%");
            list = assetsReceiveMapper.selectReceiveRecordAllLike(paramMmap);
        }else{
            list = assetsReceiveMapper.selectReceiveRecordAll(paramMmap);
        }

        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    public TableResultResponse<VAssetsParameter> queryAssetsReturnPage(Map<String, Object> params){
        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
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
        paramMmap.put("principal",BaseContextHandler.getUserID());

        List statusList = new ArrayList();
        statusList.add(AssetsStatus.RECEIVE.getCode());
        statusList.add(AssetsStatus.RETURN.getCode());
        paramMmap.put("assetStatusList",statusList);

        List<VAssetsParameter> list = assetsParameterMapper.selectAssetsParameterAll(rb,paramMmap);
        return new TableResultResponse<VAssetsParameter>(list.size(), list);
    }

    public TableResultResponse<VAssetsReceiveVo> queryAssetsReturnConfirmPage(Map<String, Object> params){
        RowBounds rb = new RowBounds(0, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
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
        paramMmap.put("type",AssetsType.ASSET.getCode());
        paramMmap.put("principal",BaseContextHandler.getUserID());
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            //待办
            paramMmap.put("assetsType",AssetsStatus.RETURN.getCode());

            List assetStatusList = new ArrayList();
            assetStatusList.add(AssetsStatus.RETURN.getCode());
            paramMmap.put("assetStatusList",assetStatusList);
//            paramMmap.put("isBacklog","1");
        }else{
            //已办
            paramMmap.put("assetsType",AssetsStatus.RETURN_CONFIRM.getCode());

            List assetStatusList = new ArrayList();
            assetStatusList.add(AssetsStatus.RETURN_CONFIRM.getCode());
            paramMmap.put("assetStatusList",assetStatusList);

            paramMmap.put("updUserId",BaseContextHandler.getUserID());
        }
        List<VAssetsReceiveVo> list = assetsReceiveMapper.selectExample(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(list.size(), list);
    }

    public void receive( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.RECEIVE.getCode());
        entity.setPrincipal(vo.getRecipient());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        //查询最大版本号
        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.RECEIVE.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //保存领用主表
        VAssetsReceiveMain assetsReceiveMain = new VAssetsReceiveMain();
        assetsReceiveMain.setId(UUIDUtils.generateUuid());
        assetsReceiveMain.setAssetsId(vo.getId());
        assetsReceiveMain.setAssetsStatus(AssetsStatus.RECEIVE.getCode());
        assetsReceiveMain.setRecipient(vo.getRecipient());
        assetsReceiveMain.setRecipientTime(vo.getRecipientTime());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setType(AssetsType.ASSET.getCode());
        EntityUtils.setCreateInfo(assetsReceiveMain);
        assetsReceiveMainMapper.insertSelective(assetsReceiveMain);

        this.insertVAssetsReceive(vo,version,assetsReceiveMain.getId(),AssetsStatus.RECEIVE.getCode());
    }

    public void insertVAssetsReceive(VAssetsReceiveVo vo,Integer version,String mainId,String status){
        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setAssetsStatus(status);
        assetsReceive.setAssetsReceiveMainId(mainId);
        assetsReceive.setRecipient(vo.getRecipient());
        assetsReceive.setRecipientTime(vo.getRecipientTime());
        assetsReceive.setCurrentStatus(vo.getCurrentStatus());
        assetsReceive.setType(AssetsType.ASSET.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        assetsReceive.setVersion(version);
        super.insertSelective(assetsReceive);
    }

    public void assetsReturn( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.RETURN.getCode());
        entity.setPrincipal(vo.getReturnPeople());
        entity.setUpdTime(new Date());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.RETURN.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        //保存归还主表
        VAssetsReceiveMain assetsReceiveMain = new VAssetsReceiveMain();
        assetsReceiveMain.setId(UUIDUtils.generateUuid());
        assetsReceiveMain.setAssetsId(vo.getId());
        assetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN.getCode());
        assetsReceiveMain.setReturnTime(vo.getReturnTime());
        assetsReceiveMain.setReturnPeople(vo.getReturnPeople());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setType(AssetsType.ASSET.getCode());
        EntityUtils.setCreateInfo(assetsReceiveMain);
        assetsReceiveMainMapper.insertSelective(assetsReceiveMain);

        this.insertVAssetsReceive(vo,version,assetsReceiveMain.getId(),AssetsStatus.RETURN.getCode());

    }

    public void assetsReturnConfirm( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getAssetsId());
        entity.setAssetsStatus(AssetsStatus.RETURN_CONFIRM.getCode());
        entity.setUpdTime(new Date());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        Map map = new HashMap();
        map.put("id",vo.getAssetsId());
        map.put("assetsStatus",AssetsStatus.RETURN_CONFIRM.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);


        //更新归还主表
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null == assetsReceiveMain){
            throw new BusinessException("表数据异常");
        }
        assetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN_CONFIRM.getCode());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setConfirmPerson(BaseContextHandler.getUserID());
        EntityUtils.setUpdatedInfo(assetsReceiveMain);
        assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

        VAssetsReceiveRecords model = assetsReceiveMapper.selectByPrimaryKey(vo.getId());

        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getAssetsId());
        if(!StringUtils.isEmpty(model)){
            assetsReceive.setReturnPeople(model.getReturnPeople());
            assetsReceive.setReturnTime(model.getReturnTime());
            assetsReceive.setCurrentStatus(model.getCurrentStatus());
        }
        assetsReceive.setAssetsReceiveMainId(assetsReceiveMain.getId());
        assetsReceive.setAssetsStatus(AssetsStatus.RETURN_CONFIRM.getCode());
        assetsReceive.setType(AssetsType.ASSET.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        assetsReceive.setVersion(version);
        super.insertSelective(assetsReceive);
    }

    public List<VAssetsReceiveRecords> queryAutoCommit(){
        Example example = new Example(VAssetsReceiveRecords.class);
        example.createCriteria().andEqualTo("crtUserId",BaseContextHandler.getUserID()).andEqualTo("autoConfirm","1");
        List<VAssetsReceiveRecords> list = super.selectByExample(example);
        return list;
    }

}