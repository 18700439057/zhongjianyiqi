package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VCostRecord;
import com.sibo.business.entity.VCronLog;
import com.sibo.business.mapper.VCostRecordMapper;
import com.sibo.business.mapper.VCronLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class VCronLogBiz extends BusinessBiz<VCronLogMapper,VCronLog> {
    @Override
    public void insertSelective(VCronLog entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }
}
