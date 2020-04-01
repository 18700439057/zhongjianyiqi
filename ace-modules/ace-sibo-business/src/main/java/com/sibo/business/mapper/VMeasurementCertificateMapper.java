package com.sibo.business.mapper;

import com.sibo.business.entity.VMeasurementCertificate;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

/**
 * 计量证书表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Repository
public interface VMeasurementCertificateMapper extends CommonMapper<VMeasurementCertificate> {

    String getCertificateMaxNum();
	
}
