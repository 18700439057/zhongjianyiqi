package com.sibo.business.calculate.impl;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微机控制电子万能试验机
 */
public class MicrocomputerControlledElectronicUniversalTestingMachineImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        List vStandardList = new ArrayList();
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 11; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(4);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(9);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6 || i==9){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else if(i == 7 || i==10){
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
					vStandardList.add(vStandard);
				}else if(i == 8){
					vStandard = new VStandard();
					vStandard.setId(UUIDUtils.generateUuid());
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }


        }
        map.put("vStandardList",vStandardList);
        VSourceRecord record = new VSourceRecord();
        VSourceRecord record1 = new VSourceRecord();
        VSourceRecord record2 = new VSourceRecord();
        Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
        for (int i = 15; i <24; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				if(i==15){
					XSSFCell setValue = xssfRow.getCell(4);
					record.setId(UUIDUtils.generateUuid());
					if( StringUtils.isNotEmpty(setValue+"")){
						Matcher standarValueIsNum = pattern.matcher(setValue+"");
						if(!standarValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record.setSetValue(ImportExcelUtils.getValue(setValue));
					}
				}else if(i == 17){
					XSSFCell setValue = xssfRow.getCell(4);
					record1.setId(UUIDUtils.generateUuid());
					if( StringUtils.isNotEmpty(setValue+"")){
						Matcher standarValueIsNum = pattern.matcher(setValue+"");
						if(!standarValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record1.setSetValue(ImportExcelUtils.getValue(setValue));
					}
				}else if(i ==19){
					XSSFCell setValue = xssfRow.getCell(4);
					record2.setId(UUIDUtils.generateUuid());
					if( StringUtils.isNotEmpty(setValue+"")){
						Matcher standarValueIsNum = pattern.matcher(setValue+"");
						if(!standarValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record2.setSetValue(ImportExcelUtils.getValue(setValue));
					}
				}else if(i == 21){
					double standardValues = xssfRow.getCell(4).getNumericCellValue();
					double intrinsicError = xssfRow.getCell(12).getNumericCellValue();
					if( StringUtils.isNotEmpty(standardValues+"") && StringUtils.isNotEmpty(intrinsicError+"")){
						String standardValuesNew = String.format("%.2f", standardValues);
						Matcher standardValuesIsNum = pattern.matcher(standardValuesNew+"");
						if(!standardValuesIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}

						String intrinsicErrorNew = String.format("%.2f", intrinsicError);
						Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorNew+"");
						if(!intrinsicErrorIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record.setAverageValue(Float.valueOf(standardValuesNew));
						record.setIntrinsicError(Float.valueOf(intrinsicErrorNew));
					}

				}else if(i == 22){
					double standardValues = xssfRow.getCell(4).getNumericCellValue();
					double intrinsicError = xssfRow.getCell(12).getNumericCellValue();
					if( StringUtils.isNotEmpty(standardValues+"") && StringUtils.isNotEmpty(intrinsicError+"")){
						String standardValuesNew = String.format("%.2f", standardValues);
						Matcher standardValuesIsNum = pattern.matcher(standardValuesNew+"");
						if(!standardValuesIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}

						String intrinsicErrorNew = String.format("%.2f", intrinsicError);
						Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorNew+"");
						if(!intrinsicErrorIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record1.setAverageValue(Float.valueOf(standardValuesNew));
						record1.setIntrinsicError(Float.valueOf(intrinsicErrorNew));
					}
				}else if(i == 23){
					double standardValues = xssfRow.getCell(4).getNumericCellValue();
					double intrinsicError = xssfRow.getCell(12).getNumericCellValue();
					if( StringUtils.isNotEmpty(standardValues+"") && StringUtils.isNotEmpty(intrinsicError+"")){
						String standardValuesNew = String.format("%.2f", standardValues);
						Matcher standardValuesIsNum = pattern.matcher(standardValuesNew+"");
						if(!standardValuesIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}

						String intrinsicErrorNew = String.format("%.2f", intrinsicError);
						Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorNew+"");
						if(!intrinsicErrorIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行值错误");
						}
						record2.setAverageValue(Float.valueOf(standardValuesNew));
						record2.setIntrinsicError(Float.valueOf(intrinsicErrorNew));
					}
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        list.add(record);
        list.add(record1);
        list.add(record2);

        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 25; i <27; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(8);
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
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(8);
				XSSFCell day = xssfRow.getCell(9);
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
        List  list = new ArrayList<>();
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            vo = new VSourceRecordVo();
            org.springframework.beans.BeanUtils.copyProperties(vSourceRecord,vo);
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/微机控制电子万能试验机.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","微机控制电子万能试验机.xls");
        return resultMap;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new MicrocomputerControlledElectronicUniversalTestingMachineImpl();
    }
}
