package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.DepartBiz;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.vo.DepartTree;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ace
 */
@RestController
@RequestMapping("depart")
@CheckClientToken
@CheckUserToken
@Api(tags = "部门管理")
public class DepartController extends BaseController<DepartBiz,Depart,String> {
    @Autowired
    private DepartBiz DepartBiz;
    @ApiOperation("获取部门树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @IgnoreUserToken
    @IgnoreClientToken
    public List<DepartTree> getTree() {
        List<Depart> departs = this.baseBiz.selectListAll();
        List<DepartTree> trees = new ArrayList<>();
        departs.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }
    @ApiOperation("获取部门关联用户")
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public TableResultResponse<User> getDepartUsers(String departId,String userName){
        return this.baseBiz.getDepartUsers(departId,userName);
    }

    @ApiOperation("部门添加用户")
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> addDepartUser(String departId, String userIds){
        this.baseBiz.addDepartUser(departId,userIds);
        return new ObjectRestResponse<>().data(true);
    }

    @ApiOperation("部门移除用户")
    @RequestMapping(value = "user",method = RequestMethod.DELETE)
    public ObjectRestResponse<Boolean> delDepartUser(String departId,String userId){
        this.baseBiz.delDepartUser(departId,userId);
        return new ObjectRestResponse<>().data(true);
    }

    @ApiOperation("获取部门信息")
    @RequestMapping(value = "getByPK/{id}",method = RequestMethod.GET)
    public Map<String,String> getDepart(@PathVariable String id){
        return this.baseBiz.getDeparts(id);
    }


    @ApiOperation("用户id获取部门信息")
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "getByUserId/{userId}",method = RequestMethod.GET)
    public Depart getDepartByUserId(@PathVariable("userId") String userId){
        return this.baseBiz.getDepartByUserId(userId);
    }

    @ApiOperation("lims获取部门数据")
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public void saveBatchOrg(@RequestBody  List<Depart> departList){
        DepartBiz.saveDept(departList);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public List<Depart> all() {
        return super.all();
    }
}