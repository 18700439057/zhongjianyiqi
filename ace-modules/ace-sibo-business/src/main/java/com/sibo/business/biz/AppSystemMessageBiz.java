package com.sibo.business.biz;

import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.annonation.QueryParmentType;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.config.StorageProperties;
import com.sibo.business.entity.AppSystemMessage;
import com.sibo.business.entity.Depart;
import com.sibo.business.entity.VAssetsAccessory;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VAssetsReceiveMain;
import com.sibo.business.entity.VAssetsReceiveRecords;
import com.sibo.business.entity.VAssetsUpdateRecords;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.entity.VMeasurementCertificate;
import com.sibo.business.entity.VMeasurementVerificationPlan;
import com.sibo.business.enums.AssetsStatus;
import com.sibo.business.enums.EquipmentSubordinate;
import com.sibo.business.enums.FileType;
import com.sibo.business.enums.MeasueementVerificationStatu;
import com.sibo.business.enums.MeasurementCheckClass;
import com.sibo.business.enums.MetrologicalReviewType;
import com.sibo.business.feign.DeptFeign;
import com.sibo.business.feign.DictFeign;
import com.sibo.business.feign.UserFeign;
import com.sibo.business.mapper.AppSystemMessageMapper;
import com.sibo.business.mapper.VAssetsAccessoryMapper;
import com.sibo.business.mapper.VAssetsParameterMapper;
import com.sibo.business.mapper.VAssetsReceiveMainMapper;
import com.sibo.business.mapper.VAssetsReceiveMapper;
import com.sibo.business.mapper.VAssetsUpdateRecordsMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.mapper.VMeasurementCertificateMapper;
import com.sibo.business.mapper.VMeasurementVerificationPlanMapper;
import com.sibo.business.utils.Constant;
import com.sibo.business.utils.ExportExcelUtil;
import com.sibo.business.utils.ExportUtils;
import com.sibo.business.utils.MessagePush;
import com.sibo.business.vo.DictValue;
import com.sibo.business.vo.LimsVAssetsParameterVo;
import com.sibo.business.vo.StatisticsAssetsNumVo;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VAssetsReceiveVo;
import com.sibo.business.vo.VBusinessFileVo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:34
 */
@Service
//@Transactional
public class AppSystemMessageBiz extends BusinessBiz<AppSystemMessageMapper,AppSystemMessage> {
    private Logger logger = LoggerFactory.getLogger(AppSystemMessageBiz.class);
    @Autowired
    private VAssetsParameterMapper assetsParameterMapper;

    

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    

   
}