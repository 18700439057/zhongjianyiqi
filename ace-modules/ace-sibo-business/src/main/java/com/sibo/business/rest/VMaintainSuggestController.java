package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMaintainSuggestBiz;
import com.sibo.business.entity.VMaintainSuggest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("vMaintainSuggest")
@CheckClientToken
@CheckUserToken
public class VMaintainSuggestController extends BaseController<VMaintainSuggestBiz,VMaintainSuggest,String> {

}