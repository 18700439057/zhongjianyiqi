package com.sibo.business.biz;

import org.springframework.stereotype.Service;

import com.sibo.business.entity.VSourceRecord;
import com.sibo.business.mapper.VSourceRecordMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

/**
 * 原始记录表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-10 18:33:32
 */
@Service
@Transactional
public class VSourceRecordBiz extends BusinessBiz<VSourceRecordMapper,VSourceRecord> {
}