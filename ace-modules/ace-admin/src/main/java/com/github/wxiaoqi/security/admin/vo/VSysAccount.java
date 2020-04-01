package com.github.wxiaoqi.security.admin.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
public class VSysAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@Id
	private String id;

	//
	private String dep;

	//
	private String loginName;

	//
	private String password;

	//
	private String signature;

	//
	private String status;

	//
	private String orgId;

	//
	private String userId;

	//
	private String handleType;

	private String deviceId;

	//
	private String ifOnline;

	//
	private BigDecimal createTime;

	//
	private BigDecimal isDel;

	private BigDecimal lastUpdTime;

	//
	private BigDecimal sort;

	private BigDecimal version;


	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setDep(String dep) {
		this.dep = dep;
	}
	/**
	 * 获取：
	 */
	public String getDep() {
		return dep;
	}
	/**
	 * 设置：
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取：
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * 获取：
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	/**
	 * 获取：
	 */
	public String getHandleType() {
		return handleType;
	}
	/**
	 * 设置：
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：
	 */
	public void setIfOnline(String ifOnline) {
		this.ifOnline = ifOnline;
	}
	/**
	 * 获取：
	 */
	public String getIfOnline() {
		return ifOnline;
	}

	public BigDecimal getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigDecimal createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getIsDel() {
		return isDel;
	}

	public void setIsDel(BigDecimal isDel) {
		this.isDel = isDel;
	}

	public BigDecimal getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(BigDecimal lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public BigDecimal getVersion() {
		return version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}
}
