package com.sibo.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.wxiaoqi.merge.annonation.MergeField;
import com.github.wxiaoqi.security.common.annonation.QueryParmentType;
import com.sibo.business.feign.DictFeign;
import com.sibo.business.feign.UserFeign;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:34
 */
@Table(name = "v_assets_parameter")
public class VAssetsParameter implements Serializable {

	    //主键
    @Id
    private String id;
	
	    //统一编号
    @Column(name = "UNIFIED_CODE")
    private String unifiedCode;
	
	    //设备名称
    @Column(name = "DEVICENAME")
    private String devicename;
	
	    //设备型号
    @Column(name = "UNIT_TYPE")
    private String unitType;
	
	    //测量范围
    @Column(name = "MEASUREMENT_RANGE")
    private String measurementRange;
	
	    //原值
    @Column(name = "ORIGINAL_VALUE")
    private Float originalValue;
	
	    //等级
    @Column(name = "GRADE")
    private String grade;
	
	    //税率
    @Column(name = "TAX_RATE")
    private Float taxRate;
	
	    //税后价
    @Column(name = "AFTER_TAX_PRICE")
    private Float afterTaxPrice;
	
	    //资产类别
    @Column(name = "ASSETS_CLASS")
	@MergeField(key="assetsClass",feign = DictFeign.class,method ="getDictValues")
    private String assetsClass;
	
	    //计量校验类别
    @Column(name = "MEASUREMENT_CHECK_CLASS")
	@MergeField(key="measurementClass",feign = DictFeign.class,method ="getDictValues")
    private String measurementCheckClass;
	
	    //设备类别
    @Column(name = "FACITITY_CATEGORY")
	@MergeField(key="facitityCategory",feign = DictFeign.class,method ="getDictValues")
    private String facitityCategory;
	
	    //装机容量
    @Column(name = "INSTALLED_CAPACITY")
    private String installedCapacity;
	
	    //到货日期
//    @Column(name = "DELIVERY_TIME")
//	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date deliveryTime;
	
	    //使用日期
    @Column(name = "USES_TIME")
	@JsonFormat( pattern="yyyy-MM-dd")
    private Date usesTime;
	
	    //设备状态
    @Column(name = "FACITITY_STATUS")
	@MergeField(key="facitityStatus",feign = DictFeign.class,method ="getDictValues")
    private String facitityStatus;
	
	    //设备存放地
    @Column(name = "FACILITY_DESIGNATED_AREA")
	@MergeField(key="designatedArea",feign = DictFeign.class,method ="getDictValues")
	private String facilityDesignatedArea;
	
	    //制造商
    @Column(name = "MANUFACTURER")
	@MergeField(key="zzs",feign = DictFeign.class,method ="getDictValues")
	@QueryParmentType(key="=")
    private String manufacturer;
	
	    //负责人
    @Column(name = "PRINCIPAL")
    private String principal;
	
	    //设备从属
    @Column(name = "EQUIPMENT_SUBORDINATE")
	@MergeField(key="equipmentSubordinate",feign = DictFeign.class,method ="getDictValues")
    private String equipmentSubordinate;
	
	    //设备所属机构
    @Column(name = "EQUIPMENT_OWNER")
    private String equipmentOwner;
	
	    //项目编号
    @Column(name = "ITEM_NUMBER")
    private String itemNumber;
	
	    //设备所属部门
    @Column(name = "EQUIPMENT_DEPARTMENT")
	@MergeField(key="equipmentDepartment",feign = DictFeign.class,method ="getDictValues")
    private String equipmentDepartment;
	
	    //是否关键设备
    @Column(name = "CRITICAL_EQUIPMENT")
	@MergeField(key="criticalEquipment",feign = DictFeign.class,method ="getDictValues")
    private String criticalEquipment;
	
	    //停用日期
    @Column(name = "STOP_TIME")
	@JsonFormat( pattern="yyyy-MM-dd")
    private Date stopTime;
	
	    //总价
    @Column(name = "TOTAL_PRICE")
    private Float totalPrice;
	
	    //主要用途
    @Column(name = "MAIN_USES")
    private String mainUses;
	
	    //备注
    @Column(name = "REMARK")
    private String remark;
	
	    //创建人名称
    @Column(name = "CRT_USER_NAME")
    private String crtUserName;
	
	    //创建人id
    @Column(name = "CRT_USER_ID")
    private String crtUserId;
	
	    //创建时间
    @Column(name = "CRT_TIME")
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
	
	    //资产类型（1.录入2.附属设备3.追加）
    @Column(name = "ASSET_TYPE")
    private String assetType;
	
	    //关联上级ID
    @Column(name = "PARENT_ID")
    private String parentId;

	/**
	 * 追加数量
	 */
	@Column(name = "AMOUNT")
    private Integer amount;

	/**
	 * 追加名称
	 */
	@Column(name = "ADDITIONALNAME")
	private String  additionalName;

	/**
	 * 追加日期
	 */
	@Column(name = "ADDDATE")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date addDate;

	/**
	 * 追加人
	 */
	@Column(name = "ADMIN")
	private String admin;

	/**
	 * 经办人
	 */
	@Column(name = "AGENT")
	private String agent;

	/**
	 * 设备编号
	 */
	@Column(name = "EQUIPMENT_NUMBER")
	private String equipmentNumber;

	/**
	 * 驳回原因
	 */
	@Column(name = "DISMISS_REASON")
	private String dismissReason;

	/**
	 * 资产状态（1.正常，2.变更确认中）
	 */
	@Column(name = "ASSETS_STATUS")
	private String assetsStatus;

	//状态描述
	@Column(name = "STATUS_DESCRIPTION")
	private String statusDescription;

	//外观描述
	@Column(name = "APPEARANCE_DESCRIPTION")
	private String appearanceDescription;

	/**
	 * 关键维修次数
	 */
	@Column(name = "CRUX_MAINTAIN_NUM")
	private Integer cruxMaintainNum;

	/**
	 * 日常维修次数
	 */
	@Column(name = "EVERYDAY_MAINTAIN_NUM")
	private Integer everyDayMaintainNum;

	/**
	 * 维修费用
	 */
	@Column(name = "CHARGE")
	private Double charge;

	//最近计量计划日期
	@Column(name = "RECENT_MEASUREMENT_PLAN_TIME")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date recentMeasurementPlanTime;

	//计量周期（月）
	@Column(name = "MEASUREMENT_CYCLE")
	private int measurementCycle;

	//计量有效期
	@Column(name = "MEASUREMENT_VALIDITY")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date measurementValidity;

	//计量单位
	@Column(name = "MEASUREMENT_UNIT")
	@QueryParmentType(key="=")
	@MergeField(key="testUnit",feign = DictFeign.class,method ="getDictValues")
	private String measurementUnit;

	//计量依据名称
	@Column(name = "MEASUREMENT_GIST_NAME")
	private String measurementGistName;

	//计量依据代码
	@Column(name = "MEASUREMENT_GIST_CODE")
	private String measurementGistCode;

	//计量结果
	@Column(name = "MEASUREMENT_RESULT")
	@MergeField(key="measurementResult",feign = DictFeign.class,method ="getDictValues")
	private String measurementResult;

	//计量费用
	@Column(name = "MEASUREMENT_COST")
	private Float measurementCost;

	//证书编号
	@Column(name = "CERTIFICATE_NUMBER")
	private String certificateNumber;

	//计量状态
	@Column(name = "MEASUEEMENT_STATUS")
	@MergeField(key="measueementStatus",feign = DictFeign.class,method ="getDictValues")
	private String measueementStatus;

	//核定目前状态
	@Column(name = "VERIFY_CURRENT_STATUS")
	private String verifyCurrentStatus;

	//标准限值
	@Column(name = "STANDARD_LIMIT")
	private String standardLimit;

	//期间核查日期
	@Column(name = "PERIOD_DATE")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date periodDate;

	//期间核查周期
	@Column(name = "PERIOD_CHECK_CYCLE")
	private BigDecimal periodCheckCycle;

	//是否需要审查
	@Column(name = "IS_REVIEWED")
	@MergeField(key="isReviewed",feign = DictFeign.class,method ="getDictValues")
	private String isReviewed;



	//证书附件id
	@Column(name = "CREDENTIAL_FILE_ID")
	private String credentialFileId;

	/**
	 * 法定值
	 */
	@Column(name = "LEGAL_VALUE")
	private String  legalValue;

	/**
	 * 计量项目
	 */
	@Column(name = "MEASURING_PROJECT")
	private String measuringProject;

	/**
	 * 资料负责人
	 */
	@Column(name = "DATA_MANAGER")
	private String dataManager;

	/**
	 * 是否附属设备
	 */
	@Column(name = "ACCESSORY_EQUIPMENT")
	private String  accessoryEquipment;

	//软件名称
	@Column(name = "SOFTWARE_BRAND")
	private String softwareBrand;

	//软件版本
	@Column(name = "SOFTWARE_VERSION")
	private String softwareVersion;

	@Column(name = "PERIOD_DATE_STOP")
	@JsonFormat( pattern="yyyy-MM-dd")
	private Date periodDateStop;

	/*
	编号排序
	 */
	@Column(name = "UNIFIED_CODE_ORDER")
	private String unifiedCodeOrder;

	@Column(name = "HANDING_OUT_PEOPLE")
	private String handingOutPeople;


	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
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

	public int getMeasurementCycle() {
		return measurementCycle;
	}

	public void setMeasurementCycle(int measurementCycle) {
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getLegalValue() {
		return legalValue;
	}

	public void setLegalValue(String legalValue) {
		this.legalValue = legalValue;
	}

	public String getMeasuringProject() {
		return measuringProject;
	}

	public void setMeasuringProject(String measuringProject) {
		this.measuringProject = measuringProject;
	}

	public String getDataManager() {
		return dataManager;
	}

	public void setDataManager(String dataManager) {
		this.dataManager = dataManager;
	}

	public String getAccessoryEquipment() {
		return accessoryEquipment;
	}

	public void setAccessoryEquipment(String accessoryEquipment) {
		this.accessoryEquipment = accessoryEquipment;
	}

	public String getSoftwareBrand() {
		return softwareBrand;
	}

	public void setSoftwareBrand(String softwareBrand) {
		this.softwareBrand = softwareBrand;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Date getPeriodDateStop() {
		return periodDateStop;
	}

	public void setPeriodDateStop(Date periodDateStop) {
		this.periodDateStop = periodDateStop;
	}

	public String getUnifiedCodeOrder() {
		return unifiedCodeOrder;
	}

	public void setUnifiedCodeOrder(String unifiedCodeOrder) {
		this.unifiedCodeOrder = unifiedCodeOrder;
	}

	public String getHandingOutPeople() {
		return handingOutPeople;
	}

	public void setHandingOutPeople(String handingOutPeople) {
		this.handingOutPeople = handingOutPeople;
	}
}





