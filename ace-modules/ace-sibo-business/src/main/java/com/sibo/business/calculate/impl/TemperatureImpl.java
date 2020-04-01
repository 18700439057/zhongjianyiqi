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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 温度解析
 */
public class TemperatureImpl implements AnalysisFactory {

    @Override
    public AnalysisFactory getAnalysis() {
        return new TemperatureImpl();
    }

    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
//        VStandard vStandard = new VStandard();
//        vStandard.setId(UUIDUtils.generateUuid());
//        for (int i = 5; i < 8; i++) {
//            XSSFRow xssfRow = xssfSheet.getRow(i);
//            XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
//            XSSFCell value1 = xssfRow.getCell(5);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
//            if(i==5){
//                vStandard.setName(ImportExcelUtils.getValue(value));
//                vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
//            }else if(i == 6){
//                vStandard.setModelNumber(ImportExcelUtils.getValue(value));
//                vStandard.setValidity(ImportExcelUtils.getValue(value1));
//            }else{
//                vStandard.setCode(ImportExcelUtils.getValue(value));
//                vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
//            }
//        }
        VStandard vStandard = ImportExcelUtils.getStandard2(xssfSheet);
        map.put("vStandard",vStandard);

        for (int i = 13; i <= xssfSheet.getLastRowNum()-11; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				int minCell = xssfRow.getFirstCellNum();
				int maxCell = xssfRow.getLastCellNum();
				XSSFCell standarValue = xssfRow.getCell(2);
				XSSFCell value1 = xssfRow.getCell(3);
				XSSFCell value2 = xssfRow.getCell(4);
				XSSFCell value3 = xssfRow.getCell(5);
				XSSFCell value4 = xssfRow.getCell(6);
				XSSFCell value5 = xssfRow.getCell(7);
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(value1+"") && StringUtils.isNotEmpty(value2+"") &&
						StringUtils.isNotEmpty(value3+"") && StringUtils.isNotEmpty(value4+"") ){
					Matcher value1IsNum = pattern.matcher(value1+"");
					if(!value1IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					Matcher value2IsNum = pattern.matcher(value2+"");
					if(!value2IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					Matcher value3IsNum = pattern.matcher(value3+"");
					if(!value3IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					Matcher value4IsNum = pattern.matcher(value4+"");
					if(!value4IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					Matcher value5IsNum = pattern.matcher(value5+"");
					if(!value5IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}
					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(Float.valueOf(ImportExcelUtils.getValue(standarValue)));
					record.setValue1(Float.valueOf(ImportExcelUtils.getValue(value1)));
					record.setValue2(Float.valueOf(ImportExcelUtils.getValue(value2)));
					record.setValue3(Float.valueOf(ImportExcelUtils.getValue(value3)));
					record.setValue4(Float.valueOf(ImportExcelUtils.getValue(value4)));
					record.setIntrinsicError(Float.valueOf(ImportExcelUtils.getValue(value5)));
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 31; i <33; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(5);
				if(i==31){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));

				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);
        for (int i = 33; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(3);
				XSSFCell month = xssfRow.getCell(4);
				XSSFCell day = xssfRow.getCell(5);
				if(i==33){
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
    public Map exportExcel(List<VSourceRecord> sourceList,Map<String, Object> resultMap) throws BusinessException,Exception {
        //处理测量值并计量修正值
        List<VSourceRecordVo>  list = new ArrayList<>();
        float[] valueArr = new float[4];
        Map<Float,Float> map = new HashMap<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            valueArr[0]= Math.abs(vSourceRecord.getValue1()-vSourceRecord.getStandardValues());
            valueArr[1]= Math.abs(vSourceRecord.getValue2()-vSourceRecord.getStandardValues());
            valueArr[2]= Math.abs(vSourceRecord.getValue3()-vSourceRecord.getStandardValues());
            valueArr[3]= Math.abs(vSourceRecord.getValue4()-vSourceRecord.getStandardValues());
            map.put(valueArr[0],vSourceRecord.getValue1());
            map.put(valueArr[1],vSourceRecord.getValue2());
            map.put(valueArr[2],vSourceRecord.getValue3());
            map.put(valueArr[3],vSourceRecord.getValue4());

            //排序，获取最大值
            Arrays.sort(valueArr);
            VSourceRecordVo vo = new VSourceRecordVo();
            float standardValues = vSourceRecord.getStandardValues();
            float testValue = valueArr[valueArr.length-1];
            vo.setStandardValues(standardValues);
            vo.setTestValue(map.get(testValue));
            vo.setErrorValue(vSourceRecord.getIntrinsicError());
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/温度.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","温度.xls");
        return resultMap;
    }


}
