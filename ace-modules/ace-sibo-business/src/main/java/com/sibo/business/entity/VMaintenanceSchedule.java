package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 维修计划表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-30 14:16:22
 */
@Table(name = "v_maintenance_schedule")
public class VMaintenanceSchedule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSET_ID")
    private String assetId;
	
	    //工作内容
    @Column(name = "JOB_CONTENT")
    private String jobContent;
	
	    //性质
    @Column(name = "NATURE")
    private String nature;
	
	    //责任人
    @Column(name = "PERSON_CHARGE")
    private String personCharge;
	
	    //开始时间
    @Column(name = "START_TIME")
    private Date startTime;
	
	    //结束时间
    @Column(name = "END_TIME")
    private Date endTime;
	
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

	@Column(name = "STATUS")
	private String status;
	

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
	 * 设置：工作内容
	 */
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	/**
	 * 获取：工作内容
	 */
	public String getJobContent() {
		return jobContent;
	}
	/**
	 * 设置：性质
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}
	/**
	 * 获取：性质
	 */
	public String getNature() {
		return nature;
	}
	/**
	 * 设置：责任人
	 */
	public void setPersonCharge(String personCharge) {
		this.personCharge = personCharge;
	}
	/**
	 * 获取：责任人
	 */
	public String getPersonCharge() {
		return personCharge;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
