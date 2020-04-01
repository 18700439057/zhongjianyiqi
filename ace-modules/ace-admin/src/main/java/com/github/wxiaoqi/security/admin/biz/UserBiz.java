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

package com.github.wxiaoqi.security.admin.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.vo.VSysAccount;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    private Logger logger = LoggerFactory.getLogger(UserBiz.class);
    @Autowired
    private MergeCore mergeCore;

    @Autowired
    private DepartMapper departMapper;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();


    @Override
    public User selectById(Object id) {
        User user = super.selectById(id);
        try {
            mergeCore.mergeOne(User.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    public Boolean changePassword(String oldPass, String newPass) {
        User user = this.getUserByUsername(BaseContextHandler.getUsername());
        if (encoder.matches(oldPass, user.getPassword())) {
            String password = encoder.encode(newPass);
            user.setPassword(password);
            this.updateSelectiveById(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> selectListAll(){
        Example example = new Example(User.class);
        example.createCriteria().andNotEqualTo("username","admin").andNotEqualTo("username","suadmin");
        example.setOrderByClause("nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M')");
        return super.selectByExample(example);
//        return super.selectListAll();
    }

    @Override
    public TableResultResponse<User> selectByQuery(Query query) {
        Class<User> clazz = (Class<User>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        query2criteria(query, example);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        example.setOrderByClause("nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M')");
        List<User> list = this.selectByExample(example);
        return new TableResultResponse<User>(result.getTotal(), list);
    }

    @Override
    public void insertSelective(User entity) {
        String password = encoder.encode(entity.getPassword());
        String departId = entity.getDepartId();
        EntityUtils.setCreatAndUpdatInfo(entity);
        entity.setPassword(password);
        entity.setDepartId(departId);
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        entity.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        entity.setStatus(entity.getStatus());
        String userId = UUIDUtils.generateUuid();
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setId(userId);
        entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(User entity) {
        EntityUtils.setUpdatedInfo(entity);
        User user = mapper.selectByPrimaryKey(entity.getId());
        user.setStatus(entity.getStatus());
        if (!user.getDepartId().equals(entity.getDepartId())) {
            departMapper.deleteDepartUser(user.getDepartId(), entity.getId());
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setTenantId(BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        User user = mapper.selectByPrimaryKey(id);
        user.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        this.updateSelectiveById(user);
    }

    @Override
    public List<User> selectByExample(Object obj) {
        Example example = (Example) obj;
        example.createCriteria().andEqualTo("isDeleted", BooleanUtil.BOOLEAN_FALSE);
        List<User> users = super.selectByExample(example);
        try {
            mergeCore.mergeResult(User.class, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        user.setStatus(BooleanUtil.BOOLEAN_TRUE);
        User resultUser = mapper.selectOne(user);
        if(null == resultUser){
            throw new BusinessRuntimeException("用户不存在");
        }
        return resultUser;
    }

    @Override
    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                example.or(criteria);
            }
        }
    }

    public List<String> getUserDataDepartIds(String userId) {
        return mapper.selectUserDataDepartIds(userId);
    }

    /**
     * 人员插入lims数据
     * @param userList
     */
    public void saveBatchUser(List<User> userList){
        List<User> oldList = mapper.selectAll();
        try{
            //删除源数据
            /*List<String> oldIds = userList.stream().map(o->o.getId()).collect(Collectors.toList());
            Example example = new Example(User.class);
            example.createCriteria().andIn("id",oldIds);
            mapper.deleteByExample(example);

            for (User user : userList){
                super.insertSelective(user);
            }*/
            Map<String,User> map = oldList.stream().collect(Collectors.toMap(user -> user.getName(),user -> user));
            for (User user : userList){
                if(null != map.get(user.getName())){
                    User ouser = map.get(user.getName());
                    BeanUtils.copyProperties(user,ouser);
                    super.updateSelectiveById(ouser);
                }else{
                    try{
                        super.insertSelective(user);
                        logger.info("人员insert lims数据成功");
                    }catch (Exception e){
                        if(e.getMessage().indexOf("违反唯一约束条件") > 0){
                            logger.error("人员插入失败,主键冲突开始删除并重新插入");
                            super.deleteById(user.getId());
                            super.insertSelective(user);
                            logger.error("人员插入失败,主键冲突开始删除并重新插入成功");
                        }else{
                            throw new BusinessException(e.getMessage());
                        }
                    }
                }
            }

            Map<String,User> newMap = userList.stream().collect(Collectors.toMap(user -> user.getName(),user -> user));
            for (User user : oldList){
                if(null == newMap.get(user.getName()) && !user.getUsername().equals("admin") && !user.getUsername().equals("suadmin")){
                    super.delete(user);
                    logger.info("人员"+user.getName()+"被删除");
                }
            }
            logger.info("人员插入lims数据成功");
        }catch (Exception e){
            logger.error("人员插入lims数据失败:"+e.getMessage());
            throw new BusinessException("人员插入lims数据失败:"+e.getMessage());
        }


    }

    /**
     * 用户部门做数据关联
     * @param list
     */
    public void relevanceUserAndDept(List<VSysAccount> list){
        //1.循环获取到的账户数据，判断用户id是否存在，如果存在则更新账号、密码、部门
        List<User> oldList = mapper.selectAll();
        try{
            oldList.forEach(oldValue -> {
                list.forEach(accountValue -> {
                    if(oldValue.getId().equals(accountValue.getUserId()) && !accountValue.getLoginName().equals("suadmin")){
                        logger.info("accountValue.getLoginName():"+accountValue.getLoginName()+"=========用户部门做数据关联========oldValue.getLoginName"+oldValue.getUsername());
                        oldValue.setUsername(accountValue.getLoginName());
                        oldValue.setPassword(accountValue.getPassword()); //
                        oldValue.setDepartId(accountValue.getOrgId());
                        mapper.updateByPrimaryKeySelective(oldValue);
                        //2.部门用户关联表做数据存储
                        departMapper.deleteDepartUser(accountValue.getOrgId(),accountValue.getUserId());
                        departMapper.insertDepartUser(UUIDUtils.generateUuid(),accountValue.getOrgId(),accountValue.getUserId(),"ac88ceb386aa4231b09bf472cb937c24");
                    }

                });
            });
            logger.info("部门用户关联表做数据存储成功:");
        }catch (Exception e){
            logger.error("部门用户关联表做数据存储失败:"+e.getMessage());
        }


    }


    public List<User> queryUserAll(){
        Example example = new Example(User.class);
        example.setOrderByClause("userName asc");
        return super.selectByExample(example);
    }

}
