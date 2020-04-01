package com.sibo.business.enums;

public enum MaintainClassfiy {
    CRUX("0","关键"),
    EVERYDAY("1","日常"),
    CHARGE("2","费用");

    private String code;
    private String name;

    private MaintainClassfiy(String code, String name) {
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
