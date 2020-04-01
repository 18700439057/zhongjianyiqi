/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.security.admin.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.admin.biz.DepartBiz;
import com.github.wxiaoqi.security.admin.biz.MenuBiz;
import com.github.wxiaoqi.security.admin.biz.PositionBiz;
import com.github.wxiaoqi.security.admin.biz.UserBiz;
import com.github.wxiaoqi.security.admin.entity.Element;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.rpc.service.PermissionService;
import com.github.wxiaoqi.security.admin.vo.AuthUser;
import com.github.wxiaoqi.security.admin.vo.FrontUser;
import com.github.wxiaoqi.security.admin.vo.MenuTree;
import com.github.wxiaoqi.security.admin.vo.VSysAccount;
import com.github.wxiaoqi.security.api.vo.user.UserInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
@CheckUserToken
@CheckClientToken
@Api(tags = "用户模块")
public class UserController extends BaseController<UserBiz, User,String> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PositionBiz positionBiz;

    @Autowired
    private MenuBiz menuBiz;

    @Autowired
    private UserBiz userBiz;





    @Override
    @IgnoreUserToken
    @IgnoreClientToken
    public List<User> all() {
        return super.all();
    }

    @IgnoreUserToken
    @ApiOperation("账户密码验证")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ObjectRestResponse<UserInfo> validate(String username, String password) {
        return new ObjectRestResponse<UserInfo>().data(permissionService.validate(username, password));
    }

    @IgnoreUserToken
    @ApiOperation("根据账户名获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ObjectRestResponse<AuthUser> validate(String username) {
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(baseBiz.getUserByUsername(username), user);
        return new ObjectRestResponse<AuthUser>().data(user);
    }


    @ApiOperation("账户修改密码")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> changePassword(String oldPass, String newPass) {
        return new ObjectRestResponse<Boolean>().data(baseBiz.changePassword(oldPass, newPass));
    }



    @ApiOperation("获取用户信息接口")
    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo() throws Exception {
        FrontUser userInfo = permissionService.getUserInfo();
        if (userInfo == null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @ApiOperation("获取用户访问菜单")
    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuTree> getMenusByUsername() throws Exception {
        List<MenuTree> list=  permissionService.getMenusByUsername();
        return list;
    }

    @ApiOperation("获取所有菜单")
    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }

    @ApiOperation("获取用户可管辖部门id列表")
    @RequestMapping(value = "/dataDepart", method = RequestMethod.GET)
    public List<String> getUserDataDepartIds(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return baseBiz.getUserDataDepartIds(userId);
        }
        return new ArrayList<>();
    }

    @ApiOperation("获取用户流程审批岗位")
    @RequestMapping(value = "/flowPosition", method = RequestMethod.GET)
    public List<String> getUserFlowPositions(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return positionBiz.getUserFlowPosition(userId).stream().map(Position::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @ApiOperation("保存lims用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @IgnoreUserToken
    public List<String> saveBatchUser(@RequestBody List<User> userList) {
        userBiz.saveBatchUser(userList);
        return new ArrayList<>();
    }
    @IgnoreUserToken
    @RequestMapping(value = "/relevancy",method = RequestMethod.POST)
    public void saveUserAndDept(@RequestBody List<VSysAccount> list){
        userBiz.relevanceUserAndDept(list);
    }

    @RequestMapping(value = "/alltest", method = RequestMethod.GET)
    @IgnoreUserToken
    @IgnoreClientToken
    public List<User> getAllUser() {
        return userBiz.queryUserAll();
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @ApiOperation("账户密码验证")
    @RequestMapping(value = "/app/loginApp", method = RequestMethod.POST)
    public ObjectRestResponse<UserInfo> loginApp(String username, String password) {
        return new ObjectRestResponse<UserInfo>().data(permissionService.appLogin(username, password));

    }

    @ApiOperation("获取用户访问菜单")
    @RequestMapping(value = "/app/menus", method = RequestMethod.GET)
    @IgnoreUserToken
    @IgnoreClientToken
    public List<MenuTree> getAppMenusByUserId(String uid) throws Exception {
        List<MenuTree> list=  permissionService.getAppMenusByUserId(uid);
        return list;
    }

    @ApiOperation("获取用户访问按钮")
    @RequestMapping(value = "/app/btns", method = RequestMethod.GET)
    @IgnoreUserToken
    @IgnoreClientToken
    public List<Element> getAppBtnsByMenuId(String mid) throws Exception {
        List<Element> list=  permissionService.getAppBtnsByMenuId(mid);
        return list;
    }

    @ApiOperation("获取用户信息(权限，部门，菜单)")
    @RequestMapping(value = "/app/userInfo", method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @IgnoreClientToken
    public ResponseEntity<?> getUserInfoAndQx(String userName) throws Exception {
        FrontUser userInfo = permissionService.getUserInfoAndQx(userName);
        if (userInfo == null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }



}
