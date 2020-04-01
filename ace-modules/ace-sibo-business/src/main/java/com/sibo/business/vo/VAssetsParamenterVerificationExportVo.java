package com.sibo.business.vo;

/**
 *
 *
 * @author henry
 * @email 137022680@qq.com
 * @version 2019-2-25 14:26:34
 */
public class VAssetsParamenterVerificationExportVo {


    //设备名称
    private String devicename;

    //设备型号
    private String unitType;


    /**
     * 设备编号
     */
    private String equipmentNumber;

    //核查有效期至
    private String periodDateStop;

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

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getPeriodDateStop() {
        return periodDateStop;
    }

    public void setPeriodDateStop(String periodDateStop) {
        this.periodDateStop = periodDateStop;
    }
}
