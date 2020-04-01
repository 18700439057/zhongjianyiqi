package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.BeanUtils;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TorqueImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 8; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(6);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else{
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        map.put("vStandard",vStandard);

        for (int i = 14; i <= xssfSheet.getLastRowNum()-5; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value9 = xssfRow.getCell(0);
				double standarValue = xssfRow.getCell(1).getNumericCellValue();
				XSSFCell value1 = xssfRow.getCell(2);
				XSSFCell value2 = xssfRow.getCell(3);
				XSSFCell value3 = xssfRow.getCell(4);
				XSSFCell value4 = xssfRow.getCell(5);
				double value5 = xssfRow.getCell(6).getNumericCellValue();
				double value6 = xssfRow.getCell(7).getNumericCellValue();
				double value7 = xssfRow.getCell(8).getNumericCellValue();
				double value8 = xssfRow.getCell(9).getNumericCellValue();
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(value1+"") && StringUtils.isNotEmpty(value2+"") &&  StringUtils.isNotEmpty(standarValue+"") &&
						StringUtils.isNotEmpty(value3+"") && StringUtils.isNotEmpty(value4+"") &&
						StringUtils.isNotEmpty(value5+"") && StringUtils.isNotEmpty(value6+"") &&
						StringUtils.isNotEmpty(value7+"") && StringUtils.isNotEmpty(value8+"") && StringUtils.isNotEmpty(value9+"")){

					Matcher value9IsNum = pattern.matcher(value9+"");
					if(!value9IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;砝码数据值错误");
					}

					Matcher value1IsNum = pattern.matcher(value1+"");
					if(!value1IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;正向加数据值错误");
					}

					Matcher value2IsNum = pattern.matcher(value2+"");
					if(!value2IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;正向减值错误");
					}

					Matcher value3IsNum = pattern.matcher(value3+"");
					if(!value3IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;反向加值错误");
					}

					Matcher value4IsNum = pattern.matcher(value4+"");
					if(!value4IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;反向减值错误");
					}

					DecimalFormat value5Bg =new DecimalFormat("0.00");
					String value5New = value5Bg.format(value5);
					Matcher value5IsNum = pattern.matcher(value5New+"");
					if(!value5IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;正向偏差“加”值错误");
					}

					DecimalFormat value6Bg =new DecimalFormat("0.00");
					String value6New = value6Bg.format(value6);
					Matcher value6IsNum = pattern.matcher(value6New+"");
					if(!value6IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;正向偏差“减”值错误");
					}

					DecimalFormat value7Bg =new DecimalFormat("0.00");
					String value7New = value7Bg.format(value7);
					Matcher value7IsNum = pattern.matcher(value7New+"");
					if(!value7IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;反向偏差“加”值错误");
					}

					DecimalFormat value8Bg =new DecimalFormat("0.00");
					String value8New = value8Bg.format(value8);
					Matcher value8IsNum = pattern.matcher(value8New+"");
					if(!value8IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;反向偏差“减”值错误");
					}

					DecimalFormat standarValueBg =new DecimalFormat("0.00");
					String standarValueNew = standarValueBg.format(standarValue);
					Matcher standarValueIsNum = pattern.matcher(standarValueNew+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;标准值错误");
					}


					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(Float.valueOf(standarValueNew));
					record.setValue1(Float.valueOf(ImportExcelUtils.getValue(value1)));
					record.setValue2(Float.valueOf(ImportExcelUtils.getValue(value2)));
					record.setValue3(Float.valueOf(ImportExcelUtils.getValue(value3)));
					record.setValue4(Float.valueOf(ImportExcelUtils.getValue(value4)));
					record.setValue5(Float.valueOf(value5New));
					record.setValue6(Float.valueOf(value6New));
					record.setValue7(Float.valueOf(value7New));
					record.setValue8(Float.valueOf(value8New));
					record.setValue9(Float.valueOf(ImportExcelUtils.getValue(value9)));
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 41; i <43; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(6);
				if(i==41){
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
        for (int i = 43; i <= xssfSheet.getLastRowNum(); i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(4);
				XSSFCell month = xssfRow.getCell(5);
				XSSFCell day = xssfRow.getCell(6);
				if(i==43){
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
    public Map exportExcel(List<VSourceRecord> sourceList,Map<String,Object> resultMap) throws BusinessException,Exception {
        //处理测量值并计量修正值
        List<VSourceRecordVo>  list = new ArrayList<>();
        float[] valueArr = new float[4];
        Map<Float,Float> map = new HashMap<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            org.springframework.beans.BeanUtils.copyProperties(vSourceRecord,vo);
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/扭矩.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","扭矩.xls");
        return resultMap;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new TorqueImpl();
    }
}
