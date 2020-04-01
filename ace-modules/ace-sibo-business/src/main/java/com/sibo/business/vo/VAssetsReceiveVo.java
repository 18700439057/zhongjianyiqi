package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 资产领用记录
 *
 */
public class VAssetsReceiveVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	//资产ID
	private String assetsId;

	//主表
	private String assetsReceiveMainId;

	//资产状态（1.提交带确认2.确认）
	private String assetsStatus;

	//创建人名称
	private String crtUserName;

	//创建人id
	private String crtUserId;

	//创建时间
	private Date crtTime;

	//更新人名称
	private String updUserName;

	//更新人id
	private String updUserId;

	//更新时间
	private Date updTime;

	private String unifiedCode;

	//设备名称
	private String devicename;

	//设备型号
	private String unitType;

	//测量范围
	private String measurementRange;

	//原值
	private Float originalValue;

	//等级
	private String grade;

	//税率
	private Float taxRate;

	//税后价
	private Float afterTaxPrice;

	//资产类别
	private String assetsClass;

	//计量校验类别
	private String measurementCheckClass;

	//设备类别
	private String facitityCategory;

	//装机容量
	private String installedCapacity;

	//到货日期
	private Date deliveryTime;

	//使用日期
	private Date usesTime;

	//设备状态
	private String facitityStatus;

	//设备存放地
	private String facilityDesignatedArea;

	//制造商
	private String manufacturer;

	//负责人
	private String principal;

	//设备从属
	private String equipmentSubordinate;

	//设备所属机构
	private String equipmentOwner;

	//项目编号
	private String itemNumber;

	//设备所属部门
	private String equipmentDepartment;

	//是否关键设备
	private String criticalEquipment;

	//停用日期
	private Date stopTime;

	//总价
	private Float totalPrice;

	//主要用途
	private String mainUses;

	//备注
	private String remark;

	//资产类型（1.录入2.附属设备3.追加）
	private String assetType;

	//关联上级ID
	private String parentId;

	/**
	 * 追加数量
	 */
	private Integer amount;

	/**
	 * 追加名称
	 */
	private String  additionalName;

	/**
	 * 追加日期
	 */
	private Date addDate;

	/**
	 * 追加人
	 */
	private String admin;

	/**
	 * 设备编号
	 */
	private String equipmentNumber;

	/**
	 * 驳回原因
	 */
	private String dismissReason;

	/**
	 * 当前状态
	 */
	private String currentStatus;

	/**
	 * 领用人
	 */
	private String recipient;

	/**
	 * 领用时间
	 */
	private Date recipientTime;

	/**
	 * 归还人
	 */
	private String returnPeople;

	/**
	 * 归还时间
	 */
	private Date returnTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(String assetsId) {
		this.assetsId = assetsId;
	}

	public String getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
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

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getMeasurementRange() {
		return measurementRange;
	}

	public void setMeasurementRange(String measurementRange) {
		this.measurementRange = measurementRange;
	}

	public Float getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Float originalValue) {
		this.originalValue = originalValue;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

	public Float getAfterTaxPrice() {
		return afterTaxPrice;
	}

	public void setAfterTaxPrice(Float afterTaxPrice) {
		this.afterTaxPrice = afterTaxPrice;
	}

	public String getAssetsClass() {
		return assetsClass;
	}

	public void setAssetsClass(String assetsClass) {
		this.assetsClass = assetsClass;
	}

	public String getMeasurementCheckClass() {
		return measurementCheckClass;
	}

	public void setMeasurementCheckClass(String measurementCheckClass) {
		this.measurementCheckClass = measurementCheckClass;
	}

	public String getFacitityCategory() {
		return facitityCategory;
	}

	public void setFacitityCategory(String facitityCategory) {
		this.facitityCategory = facitityCategory;
	}

	public String getInstalledCapacity() {
		return installedCapacity;
	}

	public void setInstalledCapacity(String installedCapacity) {
		this.installedCapacity = installedCapacity;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getUsesTime() {
		return usesTime;
	}

	public void setUsesTime(Date usesTime) {
		this.usesTime = usesTime;
	}

	public String getFacitityStatus() {
		return facitityStatus;
	}

	public void setFacitityStatus(String facitityStatus) {
		this.facitityStatus = facitityStatus;
	}

	public String getFacilityDesignatedArea() {
		return facilityDesignatedArea;
	}

	public void setFacilityDesignatedArea(String facilityDesignatedArea) {
		this.facilityDesignatedArea = facilityDesignatedArea;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getEquipmentSubordinate() {
		return equipmentSubordinate;
	}

	public void setEquipmentSubordinate(String equipmentSubordinate) {
		this.equipmentSubordinate = equipmentSubordinate;
	}

	public String getEquipmentOwner() {
		return equipmentOwner;
	}

	public void setEquipmentOwner(String equipmentOwner) {
		this.equipmentOwner = equipmentOwner;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getEquipmentDepartment() {
		return equipmentDepartment;
	}

	public void setEquipmentDepartment(String equipmentDepartment) {
		this.equipmentDepartment = equipmentDepartment;
	}

	public String getCriticalEquipment() {
		return criticalEquipment;
	}

	public void setCriticalEquipment(String criticalEquipment) {
		this.criticalEquipment = criticalEquipment;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMainUses() {
		return mainUses;
	}

	public void setMainUses(String mainUses) {
		this.mainUses = mainUses;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAdditionalName() {
		return additionalName;
	}

	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getDismissReason() {
		return dismissReason;
	}

	public void setDismissReason(String dismissReason) {
		this.dismissReason = dismissReason;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Date getRecipientTime() {
		return recipientTime;
	}

	public void setRecipientTime(Date recipientTime) {
		this.recipientTime = recipientTime;
	}

	public String getReturnPeople() {
		return returnPeople;
	}

	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getAssetsReceiveMainId() {
		return assetsReceiveMainId;
	}

	public void setAssetsReceiveMainId(String assetsReceiveMainId) {
		this.assetsReceiveMainId = assetsReceiveMainId;
	}
}

