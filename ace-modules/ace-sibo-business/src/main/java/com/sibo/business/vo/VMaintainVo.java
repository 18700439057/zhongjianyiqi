package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 维修主表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
public class VMaintainVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    private String id;
	
	    //资产ID
    private String assetId;

	/**
	 * 设备编号
	 */
	private String equipmentNumber;
	
	    //故障描述
    private String errorDescription;

	//设备名称
	private String devicename;
	
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
	
	    //变更版本
    private String version;
	
	    //申请人
    private String proposer;
	
	    //申请时间
    private Date applicationTime;
	
	    //申请人状态（0.待维修1.确认中2.维修中3.申请人确认4.维修完成）
    private String status;
	
	    //部门
    private String department;
	
	    //服务用时
    private Double waiterUseTime;
	
	    //服务时间
    private Date waiterTime;
	
	    //故障标题
    private String faultTitle;
	
	    //故障描述确认
    private String errorDescriptionConfirm;
	
	    //服务描述
    private String waiterDescription;
	
	    //更换配件
    private String changeAccessory;
	
	    //费用估算
    private Float costEstimate;
	
	    //驳回意见
    private String reject;

    //维修分类
	private String maintainClassfiy;

	//维修确认人
	private String maintainConfirmPerson;

	//维修确认时间
	private String maintainConfirmTime;

	//申请确认人
	private String proposerConfirm;

	//申请人确认时间
	private String proposerConfirmTime;
	

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
	 * 设置：故障描述
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * 获取：故障描述
	 */
	public String getErrorDescription() {
		return errorDescription;
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
	 * 设置：变更版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：变更版本
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：申请人
	 */
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	/**
	 * 获取：申请人
	 */
	public String getProposer() {
		return proposer;
	}
	/**
	 * 设置：申请时间
	 */
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	/**
	 * 获取：申请时间
	 */
	public Date getApplicationTime() {
		return applicationTime;
	}
	/**
	 * 设置：申请人状态（0.待维修1.确认中2.维修中3.申请人确认4.维修完成）
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：申请人状态（0.待维修1.确认中2.维修中3.申请人确认4.维修完成）
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：部门
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 获取：部门
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * 设置：服务用时
	 */
	public void setWaiterUseTime(Double waiterUseTime) {
		this.waiterUseTime = waiterUseTime;
	}
	/**
	 * 获取：服务用时
	 */
	public Double getWaiterUseTime() {
		return waiterUseTime;
	}
	/**
	 * 设置：服务时间
	 */
	public void setWaiterTime(Date waiterTime) {
		this.waiterTime = waiterTime;
	}
	/**
	 * 获取：服务时间
	 */
	public Date getWaiterTime() {
		return waiterTime;
	}
	/**
	 * 设置：故障标题
	 */
	public void setFaultTitle(String faultTitle) {
		this.faultTitle = faultTitle;
	}
	/**
	 * 获取：故障标题
	 */
	public String getFaultTitle() {
		return faultTitle;
	}
	/**
	 * 设置：故障描述确认
	 */
	public void setErrorDescriptionConfirm(String errorDescriptionConfirm) {
		this.errorDescriptionConfirm = errorDescriptionConfirm;
	}
	/**
	 * 获取：故障描述确认
	 */
	public String getErrorDescriptionConfirm() {
		return errorDescriptionConfirm;
	}
	/**
	 * 设置：服务描述
	 */
	public void setWaiterDescription(String waiterDescription) {
		this.waiterDescription = waiterDescription;
	}
	/**
	 * 获取：服务描述
	 */
	public String getWaiterDescription() {
		return waiterDescription;
	}
	/**
	 * 设置：更换配件
	 */
	public void setChangeAccessory(String changeAccessory) {
		this.changeAccessory = changeAccessory;
	}
	/**
	 * 获取：更换配件
	 */
	public String getChangeAccessory() {
		return changeAccessory;
	}
	/**
	 * 设置：费用估算
	 */
	public void setCostEstimate(Float costEstimate) {
		this.costEstimate = costEstimate;
	}
	/**
	 * 获取：费用估算
	 */
	public Float getCostEstimate() {
		return costEstimate;
	}
	/**
	 * 设置：驳回意见
	 */
	public void setReject(String reject) {
		this.reject = reject;
	}
	/**
	 * 获取：驳回意见
	 */
	public String getReject() {
		return reject;
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

	public String getMaintainClassfiy() {
		return maintainClassfiy;
	}

	public void setMaintainClassfiy(String maintainClassfiy) {
		this.maintainClassfiy = maintainClassfiy;
	}

	public String getMaintainConfirmPerson() {
		return maintainConfirmPerson;
	}

	public void setMaintainConfirmPerson(String maintainConfirmPerson) {
		this.maintainConfirmPerson = maintainConfirmPerson;
	}

	public String getMaintainConfirmTime() {
		return maintainConfirmTime;
	}

	public void setMaintainConfirmTime(String maintainConfirmTime) {
		this.maintainConfirmTime = maintainConfirmTime;
	}

	public String getProposerConfirm() {
		return proposerConfirm;
	}

	public void setProposerConfirm(String proposerConfirm) {
		this.proposerConfirm = proposerConfirm;
	}

	public String getProposerConfirmTime() {
		return proposerConfirmTime;
	}

	public void setProposerConfirmTime(String proposerConfirmTime) {
		this.proposerConfirmTime = proposerConfirmTime;
	}
}
