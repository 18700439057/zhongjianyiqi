package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VPlannedApprovedProjectsBiz;
import com.sibo.business.entity.VPlannedApprovedProjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
/**
 * 计量/核定项目表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@RestController
@RequestMapping("vPlannedApprovedProjects")
@CheckClientToken
@CheckUserToken
public class VPlannedApprovedProjectsController extends BaseController<VPlannedApprovedProjectsBiz,VPlannedApprovedProjects,String> {

    @Autowired
    private VPlannedApprovedProjectsBiz vPlannedApprovedProjectsBiz;

    /**
     * 复制项目
     * @param id
     * @return
     */
    @RequestMapping(value = "/copyObj/{id}",method = RequestMethod.GET)
    public ObjectRestResponse<String> copyObj (@PathVariable String id){
        vPlannedApprovedProjectsBiz.copyObj(id);
        return new ObjectRestResponse<String>().data("");
    }


}