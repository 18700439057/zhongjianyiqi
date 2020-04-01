package com.sibo.business.mapper;

import com.sibo.business.entity.VMaintenanceSchedule;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.vo.VMaintenanceScheduleVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 维修计划表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Repository
public interface VMaintenanceScheduleMapper extends CommonMapper<VMaintenanceSchedule> {

    public List<VMaintenanceScheduleVo> selectMaintenanceSchedule(Map<String, Object> paramMmap);

    public List<VMaintenanceScheduleVo> selectYearSchedule(String year);
	
}
