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

package com.github.wxiaoqi.security.common.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.annonation.QueryParmentType;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.AG
 * Date: 17/1/13
 * Time: 15:13
 * Version 1.0.0
 */
public abstract class BaseBiz<M extends CommonMapper<T>, T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }


    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    public void insertSelective(T entity) {
        mapper.insertSelective(entity);
    }

    public void delete(T entity) {
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }


    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);

    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public TableResultResponse<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        query2criteria(query, example);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        example.setOrderByClause("CRT_TIME desc");
        List<T> list = this.selectByExample(example);
        return new TableResultResponse<T>(result.getTotal(), list);
    }

    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(StringUtils.isNotBlank(entry.getValue().toString())){
                    criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");

                }

            }
        }
    }
    /**
     * 全局查询
     * @param query
     * @param example
     */
    public void queryAll(String searchValue, Example example,Class obj) {
        //获取类属性
        Field[] field = obj.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            Column column = f.getAnnotation(Column.class);
            // key:得到属性名
            if(null != column ){
                String key = column.name();
                Example.Criteria criteria = example.createCriteria();
                //select * from c_table t where t.name like upper('%f%') or t.name like lower('%F%');
                //criteria.andLike(key,"%"+searchValue+"%");
                criteria.andCondition("upper("+key+") like upper('%"+searchValue+"%') or lower("+key+") like lower('%"+searchValue+"%')  or "+key+" like '%"+searchValue+"%'");
                example.or(criteria);
            }
        }
    }

    public void query2criteria(Query query, Example example,Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map annotionParmets = new HashMap();
        for (Field field : fields) {
            QueryParmentType annotation = field.getAnnotation(QueryParmentType.class);
            if(null != annotation){
                if(StringUtils.isNotEmpty(annotation.key())){
                    annotionParmets.put(field.getName(),annotation.key());
                }
            }
        }
        if (query.entrySet().size() > 0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(StringUtils.isNotBlank(entry.getValue().toString())){
                    if(null != annotionParmets.get(entry.getKey())){
                        if("=".equals(annotionParmets.get(entry.getKey()))){
                            criteria.andEqualTo(entry.getKey(), entry.getValue().toString() );
                        }else{
                            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                        }
                    }else{
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    }

                }
            }
        }
    }

}
