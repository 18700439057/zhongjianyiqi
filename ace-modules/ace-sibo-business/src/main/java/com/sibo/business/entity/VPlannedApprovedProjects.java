package com.sibo.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 计量/核定项目表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Table(name = "v_planned_approved_projects")
public class VPlannedApprovedProjects implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSET_ID")
    private String assetId;
	
	    //计量/核查项目
    @Column(name = "MEASURING_PROJECT")
    private String measuringProject;
	
	    //最近检定日期
    @Column(name = "LATEST_CALIBRATION_DATE")
	@JsonFormat( pattern="yyyy-MM-dd")
    private Date latestCalibrationDate;
	
	    //检定周期
    @Column(name = "VERIFICATION_PERIOD")
    private int verificationPeriod;
	
	    //计量/核查有效期
    @Column(name = "VALIDITY_MEASUREMENT")
	@JsonFormat( pattern="yyyy-MM-dd")
    private Date validityMeasurement;
	
	    //检定单位
    @Column(name = "TEST_UNIT")
    private String testUnit;
	
	    //检定依据名称
    @Column(name = "VERIFICATION_GIST_NAME")
    private String verificationGistName;
	
	    //校准结果
    @Column(name = "CALIBRATION_RESULT")
    private String calibrationResult;
	
	    //检定费用
    @Column(name = "TEST_COST")
    private Float testCost;
	
	    //证书编号
    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;
	
	    //计量/核查状态
    @Column(name = "MEASUEEMENT_STATUS")
    private String measueementStatus;
	
	    //损失关键依据
    @Column(name = "LOSS_CRITICAL_BASIS")
    private String lossCriticalBasis;
	
	    //法定值
    @Column(name = "LEGAL_VALUE")
    private String legalValue;
	
	    //标准限值
    @Column(name = "STANDARD_LIMIT")
    private String standardLimit;
	
	    //检定依据代码
    @Column(name = "VERIFICATION_GIST_CODE")
    private String verificationGistCode;
	
	    //规格型号
    @Column(name = "SPECIFICATION_MODEL")
    private String specificationModel;
	
	    //测量范围
    @Column(name = "MEASUREMENT_RANGE")
    private String measurementRange;
	
	    //核查目前状态
    @Column(name = "CHECK_CURRENT_STATUS")
    private String checkCurrentStatus;
	
	    //计量核查类型（1.计量2.核查）
    @Column(name = "TYPE")
    private String type;
	
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
	
	    //精度
    @Column(name = "PRECISION")
    private String precision;
	

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
	 * 设置：资产ID
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * 获取：资产ID
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * 设置：计量/核查项目
	 */
	public void setMeasuringProject(String measuringProject) {
		this.measuringProject = measuringProject;
	}
	/**
	 * 获取：计量/核查项目
	 */
	public String getMeasuringProject() {
		return measuringProject;
	}
	/**
	 * 设置：最近检定日期
	 */
	public void setLatestCalibrationDate(Date latestCalibrationDate) {
		this.latestCalibrationDate = latestCalibrationDate;
	}
	/**
	 * 获取：最近检定日期
	 */
	public Date getLatestCalibrationDate() {
		return latestCalibrationDate;
	}
	/**
	 * 设置：检定周期
	 */
	public void setVerificationPeriod(int verificationPeriod) {
		this.verificationPeriod = verificationPeriod;
	}
	/**
	 * 获取：检定周期
	 */
	public int getVerificationPeriod() {
		return verificationPeriod;
	}
	/**
	 * 设置：计量/核查有效期
	 */
	public void setValidityMeasurement(Date validityMeasurement) {
		this.validityMeasurement = validityMeasurement;
	}
	/**
	 * 获取：计量/核查有效期
	 */
	public Date getValidityMeasurement() {
		return validityMeasurement;
	}
	/**
	 * 设置：检定单位
	 */
	public void setTestUnit(String testUnit) {
		this.testUnit = testUnit;
	}
	/**
	 * 获取：检定单位
	 */
	public String getTestUnit() {
		return testUnit;
	}
	/**
	 * 设置：检定依据名称
	 */
	public void setVerificationGistName(String verificationGistName) {
		this.verificationGistName = verificationGistName;
	}
	/**
	 * 获取：检定依据名称
	 */
	public String getVerificationGistName() {
		return verificationGistName;
	}
	/**
	 * 设置：校准结果
	 */
	public void setCalibrationResult(String calibrationResult) {
		this.calibrationResult = calibrationResult;
	}
	/**
	 * 获取：校准结果
	 */
	public String getCalibrationResult() {
		return calibrationResult;
	}
	/**
	 * 设置：检定费用
	 */
	public void setTestCost(Float testCost) {
		this.testCost = testCost;
	}
	/**
	 * 获取：检定费用
	 */
	public Float getTestCost() {
		return testCost;
	}
	/**
	 * 设置：证书编号
	 */
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	/**
	 * 获取：证书编号
	 */
	public String getCertificateNumber() {
		return certificateNumber;
	}
	/**
	 * 设置：计量/核查状态
	 */
	public void setMeasueementStatus(String measueementStatus) {
		this.measueementStatus = measueementStatus;
	}
	/**
	 * 获取：计量/核查状态
	 */
	public String getMeasueementStatus() {
		return measueementStatus;
	}
	/**
	 * 设置：损失关键依据
	 */
	public void setLossCriticalBasis(String lossCriticalBasis) {
		this.lossCriticalBasis = lossCriticalBasis;
	}
	/**
	 * 获取：损失关键依据
	 */
	public String getLossCriticalBasis() {
		return lossCriticalBasis;
	}
	/**
	 * 设置：法定值
	 */
	public void setLegalValue(String legalValue) {
		this.legalValue = legalValue;
	}
	/**
	 * 获取：法定值
	 */
	public String getLegalValue() {
		return legalValue;
	}
	/**
	 * 设置：标准限值
	 */
	public void setStandardLimit(String standardLimit) {
		this.standardLimit = standardLimit;
	}
	/**
	 * 获取：标准限值
	 */
	public String getStandardLimit() {
		return standardLimit;
	}
	/**
	 * 设置：检定依据代码
	 */
	public void setVerificationGistCode(String verificationGistCode) {
		this.verificationGistCode = verificationGistCode;
	}
	/**
	 * 获取：检定依据代码
	 */
	public String getVerificationGistCode() {
		return verificationGistCode;
	}
	/**
	 * 设置：规格型号
	 */
	public void setSpecificationModel(String specificationModel) {
		this.specificationModel = specificationModel;
	}
	/**
	 * 获取：规格型号
	 */
	public String getSpecificationModel() {
		return specificationModel;
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
	 * 设置：核查目前状态
	 */
	public void setCheckCurrentStatus(String checkCurrentStatus) {
		this.checkCurrentStatus = checkCurrentStatus;
	}
	/**
	 * 获取：核查目前状态
	 */
	public String getCheckCurrentStatus() {
		return checkCurrentStatus;
	}
	/**
	 * 设置：计量核查类型（1.计量2.核查）
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：计量核查类型（1.计量2.核查）
	 */
	public String getType() {
		return type;
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
	 * 设置：精度
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	/**
	 * 获取：精度
	 */
	public String getPrecision() {
		return precision;
	}
}
