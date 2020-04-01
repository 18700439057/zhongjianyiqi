package com.sibo.lims.business.biz;

import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.lims.business.entity.VSysUser;
import com.sibo.lims.business.feign.AdminFeign;
import com.sibo.lims.business.mapper.VSysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
@Service
@Transactional
public class VSysUserBiz extends BusinessBiz<VSysUserMapper,VSysUser> {

    private Logger logger = LoggerFactory.getLogger(VSysUserBiz.class);

    @Autowired
    private AdminFeign adminFeign;

    public void queryUser(){
        Example example = new Example(VSysUser.class);
        example.createCriteria().andEqualTo("isDel","0");
        List<VSysUser> list = super.selectByExample(example);
        List<User> userList = new ArrayList<>();
        User user = null;

        for (VSysUser vSysUser:list) {
            user = new User();
            user.setId(vSysUser.getId());
            user.setName(vSysUser.getName());
            user.setAddress(vSysUser.getAddress());
            user.setBirthday(vSysUser.getBirthday());
            user.setCrtTime(new Date(vSysUser.getCreateTime().longValue()));
            user.setCrtUserName("lims同步");
            user.setUpdUserName("lims同步");
            user.setTenantId("ac88ceb386aa4231b09bf472cb937c24");// 租户
            user.setUpdTime(new Date());
            user.setIsDeleted(vSysUser.getIsDel()+"");
            //判断lims系统是否是删除状态如果是删除状态则不可用
            if(user.getIsDeleted().equals("1")){
                user.setStatus("0");//停用
            }else{
                user.setStatus("1");// 启用
            }
            user.setIsDisabled("0");
            user.setNo(vSysUser.getNo());
            user.setSex(vSysUser.getSex());
            user.setTelPhone(vSysUser.getTelephone());
            user.setIsSuperAdmin("0");
            userList.add(user);
//            logger.info("获取到的数据"+vSysUser);
        }
        adminFeign.saveUser(userList);
        logger.info("用户同步数据成功"+new Date(),userList);

    }

    public static String md5(String str){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if(i<0) i+= 256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return str;
        }
    }

    public static void main(String[] args) {
       System.out.print(md5("123456")) ;
    }




}