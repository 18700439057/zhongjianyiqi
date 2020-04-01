package com.sibo.business.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VCostRecordBiz;
import com.sibo.business.biz.VCronBiz;
import com.sibo.business.biz.VCronLogBiz;
import com.sibo.business.entity.VCostRecord;
import com.sibo.business.entity.VCron;
import com.sibo.business.entity.VCronLog;
import com.sibo.business.job.ScheduledTask;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cron")
@CheckClientToken
@CheckUserToken
public class VCronController extends BaseController<VCronBiz,VCron,String> {

    @Autowired
    private ScheduledTask scheduledTask;

    @RequestMapping(value = "/execute",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("手动执行备份")
    public ObjectRestResponse<String> execute(){
        String strcmd = "cmd /c start D:\\up\\jar\\backup.bat";
        scheduledTask.run_cmd(strcmd,BaseContextHandler.getName());
        return new ObjectRestResponse<String>().data("ok");
    }
}
