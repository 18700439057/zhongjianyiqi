
/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.dict.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.dict.entity.DictValue;
import com.github.wxiaoqi.security.dict.mapper.DictValueMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-01-30 19:45:55
 */
@Service
public class DictValueBiz extends BusinessBiz<DictValueMapper,DictValue> {
    @Override
    public void insertSelective(DictValue entity) {
        entity.setId(UUIDUtils.generateUuid());
        if(checkCode(entity.getCode(),"")){
            super.insertSelective(entity);
        }else{
            throw new BusinessException("字典编码值重复，请修改！");
        }

    }

    private boolean checkCode(String value,String id){
        Example example = new Example(DictValue.class);

        if(!StringUtils.isEmpty(id)){
            example.createCriteria().andEqualTo("code",value).andNotEqualTo("id",id);
        }else{
            example.createCriteria().andEqualTo("code",value);
        }
        List list = super.selectByExample(example);
        if(CollectionUtils.isEmpty(list))
             return true;
        return false;
    }

    @Override
    public void updateSelectiveById(DictValue entity) {
        EntityUtils.setUpdatedInfo(entity);
        if(checkCode(entity.getValue(),entity.getId())){
            super.updateSelectiveById(entity);
        }else{
            throw new BusinessException("字典编码值重复，请修改！");
        }

    }
}