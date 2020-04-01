package com.sibo.business.vo;

import java.io.Serializable;

public class LimsVAssetsParameterVo  implements Serializable {

    private String id;

    //统一编号
    private String unifiedCode;

    //设备名称
    private String devicename;

    //设备型号
    private String unitType;

    private String  periodDate;

    private String equipmentNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnifiedCode() {
        return unifiedCode;
    }

    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    @Override
    public String toString() {
        return "LimsVAssetsParameterVo{" +
                "id='" + id + '\'' +
                ", unifiedCode='" + unifiedCode + '\'' +
                ", devicename='" + devicename + '\'' +
                ", unitType='" + unitType + '\'' +
                ", periodDate='" + periodDate + '\'' +
                '}';
    }
}
