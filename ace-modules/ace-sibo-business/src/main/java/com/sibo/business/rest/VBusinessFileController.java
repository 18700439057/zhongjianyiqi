package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VBusinessFileBiz;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.entity.VSupAppraise;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vBusinessFile")
@CheckClientToken
@CheckUserToken
public class VBusinessFileController extends BaseController<VBusinessFileBiz,VBusinessFile,String> {

    @Autowired
    private VBusinessFileBiz vBusinessFileBiz;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据父id获取附件表")
    public TableResultResponse<VBusinessFile> page(@RequestParam(defaultValue = "0") String supplierId) {
        Example example = new Example(VBusinessFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bussinessId", supplierId);
        List<VBusinessFile> elements = baseBiz.selectByExample(example);
        return new TableResultResponse<VBusinessFile>(elements.size(), elements);
    }

    @RequestMapping(value = "/queryFileList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取附件表")
    public TableResultResponse<VBusinessFile> queryFileList(@RequestParam Map<String, Object> params) {
        List<VBusinessFile> list = vBusinessFileBiz.queryFileList(params);
        return new TableResultResponse<VBusinessFile>(list.size(), list);
    }

}