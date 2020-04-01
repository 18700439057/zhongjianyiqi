package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-06 18:27:28
 */
public class VMeasurementBasicVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    private String id;
	
	    //检定类型
    private String checkType;
	
	    //标准区间
    private Float standardRange;
	
	    //标准值
    private String standardValue;
	
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

	//附件上传
	private String templetedId;
	

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
	 * 设置：检定类型
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	/**
	 * 获取：检定类型
	 */
	public String getCheckType() {
		return checkType;
	}
	/**
	 * 设置：标准区间
	 */
	public void setStandardRange(Float standardRange) {
		this.standardRange = standardRange;
	}
	/**
	 * 获取：标准区间
	 */
	public Float getStandardRange() {
		return standardRange;
	}
	/**
	 * 设置：标准值
	 */
	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}
	/**
	 * 获取：标准值
	 */
	public String getStandardValue() {
		return standardValue;
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

	public String getTempletedId() {
		return templetedId;
	}

	public void setTempletedId(String templetedId) {
		this.templetedId = templetedId;
	}
}
