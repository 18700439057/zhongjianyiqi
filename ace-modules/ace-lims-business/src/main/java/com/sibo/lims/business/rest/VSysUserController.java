package com.sibo.lims.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;

import com.sibo.lims.business.biz.VSysUserBiz;
import com.sibo.lims.business.entity.VSysUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
@RestController
@RequestMapping("vSysUser")
@CheckClientToken
@CheckUserToken
public class VSysUserController extends BaseController<VSysUserBiz,VSysUser,String> {

}