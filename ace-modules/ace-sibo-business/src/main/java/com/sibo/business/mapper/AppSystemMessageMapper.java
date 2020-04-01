package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.AppSystemMessage;
import com.sibo.business.entity.VAssetsAccessory;
import com.sibo.business.entity.VAssetsParameter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 设备资料
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:35
 */
@Repository
public interface AppSystemMessageMapper extends CommonMapper<AppSystemMessage> {
	
	/**
     * 计量计划查询
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<AppSystemMessage> selectAppSystemMessageByStatus(Map<String, Object> paramMmap);
}
