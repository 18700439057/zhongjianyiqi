package com.sibo.business.enums;

/**
 * 存放地
 */
public enum DesignatedArea {
    YQK("1","仪器库");

    private String code;
    private String name;

    private DesignatedArea(String code, String name) {
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
