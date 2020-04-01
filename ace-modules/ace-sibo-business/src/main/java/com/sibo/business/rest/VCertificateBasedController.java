package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VCertificateBasedBiz;
import com.sibo.business.entity.VCertificateBased;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 计量证书依据表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@RestController
@RequestMapping("vCertificateBased")
@CheckClientToken
@CheckUserToken
public class VCertificateBasedController extends BaseController<VCertificateBasedBiz,VCertificateBased,String> {

}