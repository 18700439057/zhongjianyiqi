package com.sibo.business.vo;

import java.io.Serializable;
import java.util.Date;

public class VMeasurementVerificationPlanVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private String id;

    //资产ID
    private String assetId;

    //计量/核查状态
    private String measueementVerificationStatu;

    //计量有效期
    private Date measurementValidity;

    //计量/核查类型（1：计量2.核查）
    private String type;

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
     * 设置：计量/核查状态
     */
    public void setMeasueementVerificationStatu(String measueementVerificationStatu) {
        this.measueementVerificationStatu = measueementVerificationStatu;
    }
    /**
     * 获取：计量/核查状态
     */
    public String getMeasueementVerificationStatu() {
        return measueementVerificationStatu;
    }
    /**
     * 设置：计量/核查类型（1：计量2.核查）
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * 获取：计量/核查类型（1：计量2.核查）
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

    public Date getMeasurementValidity() {
        return measurementValidity;
    }

    public void setMeasurementValidity(Date measurementValidity) {
        this.measurementValidity = measurementValidity;
    }
}
