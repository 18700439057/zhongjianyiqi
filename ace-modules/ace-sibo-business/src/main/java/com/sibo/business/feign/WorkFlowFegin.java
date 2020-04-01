package com.sibo.business.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ace-workflow",configuration = FeignApplyConfiguration.class)
public interface WorkFlowFegin {

    @RequestMapping(value = "test/firstFlow",method = RequestMethod.GET)
    public String  startInstance(@RequestParam("businessId")  String businessId);

    @RequestMapping(value = "tasks/todo",method = RequestMethod.GET)
    public List<String> getMyApproveTasks(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);


}
