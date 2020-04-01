package com.sibo.business.vo;

import com.sibo.business.entity.VMeasurementCertificate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:34
 */
public class VAssetsParameterExportExcelVo implements Serializable {
	private static final long serialVersionUID = 1L;

	    //统一编号
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
//	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
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
	 * 资产状态（1.正常，2.变更确认中）
	 */
	private String assetsStatus;

	//状态描述
	private String statusDescription;

	//外观描述
	private String appearanceDescription;
	/**
	 * 关键维修次数
	 */
	private Integer cruxMaintainNum;

	/**
	 * 日常维修次数
	 */
	private Integer everyDayMaintainNum;

	/**
	 * 维修费用
	 */
	private Double charge;

	//最近计量计划日期
	private Date recentMeasurementPlanTime;

	//计量周期（月）
	private BigDecimal measurementCycle;

	//计量有效期
	private Date measurementValidity;

	//计量单位
	private String measurementUnit;

	//计量依据名称
	private String measurementGistName;

	//计量依据代码
	private String measurementGistCode;

	//计量结果
	private String measurementResult;

	//计量费用
	private Float measurementCost;

	//证书编号
	private String certificateNumber;

	//计量状态
	private String measueementStatus;

	//核定目前状态
	private String verifyCurrentStatus;

	//标准限值
	private String standardLimit;

	//期间核查日期
	private Date periodDate;

	//期间核查周期
	private BigDecimal periodCheckCycle;

	//是否需要审查
	private String isReviewed;

	//证书附件id
	private String credentialFileId;

	//计量类型
	private String measurementType;

	//附件上传
	private List<VBusinessFileVo> imageList;

	//证书有效期开始时间
	private String certificateStartTime;

	//证书有效期结束时间
	private String certificateStartEndTime;


	//检定单位
	private String testUnit;

	//修改原因
	private String desc;

	private List<VMeasurementCertificate> vMeasurementCertificateVoList;

	/**
	 * 设置：统一编号
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	/**
	 * 获取：统一编号
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}
	/**
	 * 设置：设备名称
	 */
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	/**
	 * 获取：设备名称
	 */
	public String getDevicename() {
		return devicename;
	}
	/**
	 * 设置：设备型号
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	/**
	 * 获取：设备型号
	 */
	public String getUnitType() {
		return unitType;
	}
	/**
	 * 设置：测量范围
	 */
	public void setMeasurementRange(String measurementRange) {
		this.measurementRange = measurementRange;
	}
	/**
	 * 获取：测量范围
	 */
	public String getMeasurementRange() {
		return measurementRange;
	}
	/**
	 * 设置：原值
	 */
	public void setOriginalValue(Float originalValue) {
		this.originalValue = originalValue;
	}
	/**
	 * 获取：原值
	 */
	public Float getOriginalValue() {
		return originalValue;
	}
	/**
	 * 设置：等级
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 获取：等级
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：税率
	 */
	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}
	/**
	 * 获取：税率
	 */
	public Float getTaxRate() {
		return taxRate;
	}
	/**
	 * 设置：税后价
	 */
	public void setAfterTaxPrice(Float afterTaxPrice) {
		this.afterTaxPrice = afterTaxPrice;
	}
	/**
	 * 获取：税后价
	 */
	public Float getAfterTaxPrice() {
		return afterTaxPrice;
	}
	/**
	 * 设置：资产类别
	 */
	public void setAssetsClass(String assetsClass) {
		this.assetsClass = assetsClass;
	}
	/**
	 * 获取：资产类别
	 */
	public String getAssetsClass() {
		return assetsClass;
	}
	/**
	 * 设置：计量校验类别
	 */
	public void setMeasurementCheckClass(String measurementCheckClass) {
		this.measurementCheckClass = measurementCheckClass;
	}
	/**
	 * 获取：计量校验类别
	 */
	public String getMeasurementCheckClass() {
		return measurementCheckClass;
	}
	/**
	 * 设置：设备类别
	 */
	public void setFacitityCategory(String facitityCategory) {
		this.facitityCategory = facitityCategory;
	}
	/**
	 * 获取：设备类别
	 */
	public String getFacitityCategory() {
		return facitityCategory;
	}
	/**
	 * 设置：装机容量
	 */
	public void setInstalledCapacity(String installedCapacity) {
		this.installedCapacity = installedCapacity;
	}
	/**
	 * 获取：装机容量
	 */
	public String getInstalledCapacity() {
		return installedCapacity;
	}
	/**
	 * 设置：到货日期
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * 获取：到货日期
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * 设置：使用日期
	 */
	public void setUsesTime(Date usesTime) {
		this.usesTime = usesTime;
	}
	/**
	 * 获取：使用日期
	 */
	public Date getUsesTime() {
		return usesTime;
	}
	/**
	 * 设置：设备状态
	 */
	public void setFacitityStatus(String facitityStatus) {
		this.facitityStatus = facitityStatus;
	}
	/**
	 * 获取：设备状态
	 */
	public String getFacitityStatus() {
		return facitityStatus;
	}
	/**
	 * 设置：设备存放地
	 */
	public void setFacilityDesignatedArea(String facilityDesignatedArea) {
		this.facilityDesignatedArea = facilityDesignatedArea;
	}
	/**
	 * 获取：设备存放地
	 */
	public String getFacilityDesignatedArea() {
		return facilityDesignatedArea;
	}
	/**
	 * 设置：制造商
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * 获取：制造商
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * 设置：负责人
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	/**
	 * 获取：负责人
	 */
	public String getPrincipal() {
		return principal;
	}
	/**
	 * 设置：设备从属
	 */
	public void setEquipmentSubordinate(String equipmentSubordinate) {
		this.equipmentSubordinate = equipmentSubordinate;
	}
	/**
	 * 获取：设备从属
	 */
	public String getEquipmentSubordinate() {
		return equipmentSubordinate;
	}
	/**
	 * 设置：设备所属机构
	 */
	public void setEquipmentOwner(String equipmentOwner) {
		this.equipmentOwner = equipmentOwner;
	}
	/**
	 * 获取：设备所属机构
	 */
	public String getEquipmentOwner() {
		return equipmentOwner;
	}
	/**
	 * 设置：项目编号
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * 获取：项目编号
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * 设置：设备所属部门
	 */
	public void setEquipmentDepartment(String equipmentDepartment) {
		this.equipmentDepartment = equipmentDepartment;
	}
	/**
	 * 获取：设备所属部门
	 */
	public String getEquipmentDepartment() {
		return equipmentDepartment;
	}
	/**
	 * 设置：是否关键设备
	 */
	public void setCriticalEquipment(String criticalEquipment) {
		this.criticalEquipment = criticalEquipment;
	}
	/**
	 * 获取：是否关键设备
	 */
	public String getCriticalEquipment() {
		return criticalEquipment;
	}
	/**
	 * 设置：停用日期
	 */
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	/**
	 * 获取：停用日期
	 */
	public Date getStopTime() {
		return stopTime;
	}
	/**
	 * 设置：总价
	 */
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取：总价
	 */
	public Float getTotalPrice() {
		return totalPrice;
	}
	/**
	 * 设置：主要用途
	 */
	public void setMainUses(String mainUses) {
		this.mainUses = mainUses;
	}
	/**
	 * 获取：主要用途
	 */
	public String getMainUses() {
		return mainUses;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：创建人名称
	 */
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	/**
	 * 获取：创建人名称
	 */
	public String getCrtUserName() {
		return crtUserName;
	}
	/**
	 * 设置：创建人id
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：创建人id
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：更新人名称
	 */
	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}
	/**
	 * 获取：更新人名称
	 */
	public String getUpdUserName() {
		return updUserName;
	}
	/**
	 * 设置：更新人id
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：更新人id
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdTime() {
		return updTime;
	}
	/**
	 * 设置：资产类型（1.录入2.附属设备3.追加）
	 */
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	/**
	 * 获取：资产类型（1.录入2.附属设备3.追加）
	 */
	public String getAssetType() {
		return assetType;
	}
	/**
	 * 设置：关联上级ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：关联上级ID
	 */
	public String getParentId() {
		return parentId;
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

	public List<VBusinessFileVo> getImageList() {
		return imageList;
	}

	public void setImageList(List<VBusinessFileVo> imageList) {
		this.imageList = imageList;
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

	public String getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getAppearanceDescription() {
		return appearanceDescription;
	}

	public void setAppearanceDescription(String appearanceDescription) {
		this.appearanceDescription = appearanceDescription;
	}

	public Integer getCruxMaintainNum() {
		return cruxMaintainNum;
	}

	public void setCruxMaintainNum(Integer cruxMaintainNum) {
		this.cruxMaintainNum = cruxMaintainNum;
	}

	public Integer getEveryDayMaintainNum() {
		return everyDayMaintainNum;
	}

	public void setEveryDayMaintainNum(Integer everyDayMaintainNum) {
		this.everyDayMaintainNum = everyDayMaintainNum;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public Date getRecentMeasurementPlanTime() {
		return recentMeasurementPlanTime;
	}

	public void setRecentMeasurementPlanTime(Date recentMeasurementPlanTime) {
		this.recentMeasurementPlanTime = recentMeasurementPlanTime;
	}

	public BigDecimal getMeasurementCycle() {
		return measurementCycle;
	}

	public void setMeasurementCycle(BigDecimal measurementCycle) {
		this.measurementCycle = measurementCycle;
	}

	public Date getMeasurementValidity() {
		return measurementValidity;
	}

	public void setMeasurementValidity(Date measurementValidity) {
		this.measurementValidity = measurementValidity;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getMeasurementGistName() {
		return measurementGistName;
	}

	public void setMeasurementGistName(String measurementGistName) {
		this.measurementGistName = measurementGistName;
	}

	public String getMeasurementGistCode() {
		return measurementGistCode;
	}

	public void setMeasurementGistCode(String measurementGistCode) {
		this.measurementGistCode = measurementGistCode;
	}

	public String getMeasurementResult() {
		return measurementResult;
	}

	public void setMeasurementResult(String measurementResult) {
		this.measurementResult = measurementResult;
	}

	public Float getMeasurementCost() {
		return measurementCost;
	}

	public void setMeasurementCost(Float measurementCost) {
		this.measurementCost = measurementCost;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getMeasueementStatus() {
		return measueementStatus;
	}

	public void setMeasueementStatus(String measueementStatus) {
		this.measueementStatus = measueementStatus;
	}

	public String getVerifyCurrentStatus() {
		return verifyCurrentStatus;
	}

	public void setVerifyCurrentStatus(String verifyCurrentStatus) {
		this.verifyCurrentStatus = verifyCurrentStatus;
	}

	public String getStandardLimit() {
		return standardLimit;
	}

	public void setStandardLimit(String standardLimit) {
		this.standardLimit = standardLimit;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public BigDecimal getPeriodCheckCycle() {
		return periodCheckCycle;
	}

	public void setPeriodCheckCycle(BigDecimal periodCheckCycle) {
		this.periodCheckCycle = periodCheckCycle;
	}

	public String getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(String isReviewed) {
		this.isReviewed = isReviewed;
	}

	public String getCredentialFileId() {
		return credentialFileId;
	}

	public void setCredentialFileId(String credentialFileId) {
		this.credentialFileId = credentialFileId;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}


	public String getTestUnit() {
		return testUnit;
	}

	public void setTestUnit(String testUnit) {
		this.testUnit = testUnit;
	}

	public String getCertificateStartTime() {
		return certificateStartTime;
	}

	public void setCertificateStartTime(String certificateStartTime) {
		this.certificateStartTime = certificateStartTime;
	}

	public String getCertificateStartEndTime() {
		return certificateStartEndTime;
	}

	public void setCertificateStartEndTime(String certificateStartEndTime) {
		this.certificateStartEndTime = certificateStartEndTime;
	}

	public List<VMeasurementCertificate> getvMeasurementCertificateVoList() {
		return vMeasurementCertificateVoList;
	}

	public void setvMeasurementCertificateVoList(List<VMeasurementCertificate> vMeasurementCertificateVoList) {
		this.vMeasurementCertificateVoList = vMeasurementCertificateVoList;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}


