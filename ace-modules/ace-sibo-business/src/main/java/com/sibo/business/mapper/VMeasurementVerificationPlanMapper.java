package com.sibo.business.mapper;

import com.sibo.business.entity.VMeasurementVerificationPlan;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计量/核查计划表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Repository
public interface VMeasurementVerificationPlanMapper extends CommonMapper<VMeasurementVerificationPlan> {

    /**
     * 查询首页计量年度数据
     * @return
     */
    public List<VMeasurementVerificationPlan> queryDashboardVerificationPlanCount(String year);
	
}
