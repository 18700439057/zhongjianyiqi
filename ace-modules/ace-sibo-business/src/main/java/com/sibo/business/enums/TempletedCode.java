package com.sibo.business.enums;

public enum TempletedCode {

    GENERAL("wd","温度"),
    MEASUREMENT_PLAN("zs","发动机转速")
    ;

    private final String code;
    private final String name;

    private TempletedCode(String code, String name) {
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
