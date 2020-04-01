package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VAssetsChangeRecords;
import com.sibo.business.vo.VAssetsChangeRecordsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资产变更记录
 * 
 * @author henry_liu
 * @email 137022680@qq.com
 * @version 2018-10-22 14:22:08
 */
@Repository
public interface VAssetsChangeRecordsMapper extends CommonMapper<VAssetsChangeRecords> {

    public List<VAssetsChangeRecords> selectall(RowBounds rb, Map<String, Object> paramMmap);//需要传RowBounds 类型的参数

    public List<VAssetsChangeRecords> selectChangeFinish(RowBounds rb, Map<String, Object> paramMmap);

    /**
     * 统计查询变更记录
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsChangeRecords> selectRecordAll(RowBounds rb, Map<String, Object> paramMmap);

    /**
     * 查询版本
     * @param paramMmap
     * @return
     */
    public Integer selectVersion(Map<String, Object> paramMmap);
	
}
