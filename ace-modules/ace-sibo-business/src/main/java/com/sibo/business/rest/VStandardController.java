package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VStandardBiz;
import com.sibo.business.entity.VStandard;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 计量标准器
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-13 16:27:47
 */
@RestController
@RequestMapping("vStandard")
@CheckClientToken
@CheckUserToken
public class VStandardController extends BaseController<VStandardBiz,VStandard,String> {

}