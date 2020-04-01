package com.sibo.business.calculate.impl;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 后视镜撞击试验台
 */
public class RearviewMirrorImpact implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        List vStandardList = new ArrayList();
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 14; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(4);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(9);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6 || i==9 || i== 12){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else if(i == 7 || i==10 || i== 13){
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
					vStandardList.add(vStandard);
				}else if(i == 8 || i == 11){
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
        record.setId(UUIDUtils.generateUuid());
        for (int i = 18; i <26; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell project = xssfRow.getCell(0);
				XSSFCell setValue = xssfRow.getCell(4);
				double averageValue = xssfRow.getCell(11).getNumericCellValue();
				double intrinsicError = xssfRow.getCell(12).getNumericCellValue();
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");

				if(i < 20){
					if( StringUtils.isNotEmpty(setValue+"") && StringUtils.isNotEmpty(averageValue+"") && StringUtils.isNotEmpty(intrinsicError+"")){
							Matcher setValueIsNum = pattern.matcher(setValue+"");
							if(!setValueIsNum.matches()){
								throw new BusinessRuntimeException("第"+i+"行设定值错误");
							}

							String averageValueNew = String.format("%.2f", averageValue);
							Matcher averageValueIsNum = pattern.matcher(averageValueNew+"");
							if(!averageValueIsNum.matches()){
								throw new BusinessRuntimeException("第"+i+"行平均值错误");
							}

							String intrinsicErrorNew = String.format("%.2f", intrinsicError);
							Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorNew+"");
							if(!intrinsicErrorIsNum.matches()){
								throw new BusinessRuntimeException("第"+i+"行示值误差错误");
							}

							VSourceRecord record1 = new VSourceRecord();
							record1.setId(UUIDUtils.generateUuid());
							record1.setFormula(ImportExcelUtils.getValue(project));
							record1.setSetValue(ImportExcelUtils.getValue(setValue));
							record1.setAverageValue(Float.valueOf(averageValueNew));
							record1.setIndicationError(Float.valueOf(intrinsicErrorNew));
							list.add(record1);

					}
				}else{
					if(StringUtils.isNotEmpty(averageValue+"")){
						String averageValueNew = String.format("%.2f", averageValue);
						Matcher averageValueIsNum = pattern.matcher(averageValueNew+"");
						if(!averageValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行平均值错误");
						}
						//record.setFormula(ImportExcelUtils.getValue(project));
						if(i == 20){
							record.setValue1(Float.valueOf(averageValueNew));
						}else if(i == 21){
							record.setValue2(Float.valueOf(averageValueNew));
						}else if(i == 22){
							record.setValue3(Float.valueOf(averageValueNew));
						}else if(i == 23){
							record.setValue4(Float.valueOf(averageValueNew));
						}else if(i == 24){
							record.setValue5(Float.valueOf(averageValueNew));
						}else if(i == 25){
							record.setValue6(Float.valueOf(averageValueNew));
						}

					}

				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        list.add(record);
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 27; i <29; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(8);
				if(i==27){
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
        for (int i = 29; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(6);
				XSSFCell month = xssfRow.getCell(7);
				XSSFCell day = xssfRow.getCell(8);
				if(i==29){
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
            if(null == vSourceRecord.getFormula()){
                vo = new VSourceRecordVo();
                vo.setValue1(vSourceRecord.getValue1());
                vo.setValue2(vSourceRecord.getValue2());
                vo.setValue3(vSourceRecord.getValue3());
                vo.setValue4(vSourceRecord.getValue4());
                vo.setValue5(vSourceRecord.getValue5());
                vo.setValue6(vSourceRecord.getValue6());
                resultMap.put("other3",vo);
            }else{
                String name = vSourceRecord.getFormula().substring(0,5);
                if(name.equals("撞击球外径")){
                    vo = new VSourceRecordVo();
                    vo.setSetValue(vSourceRecord.getSetValue());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIndicationError(vSourceRecord.getIndicationError());
                    resultMap.put("other1",vo);
                }else if(name.equals("摆锤轴至中")){
                    vo = new VSourceRecordVo();
                    vo.setSetValue(vSourceRecord.getSetValue());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIndicationError(vSourceRecord.getIndicationError());
                    resultMap.put("other2",vo);
                }
            }

        }
//        String tmpFile = "classpath:static/后视镜撞击试验台.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","后视镜撞击试验台.xls");
        return resultMap;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new RearviewMirrorImpact();
    }
}
