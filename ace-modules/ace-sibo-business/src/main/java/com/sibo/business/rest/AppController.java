package com.sibo.business.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sibo.business.biz.VAssetsParameterBiz;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.biz.AppParameterBiz;
import com.sibo.business.biz.AppSystemMessageBiz;
import com.sibo.business.biz.VInstrumentLibraryBiz;
import com.sibo.business.entity.AppSystemMessage;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsReceiveMain;
import com.sibo.business.entity.VAssetsUpdateRecords;
import com.sibo.business.utils.MessagePush;
import com.sibo.business.vo.LimsVAssetsParameterVo;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VAssetsReceiveVo;

import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("app")
@CheckClientToken
@CheckUserToken
public class AppController extends BaseController<AppParameterBiz,VAssetsParameter,String> {
    private Logger logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private AppParameterBiz appParameterBiz;
    
    @Autowired
    private VInstrumentLibraryBiz instrumentLibraryBiz;
    
    @Autowired
    private AppSystemMessageBiz appSystemMessageBiz;

    @Autowired
    private VAssetsParameterBiz assetsParameterBiz;

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
	@IgnoreClientToken
    @IgnoreUserToken
    @ResponseBody
    public TableResultResponse<VAssetsParameter> pageList(@RequestParam Map<String, Object> params){
        //查询列表数据
        return appParameterBiz.queryAssetsParameterPage(params);
    }

    @ApiOperation("待分配分页获取数据")
    @RequestMapping(value = "/equipmentPage",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> equipmentPage(@RequestParam Map<String, Object> params){
        //查询列表数据
        return appParameterBiz.equipmentPage(params);
    }

    @ApiOperation("分页获取数据")
    @RequestMapping(value = "/pageListAll",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameterVo> getAll(@RequestParam Map<String, Object> params){
        //查询列表数据
        return appParameterBiz.queryVAssetsParameterMapperAll(params);
    }

    /**
     * 创建编号
     * @param facitityCategory
     * @param measurementCheckClass
     * @return
     */
    @RequestMapping(value = "/createNum",method = RequestMethod.GET)
    public ObjectRestResponse<String> createNum(@RequestParam(defaultValue = "0") String facitityCategory,
                                                @RequestParam(defaultValue = "0") String measurementCheckClass,
                                                @RequestParam(defaultValue = "0") String assetsClass,
                                                @RequestParam(defaultValue = "0") String assetType,
                                                @RequestParam(defaultValue = "1970-01-01") String useTime,
                                                @RequestParam(defaultValue = "0") String unifiedCode){

        String str = appParameterBiz.getMaxNum(assetType,facitityCategory, measurementCheckClass, assetsClass,useTime,unifiedCode);
        return new ObjectRestResponse<String>().data(str);
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/addAssets",method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsParameterVo> addAssets(@RequestBody VAssetsParameterVo vo) {
        appParameterBiz.add(vo);
        return  new ObjectRestResponse<VAssetsParameterVo>().data(vo);
    }

    @ApiOperation("资产追加新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ObjectRestResponse<VAssetsParameterVo> addSup(@RequestBody VAssetsParameterVo record) {
        String id = appParameterBiz.insertAdditionalAsset(record);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation("更新单个对象")
    public ObjectRestResponse<VAssetsParameterVo> update(@RequestBody VAssetsParameterVo vo){
        VAssetsParameterVo entity =  appParameterBiz.updateAdditionalAsset(vo);
        return new ObjectRestResponse<VAssetsParameterVo>().data(entity);
    }

    
    @RequestMapping(value = "/downDoc",method = RequestMethod.GET)
    public ObjectRestResponse<String> downDoc(@RequestParam(defaultValue = "0") String type,
                                                     @RequestParam(defaultValue = "0") String id,
                                                     HttpServletRequest request,
                                                     HttpServletResponse resp)  throws Exception {
        String path = appParameterBiz.exportExcel(type,id);
        //String fileName = "设备台账卡";
        //ExportUtils.down(path,fileName,request,resp);
        //return new ObjectRestResponse<String>().data(path);
        return new ObjectRestResponse<String>().data(path);
    }


    @ApiOperation("核查计划分页获取数据")
    @RequestMapping(value = "/pageInspectList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsParameter> pageInspectList(@RequestParam Map<String, Object> params){
        //查询列表数据
        return appParameterBiz.pageInspectList(params);
    }

    @ApiOperation("修改记录获取数据")
    @RequestMapping(value = "/pageUpdateRecordList",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<VAssetsUpdateRecords> pageUpdateRecordList(@RequestParam String assetsId){
        //查询列表数据
        return appParameterBiz.pageUpdateRecords(assetsId);
    }

    @ApiOperation("导出指定字段")
    @RequestMapping(value = "/exportAsset", method = RequestMethod.GET)
    public ObjectRestResponse<String> addSup(@RequestParam Map<String, Object> params,HttpServletResponse resp) throws Exception{
        String path = appParameterBiz.exportAssetsParameter(params,resp);
        logger.info("controlle返回路径："+path);
        return new ObjectRestResponse<String>().data(path);
    }


    @RequestMapping(value = "/copy/{id}",method = RequestMethod.GET)
    @ApiOperation("复制个对象")
    public ObjectRestResponse<VAssetsParameterVo> copy(@PathVariable String id){
        appParameterBiz.copyAssets(id);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo(id));
    }

    @RequestMapping(value = "/getAssets",method = RequestMethod.GET)
    @ApiOperation("lims接口调用")
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<List<LimsVAssetsParameterVo>> getAssets(@RequestParam(defaultValue = "")  String devicename, @RequestParam(defaultValue = "")  String unitType, @RequestParam(defaultValue = "")  String unifiedCode, @RequestParam(defaultValue = "")  String equipmentNumber){
        logger.info("lims请求接口开始，参数为：【devicename="+devicename+"】【unitType="+unitType+"】【unifiedCode="+unifiedCode+"】【equipmentNumber="+equipmentNumber+"】");
        List<LimsVAssetsParameterVo> list = appParameterBiz.limsQuery(devicename,unitType,unifiedCode,equipmentNumber);
        logger.info("lims请求接口结束，返回的数据为："+list.toString());
        return new ObjectRestResponse<List<LimsVAssetsParameterVo>>().data(list);
    }



    @RequestMapping(value = "/getAssetsAll",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<VAssetsParameter> all(@RequestParam Map<String, Object> params){
    	//http://192.168.0.9:9527/api/business/vAssetsParameter/equipmentPage?&facilityDesignatedArea=0&facitityStatus=0&assetsStatus=0
        //查询列表数据
    	params.put("facilityDesignatedArea", 0);
    	params.put("facitityStatus",0);
    	params.put("assetsStatus", 0);
        return appParameterBiz.equipmentPageDept(params);
    }
	
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<VAssetsParameter> getsAll(@RequestParam Map<String, Object> params){
        return appParameterBiz.equipmentPageDept(params);
    }
    
    @RequestMapping(value = "/getAssetById",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameter> getAssetById(@RequestParam String id) {
        VAssetsParameter vo = appParameterBiz.customSelectById(id);
        return new ObjectRestResponse<VAssetsParameter>().data(vo);
    }
    
    @RequestMapping(value = "/getAssetByMainId",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameter> getAssetByMainId(@RequestParam String id) {
        VAssetsParameter vo = appParameterBiz.customSelectByMainId(id);
        return new ObjectRestResponse<VAssetsParameter>().data(vo);
    }
    
    @RequestMapping(value = "/getYuanYinByMainId",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsReceiveMain> getYuanYinByMainId(@RequestParam String id) {
    	VAssetsReceiveMain vo = appParameterBiz.customSelectYuanYinByMainId(id);
        return new ObjectRestResponse<VAssetsReceiveMain>().data(vo);
    }
    
    
    @RequestMapping(value = "/getByUnifiedCode/{unifiedCode}",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> getByUnifiedCode(@PathVariable String unifiedCode) {
        VAssetsParameterVo vo = appParameterBiz.getVAssetsParameterByUnifiedCode(unifiedCode);
        return new ObjectRestResponse<VAssetsParameterVo>().data(vo);
    }
    
    
    @ApiOperation("设备派发")
    @RequestMapping(value = "/devicePaiFa", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> devicePaiFa(String id, String principal, String inputer,String name) {
    	
    	VAssetsParameterVo vo=new VAssetsParameterVo();
    	vo.setId(id);
    	vo.setPrincipal(principal);
    	vo.setInputer(inputer);
    	vo.setCrtUserName(name);
        instrumentLibraryBiz.distribute(vo);
        MessagePush.sendPush(principal, "【"+name+"】给您配发了一个设备，请确认并领取！");
        appParameterBiz.saveMessage(name+"给您配发了一个设备，请确认并领取！",principal);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    
    
    
    @ApiOperation("计量计划分页获取数据")
    @RequestMapping(value = "/queryDevice",method = RequestMethod.GET)
    @ResponseBody
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<VAssetsParameter> pagePlayList(@RequestParam Map<String, Object> params,@RequestParam String status){
        //查询列表数据
    	params.put("status", status);
        return appParameterBiz.queryDeviceList(params);
    }
    
    
    @ApiOperation("待确认查询")
    @RequestMapping(value = "/queryWaitCommit", method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<VAssetsParameter> queryWaitCommit(@RequestParam Map<String, Object> params,String id) {
    	
        return appParameterBiz.queryWaitCommit(params,id);
    }
    
    
    @ApiOperation("设备归还确认查询")
    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/queryGiveBackAffirm", method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryGiveBackAffirm(@RequestParam Map<String, Object> params,String id) {
        return appParameterBiz.queryGiveBackAffirm(params,id);
    }
    
    @ApiOperation("驳回数据查询")
    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/queryRejectData", method = RequestMethod.GET)
    public TableResultResponse<VAssetsReceiveVo> queryRejectData(@RequestParam Map<String, Object> params,String id) {
        return appParameterBiz.queryRejectData(params,id);
    }
    
    
    @ApiOperation("确认")
    @RequestMapping(value = "/affirm", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> affirm(String assetId,String uid, String depart,String uname) {
    	VAssetsParameterVo vo=new VAssetsParameterVo();
    	vo.setId(assetId);
    	vo.setPrincipal(uid);
    	vo.setEquipmentDepartment(depart);
    	appParameterBiz.affirm(vo,uname);
    	
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    
    @ApiOperation("错误数据驳回")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> reject(String id, String uid,String remark,String uname) {
    	VAssetsParameterVo vo=new VAssetsParameterVo();
    	vo.setId(id);
    	vo.setInputer(uid);
    	vo.setRemark(remark);
    	appParameterBiz.reject(vo,uname);
    	
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    
    @ApiOperation("设备归库")
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> cancel(@RequestParam String asstesId) {
    	appParameterBiz.cancel(asstesId);
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    
    
    @ApiOperation("待归还查询")
    @RequestMapping(value = "/queryGiveBack", method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public TableResultResponse<VAssetsParameter> queryGiveBack(@RequestParam Map<String, Object> params,String uid) {
        return appParameterBiz.queryGiveBack(params,uid);
    }
    
    
    @ApiOperation("设备归还")
    @RequestMapping(value = "/giveBack", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<VAssetsParameterVo> giveBack(String assetId,String uid,String uname) {
    	VAssetsParameterVo vo=new VAssetsParameterVo();
    	vo.setId(assetId);
    	vo.setPrincipal(uid);
    	vo.setInputer(uid);
    	appParameterBiz.giveBack(vo,uname);
    	
        return new ObjectRestResponse<VAssetsParameterVo>().data(new VAssetsParameterVo());
    }
    
    @ApiOperation("查询消息数据")
    @RequestMapping(value = "/queryMessage",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public List<AppSystemMessage> queryMessage(@RequestParam String userId){
        //查询列表数据
    	Example example =new Example(AppSystemMessage.class);
    	example.createCriteria().andEqualTo("userId",userId).andEqualTo("status",0);
    	
    	example.setOrderByClause("crt_time desc");
        return appSystemMessageBiz.selectByExample(example);
    }
    
    @ApiOperation("设置消息已读或未读")
    @RequestMapping(value = "/setMessageStatus",method = RequestMethod.GET)
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<Integer> setMessageStatus(@RequestParam String id,@RequestParam String status){
        //查询列表数据
    	int result=0;
    	AppSystemMessage appSystemMessage=appSystemMessageBiz.selectById(id);
    	appSystemMessage.setStatus(status);
    	appSystemMessageBiz.updateById(appSystemMessage);
    	if(appSystemMessage.getStatus().equals(status)) {
    		result=0;
    	}else {
    		result=1;
    	}
        return new ObjectRestResponse<Integer>().data(result);
    }

    @RequestMapping(value = "/updateAssetsPrincipalDept",method = RequestMethod.GET)
    @ApiOperation("lims修改用户组织")
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse<String> updateAssetsPrincipalDept(@RequestParam(defaultValue = "")  String userId, @RequestParam(defaultValue = "")  String deptCode){
        logger.info("lims修改用户组织请求接口开始，参数为：【userId="+userId+"】【deptCode="+deptCode+"】");
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptCode) ){
            String list = assetsParameterBiz.updateAssetsPrincipalDept(userId,deptCode);
            logger.info("lims修改用户组织请求接口结束，返回的数据为："+list);
            return new ObjectRestResponse<String>().data(list);
        }
        return new ObjectRestResponse<String>().data("-1");
    }
    
}