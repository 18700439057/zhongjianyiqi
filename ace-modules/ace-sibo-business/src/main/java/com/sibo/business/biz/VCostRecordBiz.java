package com.sibo.business.biz;

import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.lorne.core.framework.utils.DateUtil;
import com.sibo.business.config.StorageProperties;
import com.sibo.business.entity.Depart;
import com.sibo.business.entity.VCostRecord;
import com.sibo.business.enums.CostHeadersEnums;
import com.sibo.business.feign.DeptFeign;
import com.sibo.business.mapper.VCostRecordMapper;
import com.sibo.business.rest.VCostRecordController;
import com.sibo.business.utils.ExportExcelUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VCostRecordBiz extends BusinessBiz<VCostRecordMapper,VCostRecord> {
    private Logger logger = LoggerFactory.getLogger(VCostRecordBiz.class);
    @Autowired
    private VCostRecordMapper vCostRecordMapper;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private MergeCore mergeCor;

    @Autowired
    private DeptFeign deptFeign;

    @Override
    public void insertSelective(VCostRecord entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public List<VCostRecord> queryCurrentYearCost(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<VCostRecord> list =  vCostRecordMapper.selectCurrentyearList(year+"");
        return list;
    }

    public static void main(String[] args) {
        System.out.println(  CostHeadersEnums.getKeys().length);
    }

    public String  exportCost(Map<String, Object> params, HttpServletResponse resp) throws Exception {
        logger.info("--------------开始导出数据，正在查询...");
        Example example = new Example(VCostRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(params.get("department"))){
            criteria.andEqualTo("department",params.get("department"));
        }

        if(!StringUtils.isEmpty(params.get("maintenanceType"))){
            criteria.andEqualTo("maintenanceType",params.get("maintenanceType"));
        }
        criteria.andEqualTo("type","1");
        List<VCostRecord> list = vCostRecordMapper.selectByExample(example);
        mergeCor.mergeResult(VCostRecord.class,list);
        logger.info("--------------查询结束...查询数据共：["+list.size()+"]条");

        logger.info("-----部门翻译---------开始远程获取部门");
        List<Depart> deptList = deptFeign.getDeptAll();
        if(!CollectionUtils.isEmpty(deptList)){
            Map<String,Depart> map = deptList.stream().collect(Collectors.toMap(dept -> dept.getCode(), dept -> dept));
            list.stream().map(v ->{
                if(null != v.getDepartment() && null != map.get(v.getDepartment())){
                    v.setDepartment(map.get(v.getDepartment()).getName());
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
        String path = storageProperties.getDownPath()+"维修费用"+year+"年"+month+"月"+day+"日--"+new Random(100).nextInt()+".xlsx";
        String paths = path;
        logger.info("--------------文件路径名称：：："+path);
        util.exportExcel2007("维修费用录入",CostHeadersEnums.getKeys(),list,new FileOutputStream(path),"yyyy-MM-dd HH:mm:ss",CostHeadersEnums.toJson());
        logger.info("--------------生成文件结束:"+paths);
        return paths;
    }
}
