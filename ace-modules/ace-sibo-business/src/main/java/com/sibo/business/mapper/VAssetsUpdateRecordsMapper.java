package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VAssetsChangeRecords;
import com.sibo.business.entity.VAssetsUpdateRecords;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资产修改记录
 * 
 * @author henry_liu
 * @email 137022680@qq.com
 * @version 2018-10-22 14:22:08
 */
@Repository
public interface VAssetsUpdateRecordsMapper extends CommonMapper<VAssetsUpdateRecords> {


}
