package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMaintainSuggest;
import com.sibo.business.mapper.VMaintainSuggestMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 维修建议设置
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Service
public class VMaintainSuggestBiz extends BusinessBiz<VMaintainSuggestMapper,VMaintainSuggest> {

    @Override
    public void insertSelective(VMaintainSuggest entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }
}