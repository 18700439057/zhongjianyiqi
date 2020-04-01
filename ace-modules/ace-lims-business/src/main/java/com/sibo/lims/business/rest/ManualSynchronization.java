package com.sibo.lims.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.sibo.lims.business.biz.VSysAccountBiz;
import com.sibo.lims.business.biz.VSysOrgBiz;
import com.sibo.lims.business.biz.VSysUserBiz;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 手动同步用户信息
 */
@RestController
@RequestMapping("/syncUser")
public class ManualSynchronization{
    private Logger logger = LoggerFactory.getLogger(ManualSynchronization.class);

    @Autowired
    private VSysUserBiz vSysUserBiz;

    @Autowired
    private VSysOrgBiz vSysOrgBiz;

    @Autowired
    private VSysAccountBiz vSysAccountBiz;

    @ApiOperation("同步用户")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ObjectRestResponse<String> sync(){
        logger.info("手动同步数据开始：时间【"+new Date()+"】");
        vSysUserBiz.queryUser();
        vSysOrgBiz.queryDept();
        vSysAccountBiz.queryAccount();
        logger.info("手动同步数据结束：时间【"+new Date()+"】");
        return new ObjectRestResponse<String>().data("同步成功");
    }
}
