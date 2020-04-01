package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMaintenanceAdviceBiz;
import com.sibo.business.entity.VMaintenanceAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("vMaintenanceAdvice")
@CheckClientToken
@CheckUserToken
public class VMaintenanceAdviceController extends BaseController<VMaintenanceAdviceBiz,VMaintenanceAdvice,String> {

}