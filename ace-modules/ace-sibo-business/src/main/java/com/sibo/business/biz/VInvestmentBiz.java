package com.sibo.business.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.config.StorageProperties;
import com.sibo.business.entity.Depart;
import com.sibo.business.entity.VCostRecord;
import com.sibo.business.enums.CostHeadersEnums;
import com.sibo.business.enums.InvestmentHeadersEnums;
import com.sibo.business.feign.DeptFeign;
import com.sibo.business.feign.UserFeign;
import com.sibo.business.utils.ExportExcelUtil;
import com.sibo.business.vo.VInvestmentVo;
import org.apache.poi.ss.formula.functions.T;
import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VInvestment;
import com.sibo.business.mapper.VInvestmentMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-04-15 16:45:04
 */
@Service
@Transactional
public class VInvestmentBiz extends BusinessBiz<VInvestmentMapper,VInvestment> {

    private Logger logger = LoggerFactory.getLogger(VInvestmentBiz.class);
    @Autowired
    private VInvestmentMapper investmentMapper;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private MergeCore mergeCor;

    @Autowired
    private DeptFeign deptFeign;

    @Autowired
    private UserFeign userFeign;


    @Override
    public void insertSelective(VInvestment entity) {
        entity.setId(UUIDUtils.generateUuid());
        EntityUtils.setCreateInfo(entity);
        super.insertSelective(entity);
    }

    @Override
    public TableResultResponse<VInvestment> selectByQuery(Query query) {
        Example example = new Example(VInvestment.class);
        Example.Criteria criteria = example.createCriteria();
        StringBuffer sql = new StringBuffer();
        if(!StringUtils.isEmpty(query.get("projectName"))){
            sql.append("(upper(PROJECT_NAME) like upper('%"+query.get("projectName")+"%') or lower(PROJECT_NAME) like lower('%"+query.get("projectName")+"%')  or PROJECT_NAME like '%"+query.get("projectName")+"%')");
        }
        if(!StringUtils.isEmpty(query.get("investmentNature"))){
            sql.append(" and INVESTMENT_NATURE = '"+query.get("investmentNature")+"'");
        }

        if(!StringUtils.isEmpty(query.get("investmentCategory"))){
            sql.append(" and INVESTMENT_CATEGORY = '"+query.get("investmentCategory")+"'");
        }
        if(sql.length()>0){
            criteria.andCondition(sql.toString());
        }

        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        example.setOrderByClause("CRT_TIME desc");
        List<VInvestment> list = this.selectByExample(example);
        return new TableResultResponse<VInvestment>(result.getTotal(), list);
    }


    public List<VInvestment> queryYearInvestmentCost(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<VInvestment> list = investmentMapper.queryYearInvestment(year+"");
        return list;
    }

    public void copyData(String id){
        VInvestment investment = investmentMapper.selectByPrimaryKey(id);
        if(null == investment){
            throw new BusinessException("复制数据不存在，请刷新重试");
        }
        VInvestment newInvestment = new VInvestment();
        org.springframework.beans.BeanUtils.copyProperties(investment,newInvestment,new String[]{"kYstartTime","kY_endTime","zB_startTime","zB_endTime","hT_startTime","hT_endTime","sS_startTime","sS_endTime","gN_startTime","gN_endTime","jG_startTime","jG_endTime"});
        newInvestment.setId(UUIDUtils.generateUuid());
        investmentMapper.insertSelective(newInvestment);
    }

    public String exportExcel(Map<String, Object> params, HttpServletResponse resp) throws Exception {
        List<VInvestment> list = null;
        Example example = new Example(VInvestment.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(params.get("projectName"))){
            criteria.andEqualTo("projectName",params.get("projectName"));
        }

        if(!StringUtils.isEmpty(params.get("investmentCategory"))){
            criteria.andEqualTo("investmentCategory",params.get("investmentCategory"));
        }

        if(!StringUtils.isEmpty(params.get("investmentNature"))){
            criteria.andEqualTo("investmentNature",params.get("investmentNature"));
        }
        list = investmentMapper.selectByExample(example);

        mergeCor.mergeResult(VInvestment.class,list);
        logger.info("--------------查询结束...查询数据共：["+list.size()+"]条");
        logger.info("--------------开始远程获取人员");
        //合并部门和人员
        List<User> userList = userFeign.getAllUser();
        logger.info("--------------结束远程获取人员");
        if(!CollectionUtils.isEmpty(userList)){
            // 将userlist转换为Map
            Map<String,User> map = userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
            //map中获取对应值
            list.stream().map(v ->{
                if(null != v.getProjectLeader() && null != map.get(v.getProjectLeader())){
                    v.setProjectLeader(map.get(v.getProjectLeader()).getName());
                }
                return v;
            }).collect(Collectors.toList());
        }
        logger.info("-----部门翻译---------开始远程获取部门");
        List<Depart> deptList = deptFeign.getDeptAll();
        if(!CollectionUtils.isEmpty(deptList)){
            Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(), dept -> dept));
            list.stream().map(v ->{
                if(null != v.getResponsibleDepartment() && null != map.get(v.getResponsibleDepartment())){
                    v.setResponsibleDepartment(map.get(v.getResponsibleDepartment()).getName());
                }
                return v;
            }).collect(Collectors.toList());
        }
        logger.info("------部门翻译--------结束远程获取部门");
        logger.info("--------------开始生成文件");
        ExportExcelUtil util = new ExportExcelUtil<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        String path = storageProperties.getDownPath()+"投资计划"+year+"年"+month+"月"+day+"日--"+new Random(100).nextInt()+".xlsx";
        String paths = path;
        logger.info("--------------文件路径名称：：："+path);
        util.exportExcel2007("投资计划",InvestmentHeadersEnums.getKeys(),list,new FileOutputStream(path),"yyyy-MM-dd HH:mm:ss",InvestmentHeadersEnums.toJson());
        logger.info("--------------生成文件结束:"+paths);
        return paths;

    }

}