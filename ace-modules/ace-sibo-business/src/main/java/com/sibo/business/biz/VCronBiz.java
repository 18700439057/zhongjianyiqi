package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VCron;
import com.sibo.business.entity.VCronLog;
import com.sibo.business.mapper.VCronLogMapper;
import com.sibo.business.mapper.VCronMapper;
import org.springframework.stereotype.Service;

@Service
public class VCronBiz extends BusinessBiz<VCronMapper,VCron> {

    @Override
    public void insertSelective(VCron entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(VCron entity) {
        entity.setCron("0 0 "+entity.getCron()+" * * ?");
        super.updateSelectiveById(entity);
    }
}
