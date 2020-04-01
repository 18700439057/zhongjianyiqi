package com.sibo.business.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 费用导出表头
 */
public enum CostHeadersEnums {
    MAINTENANCE_TYPE("maintenanceType","维修类别"),
    DEVICENAME("devicename","设备名称"),
    EQUIPMENT_NUMBER("equipmentNumber","设备编号"),
    DEPARTMENT("department","设备所属部门"),
    WAITER_USE_TIME("waiterUseTime","服务用时"),
    CRT_TIME("crtTime","维修时间"),
    MAINTENANCE_MODE("maintenanceMode","维修方式"),
    ERROR_DESCRIPTION("errorDescription","故障描述"),
    COST_ESTIMATE("costEstimate","费用结算"),
    CHANGE_ACCESSORY("changeAccessory","更换配件");
    private String code;
    private String name;

    private CostHeadersEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code获取ENUM
     *
     * @param code
     * @return
     */
    public static JSONArray  toJson() {
        JSONArray jsonArray = new JSONArray();
        for (CostHeadersEnums e : CostHeadersEnums.values()) {
            JSONObject object = new JSONObject();
            object.put("value", e.getCode());
            object.put("name", e.getName());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    public static String [] getKeys() {
        String [] str = new String[CostHeadersEnums.values().length];
        int i = 0;
        for (CostHeadersEnums costHeadersEnums : CostHeadersEnums.values()) {
            str[i] = costHeadersEnums.getName();
            i++;
        }

        return str;
    }
}
