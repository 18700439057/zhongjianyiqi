package com.sibo.business.entity;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.sibo.business.feign.DictFeign;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-04-15 16:45:04
 */
@Table(name = "v_investment")
public class VInvestment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "PROJECT_NAME")
    private String projectName;
	
	    //
    @Column(name = "PLAN_SUM_INVESTMENT")
    private String planSumInvestment;
	
	    //
    @Column(name = "PRACTICAL_INVESTMENT")
    private String practicalInvestment;
	

	
	    //
    @Column(name = "ACCUMULATIVE_FINISH")
    private String accumulativeFinish;
	
	    //
    @Column(name = "PROJECT_PROGRESS")
	@MergeField(key="projectProgress",feign = DictFeign.class,method ="getDictValues")
    private String projectProgress;

    //投资类别
	@Column(name = "INVESTMENT_CATEGORY")
	@MergeField(key="investmentCategory",feign = DictFeign.class,method ="getDictValues")
    private String investmentCategory;

	//投资性质
	@Column(name = "INVESTMENT_NATURE")
	@MergeField(key="investmentNature",feign = DictFeign.class,method ="getDictValues")
	private String investmentNature;

	//资金来源
	@Column(name = "CAPITAL_SOURCE")
	@MergeField(key="capitalSource",feign = DictFeign.class,method ="getDictValues")
	private String capitalSource;

	//建设内容
	@Column(name = "CONSTRUCTION_CONTENT")
	private String constructionContent;

	//建设必要性
	@Column(name = "CONSTRUCTION_NECESSITY")
	private String constructionNecessity;

	//项目负责人
	@Column(name = "PROJECT_LEADER")
	private String projectLeader;

	//责任部门
	@Column(name = "RESPONSIBLE_DEPARTMENT")
	private String responsibleDepartment;
	    //
    @Column(name = "REMARK")
    private String remark;
	
	    //
    @Column(name = "CRT_USER_NAME")
    private String crtUserName;
	
	    //
    @Column(name = "CRT_USER_ID")
    private String crtUserId;

	@Column(name = "KY_START_TIME")
	private String kyStartTime;

	//
	@Column(name = "KY_END_TIME")
	private String kyEndTime;

	//
	@Column(name = "ZB_START_TIME")
	private String zbStartTime;

	@Column(name = "ZB_END_TIME")
	private String zbEndTime;

	@Column(name = "HT_START_TIME")
	private String htStartTime;

	@Column(name = "HT_END_TIME")
	private String htEndTime;

	@Column(name = "SS_START_TIME")
	private String ssStartTime;

	@Column(name = "SS_END_TIME")
	private String ssEndTime;

	@Column(name = "GN_START_TIME")
	private String gnStartTime;

	@Column(name = "GN_END_TIME")
	private String gnEndTime;

	@Column(name = "JG_START_TIME")
	private String jgStartTime;

	@Column(name = "JG_END_TIME")
	private String jgEndTime;


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

	//项目名称简称
	@Column(name = "PROJECT_SHORT_NAME")
	private String projectShortName;

	

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
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：
	 */
	public void setPlanSumInvestment(String planSumInvestment) {
		this.planSumInvestment = planSumInvestment;
	}
	/**
	 * 获取：
	 */
	public String getPlanSumInvestment() {
		return planSumInvestment;
	}
	/**
	 * 设置：
	 */
	public void setPracticalInvestment(String practicalInvestment) {
		this.practicalInvestment = practicalInvestment;
	}
	/**
	 * 获取：
	 */
	public String getPracticalInvestment() {
		return practicalInvestment;
	}

	/**
	 * 设置：
	 */
	public void setAccumulativeFinish(String accumulativeFinish) {
		this.accumulativeFinish = accumulativeFinish;
	}
	/**
	 * 获取：
	 */
	public String getAccumulativeFinish() {
		return accumulativeFinish;
	}
	/**
	 * 设置：
	 */
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	/**
	 * 获取：
	 */
	public String getProjectProgress() {
		return projectProgress;
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
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	/**
	 * 获取：
	 */
	public String getCrtUserName() {
		return crtUserName;
	}
	/**
	 * 设置：
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：
	 */
	public String getCrtUserId() {
		return crtUserId;
	}

	public String getKyStartTime() {
		if(StringUtils.isBlank(kyStartTime)){
			this.kyStartTime = "";
		}
		return kyStartTime;
	}

	public void setKyStartTime(String kyStartTime) {
		this.kyStartTime = kyStartTime;
	}

	public String getKyEndTime() {
		if(StringUtils.isBlank(kyEndTime)){
			this.kyEndTime = "";
		}
		return kyEndTime;
	}

	public void setKyEndTime(String kyEndTime) {
		this.kyEndTime = kyEndTime;
	}

	public String getZbStartTime() {
		if(StringUtils.isBlank(zbStartTime)){
			this.zbStartTime = "";
		}
		return zbStartTime;
	}

	public void setZbStartTime(String zbStartTime) {
		this.zbStartTime = zbStartTime;
	}

	public String getZbEndTime() {
		if(StringUtils.isBlank(zbEndTime)){
			this.zbEndTime = "";
		}
		return zbEndTime;
	}

	public void setZbEndTime(String zbEndTime) {
		this.zbEndTime = zbEndTime;
	}

	public String getHtStartTime() {
		if(StringUtils.isBlank(htStartTime)){
			this.htStartTime = "";
		}
		return htStartTime;
	}

	public void setHtStartTime(String htStartTime) {
		this.htStartTime = htStartTime;
	}

	public String getHtEndTime() {
		if(StringUtils.isBlank(htEndTime)){
			this.htEndTime = "";
		}
		return htEndTime;
	}

	public void setHtEndTime(String htEndTime) {
		this.htEndTime = htEndTime;
	}

	public String getSsStartTime() {
		if(StringUtils.isBlank(ssStartTime)){
			this.ssStartTime = "";
		}
		return ssStartTime;
	}

	public void setSsStartTime(String ssStartTime) {
		this.ssStartTime = ssStartTime;
	}

	public String getSsEndTime() {
		if(StringUtils.isBlank(ssEndTime)){
			this.ssEndTime = "";
		}
		return ssEndTime;
	}

	public void setSsEndTime(String ssEndTime) {
		this.ssEndTime = ssEndTime;
	}

	public String getGnStartTime() {
		if(StringUtils.isBlank(gnStartTime)){
			this.gnStartTime = "";
		}
		return gnStartTime;
	}

	public void setGnStartTime(String gnStartTime) {
		this.gnStartTime = gnStartTime;
	}

	public String getGnEndTime() {
		if(StringUtils.isBlank(gnEndTime)){
			this.gnEndTime = "";
		}
		return gnEndTime;
	}

	public void setGnEndTime(String gnEndTime) {
		this.gnEndTime = gnEndTime;
	}

	public String getJgStartTime() {
		if(StringUtils.isBlank(jgStartTime)){
			this.jgStartTime = "";
		}
		return jgStartTime;
	}

	public void setJgStartTime(String jgStartTime) {
		this.jgStartTime = jgStartTime;
	}

	public String getJgEndTime() {
		return jgEndTime;
	}

	public void setJgEndTime(String jgEndTime) {
		this.jgEndTime = jgEndTime;
	}

	public String getInvestmentCategory() {
		return investmentCategory;
	}

	public void setInvestmentCategory(String investmentCategory) {
		this.investmentCategory = investmentCategory;
	}

	public String getInvestmentNature() {
		return investmentNature;
	}

	public void setInvestmentNature(String investmentNature) {
		this.investmentNature = investmentNature;
	}

	public String getCapitalSource() {
		return capitalSource;
	}

	public void setCapitalSource(String capitalSource) {
		this.capitalSource = capitalSource;
	}

	public String getConstructionContent() {
		return constructionContent;
	}

	public void setConstructionContent(String constructionContent) {
		this.constructionContent = constructionContent;
	}

	public String getConstructionNecessity() {
		return constructionNecessity;
	}

	public void setConstructionNecessity(String constructionNecessity) {
		this.constructionNecessity = constructionNecessity;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getResponsibleDepartment() {
		return responsibleDepartment;
	}

	public void setResponsibleDepartment(String responsibleDepartment) {
		this.responsibleDepartment = responsibleDepartment;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdUserName() {
		return updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getProjectShortName() {
		return projectShortName;
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}
}
