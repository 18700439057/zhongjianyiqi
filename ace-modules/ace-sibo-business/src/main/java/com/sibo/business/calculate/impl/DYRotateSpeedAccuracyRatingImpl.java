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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DY转速准确度
 */
public class DYRotateSpeedAccuracyRatingImpl implements AnalysisFactory {
    @Override
    public AnalysisFactory getAnalysis() {
        return new DYRotateSpeedAccuracyRatingImpl();
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
        String formula = "";
        for (int i = 6; i < 7; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				formula = ImportExcelUtils.getValue(xssfRow.getCell(14));
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
            

        }

        for (int i = 10; i <= xssfSheet.getLastRowNum()-5; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);

				XSSFCell setValue = xssfRow.getCell(2);//设定值2
				double standarValue = xssfRow.getCell(3).getNumericCellValue();
				XSSFCell value1 = xssfRow.getCell(4);
				XSSFCell value2 = xssfRow.getCell(5);
				XSSFCell value3 = xssfRow.getCell(6);
				XSSFCell value4 = xssfRow.getCell(7);
				XSSFCell value5 = xssfRow.getCell(8);

				XSSFCell value6 = xssfRow.getCell(9);
				XSSFCell value7 = xssfRow.getCell(10);
				XSSFCell value8 = xssfRow.getCell(11);
				XSSFCell value9 = xssfRow.getCell(12);
				XSSFCell value10 = xssfRow.getCell(13);

				double averageValue = xssfRow.getCell(14).getNumericCellValue();
				double indicationError = xssfRow.getCell(15).getNumericCellValue();



				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(value1+"") && StringUtils.isNotEmpty(value2+"") &&
						StringUtils.isNotEmpty(value3+"") && StringUtils.isNotEmpty(value4+"") &&
						StringUtils.isNotEmpty(value5+"") && StringUtils.isNotEmpty(value6+"") &&
						StringUtils.isNotEmpty(value7+"") && StringUtils.isNotEmpty(value8+"") &&
						StringUtils.isNotEmpty(value9+"") && StringUtils.isNotEmpty(value10+"") &&
						StringUtils.isNotEmpty(standarValue+"")){

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



					BigDecimal indicationErrorBg = new BigDecimal(indicationError).setScale(2, RoundingMode.UP);
					float indicationErrorNew = indicationErrorBg.floatValue();
					Matcher indicationErrorIsNum = pattern.matcher(indicationErrorNew+"");
					if(!indicationErrorIsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal averageValueBg = new BigDecimal(averageValue).setScale(2, RoundingMode.UP);
					float averageValueNew = averageValueBg.floatValue();
					Matcher averageValueIsNum = pattern.matcher(averageValueNew+"");
					if(!averageValueIsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal standarValueBg = new BigDecimal(standarValue).setScale(2, RoundingMode.UP);
					float standarValueNew = standarValueBg.floatValue();
					Matcher standarValueIsNum = pattern.matcher(standarValueNew+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessException("值错误");
					}


					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setSetValue(ImportExcelUtils.getValue(setValue));
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
					record.setFormula(formula);
					record.setIndicationError(indicationErrorNew);//示值误差
					record.setAverageValue(averageValueNew);//平均值
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }

        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i =17; i <19; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(10);
				if(i==17){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));
				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);

        for (int i = 19; i <21; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(8);
				XSSFCell day = xssfRow.getCell(9);
				if(i==19){
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
        float[] valueArr = new float[10];
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            valueArr[0] = vSourceRecord.getValue1();
            valueArr[1] = vSourceRecord.getValue2();
            valueArr[2] = vSourceRecord.getValue3();
            valueArr[3] = vSourceRecord.getValue4();
            valueArr[4] = vSourceRecord.getValue5();
            valueArr[5] = vSourceRecord.getValue6();
            valueArr[6] = vSourceRecord.getValue7();
            valueArr[7] = vSourceRecord.getValue8();
            valueArr[8] = vSourceRecord.getValue9();
            valueArr[9] = vSourceRecord.getValue10();
            Arrays.sort(valueArr);

            //计算示值变动性 测量值最大值-最小值/标准值*100
            float cz = valueArr[sourceList.size()-1] - valueArr[0];//差值
            double bdx = cz/vSourceRecord.getStandardValues()*100;
            BigDecimal bdxBg = new BigDecimal(bdx).setScale(2, RoundingMode.UP);
            vo.setVariability(bdxBg.floatValue()+"");
            //计算准确度 4.09*|标准值-设定值|/3*标准值*100
            float jdz = Math.abs(vSourceRecord.getStandardValues()-Float.valueOf(vSourceRecord.getSetValue()));//绝对值
            double zqd = 4.09*jdz/3*vSourceRecord.getStandardValues()*100;
            BigDecimal zqdBg = new BigDecimal(zqd).setScale(2, RoundingMode.UP);
            vo.setAccuracy(zqdBg.floatValue()+"");

            vo.setStandardValues(vSourceRecord.getStandardValues());
            vo.setSetValue(vSourceRecord.getSetValue());
            list.add(vo);
        }
//        String tmpFile = "classpath:static/DY准确度.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","DY准确度.xls");
        resultMap.put("sourceList",list);
        return resultMap;
    }
}
