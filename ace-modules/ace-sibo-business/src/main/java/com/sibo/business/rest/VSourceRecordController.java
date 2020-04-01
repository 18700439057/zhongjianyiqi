package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VSourceRecordBiz;
import com.sibo.business.entity.VSourceRecord;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 原始记录表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-10 18:33:32
 */
@RestController
@RequestMapping("vSourceRecord")
@CheckClientToken
@CheckUserToken
public class VSourceRecordController extends BaseController<VSourceRecordBiz,VSourceRecord,String> {

}