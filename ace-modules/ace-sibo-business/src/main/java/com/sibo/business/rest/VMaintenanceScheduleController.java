package com.sibo.business.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.sibo.business.biz.VMaintenanceScheduleBiz;
import com.sibo.business.entity.VMaintenanceSchedule;
import com.sibo.business.vo.VMaintenanceScheduleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("vMaintenanceSchedule")
//@CheckClientToken
//@CheckUserToken
public class VMaintenanceScheduleController extends BaseController<VMaintenanceScheduleBiz,VMaintenanceSchedule,String> {

    @Autowired
    private VMaintenanceScheduleBiz maintenanceScheduleBiz;

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/listPage",method = RequestMethod.GET)
    public TableResultResponse<VMaintenanceScheduleVo> listPage(@RequestParam Map<String, Object> params){
        return maintenanceScheduleBiz.listPage(params);
    }

    @PostMapping("/import")
    public ObjectRestResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                       RedirectAttributes redirectAttributes) {

        maintenanceScheduleBiz.importExcel(file);

        return new ObjectRestResponse<>().data("0");
    }



}