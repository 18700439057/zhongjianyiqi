package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 *
 * @author henry
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:34
 */
public class VAssetsParameterMeteringExportVo implements Serializable {


	/**
	 * 设备名称
	 */
    private String devicename;

	/**
	 *测量范围
	 */
	private String measurementRange;

	/**
	 * 型号
	 */
	private String unitType;

	/**
	 * 设备编号
	 */
	private String equipmentNumber;

	/**
	 *精度
	 */
	private String grade;

	/**
	 *计量周期（月）
	 */
	private int measurementCycle;

	/**
	 * 生产厂家
	 */
	private String manufacturer;

	/**
	 *计量单位
	 */
	private String measurementUnit;

	/**、
	 * 最近计量计划日期
	 */
	private String recentMeasurementPlanTime;

	/**、
	 * 计量有效期
	 */
	private String measurementValidity;


	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getMeasurementRange() {
		return measurementRange;
	}

	public void setMeasurementRange(String measurementRange) {
		this.measurementRange = measurementRange;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getMeasurementCycle() {
		return measurementCycle;
	}

	public void setMeasurementCycle(int measurementCycle) {
		this.measurementCycle = measurementCycle;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getRecentMeasurementPlanTime() {
		return recentMeasurementPlanTime;
	}

	public void setRecentMeasurementPlanTime(String recentMeasurementPlanTime) {
		this.recentMeasurementPlanTime = recentMeasurementPlanTime;
	}

	public String getMeasurementValidity() {
		return measurementValidity;
	}

	public void setMeasurementValidity(String measurementValidity) {
		this.measurementValidity = measurementValidity;
	}
}




