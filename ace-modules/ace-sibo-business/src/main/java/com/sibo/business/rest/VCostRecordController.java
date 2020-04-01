package com.sibo.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VCostRecordBiz;
import com.sibo.business.entity.VCostRecord;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("vMaintenanceRecord")
@CheckClientToken
@CheckUserToken
public class VCostRecordController extends BaseController<VCostRecordBiz,VCostRecord,String> {
    private Logger logger = LoggerFactory.getLogger(VCostRecordController.class);

    @Autowired
    private VCostRecordBiz costRecordBiz;

    @ApiOperation("导出")
    @RequestMapping(value = "/exportCost", method = RequestMethod.GET)
    public ObjectRestResponse<String> exportCost(@RequestParam Map<String, Object> params, HttpServletResponse resp) throws Exception{
//        String path = assetsParameterBiz.exportAssetsParameter(params,resp);
//        logger.info("controlle返回路径："+path);
        String path = costRecordBiz.exportCost(params,resp);
        logger.info("controlle返回路径："+path);
        return new ObjectRestResponse<String>().data(path);
    }
}
