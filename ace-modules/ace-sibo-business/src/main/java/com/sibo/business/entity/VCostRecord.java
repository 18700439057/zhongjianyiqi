package com.sibo.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.wxiaoqi.merge.annonation.MergeField;
import com.sibo.business.feign.DictFeign;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 费用记录
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-1-16 14:16:22
 */
@Table(name = "V_COST_RECORD")
public class VCostRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	@Column(name = "DEVICENAME")
	private String devicename;

	//维修类别
	@Column(name = "MAINTENANCE_TYPE")
	@MergeField(key="maintenanceType",feign = DictFeign.class,method ="getDictValues")
	private String maintenanceType;

	/**
	 * 设备编号
	 */
	@Column(name = "EQUIPMENT_NUMBER")
	private String equipmentNumber;

	//设备型号
	@Column(name = "UNIT_TYPE")
	private String unitType;
	
	    //故障描述
    @Column(name = "ERROR_DESCRIPTION")
    private String errorDescription;

	
	    //维修方式
    @Column(name = "MAINTENANCE_MODE")
	@MergeField(key="maintenanceMode",feign = DictFeign.class,method ="getDictValues")
    private String maintenanceMode;

	    //部门
    @Column(name = "DEPARTMENT")
    private String department;
	
	    //服务用时
    @Column(name = "WAITER_USE_TIME")
    private Double waiterUseTime;

	
	    //更换配件
    @Column(name = "CHANGE_ACCESSORY")
    private String changeAccessory;
	
	    //费用估算
    @Column(name = "COST_ESTIMATE")
    private Float costEstimate;


	//维修人数
	@Column(name = "MAINTENANCE_NUMBER_PEOPLE")
	private String maintenanceNumberPeople;

	//创建人名称
	@Column(name = "CRT_USER_NAME")
	private String crtUserName;

	//创建人id
	@Column(name = "CRT_USER_ID")
	private String crtUserId;

	//创建时间
	@Column(name = "CRT_TIME")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date crtTime;

	//更新人名称
	@Column(name = "UPD_USER_NAME")
	private String updUserName;

	//更新人id
	@Column(name = "UPD_USER_ID")
	private String updUserId;

	//更新时间
	@Column(name = "UPD_TIME")
	private Date updTime;

	//类型
	@Column(name = "TYPE")
	private String type;

	@Column(name = "COST_CATEGORY")
	private String costCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getMaintenanceMode() {
		return maintenanceMode;
	}

	public void setMaintenanceMode(String maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getWaiterUseTime() {
		return waiterUseTime;
	}

	public void setWaiterUseTime(Double waiterUseTime) {
		this.waiterUseTime = waiterUseTime;
	}

	public String getChangeAccessory() {
		return changeAccessory;
	}

	public void setChangeAccessory(String changeAccessory) {
		this.changeAccessory = changeAccessory;
	}

	public Float getCostEstimate() {
		return costEstimate;
	}

	public void setCostEstimate(Float costEstimate) {
		this.costEstimate = costEstimate;
	}

	public String getMaintenanceNumberPeople() {
		return maintenanceNumberPeople;
	}

	public void setMaintenanceNumberPeople(String maintenanceNumberPeople) {
		this.maintenanceNumberPeople = maintenanceNumberPeople;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdUserName() {
		return updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCostCategory() {
		return costCategory;
	}

	public void setCostCategory(String costCategory) {
		this.costCategory = costCategory;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	@Override
	public String toString() {
		return "VCostRecord{" +
				"id='" + id + '\'' +
				", devicename='" + devicename + '\'' +
				", maintenanceType='" + maintenanceType + '\'' +
				", equipmentNumber='" + equipmentNumber + '\'' +
				", errorDescription='" + errorDescription + '\'' +
				", maintenanceMode='" + maintenanceMode + '\'' +
				", department='" + department + '\'' +
				", waiterUseTime=" + waiterUseTime +
				", changeAccessory='" + changeAccessory + '\'' +
				", costEstimate=" + costEstimate +
				", maintenanceNumberPeople='" + maintenanceNumberPeople + '\'' +
				", crtUserName='" + crtUserName + '\'' +
				", crtUserId='" + crtUserId + '\'' +
				", crtTime=" + crtTime +
				", updUserName='" + updUserName + '\'' +
				", updUserId='" + updUserId + '\'' +
				", updTime=" + updTime +
				", type='" + type + '\'' +
				", costCategory='" + costCategory + '\'' +
				'}';
	}
}
