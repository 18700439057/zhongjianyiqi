package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.calculate.AnalysisFactory;
import com.sibo.business.entity.VMeasurementCertificate;
import com.sibo.business.entity.VSourceRecord;
import com.sibo.business.entity.VStandard;
import com.sibo.business.utils.ImportExcelUtils;
import com.sibo.business.vo.VSourceRecordVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 距离
 */
public class DistanceImpl implements AnalysisFactory {

    @Override
    public AnalysisFactory getAnalysis() {
        return new DistanceImpl();
    }

    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        VStandard vStandard = ImportExcelUtils.getStandard2(xssfSheet);
        map.put("vStandard",vStandard);

        for (int i = 13; i <= xssfSheet.getLastRowNum()-10; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				double standarValue = xssfRow.getCell(2).getNumericCellValue();
				XSSFCell value1 = xssfRow.getCell(3);
				double intrinsicError = xssfRow.getCell(4).getNumericCellValue();
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(value1+"") && StringUtils.isNotEmpty(intrinsicError+"") &&  StringUtils.isNotEmpty(standarValue+"") ){
					Matcher value1IsNum = pattern.matcher(value1+"");
					if(!value1IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}


					DecimalFormat standarValueBg =new DecimalFormat("#.00");
					String standarValueNew = standarValueBg.format(standarValue);
					Matcher standarValueIsNum = pattern.matcher(standarValueNew+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					String intrinsicErrorErrorNew = String.format("%.2f", intrinsicError);
					Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorErrorNew+"");
					if(!intrinsicErrorIsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}


					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(Float.valueOf(standarValueNew));
					record.setValue1(Float.valueOf(ImportExcelUtils.getValue(value1)));
					record.setIntrinsicError(Float.valueOf(intrinsicErrorErrorNew));
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 25; i <27; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(5);
				if(i==25){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));
				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);

        //计量日期
        for (int i = 27; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(3);
				XSSFCell month = xssfRow.getCell(4);
				XSSFCell day = xssfRow.getCell(5);
				if(i==27){
					map.put("recentMeasurementPlanTime",ImportExcelUtils.getValue(year)+ImportExcelUtils.getValue(month)+ImportExcelUtils.getValue(day));
				}else{
					map.put("measurementValidity",ImportExcelUtils.getValue(year)+ImportExcelUtils.getValue(month)+ImportExcelUtils.getValue(day));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        map.put("list",list);
        return map;
    }

    @Override
    public Map exportExcel(List<VSourceRecord> sourceList,Map<String, Object> resultMap) throws Exception {
        //处理测量值并计量修正值
        List<VSourceRecordVo>  list = new ArrayList<>();
        float[] valueArr = new float[4];
        Map<Float,Float> map = new HashMap<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            vo.setStandardValues(vSourceRecord.getStandardValues());
            vo.setTestValue(vSourceRecord.getValue1());
            vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/距离.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","距离.xls");
        return resultMap;
    }


}
