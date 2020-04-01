package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VCostRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VCostRecordMapper extends CommonMapper<VCostRecord> {

    public List<VCostRecord> selectCurrentyearList(String year);

}
