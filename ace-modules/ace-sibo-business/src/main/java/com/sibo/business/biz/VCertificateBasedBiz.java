package com.sibo.business.biz;

import org.springframework.stereotype.Service;

import com.sibo.business.entity.VCertificateBased;
import com.sibo.business.mapper.VCertificateBasedMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计量证书依据表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Service
@Transactional
public class VCertificateBasedBiz extends BusinessBiz<VCertificateBasedMapper,VCertificateBased> {
}