package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VInvestmentBiz;
import com.sibo.business.entity.VInvestment;
import com.sibo.business.vo.VAssetsParameterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-04-15 16:45:04
 */
@RestController
@RequestMapping("vInvestment")
@CheckClientToken
@CheckUserToken
public class VInvestmentController extends BaseController<VInvestmentBiz,VInvestment,String> {

    @Autowired
    private VInvestmentBiz investmentBiz;

    @RequestMapping(value = "/copy/{id}",method = RequestMethod.GET)
    @ApiOperation("复制个对象")
    public ObjectRestResponse<VInvestment> copy(@PathVariable String id){
        investmentBiz.copyData(id);
        return new ObjectRestResponse<VInvestment>().data(new VInvestment());
    }

    @RequestMapping(value = "/exportExcel",method = RequestMethod.GET)
    @ApiOperation("导出")
    public ObjectRestResponse<String> exportExcel(@RequestParam Map<String, Object> params, HttpServletResponse resp) throws Exception{
        String path = investmentBiz.exportExcel(params,resp);
        return new ObjectRestResponse<String>().data(path);
    }

}