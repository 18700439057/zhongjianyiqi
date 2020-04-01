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
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送机转速原始记录解析
 */
public class EngineSpeedImpl implements AnalysisFactory {
    @Override
    public AnalysisFactory getAnalysis() {
        return new EngineSpeedImpl();
    }

    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessException {
//        VStandard vStandard = new VStandard();
//        vStandard.setId(UUIDUtils.generateUuid());
//        for (int i = 4; i < 6; i++) {
//            XSSFRow xssfRow = xssfSheet.getRow(i);
//            XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
//            XSSFCell value1 = xssfRow.getCell(6);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
//            XSSFCell value2 = xssfRow.getCell(10);
//            if(i==4){
//                vStandard.setName(ImportExcelUtils.getValue(value));
//                vStandard.setModelNumber(ImportExcelUtils.getValue(value1));
//                vStandard.setCertificateNumber(ImportExcelUtils.getValue(value2));
//            }else{
//                vStandard.setAccuracy(ImportExcelUtils.getValue(value));
//                vStandard.setCode(ImportExcelUtils.getValue(value1));
//                vStandard.setValidity(ImportExcelUtils.getValue(value2));
//            }
//        }
        VStandard vStandard = ImportExcelUtils.getStandard3(xssfSheet);
        map.put("vStandard",vStandard);

        for (int i = 10; i <= xssfSheet.getLastRowNum()-5; i++) {
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

				XSSFCell value6 = xssfRow.getCell(8);
				XSSFCell value7 = xssfRow.getCell(9);
				XSSFCell value8 = xssfRow.getCell(10);
				XSSFCell value9 = xssfRow.getCell(11);
				XSSFCell value10 = xssfRow.getCell(12);

				double averageValue = xssfRow.getCell(13).getNumericCellValue();
				double intrinsicError = xssfRow.getCell(14).getNumericCellValue();
				double indicationError = xssfRow.getCell(15).getNumericCellValue();

				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(value1+"") && StringUtils.isNotEmpty(value2+"") &&
						StringUtils.isNotEmpty(value3+"") && StringUtils.isNotEmpty(value4+"") &&
						StringUtils.isNotEmpty(value5+"") && StringUtils.isNotEmpty(value6+"") &&
						StringUtils.isNotEmpty(value7+"") && StringUtils.isNotEmpty(value8+"") &&
						StringUtils.isNotEmpty(value9+"") && StringUtils.isNotEmpty(value10+"") &&
						StringUtils.isNotEmpty(standarValue+"") && StringUtils.isNotEmpty(averageValue+"") &&
						StringUtils.isNotEmpty(intrinsicError+"") && StringUtils.isNotEmpty(indicationError+"")){

					Matcher value1IsNum = pattern.matcher(value1+"");
					if(!value1IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value2IsNum = pattern.matcher(value2+"");
					if(!value2IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value3IsNum = pattern.matcher(value3+"");
					if(!value3IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value4IsNum = pattern.matcher(value4+"");
					if(!value4IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value5IsNum = pattern.matcher(value5+"");
					if(!value5IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value6IsNum = pattern.matcher(value6+"");
					if(!value6IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value7IsNum = pattern.matcher(value7+"");
					if(!value7IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value8IsNum = pattern.matcher(value8+"");
					if(!value8IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value9IsNum = pattern.matcher(value9+"");
					if(!value9IsNum.matches()){
						throw new BusinessException("值错误");
					}

					Matcher value10IsNum = pattern.matcher(value10+"");
					if(!value10IsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal bg = new BigDecimal(averageValue).setScale(2, RoundingMode.UP);
					float averageValueNew = bg.floatValue();
					Matcher averageValueIsNum = pattern.matcher(averageValueNew+"");
					if(!averageValueIsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal indicationErrorBg = new BigDecimal(indicationError).setScale(2, RoundingMode.UP);
					float indicationErrorNew = indicationErrorBg.floatValue();
					Matcher indicationErrorIsNum = pattern.matcher(indicationErrorNew+"");
					if(!indicationErrorIsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal intrinsicErrorBg = new BigDecimal(intrinsicError).setScale(2, RoundingMode.UP);
					float intrinsicErrorNew = intrinsicErrorBg.floatValue();
					Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorNew+"");
					if(!intrinsicErrorIsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal standarValueBg = new BigDecimal(ImportExcelUtils.getValue(standarValue)).setScale(2, RoundingMode.UP);
					float standarValueNew = standarValueBg.floatValue();
					Matcher standarValueIsNum = pattern.matcher(standarValueNew+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessException("值错误");
					}

					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(standarValueNew);//标准值
					record.setValue1(Float.valueOf(ImportExcelUtils.getValue(value1)));
					record.setValue2(Float.valueOf(ImportExcelUtils.getValue(value2)));
					record.setValue3(Float.valueOf(ImportExcelUtils.getValue(value3)));
					record.setValue4(Float.valueOf(ImportExcelUtils.getValue(value4)));
					record.setValue5(Float.valueOf(ImportExcelUtils.getValue(value5)));
					record.setValue6(Float.valueOf(ImportExcelUtils.getValue(value6)));
					record.setValue7(Float.valueOf(ImportExcelUtils.getValue(value7)));
					record.setValue8(Float.valueOf(ImportExcelUtils.getValue(value8)));
					record.setValue9(Float.valueOf(ImportExcelUtils.getValue(value9)));
					record.setValue10(Float.valueOf(ImportExcelUtils.getValue(value10)));
					record.setIntrinsicError(intrinsicErrorNew);//基本误差
					record.setIndicationError(indicationErrorNew);//示值误差
					record.setAverageValue(averageValueNew);//平均值
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确,数据可能为公式");
            }
        }

        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i =21; i <23; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(10);
				if(i==21){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));

				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);


        for (int i = 23; i <25; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(8);
				XSSFCell day = xssfRow.getCell(9);
				if(i==23){
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
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            vo.setStandardValues(vSourceRecord.getStandardValues());
            vo.setAverageValue(vSourceRecord.getAverageValue());
            vo.setIndicationError(vSourceRecord.getIndicationError());
            list.add(vo);
        }
        String data = "";
        ClassPathResource cpr = new ClassPathResource("static/fsjzs.xls");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
        }
        //String tmpFile = "classpath:static/fsjzs.xls";
//        File file = ResourceUtils.getFile(stringBuffer.toString());
        resultMap.put("filePath","发送机转速.xls");
        resultMap.put("sourceList",list);
        return resultMap;
    }
}
