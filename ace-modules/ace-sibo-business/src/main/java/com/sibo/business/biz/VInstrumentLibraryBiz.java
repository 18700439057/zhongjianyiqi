package com.sibo.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.lorne.core.framework.utils.DateUtil;
import com.sibo.business.entity.*;
import com.sibo.business.enums.*;
import com.sibo.business.feign.DeptFeign;
import com.sibo.business.feign.DictFeign;
import com.sibo.business.feign.UserFeign;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VAssetsReceiveMainMapper;
import com.sibo.business.mapper.VAssetsReceiveMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.utils.Constant;
import com.sibo.business.utils.DateUtils;
import com.sibo.business.utils.MessagePush;
import com.sibo.business.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 仪器库
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-24 15:25:01
 */
@Service
@Transactional
public class VInstrumentLibraryBiz extends BusinessBiz<VAssetsReceiveMapper,VAssetsReceiveRecords> {
    private Logger logger = LoggerFactory.getLogger(VAssetsParameterBiz.class);
    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Autowired
    private VAssetsReceiveMapper assetsReceiveMapper;

    @Autowired
    private VAssetsReceiveMainMapper assetsReceiveMainMapper;

    @Autowired
    private DeptFeign deptFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AppParameterBiz appParameterBiz;

    @Autowired
    private DictFeign dictFeign;


    public TableResultResponse<VInstrumentVo> queryAssetsChangePage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.valueOf((String)params.get("limit")));
        Query query = new Query(params);
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

        if(!StringUtils.isEmpty(params.get("facitityStatus"))){
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
        }

        if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
            paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
        }

        paramMmap.put("facilityDesignatedArea","1");
        List<VAssetsParameter> list = assetsParameterMapper.selectAssetsParameterPageList(paramMmap);
        //
        LinkedList resultList = new LinkedList();
        if(!CollectionUtils.isEmpty(list)){
            for (VAssetsParameter model:list) {
                VInstrumentVo vo = new VInstrumentVo();
                BeanUtils.copyProperties(model,vo);

                //查询图片数量
                Example example1 = new Example(VBusinessFile.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("bussinessId",model.getId());
                int imageNum = businessFileMapper.selectCountByExample(example1);
                vo.setImageNum(imageNum);

                //查询记录表
                Example example = new Example(VAssetsReceiveRecords.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("assetsId",model.getId())
                .andEqualTo("type", AssetsType.APPARATUS.getCode());
                List<VAssetsReceiveRecords> assetsReceiveList = super.selectByExample(example);
                if(!CollectionUtils.isEmpty(assetsReceiveList)){
                    Map<String,VAssetsReceiveRecords> map = new HashMap();
                    for (VAssetsReceiveRecords receive: assetsReceiveList) {
                        //判断有没有借出确认记录
                        if(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode().equals(receive.getAssetsStatus())){
                            map.put(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode(),receive);
                        }
                        if(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode().equals(receive.getAssetsStatus())){
                            map.put(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode(),receive);
                        }
                    }
                    //判断map中是否有借出记录，如果有借出记录d但是没有归还记录则视为过期为归还
                    if(null != map.get(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode()) && null == map.get(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode())){
                            //判断时间是否超过90天
                        VAssetsReceiveRecords assetsReceive = map.get(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
                        if(System.currentTimeMillis()-assetsReceive.getCrtTime().getTime() > 90*(24*60*60*1000)){
                            resultList.addFirst(vo);
                        }
                    }else{
                        resultList.add(vo);
                    }
                }else{
                    resultList.add(vo);
                }

            }
        }
        return new TableResultResponse<VInstrumentVo>(result.getTotal(), resultList);
    }

    public void updateInstrument(VInstrumentVo instrumentVo){
        VAssetsParameter assetsParameter = new VAssetsParameter();
        BeanUtils.copyProperties(instrumentVo,assetsParameter);
        assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

        if(null != instrumentVo.getImageList() && instrumentVo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:instrumentVo.getImageList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(assetsParameter.getId());
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")));
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }
    }

    /**
     * 借出查询
     * @param
     */
    public TableResultResponse<VAssetsReceiveVo> queryLoanPage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(0, Integer.parseInt(params.get("limit").toString()));
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        //判断是已办工作还是待办工作查询
        List<VAssetsReceiveVo> list = new ArrayList<>();
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
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

            if(!StringUtils.isEmpty(params.get("facitityStatus"))){
                paramMmap.put("facitityStatus",params.get("facitityStatus"));
            }

            if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
                paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
            }
            paramMmap.put("facilityDesignatedArea","1");

            List assetStatusList = new ArrayList();
            assetStatusList.add(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
            assetStatusList.add(AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
            assetStatusList.add(AssetsStatus.INSTRUMENT_LOAN_REJECT.getCode());
            assetStatusList.add(AssetsStatus.ENTERING.getCode());
            paramMmap.put("assetStatusList",assetStatusList);

            List<VAssetsParameter> resultList = assetsParameterMapper.selectAssetsParameterPageList(paramMmap);
            for (VAssetsParameter vAssetsParameter : resultList) {
                VAssetsReceiveVo assetsReceiveVo = new VAssetsReceiveVo();
                BeanUtils.copyProperties(vAssetsParameter,assetsReceiveVo);
                list.add(assetsReceiveVo);
            }
        }else{
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

            if(!StringUtils.isEmpty(params.get("facitityStatus"))){
                paramMmap.put("facitityStatus",params.get("facitityStatus"));
            }

            if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
                paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
            }
            //领用人
            paramMmap.put("recipient",BaseContextHandler.getUserID());

            //状态为借出
            //paramMmap.put("assetsType",AssetsStatus.INSTRUMENT_LOAN.getCode());

            //仪器
            paramMmap.put("type",AssetsType.APPARATUS.getCode());

            paramMmap.put("crtUserId",BaseContextHandler.getUserID());

            list = assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        }
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    /**
     * 借出
     * @param vo
     */
    public void loan( VAssetsReceiveVo vo){
        VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsParameter && AssetsStatus.INSTRUMENT_LOAN.getCode().equals(assetsParameter.getAssetsStatus())){
            throw new BusinessException("仪器已经被借出，请查询后确认仪器状态是否为未借出状态!");
        }
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setPrincipal(vo.getRecipient());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN.getCode());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        //查询最大版本号
        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.INSTRUMENT_LOAN.getCode());
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
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN.getCode());
        assetsReceiveMain.setRecipient(vo.getRecipient());
        assetsReceiveMain.setRecipientTime(vo.getRecipientTime());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        EntityUtils.setCreateInfo(assetsReceiveMain);
        assetsReceiveMainMapper.insertSelective(assetsReceiveMain);

        this.insertVAssetsReceive(vo,version,assetsReceiveMain.getId(),AssetsStatus.INSTRUMENT_LOAN.getCode());

    }

    public void insertVAssetsReceive(VAssetsReceiveVo vo,Integer version,String mainId, String status){
        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setAssetsStatus(status);
        assetsReceive.setAssetsReceiveMainId(mainId);
        assetsReceive.setRecipient(vo.getRecipient());
        assetsReceive.setRecipientTime(vo.getRecipientTime());
        assetsReceive.setCurrentStatus(vo.getCurrentStatus());
        assetsReceive.setType(AssetsType.APPARATUS.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        assetsReceive.setVersion(version);
        super.insertSelective(assetsReceive);
    }


    /**
     * 借出确认查询
     * @param
     */
    public TableResultResponse<VAssetsReceiveVo> queryLoanConfirmPage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(0, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        List<VAssetsReceiveVo> list = new ArrayList<>();
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

        if(!StringUtils.isEmpty(params.get("facitityStatus"))){
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
        }

        if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
            paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
        }
        paramMmap.put("type",AssetsType.APPARATUS.getCode());
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            paramMmap.put("assetsType",AssetsStatus.INSTRUMENT_LOAN.getCode());
            List assetStatusList = new ArrayList();
            assetStatusList.add(AssetsStatus.INSTRUMENT_LOAN.getCode());
            paramMmap.put("assetStatusList",assetStatusList);

//            paramMmap.put("isBacklog","1");//判断是否待办，如果是待办需要添加查询条件

        }else{
//            List assetStatusList = new ArrayList();
//            assetStatusList.add(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
//            paramMmap.put("assetStatusList",assetStatusList);
            paramMmap.put("assetsType",AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());

            paramMmap.put("updUserId",BaseContextHandler.getUserID());
        }


        list = assetsReceiveMapper.selectExample(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    /**
     * 借出确认
     * @param vo
     */
    public void loanConfirm( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

//        //查询最大版本号
        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);

        Example example = new Example(VAssetsReceiveRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("assetsId",vo.getId())
                .andEqualTo("version",version)
                .andEqualTo("assetsStatus",vo.getAssetsStatus());
        List<VAssetsReceiveRecords> list = assetsReceiveMapper.selectByExample(example);

        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getAssetsReceiveMainId());
        if(null == assetsReceiveMain){
            throw new BusinessException("数据异常");
        }

        assetsReceiveMain.setAssetsId(vo.getId());
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        if(!CollectionUtils.isEmpty(list)){
            assetsReceiveMain.setRecipient(list.get(0).getRecipient());
            assetsReceiveMain.setRecipientTime(list.get(0).getRecipientTime());
            assetsReceiveMain.setCurrentStatus(list.get(0).getCurrentStatus());
        }
        EntityUtils.setUpdatedInfo(assetsReceiveMain);
        assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);


        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setVersion(version);
        assetsReceive.setAssetsReceiveMainId(assetsReceiveMain.getId());
        assetsReceive.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
        if(!CollectionUtils.isEmpty(list)){
            assetsReceive.setRecipient(list.get(0).getRecipient());
            assetsReceive.setRecipientTime(list.get(0).getRecipientTime());
            assetsReceive.setCurrentStatus(list.get(0).getCurrentStatus());
        }
        assetsReceive.setType(AssetsType.APPARATUS.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        super.insertSelective(assetsReceive);
    }

    /**
     * 借出拒绝
     * @param vo
     */
    public void loanReject( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getAssetsId());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_REJECT.getCode());
        entity.setDismissReason(vo.getDismissReason());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);


        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null == assetsReceiveMain){
            throw new BusinessException("数据异常");
        }
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_REJECT.getCode());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        EntityUtils.setUpdatedInfo(assetsReceiveMain);
        assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_REJECT.getCode());
        assetsReceive.setAssetsReceiveMainId(assetsReceiveMain.getId());
        assetsReceive.setType(AssetsType.APPARATUS.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        super.insertSelective(assetsReceive);





    }


    /**
     * 仪器归还
     * @return
     */
    public TableResultResponse<VAssetsReceiveVo> queryReturnPage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(0, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        paramMmap.put("devicename",params.get("devicename"));
        List<VAssetsReceiveVo> list = new ArrayList<>();
        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){

            paramMmap.put("devicename",params.get("devicename"));
            paramMmap.put("facilityDesignatedArea","1");
            List assetStatusList = new ArrayList();
            assetStatusList.add(AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
            paramMmap.put("assetStatusList",assetStatusList);
            paramMmap.put("principal",BaseContextHandler.getUserID());
            List<VAssetsParameter> resultList = assetsParameterMapper.selectAssetsParameterPageList(paramMmap);
            for (VAssetsParameter vAssetsParameter : resultList) {
                VAssetsReceiveVo assetsReceiveVo = new VAssetsReceiveVo();
                BeanUtils.copyProperties(vAssetsParameter,assetsReceiveVo);
                list.add(assetsReceiveVo);
            }
        }else{
            paramMmap.put("assetStatus",AssetsStatus.CHANGE_CONFIRMATION.getCode());
            paramMmap.put("recipient",BaseContextHandler.getUserID());
            List<VAssetsParameter> resultList = assetsParameterMapper.selectAssetsParameterPageList(paramMmap);
        }


        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    /**
     * 仪器归还
     * @param vo
     */
    public void returnInstrument( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.INSTRUMENT_LOAN_CONFIRM.getCode());
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
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setReturnPeople(vo.getReturnPeople());
        assetsReceiveMain.setReturnTime(vo.getReturnTime());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        EntityUtils.setCreateInfo(assetsReceiveMain);
        assetsReceiveMainMapper.insertSelective(assetsReceiveMain);

        this.insertVAssetsReceive(vo,version,assetsReceiveMain.getId(),AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode());
    }

    /**
     * 仪器归还确认查询
     * @return
     */
    public TableResultResponse<VAssetsReceiveVo> queryReturnConfirmPage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(0, Integer.parseInt(params.get("limit").toString()));
        Query query = new Query(params);
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

        if(!StringUtils.isEmpty(params.get("facitityStatus"))){
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
        }

        if(!StringUtils.isEmpty(params.get("equipmentOwner"))){
            paramMmap.put("equipmentOwner",params.get("equipmentOwner"));
        }

        if(null != params.get("workType") && WorkType.WAIT_WORK.getCode().equals(params.get("workType"))){
            paramMmap.put("assetsType",AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode());
            paramMmap.put("type",AssetsType.APPARATUS.getCode());

            List statusList = new ArrayList();
            statusList.add(AssetsStatus.INSTRUMENT_LOAN_RETURN.getCode());
            paramMmap.put("assetStatusList",statusList);

//            paramMmap.put("isBacklog","1");//判断是否待办，如果是待办需要添加查询条件
        }else{
            paramMmap.put("assetsType",AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
            paramMmap.put("type",AssetsType.APPARATUS.getCode());
            paramMmap.put("updUserId",BaseContextHandler.getUserID());
        }


        List<VAssetsReceiveVo> list = assetsReceiveMapper.selectExample(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    /**
     * 仪器归还提交
     * @param vo
     */
    public void returnConfirm( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        //查询最大版本号
        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getAssetsReceiveMainId());
        if(null == assetsReceiveMain){
            throw new BusinessException("数据异常");
        }
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        EntityUtils.setUpdatedInfo(assetsReceiveMain);
        assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);




        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setVersion(version);
        assetsReceive.setAssetsReceiveMainId(assetsReceiveMain.getId());
        assetsReceive.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_CONFIRM.getCode());
        assetsReceive.setType(AssetsType.APPARATUS.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        super.insertSelective(assetsReceive);
    }

    /**
     * 仪器归还拒绝
     * @param vo
     */
    public void rejectInstrument( VAssetsReceiveVo vo){
        VAssetsParameter entity = new VAssetsParameter();
        entity.setId(vo.getId());
        entity.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
        entity.setDismissReason(vo.getDismissReason());
        assetsParameterMapper.updateByPrimaryKeySelective(entity);

        //查询最大版本号
        Map map = new HashMap();
        map.put("id",vo.getId());
        map.put("assetsStatus",AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
        Integer version = assetsReceiveMapper.selectVersion(map);
        if(null == version){
            version = 1;
        }else{
            version = version+1;
        }

        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getAssetsReceiveMainId());
        if(null == assetsReceiveMain){
            throw new BusinessException("数据异常");
        }
        assetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
        assetsReceiveMain.setCurrentStatus(vo.getCurrentStatus());
        assetsReceiveMain.setType(AssetsType.APPARATUS.getCode());
        EntityUtils.setUpdatedInfo(assetsReceiveMain);
        assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);


        VAssetsReceiveRecords assetsReceive = new VAssetsReceiveRecords();
        assetsReceive.setVersion(version);
        assetsReceive.setAssetsId(vo.getId());
        assetsReceive.setAssetsReceiveMainId(assetsReceiveMain.getId());
        assetsReceive.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
        assetsReceive.setCurrentStatus(vo.getCurrentStatus());
        assetsReceive.setType(AssetsType.APPARATUS.getCode());
        assetsReceive.setId(UUIDUtils.generateUuid());
        super.insertSelective(assetsReceive);
    }

    //================================================2019.1.11 修改为以下新流程==========================================================================
    /**
     * 设备派发
     * @param vo
     */
    public void distribute(VAssetsParameterVo vo){
        //判断设备存属，跟随部门时此设备的部门和存放地不变，如果是跟随人员，需要修改部门和存放地到指定负责人的部门下面
        VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsParameter){
            //判断资产的计量信息是否完善
            if(MeasurementCheckClass.NOT_NEED_TO_MEASURE.getCode().equals(assetsParameter.getMeasurementCheckClass())){
                if(null == assetsParameter.getMeasurementValidity()){
                    throw new BusinessRuntimeException("计量信息不完整，不能派发！");
                }
            }

            if(!StringUtils.isEmpty(vo.getRoles()) && !vo.getRoles().contains("000")){
                if( !AssetsStatus.ENTERING.getCode().equals(assetsParameter.getAssetsStatus()) && !AssetsStatus.REJECT.getCode().equals(assetsParameter.getAssetsStatus()) ){
                    throw new BusinessRuntimeException("设备状态不允许此操作！现在状态为:"+AssetsStatus.getByCode(assetsParameter.getAssetsStatus()).getName());
                }
            }else{
                //删除待确认，归还待确认.,配置被拒绝状态数据 //角色为管理员时，可随时修改设备状态
                Example example = new Example(VAssetsReceiveMain.class);
                List list = new ArrayList();
                list.add(AssetsStatus.CONFIRM_CHANGE.getCode());
                list.add(AssetsStatus.RETURN.getCode());
                list.add(AssetsStatus.REJECT.getCode());
                example.createCriteria().andIn("assetsStatus",list).andEqualTo("assetsId",assetsParameter.getId());
                List<VAssetsReceiveMain> mainList = assetsReceiveMainMapper.selectByExample(example);
                for (VAssetsReceiveMain po: mainList) {
                    po.setAssetsStatus(AssetsStatus.RESET.getCode());
                    assetsReceiveMainMapper.updateByPrimaryKeySelective(po);
                }
            }


            //assetsParameter.setPrincipal(vo.getPrincipal());
            //设备主表状态变更为已派发，待确认状态
            assetsParameter.setAssetsStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
            EntityUtils.setUpdatedInfo(assetsParameter);
            //设置配发人，若存在则走APP，不存在走PC
            if(StringUtils.isEmpty(vo.getInputer())) {
                assetsParameter.setHandingOutPeople(BaseContextHandler.getUserID());//设置配发人

            }else {
                assetsParameter.setHandingOutPeople(vo.getInputer());//设置配发人
            }

            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);

            //判断是否为拒绝，如果是拒绝则修改拒绝信息
            VAssetsReceiveMain vAssetsReceiveMain = null;
            Example example = new Example(VAssetsReceiveMain.class);
            example.createCriteria().andEqualTo("assetsId",assetsParameter.getId()).andEqualTo("assetsStatus",AssetsStatus.REJECT.getCode());
            List<VAssetsReceiveMain> mainList = assetsReceiveMainMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(mainList)){
                vAssetsReceiveMain = mainList.get(0);
                if(StringUtils.isEmpty(vo.getInputer())) {
                    EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveMain);
                }else {
                    vAssetsReceiveMain.setCrtUserName(vo.getCrtUserName());
                    vAssetsReceiveMain.setCrtUserId(vo.getInputer());
                    Date date=DateUtil.getCurrentDate();
                    vAssetsReceiveMain.setCrtTime(date);
                }
                vAssetsReceiveMain.setType(AssetsType.ASSET.getCode());
                vAssetsReceiveMain.setRecipient(vo.getPrincipal());// 负责人
                vAssetsReceiveMain.setAssetsId(assetsParameter.getId());
                vAssetsReceiveMain.setAssetsStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
                //如果是跟随人员 则将部门和存放地更新为新负责人的部门
                if(EquipmentSubordinate.FOLLOW_THE_PERSONNEL.getCode().equals(assetsParameter.getEquipmentSubordinate())){
                    Depart depart = deptFeign.getDeptByUserId(vo.getPrincipal());
                    if(null == depart){
                        throw new BusinessRuntimeException("获取负责人部门失败，该负责人可能没有分配部门");
                    }
                    vAssetsReceiveMain.setEquipmentDepartment(depart.getCode());
                }
                vAssetsReceiveMain.setFacilityDesignatedArea(assetsParameter.getFacilityDesignatedArea());
                assetsReceiveMainMapper.updateByPrimaryKeySelective(vAssetsReceiveMain);
            }else{
                //设备主表状态变更为已派发，待确认状态     生成变更主表记录状态为待确认，负责人为指定的新负责人
                vAssetsReceiveMain = new VAssetsReceiveMain();
                vAssetsReceiveMain.setId(UUIDUtils.generateUuid());
                if(StringUtils.isEmpty(vo.getInputer())) {
                    EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveMain);
                }else {
                    vAssetsReceiveMain.setCrtUserName(vo.getCrtUserName());
                    vAssetsReceiveMain.setCrtUserId(vo.getInputer());
                    Date date=DateUtil.getCurrentDate();
                    vAssetsReceiveMain.setCrtTime(date);
                }

                vAssetsReceiveMain.setType(AssetsType.ASSET.getCode());
                vAssetsReceiveMain.setRecipient(vo.getPrincipal());// 负责人
                vAssetsReceiveMain.setAssetsId(assetsParameter.getId());
                vAssetsReceiveMain.setAssetsStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
                //如果是跟随人员 则将部门更新为新负责人的部门
                if(EquipmentSubordinate.FOLLOW_THE_PERSONNEL.getCode().equals(assetsParameter.getEquipmentSubordinate())){
                    Depart depart = deptFeign.getDeptByUserId(vo.getPrincipal());
                    if(null == depart){
                        throw new BusinessRuntimeException("获取负责人部门失败，该负责人可能没有分配部门");
                    }
                    vAssetsReceiveMain.setEquipmentDepartment(depart.getCode());
                }else{
                    vAssetsReceiveMain.setEquipmentDepartment(assetsParameter.getEquipmentDepartment());
                }
                vAssetsReceiveMain.setFacilityDesignatedArea(assetsParameter.getFacilityDesignatedArea());
                assetsReceiveMainMapper.insertSelective(vAssetsReceiveMain);
            }



            //变更记录表增加一条记录
            VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
            vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
            vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
            vAssetsReceiveRecords.setAssetsReceiveMainId(vAssetsReceiveMain.getId());
            if(StringUtils.isEmpty(vo.getCrtUserName())) {
                EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
            }else {
                vAssetsReceiveRecords.setCrtUserName(vo.getCrtUserName());
                vAssetsReceiveRecords.setCrtUserId(vo.getInputer());
                Date date=DateUtil.getCurrentDate();
                vAssetsReceiveRecords.setCrtTime(date);
            }
            if(!StringUtils.isEmpty(vo.getRoles()) && !vo.getRoles().contains("000")){
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.CONFIRM_CHANGE.getCode());
            }else{
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.RESET.getCode());
            }
            vAssetsReceiveRecords.setRecipient(vo.getPrincipal());
            assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);



            MessagePush.sendPush(vo.getPrincipal(), "【"+BaseContextHandler.getName()+"】给您配发了一个设备，请确认并领取！");
            appParameterBiz.saveMessage(BaseContextHandler.getName()+"给您配发了一个设备，请确认并领取！",vo.getPrincipal());
        }else{
            throw new BusinessRuntimeException("数据不存在，请执行查询后查看数据是否存在！");
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
     * 待确认查询
     * @param vo
     */
    public TableResultResponse<VAssetsParameter> queryWaitCommit(Map<String, Object> params){
        //查询条件：变更主表负责人是登录账号，状态是待确认，id为变更主表id

        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        Map<String, Object> paramMmap = new HashMap<>();

        this.addQueryCondition(params,paramMmap);
        paramMmap.put("principal",BaseContextHandler.getUserID());
        paramMmap.put("assetsStatus",AssetsStatus.CONFIRM_CHANGE.getCode());
        if(!StringUtils.isEmpty(params.get("devicename"))){
            paramMmap.put("devicename",params.get("devicename"));
        }

        List<VAssetsParameter> list =assetsParameterMapper.selectChangePageList(paramMmap);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }



    /**
     * 查询待确认条数
     * @return
     */
    public int queryWaitConfirmCount(String userId){
        //查询条件：变更主表负责人是登录账号，状态是待确认
        RowBounds rb = new RowBounds();
        Map<String, Object> paramMmap = new HashMap<>();

        paramMmap.put("principal",userId);
        paramMmap.put("assetsStatus",AssetsStatus.CONFIRM_CHANGE.getCode());

        List<VAssetsParameter> list =assetsParameterMapper.selectChangePageList(paramMmap);
        if(!CollectionUtils.isEmpty(list)){
            return list.size();
        }
        return 0;
    }

    /**
     * 确认查询单个信息
     * @param id
     * @return
     */
    public VAssetsParameter queryWaitCommitById(String id){
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(id);
        if(null != assetsReceiveMain){
            VAssetsParameter vAssetsParameter = assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
            vAssetsParameter.setId(id);
            vAssetsParameter.setEquipmentDepartment(assetsReceiveMain.getEquipmentDepartment());
            vAssetsParameter.setFacilityDesignatedArea(assetsReceiveMain.getFacilityDesignatedArea());
            return vAssetsParameter;
        }
        return null;
    }

    /**
     * 确认
     * @param vo
     */
    public void affirm(VAssetsParameterVo vo){
        // 修改变更主表状态为已确认 此处vo里面的id为变更主表的id
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsReceiveMain){
            assetsReceiveMain.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
            EntityUtils.setUpdatedInfo(assetsReceiveMain);
            assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

            // 修改设备主表状态为已领用，负责人为当前账号
            VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
            if(null != assetsParameter){
                    assetsParameter.setPrincipal(BaseContextHandler.getUserID());
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
            vAssetsReceiveRecords.setRecipient(BaseContextHandler.getUserID());
            assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);

            MessagePush.sendPush(assetsReceiveMain.getCrtUserId(), "您给【"+BaseContextHandler.getName()+"】配发的设备已领取，请知悉！");
            appParameterBiz.saveMessage("您给【"+BaseContextHandler.getName()+"】配发的设备已领取，请知悉！",assetsReceiveMain.getCrtUserId());
        }else{
            throw new BusinessRuntimeException("数据不存在，请执行查询后查看数据是否存在！");
        }
    }

    /**
     * 自动确认任务
     */
    public void autoCommit(){
        logger.info("自动确认任务开始：【"+DateUtils.getCurrDateTime()+"】");
        this.disposePayout();
        this.disposeReject();
        this.disposeReturn();
        logger.info("自动确认任务结束：【"+DateUtils.getCurrDateTime()+"】");

    }
    /**
     * 拒绝待确认处理
     */
    private void disposeReject(){
        Example example = new Example(VAssetsReceiveMain.class);
        example.createCriteria().andEqualTo("assetsStatus",AssetsStatus.REJECT.getCode());
        List<VAssetsReceiveMain> list =assetsReceiveMainMapper.selectByExample(example);
        logger.info("归还待确认处理查询：总条数【"+list.size()+"】");

      list.forEach(assetsReceiveMain -> {
            long time = DateUtils.getTodayIntevalDays(assetsReceiveMain.getUpdTime());
            if(time >15){
                assetsReceiveMain.setAssetsStatus(AssetsStatus.IN_STORAGE.getCode());
                assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

                VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
                if(null != assetsParameter){
                    assetsParameter.setAssetsStatus(AssetsStatus.ENTERING.getCode());
                    assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
                }

                //变更记录表增加一条记录
                VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
                vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
                vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
                vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.IN_STORAGE.getCode());
                vAssetsReceiveRecords.setRecipient("");
                vAssetsReceiveRecords.setCrtUserId(assetsReceiveMain.getCrtUserId());
                vAssetsReceiveRecords.setCrtUserName(assetsReceiveMain.getCrtUserName());
                vAssetsReceiveRecords.setCrtTime(DateUtils.getCurrDate());
                vAssetsReceiveRecords.setAutoConfirm("1");
                vAssetsReceiveRecords.setIsRead("0");
                vAssetsReceiveRecords.setRemark("系统消息:统一编号【"+assetsParameter.getUnifiedCode()+"】。该条数据于"+DateUtils.format(assetsReceiveMain.getUpdTime())+"被当事人:【"+assetsReceiveMain.getUpdUserName()+"】拒绝退回，由于超过15天您未进行处理，系统已帮您自动处理入库，请知悉！");
                assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
            }
        });
        logger.info("归还待确认处理完成");
    }

    /**
     * 归还待确认处理
     */
    private void disposeReturn(){
        Example example = new Example(VAssetsReceiveMain.class);
        example.createCriteria().andEqualTo("assetsStatus",AssetsStatus.RETURN.getCode());
        List<VAssetsReceiveMain> list =assetsReceiveMainMapper.selectByExample(example);
        logger.info("归还待确认处理查询：总条数【"+list.size()+"】");
        list.forEach(assetsReceiveMain -> {
            long time = DateUtils.getTodayIntevalDays(assetsReceiveMain.getUpdTime());
            if(time >15){
                String userName = assetsReceiveMain.getUpdUserName();
                    assetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN_CONFIRM.getCode());
                    EntityUtils.setUpdatedInfo(assetsReceiveMain);
                    assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);

                //修改设备主表负责人为当前登录人账号，部门为当前登录人部门，状态为已归还
                VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
                if(null != assetsParameter){
                    assetsParameter.setPrincipal(assetsReceiveMain.getCrtUserId());
                    Depart depart = deptFeign.getDeptByUserId(assetsReceiveMain.getCrtUserId());
                    if(null == depart){
                        throw new BusinessRuntimeException("feign获取部门失败");
                    }
                    assetsParameter.setEquipmentDepartment(depart.getCode());
                    assetsParameter.setAssetsStatus(AssetsStatus.ENTERING.getCode());
                    EntityUtils.setUpdatedInfo(assetsParameter);
                    assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
                }

                //明细表新增一条归还确认记录
                VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
                vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
                vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
                vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
                vAssetsReceiveRecords.setCrtUserId(assetsReceiveMain.getCrtUserId());
                vAssetsReceiveRecords.setCrtUserName(assetsReceiveMain.getCrtUserName());
                vAssetsReceiveRecords.setCrtTime(DateUtils.getCurrDate());
                vAssetsReceiveRecords.setReturnPeople(assetsReceiveMain.getUpdUserId());
                vAssetsReceiveRecords.setReturnTime(DateUtils.getCurrDate());
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.RETURN_CONFIRM.getCode());
                vAssetsReceiveRecords.setRecipient("");
                vAssetsReceiveRecords.setAutoConfirm("1");
                vAssetsReceiveRecords.setIsRead("0");
                vAssetsReceiveRecords.setRemark("系统消息:统一编号【"+assetsParameter.getUnifiedCode()+"】。该条数据于"+DateUtils.format(assetsReceiveMain.getUpdTime())+"被当事人:【"+userName+"】归还，由于超过15天您未进行处理，系统已帮您自动处理归还入库，请知悉！");
                assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
            }
        });
        logger.info("归还待确认处理完成");


    }

    /**
     * 派发待确认处理
     */
    private void disposePayout(){
        // 派发待确认数据查询
        Example example = new Example(VAssetsReceiveMain.class);
        example.createCriteria().andEqualTo("assetsStatus",AssetsStatus.CONFIRM_CHANGE.getCode());
        List<VAssetsReceiveMain> list =assetsReceiveMainMapper.selectByExample(example);
        logger.info("派发待确认数据查询：总条数【"+list.size()+"】");

        //循环判断创建时间和当前时间是否大于7天，如果大于14天做自动确认
        list.forEach(assetsReceiveMain -> {
            long time = DateUtils.getTodayIntevalDays(assetsReceiveMain.getCrtTime());
            if(time >15){
                //修改记录主表状态为已确认
                assetsReceiveMain.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
                EntityUtils.setUpdatedInfo(assetsReceiveMain);
                assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);
                //修改资产主表状态和负责人
                VAssetsParameter assetsParameter = assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
                if(null != assetsParameter){
                    assetsParameter.setPrincipal(assetsReceiveMain.getRecipient());
                    if(EquipmentSubordinate.FOLLOW_THE_PERSONNEL.getCode().equals(assetsParameter.getEquipmentSubordinate())){
                        Depart depart = deptFeign.getDeptByUserId(assetsReceiveMain.getRecipient());
                        if(null != depart){
                            assetsParameter.setEquipmentDepartment(depart.getCode());
                        }else{
                            logger.info("统一编号:【"+assetsParameter.getUnifiedCode()+"】系统任务派发自动确认时没有找到负责人:【"+assetsReceiveMain.getRecipient()+"】部门，请检查！");
                        }
                    }
                    assetsParameter.setAssetsStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
                    assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
                }
                //记录表新增一条数据
                VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
                vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
                vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
                vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
                vAssetsReceiveRecords.setCrtUserId(assetsReceiveMain.getRecipient());

                List<User> userList = userFeign.getAllUser();
                logger.info("--------------结束远程获取人员");
                if(!CollectionUtils.isEmpty(userList)){
                    // 将userlist转换为Map
                    Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
                    logger.info("--------------获取人员名称："+map.get(assetsReceiveMain.getRecipient()).getName());
                    vAssetsReceiveRecords.setCrtUserName(map.get(assetsReceiveMain.getRecipient()).getName());
                }

                vAssetsReceiveRecords.setCrtTime(DateUtils.getCurrDate());
                vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.CHANGE_CONFIRMATION.getCode());
                vAssetsReceiveRecords.setRecipient(assetsReceiveMain.getRecipient());
                vAssetsReceiveRecords.setAutoConfirm("1");
                vAssetsReceiveRecords.setIsRead("0");
                vAssetsReceiveRecords.setRemark("系统消息:统一编号【"+assetsParameter.getUnifiedCode()+"】。该条数据于"+DateUtils.format(assetsReceiveMain.getCrtTime())+"由设备管理员:【"+assetsReceiveMain.getCrtUserName()+"】配发给您，由于超过15天您未进行处理，系统已帮您自动确认，请知悉！");
                assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);

            }
        });
        logger.info("派发待确认数据处理完成");
    }

    /**
     * 拒绝
     * @param vo
     */
    public void reject(VAssetsParameterVo vo){
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
            vAssetsReceiveRecords.setRecipient(BaseContextHandler.getUserID());
            assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);

            MessagePush.sendPush(assetsReceiveMain.getCrtUserId(), "您给【"+BaseContextHandler.getName()+"】配发的设备已拒绝，请确认！");
            appParameterBiz.saveMessage("您给【"+BaseContextHandler.getName()+"】配发的设备已拒绝，请确认！",assetsReceiveMain.getCrtUserId());
        }
    }

    /**
     * 查询拒绝待处理数据
     * @param params
     * @return
     */
    public TableResultResponse<VAssetsReceiveVo> queryRejectData(Map<String, Object> params){
        // 查询条件：设备主表和变更主表状态都是：拒绝
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));

        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        Map<String, Object> paramMmap = new HashMap<>();
        this.addQueryCondition(params,paramMmap);
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.REJECT.getCode());
        paramMmap.put("assetStatusList",assetStatusList);
        paramMmap.put("crtUserId",BaseContextHandler.getUserID());
        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);

    }

    /**
     * 查询拒绝总条数
     * @param userId
     * @return
     */
    public int queryRejectData(String userId){
        // 查询条件：设备主表和变更主表状态都是：拒绝
        RowBounds rb = new RowBounds();
        Map<String, Object> paramMmap = new HashMap<>();
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.REJECT.getCode());
        paramMmap.put("assetStatusList",assetStatusList);
        paramMmap.put("crtUserId",BaseContextHandler.getUserID());

        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        if(!CollectionUtils.isEmpty(list)){
            return list.size();
        }
        return 0;
    }



    /**
     * 设备归还查询
     * @param vo
     */
    public TableResultResponse<VAssetsParameter> queryGiveBack(Map<String, Object> params){
        //查询条件为： 查询设备主表负责人是当前登录人即可
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        this.addQueryCondition(params,paramMmap);
        paramMmap.put("principal",BaseContextHandler.getUserID());
        List statusList = new ArrayList();
        statusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
        statusList.add(AssetsStatus.ENTERING.getCode());
        statusList.add(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
        paramMmap.put("assetStatusList",statusList);

        List<VAssetsParameter> list =assetsParameterMapper.selectAssetsParameterAll(paramMmap);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }

    /**
     * 归还被拒绝
     * @param id
     */
    public void giveBackReject(VAssetsParameterVo vo){
        VAssetsReceiveMain vAssetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        vAssetsReceiveMain.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());

        VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(vAssetsReceiveMain.getAssetsId());
        if(null != assetsParameter){
            assetsParameter.setPrincipal(vAssetsReceiveMain.getReturnPeople());
            Depart depart = deptFeign.getDeptByUserId(vAssetsReceiveMain.getReturnPeople());
            if(null == depart){
                throw new BusinessRuntimeException("获取负责人部门失败，该负责人可能没有分配部门");
            }
            vAssetsReceiveMain.setEquipmentDepartment(depart.getCode());
            assetsParameter.setEquipmentDepartment(depart.getCode());
            assetsParameter.setAssetsStatus(AssetsStatus.INSTRUMENT_RETURN_REJECT.getCode());
            assetsParameter.setDismissReason(vo.getRemark());
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
            MessagePush.sendPush(vAssetsReceiveMain.getReturnPeople(), "您归还的设备被"+BaseContextHandler.getName()+"拒绝，拒绝原因："+vo.getRemark()+"，请确认！");
            appParameterBiz.saveMessage("您归还的设备被"+BaseContextHandler.getName()+"拒绝，拒绝原因："+vo.getRemark()+"，请确认！",vAssetsReceiveMain.getReturnPeople());
        }
       // assetsReceiveMainMapper.updateByPrimaryKeySelective(vAssetsReceiveMain);
    }


    /**
     * 设备归还
     * @param vo
     */
    public void giveBack(VAssetsParameterVo vo){
        //1.修改设备负责人为空，存放地为仪器库，部门为空，状态为归还待确认
        VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsParameter){
            assetsParameter.setPrincipal("");
            assetsParameter.setEquipmentDepartment(Constant.SBYWB);
//            assetsParameter.setFacilityDesignatedArea(DesignatedArea.YQK.getCode());
            assetsParameter.setAssetsStatus(AssetsStatus.RETURN.getCode());
            assetsParameter.setRemark(vo.getRemark());
            //EntityUtils.setUpdatedInfo(assetsParameter);
            if(StringUtils.isEmpty(assetsParameter.getHandingOutPeople())){
                assetsParameter.setHandingOutPeople("402881e35c9bc4f8015c9bc5abc100f8");
            }
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }

        //2.归还主表新增一条信息，状态为归还待确认
        VAssetsReceiveMain vAssetsReceiveMain = new VAssetsReceiveMain();
        Example example = new Example(VAssetsReceiveMain.class);
        example.createCriteria().andEqualTo("assetsStatus",AssetsStatus.RETURN.getCode()).andEqualTo("returnPeople",BaseContextHandler.getUserID()).andEqualTo("assetsId",assetsParameter.getId());
        List<VAssetsReceiveMain> list = assetsReceiveMainMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
            vAssetsReceiveMain = list.get(0);
            EntityUtils.setUpdatedInfo(vAssetsReceiveMain);
            assetsReceiveMainMapper.updateByPrimaryKeySelective(vAssetsReceiveMain);
        }else{
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
            }else{//如果没有发出人则默认王亚辉，处理历史数据
                vAssetsReceiveMain.setCrtUserId("402881e35c9bc4f8015c9bc5abc100f8");
                vAssetsReceiveMain.setCrtTime(new Date());
                vAssetsReceiveMain.setCrtUserName("王亚辉");

            }

            vAssetsReceiveMain.setAssetsId(assetsParameter.getId());
            vAssetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN.getCode());
            vAssetsReceiveMain.setReturnPeople(BaseContextHandler.getUserID());
            vAssetsReceiveMain.setRecipientTime(new Date());
            assetsReceiveMainMapper.insertSelective(vAssetsReceiveMain);
        }

        //3.明细记录表记录一条归还信息
        VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
        vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
        vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
        vAssetsReceiveRecords.setAssetsReceiveMainId(vAssetsReceiveMain.getId());
        EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
        vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.RETURN.getCode());
        vAssetsReceiveRecords.setRecipient(vo.getPrincipal());
        assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);

        MessagePush.sendPush(assetsParameter.getHandingOutPeople(), "您给【"+BaseContextHandler.getName()+"】配发的设备已归还，请确认！");
        appParameterBiz.saveMessage("您给【"+BaseContextHandler.getName()+"】配发的设备已归还，请确认！",assetsParameter.getHandingOutPeople());
    }



    /**
     * 设备归还确认查询
     * @param vo
     */
    public TableResultResponse<VAssetsReceiveVo> queryGiveBackAffirm(Map<String, Object> params){
        //1.查询条件 存放地为仪器库，负责人为空，部门为当前登录人的部门（设备部）,归还主表有一条数据为归还待确认的数据
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();

        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        this.addQueryCondition(params,paramMmap);

        paramMmap.put("mainPrincipal",BaseContextHandler.getUserID());
        //paramMmap.put("facilityDesignatedArea",DesignatedArea.YQK.getCode());
        paramMmap.put("equipmentDepartment",Constant.SBYWB);
        paramMmap.put("crtUserId",BaseContextHandler.getUserID());
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.RETURN.getCode());
        paramMmap.put("assetStatusList",assetStatusList);

        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        return new TableResultResponse<VAssetsReceiveVo>(result.getTotal(), list);
    }

    /**
     * 设备归还确认查询总条数
     * @return
     */
    public int queryGiveBackAffirmCount(String userId){
        //1.查询条件 存放地为仪器库，负责人为空，部门为当前登录人的部门（设备部）,归还主表有一条数据为归还待确认的数据
        RowBounds rb = new RowBounds();
        Map<String, Object> paramMmap = new HashMap<>();


        paramMmap.put("mainPrincipal",BaseContextHandler.getUserID());
        paramMmap.put("crtUserId",userId);
        //paramMmap.put("facilityDesignatedArea",DesignatedArea.YQK.getCode());
        paramMmap.put("equipmentDepartment",Constant.SBYWB);
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.RETURN.getCode());
        paramMmap.put("assetStatusList",assetStatusList);

        List<VAssetsReceiveVo> list =assetsReceiveMapper.selectConjunctiveRecord(paramMmap);
        if(!CollectionUtils.isEmpty(list)){
            return list.size();
        }
        return 0;
    }

    /**
     * 设备归还确认
     * @param vo
     */
    public void giveBackAffirm(VAssetsParameterVo vo){

        //修改归还主表状态为已归还
        VAssetsReceiveMain assetsReceiveMain = assetsReceiveMainMapper.selectByPrimaryKey(vo.getId());
        if(null != assetsReceiveMain){
            assetsReceiveMain.setAssetsStatus(AssetsStatus.RETURN_CONFIRM.getCode());
            EntityUtils.setUpdatedInfo(assetsReceiveMain);
            assetsReceiveMainMapper.updateByPrimaryKeySelective(assetsReceiveMain);
        }
        //修改设备主表负责人为当前登录人账号，部门为当前登录人部门，状态为已归还
        VAssetsParameter assetsParameter =  assetsParameterMapper.selectByPrimaryKey(assetsReceiveMain.getAssetsId());
        if(null != assetsParameter){
            assetsParameter.setPrincipal(BaseContextHandler.getUserID());
            Depart depart = deptFeign.getDeptByUserId(BaseContextHandler.getUserID());
            if(null == depart){
                throw new BusinessRuntimeException("feign获取部门失败");
            }
            assetsParameter.setEquipmentDepartment(depart.getCode());
            assetsParameter.setAssetsStatus(AssetsStatus.ENTERING.getCode());
            EntityUtils.setUpdatedInfo(assetsParameter);
            assetsParameterMapper.updateByPrimaryKeySelective(assetsParameter);
        }else{
            throw new BusinessRuntimeException("设备数据不存在，请检查！");
        }
        //明细表新增一条归还确认记录
        VAssetsReceiveRecords vAssetsReceiveRecords = new VAssetsReceiveRecords();
        vAssetsReceiveRecords.setId(UUIDUtils.generateUuid());
        vAssetsReceiveRecords.setAssetsId(assetsParameter.getId());
        vAssetsReceiveRecords.setAssetsReceiveMainId(assetsReceiveMain.getId());
        EntityUtils.setCreatAndUpdatInfo(vAssetsReceiveRecords);
        vAssetsReceiveRecords.setCurrentStatus(AssetsStatus.RETURN_CONFIRM.getCode());
        vAssetsReceiveRecords.setRecipient(BaseContextHandler.getUserID());
        assetsReceiveMapper.insertSelective(vAssetsReceiveRecords);
    }

    /**
     * 查询我的资产
     * @param params
     * @return
     */
    public TableResultResponse<VAssetsParameter> queryMineAssets(Map<String, Object> params){
        //1.查询条件 负责人为当前登录人，且状态为已确认的
//        RowBounds rb = new RowBounds(Integer.valueOf((String)params.get("page"))-1, Integer.parseInt(params.get("limit").toString()));
        Map<String, Object> paramMmap = new HashMap<>();

        this.addQueryCondition(params,paramMmap);
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

        paramMmap.put("principal",BaseContextHandler.getUserID());
        List assetStatusList = new ArrayList();
        assetStatusList.add(AssetsStatus.CHANGE_CONFIRMATION.getCode());
        assetStatusList.add(AssetsStatus.ENTERING.getCode());
        paramMmap.put("assetStatusList",assetStatusList);

        List<VAssetsParameter> list =assetsParameterMapper.selectAssetsParameterAll(paramMmap);
        return new TableResultResponse<VAssetsParameter>(result.getTotal(), list);
    }

    public void addQueryCondition(Map<String, Object> params,Map<String, Object> paramMmap){
        if(null != params.get("unifiedCode") && !"".equals(params.get("unifiedCode"))){
            paramMmap.put("unifiedCode",params.get("unifiedCode"));
        }

        if(null != params.get("devicename") && !"".equals(params.get("devicename"))){
            paramMmap.put("devicename",params.get("devicename"));
        }

        if(null != params.get("unitType") && !"".equals(params.get("unitType"))){
            paramMmap.put("unitType",params.get("unitType"));
        }

        if(null != params.get("assetsClass") && !"".equals(params.get("assetsClass"))){
            paramMmap.put("assetsClass",params.get("assetsClass"));
        }

        if(null != params.get("facitityStatus") && !"".equals(params.get("facitityStatus"))){
            paramMmap.put("facitityStatus",params.get("facitityStatus"));
        }

        if(null != params.get("equipmentDepartment") && !"".equals(params.get("equipmentDepartment"))){
            paramMmap.put("equipmentDepartment",params.get("equipmentDepartment"));
        }

        if(null != params.get("facilityDesignatedArea") && !"".equals(params.get("facilityDesignatedArea"))){
            paramMmap.put("facilityDesignatedArea",params.get("facilityDesignatedArea"));
        }

        if(null != params.get("criticalEquipment") && !"".equals(params.get("criticalEquipment"))){
            paramMmap.put("criticalEquipment",params.get("criticalEquipment"));
        }

        if(null != params.get("measurementCheckClass") && !"".equals(params.get("measurementCheckClass"))){
            paramMmap.put("measurementCheckClass",params.get("measurementCheckClass"));
        }

        if(null != params.get("principal") && !"".equals(params.get("principal"))){
            paramMmap.put("principal",params.get("principal"));
        }

        if(null != params.get("assetStatus") && !"".equals(params.get("assetStatus"))){
            paramMmap.put("assetStatus",params.get("assetStatus"));
        }
    }







}