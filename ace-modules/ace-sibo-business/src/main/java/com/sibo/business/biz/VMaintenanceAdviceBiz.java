package com.sibo.business.biz;

import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMaintenanceAdvice;
import com.sibo.business.mapper.VMaintenanceAdviceMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 维修建议表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Service
public class VMaintenanceAdviceBiz extends BusinessBiz<VMaintenanceAdviceMapper,VMaintenanceAdvice> {
}