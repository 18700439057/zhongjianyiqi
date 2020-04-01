package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VBusinessFile;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-15 10:55:56
 */
@Repository
public interface VBusinessFileMapper extends CommonMapper<VBusinessFile> {

    public List<VBusinessFile> selectFileAll(RowBounds rb, Map<String, Object> paramMmap);
	
}
