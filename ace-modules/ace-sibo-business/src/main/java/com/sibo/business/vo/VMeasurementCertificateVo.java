package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 计量证书表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
public class VMeasurementCertificateVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    private String id;

	    //资产ID
    private String assetId;
	
	    //证书编号
    private String certificateNumber;
	
	    //检测日期
    private Date inspectionDate;
	
	    //计量项目
    private String measuringProject;
	
	    //检测依据
    private String detectionPrinciple;
	
	    //检测结果
    private String detectionResult;
	
	    //证书有效期开始时间
    private Date certificateStartTime;
	
	    //证书有效期结束时间
    private Date certificateStartEndTime;

    //证书有效期区间
    private List<String>   certificateTimeRange;
	
	    //证书状态
    private String certificateStatus;
	
	    //备注
    private String remark;
	
	    //证书编制人
    private String certificator;
	
	    //证书审核人
    private String certificateExaminer;
	
	    //证书批准人
    private String certificateApprover;
	
	    //检测人
    private String inspector;
	
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
	
	    //上传人
    private String theHeir;
	
	    //上传时间
    private Date theHeirTime;

	//附件上传
	private List<VBusinessFileVo> imageList;

	private String certificateTempleted;

	//存放地
	private String facilityDesignatedArea;

	//项目id
	private String projectId;
	//证书类型
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

	public List<String> getCertificateTimeRange() {
		return certificateTimeRange;
	}

	public void setCertificateTimeRange(List<String> certificateTimeRange) {
		this.certificateTimeRange = certificateTimeRange;
	}

	public List<VBusinessFileVo> getImageList() {
		return imageList;
	}

	public void setImageList(List<VBusinessFileVo> imageList) {
		this.imageList = imageList;
	}

	public String getCertificateTempleted() {
		return certificateTempleted;
	}

	public void setCertificateTempleted(String certificateTempleted) {
		this.certificateTempleted = certificateTempleted;
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
