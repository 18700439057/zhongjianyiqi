package com.sibo.business.mapper;

import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VInvestment;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-04-15 16:45:04
 */
@Repository
public interface VInvestmentMapper extends CommonMapper<VInvestment> {

    public List<VInvestment> queryYearInvestment(String _parameter);
	
}
