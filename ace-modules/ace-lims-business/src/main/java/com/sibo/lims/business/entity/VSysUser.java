package com.sibo.lims.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
@Table(name = "v_sys_user")
public class VSysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "ADDRESS")
    private String address;
	
	    //
    @Column(name = "BIRTHDAY")
    private String birthday;
	
	    //
    @Column(name = "CREDENTIALS_NO")
    private String credentialsNo;
	
	    //
    @Column(name = "CREDENTIALS_TYPE")
    private String credentialsType;
	
	    //
    @Column(name = "DUTY")
    private String duty;
	
	    //
    @Column(name = "EDUCATION")
    private String education;
	
	    //
    @Column(name = "EMAIL")
    private String email;
	
	    //
    @Column(name = "MOBILE")
    private String mobile;
	
	    //
    @Column(name = "NAME")
    private String name;
	
	    //
    @Column(name = "NATION")
    private String nation;
	
	    //
    @Column(name = "NO")
    private String no;
	
	    //
    @Column(name = "PHOTO")
    private String photo;
	
	    //
    @Column(name = "PROFESSION")
    private String profession;
	
	    //
    @Column(name = "REMARK")
    private String remark;
	
	    //
    @Column(name = "SEX")
    private String sex;
	
	    //
    @Column(name = "STATUS")
    private String status;
	
	    //
    @Column(name = "TECH_TITLE")
    private String techTitle;
	
	    //
    @Column(name = "TELEPHONE")
    private String telephone;
	
	    //
    @Column(name = "WORK_DATE")
    private String workDate;
	
	    //
    @Column(name = "DUTIES")
    private String duties;
	
	    //
    @Column(name = "DEVICE_ID")
    private String deviceId;
	
	    //
    @Column(name = "TEST_GROUP")
    private String testGroup;
	
	    //
    @Column(name = "U_KEY_ID")
    private String uKeyId;
	
	    //
    @Column(name = "CREATE_TIME")
    private BigDecimal createTime;
	
	    //
    @Column(name = "IS_DEL")
    private BigDecimal isDel;
	
	    //
    @Column(name = "LAST_UPD_TIME")
    private BigDecimal lastUpdTime;
	
	    //
    @Column(name = "SORT")
    private BigDecimal sort;
	
	    //
    @Column(name = "VERSION")
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
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 设置：
	 */
	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}
	/**
	 * 获取：
	 */
	public String getCredentialsNo() {
		return credentialsNo;
	}
	/**
	 * 设置：
	 */
	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}
	/**
	 * 获取：
	 */
	public String getCredentialsType() {
		return credentialsType;
	}
	/**
	 * 设置：
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	/**
	 * 获取：
	 */
	public String getDuty() {
		return duty;
	}
	/**
	 * 设置：
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * 获取：
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * 设置：
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 获取：
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 设置：
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * 获取：
	 */
	public String getNo() {
		return no;
	}
	/**
	 * 设置：
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * 获取：
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * 设置：
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	/**
	 * 获取：
	 */
	public String getProfession() {
		return profession;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：
	 */
	public String getSex() {
		return sex;
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
	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}
	/**
	 * 获取：
	 */
	public String getTechTitle() {
		return techTitle;
	}
	/**
	 * 设置：
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 获取：
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 设置：
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	/**
	 * 获取：
	 */
	public String getWorkDate() {
		return workDate;
	}
	/**
	 * 设置：
	 */
	public void setDuties(String duties) {
		this.duties = duties;
	}
	/**
	 * 获取：
	 */
	public String getDuties() {
		return duties;
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
	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}
	/**
	 * 获取：
	 */
	public String getTestGroup() {
		return testGroup;
	}
	/**
	 * 设置：
	 */
	public void setUKeyId(String uKeyId) {
		this.uKeyId = uKeyId;
	}
	/**
	 * 获取：
	 */
	public String getUKeyId() {
		return uKeyId;
	}

	public String getuKeyId() {
		return uKeyId;
	}

	public void setuKeyId(String uKeyId) {
		this.uKeyId = uKeyId;
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

	@Override
	public String toString() {
		return "VSysUser{" +
				"id='" + id + '\'' +
				", address='" + address + '\'' +
				", birthday='" + birthday + '\'' +
				", credentialsNo='" + credentialsNo + '\'' +
				", credentialsType='" + credentialsType + '\'' +
				", duty='" + duty + '\'' +
				", education='" + education + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				", name='" + name + '\'' +
				", nation='" + nation + '\'' +
				", no='" + no + '\'' +
				", photo='" + photo + '\'' +
				", profession='" + profession + '\'' +
				", remark='" + remark + '\'' +
				", sex='" + sex + '\'' +
				", status='" + status + '\'' +
				", techTitle='" + techTitle + '\'' +
				", telephone='" + telephone + '\'' +
				", workDate='" + workDate + '\'' +
				", duties='" + duties + '\'' +
				", deviceId='" + deviceId + '\'' +
				", testGroup='" + testGroup + '\'' +
				", uKeyId='" + uKeyId + '\'' +
				", createTime=" + createTime +
				", isDel=" + isDel +
				", lastUpdTime=" + lastUpdTime +
				", sort=" + sort +
				", version=" + version +
				'}';
	}
}
