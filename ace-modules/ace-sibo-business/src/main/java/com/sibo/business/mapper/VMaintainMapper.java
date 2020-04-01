package com.sibo.business.mapper;

import com.sibo.business.entity.VMaintain;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.vo.VMaintainVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 维修主表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Repository
public interface VMaintainMapper extends CommonMapper<VMaintain> {

    public List<VMaintainVo> selectMaintain(RowBounds rb, Map<String, Object> paramMmap);
	
}
