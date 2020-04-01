package com.github.wxiaoqi.security.admin.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.service.TableResultParser;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class DepartBiz extends BusinessBiz<DepartMapper,Depart> {
    private Logger logger = LoggerFactory.getLogger(DepartBiz.class);

    @Autowired
    private UserMapper userMapper;
    @MergeResult(resultParser = TableResultParser.class)
    public TableResultResponse<User> getDepartUsers(String departId,String userName) {
        List<User> users = this.mapper.selectDepartUsers(departId,userName);
        return new TableResultResponse<User>(users.size(),users);
    }

    public void addDepartUser(String departId, String userIds) {
        if (!StringUtils.isEmpty(userIds)) {
            String[] uIds = userIds.split(",");
            for (String uId : uIds) {
                this.mapper.insertDepartUser(UUIDUtils.generateUuid(),departId,uId, BaseContextHandler.getTenantID());
            }
        }
    }

    @Override
    public List<Depart> selectListAll(){
        Example example = new Example(Depart.class);
        example.setOrderByClause("nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M')");
        return super.selectByExample(example);
//        return super.selectListAll();
    }

    /**
     * 根据ID批量获取部门值
     * @param departIDs
     * @return
     */
    public Map<String,String> getDeparts(String departIDs){
        if(StringUtils.isBlank(departIDs)) {
            return new HashMap<>();
        }
        departIDs = "'"+departIDs.replaceAll(",","','")+"'";
        List<Depart> departs = mapper.selectByIds(departIDs);
        return departs.stream().collect(Collectors.toMap(Depart::getId, depart -> JSONObject.toJSONString(depart)));
    }

    public void delDepartUser(String departId, String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user.getDepartId().equals(departId)){
            throw new BusinessException("无法移除用户的默认关联部门,若需移除,请前往用户模块更新用户部门!");
        }
        this.mapper.deleteDepartUser(departId,userId);
    }

    @Override
    public void insertSelective(Depart entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public Depart getDepartByUserId(String userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(null != user){
            Depart depart = this.mapper.selectByPrimaryKey(user.getDepartId());
            return depart;
        }
        return null;
    }

    public void saveDept(List<Depart> deptList){
        try{
            List<Depart> oldList = this.mapper.selectAll();
            Map<String,Depart> map = oldList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
            for (Depart depart : deptList){
                if(null != map.get(depart.getCode())){
                    Depart odepart = map.get(depart.getCode());
                    BeanUtils.copyProperties(depart,odepart);
                    super.updateSelectiveById(odepart);
                    logger.info("部门update lims数据成功");
                }else{
                    this.mapper.insertSelective(depart);
                    logger.info("部门insert lims数据成功");
                }
            }


            Map<String,Depart> newMap = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(),dept -> dept));
            for (Depart depart : oldList){
                if(null == newMap.get(depart.getCode()) && !depart.getCode().equals("root") ){
                    super.delete(depart);
                    logger.info("部门"+depart.getName()+"被删除");
                }
            }
            /*List<String> oldIds = deptList.stream().map(o->o.getId()).collect(Collectors.toList());
            //删除源数据
            Example example = new Example(Depart.class);
            example.createCriteria().andIn("id",oldIds);
            mapper.deleteByExample(example);
//        List<Depart> resultList = deptList.stream().filter(o->!oldIds.contains(o.getId())).collect(Collectors.toList());
            deptList.forEach(dept->{
                this.mapper.insertSelective(dept);
            });*/
            logger.info("插入lims获取的部门数据成功");
        }catch (Exception e){
            logger.error("插入lims获取的部门数据失败："+e.getMessage());
        }

    }


}