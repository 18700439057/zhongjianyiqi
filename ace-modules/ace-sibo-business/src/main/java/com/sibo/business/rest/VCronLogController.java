package com.sibo.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VCronLogBiz;
import com.sibo.business.entity.VCronLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cronLog")
@CheckClientToken
@CheckUserToken
public class VCronLogController extends BaseController<VCronLogBiz,VCronLog,String> {
}
