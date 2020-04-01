package com.sibo.business.entity;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.sibo.business.feign.DictFeign;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计量证书表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Table(name = "v_measurement_certificate")
public class VMeasurementCertificate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSET_ID")
    private String assetId;
	
	    //证书编号
    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;
	
	    //检测日期
    @Column(name = "INSPECTION_DATE")
    private Date inspectionDate;
	
	    //计量项目
    @Column(name = "MEASURING_PROJECT")
    private String measuringProject;
	
	    //检测依据
    @Column(name = "DETECTION_PRINCIPLE")
    private String detectionPrinciple;
	
	    //检测结果
    @Column(name = "DETECTION_RESULT")
	@MergeField(key="measurementResult",feign = DictFeign.class,method ="getDictValues")
    private String detectionResult;
	
	    //证书有效期开始时间
    @Column(name = "CERTIFICATE_START_TIME")
    private Date certificateStartTime;
	
	    //证书有效期结束时间
    @Column(name = "CERTIFICATE_START_END_TIME")
    private Date certificateStartEndTime;
	
	    //证书状态
    @Column(name = "CERTIFICATE_STATUS")
    private String certificateStatus;
	
	    //备注
    @Column(name = "REMARK")
    private String remark;
	
	    //证书编制人
    @Column(name = "CERTIFICATOR")
    private String certificator;
	
	    //证书审核人
    @Column(name = "CERTIFICATE_EXAMINER")
    private String certificateExaminer;
	
	    //证书批准人
    @Column(name = "CERTIFICATE_APPROVER")
    private String certificateApprover;
	
	    //检测人
    @Column(name = "INSPECTOR")
    private String inspector;

	//证书模板
	@Column(name = "CERTIFICATE_TEMPLETED")
    private String certificateTempleted;
	
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
	
	    //上传人
    @Column(name = "THE_HEIR")
    private String theHeir;
	
	    //上传时间
    @Column(name = "THE_HEIR_TIME")
    private Date theHeirTime;

    //原始记录类型
    @Column(name = "SOURCE_TYPE")
    private String souceType;

    //温度
	@Column(name = "TEMPERATURE")
	private String temperature;

	//湿度
	@Column(name = "HUMIDITY")
	private String humidity;

	//存放地
	@Column(name = "FACILITY_DESIGNATED_AREA")
	@MergeField(key="designatedArea",feign = DictFeign.class,method ="getDictValues")
	private String facilityDesignatedArea;

	//计量项目id
	@Column(name = "PROJECT_ID")
	private String projectId;

	//证书类型
	@Column(name = "CERTIFICATE_TYPE")
	private String certificateType;
	

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
	 * 设置：检测日期
	 */
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	/**
	 * 获取：检测日期
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}
	/**
	 * 设置：计量项目
	 */
	public void setMeasuringProject(String measuringProject) {
		this.measuringProject = measuringProject;
	}
	/**
	 * 获取：计量项目
	 */
	public String getMeasuringProject() {
		return measuringProject;
	}
	/**
	 * 设置：检测依据
	 */
	public void setDetectionPrinciple(String detectionPrinciple) {
		this.detectionPrinciple = detectionPrinciple;
	}
	/**
	 * 获取：检测依据
	 */
	public String getDetectionPrinciple() {
		return detectionPrinciple;
	}
	/**
	 * 设置：检测结果
	 */
	public void setDetectionResult(String detectionResult) {
		this.detectionResult = detectionResult;
	}
	/**
	 * 获取：检测结果
	 */
	public String getDetectionResult() {
		return detectionResult;
	}
	/**
	 * 设置：证书有效期开始时间
	 */
	public void setCertificateStartTime(Date certificateStartTime) {
		this.certificateStartTime = certificateStartTime;
	}
	/**
	 * 获取：证书有效期开始时间
	 */
	public Date getCertificateStartTime() {
		return certificateStartTime;
	}
	/**
	 * 设置：证书有效期结束时间
	 */
	public void setCertificateStartEndTime(Date certificateStartEndTime) {
		this.certificateStartEndTime = certificateStartEndTime;
	}
	/**
	 * 获取：证书有效期结束时间
	 */
	public Date getCertificateStartEndTime() {
		return certificateStartEndTime;
	}
	/**
	 * 设置：证书状态
	 */
	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}
	/**
	 * 获取：证书状态
	 */
	public String getCertificateStatus() {
		return certificateStatus;
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
	 * 设置：证书编制人
	 */
	public void setCertificator(String certificator) {
		this.certificator = certificator;
	}
	/**
	 * 获取：证书编制人
	 */
	public String getCertificator() {
		return certificator;
	}
	/**
	 * 设置：证书审核人
	 */
	public void setCertificateExaminer(String certificateExaminer) {
		this.certificateExaminer = certificateExaminer;
	}
	/**
	 * 获取：证书审核人
	 */
	public String getCertificateExaminer() {
		return certificateExaminer;
	}
	/**
	 * 设置：证书批准人
	 */
	public void setCertificateApprover(String certificateApprover) {
		this.certificateApprover = certificateApprover;
	}
	/**
	 * 获取：证书批准人
	 */
	public String getCertificateApprover() {
		return certificateApprover;
	}
	/**
	 * 设置：检测人
	 */
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	/**
	 * 获取：检测人
	 */
	public String getInspector() {
		return inspector;
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
	 * 设置：上传人
	 */
	public void setTheHeir(String theHeir) {
		this.theHeir = theHeir;
	}
	/**
	 * 获取：上传人
	 */
	public String getTheHeir() {
		return theHeir;
	}
	/**
	 * 设置：上传时间
	 */
	public void setTheHeirTime(Date theHeirTime) {
		this.theHeirTime = theHeirTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getTheHeirTime() {
		return theHeirTime;
	}

	public String getCertificateTempleted() {
		return certificateTempleted;
	}

	public void setCertificateTempleted(String certificateTempleted) {
		this.certificateTempleted = certificateTempleted;
	}

    public String getSouceType() {
        return souceType;
    }

    public void setSouceType(String souceType) {
        this.souceType = souceType;
    }

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getFacilityDesignatedArea() {
		return facilityDesignatedArea;
	}

	public void setFacilityDesignatedArea(String facilityDesignatedArea) {
		this.facilityDesignatedArea = facilityDesignatedArea;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
}

