package com.sibo.business.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.utils.ExcelData;
import com.sibo.business.vo.VMaintenanceScheduleVo;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMaintenanceSchedule;
import com.sibo.business.mapper.VMaintenanceScheduleMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 维修计划表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Service
public class VMaintenanceScheduleBiz extends BusinessBiz<VMaintenanceScheduleMapper,VMaintenanceSchedule> {

    @Autowired
    private VMaintenanceScheduleMapper maintenanceScheduleMapper;
    private Logger logger = LoggerFactory.getLogger(VMaintenanceScheduleBiz.class);

    @Override
    public void insertSelective(VMaintenanceSchedule entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public TableResultResponse<VMaintenanceScheduleVo> listPage(Map<String, Object> params){
//        RowBounds rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        Map<String, Object> paramMmap = new HashMap<>();
        paramMmap.put("assetId",params.get("assetId"));
        paramMmap.put("nature",params.get("nature"));
        paramMmap.put("personCharge",params.get("personCharge"));
        if(null != params.get("startTime") && null != params.get("endTime")){
            paramMmap.put("startTime",params.get("startTime"));
            paramMmap.put("endTime",params.get("endTime"));
        }
        List resultList = maintenanceScheduleMapper.selectMaintenanceSchedule(paramMmap);
        return new TableResultResponse<VMaintenanceScheduleVo>(result.getTotal(), resultList);
    }

    public List<VMaintenanceScheduleVo> queryYearSchedule(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return maintenanceScheduleMapper.selectYearSchedule(year+"");
    }

    public void importExcel(MultipartFile file){
        try {
            List<String[]> list = ExcelData.getExcelData(file);
            if(!CollectionUtils.isEmpty(list)){
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
                for (int i=0;i<list.size();i++) {
                    VMaintenanceSchedule vo = new VMaintenanceSchedule();
                    String[] str = list.get(i);
                    // 获取字段值
                    for (int j = 1;j<str.length;j++){
                        vo.setId(UUIDUtils.generateUuid());
                        if(!StringUtils.isEmpty(str[j]) && j==1){
                            vo.setAssetId(str[j]);
                        }else if(!StringUtils.isEmpty(str[j]) && j==2){
                            vo.setNature(str[j]);
                        }else if(!StringUtils.isEmpty(str[j]) && j==3){
                            vo.setPersonCharge(str[j]);
                        }else if(!StringUtils.isEmpty(str[j]) && j==4){
                            vo.setStartTime(sdf.parse(str[j]));
                        }else if(!StringUtils.isEmpty(str[j]) && j==5){
                            vo.setEndTime(sdf.parse(str[j]));
                        }else{
                            vo.setRemark(str[j]);
                        }
                    }
                    super.insertSelective(vo);
                }
            }
        }catch (Exception e){
            logger.error("导入异常:"+e.getMessage());
            throw new BusinessRuntimeException("导入异常");
        }

    }
}