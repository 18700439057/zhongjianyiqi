package com.sibo.business.biz;

import org.springframework.stereotype.Service;

import com.sibo.business.entity.VStandard;
import com.sibo.business.mapper.VStandardMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计量标准器
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-13 16:27:47
 */
@Service
@Transactional
public class VStandardBiz extends BusinessBiz<VStandardMapper,VStandard> {
}