package com.github.wxiaoqi.security.wf.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.wf.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
@CheckUserToken
@Api(description = "工作流任务模块", tags = "工作流任务")
public class TestWorkflow {

    @Autowired
    private FlowService flowService;


    @RequestMapping(value = "/firstFlow", method = RequestMethod.GET)
    @ApiOperation(value = "测试维护")
    public String applyLeave(String businessId) {
        ExecutionEntity pi = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建流程发起人
            map.put("employee", BaseContextHandler.getUsername());
            // 请假流程key
            String processName = "process";
            pi = flowService.createProcess(processName, map,businessId);

        } catch (Exception e){
            e.printStackTrace();
        }

        if(pi!=null) {
            return pi.getProcessInstanceId();
        }else{
            return null;
        }
    }
}
