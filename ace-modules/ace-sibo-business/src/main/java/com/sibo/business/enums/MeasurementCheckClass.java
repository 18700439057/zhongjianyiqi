package com.sibo.business.enums;

public enum MeasurementCheckClass {
    NEED_TO_MEASURE("0","不需要计量"),
    NOT_NEED_TO_MEASURE("1","需要计量")
    ;

    private String code;
    private String name;

    private MeasurementCheckClass(String code, String name) {
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
    public static AssetsType getByCode(String code) {
        for (AssetsType menuActionType : AssetsType.values()) {
            if (menuActionType.getCode().equals(code.trim())) {
                return menuActionType;
            }
        }
        return null;
    }
}
