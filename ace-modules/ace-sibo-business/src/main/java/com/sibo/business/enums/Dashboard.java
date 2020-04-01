package com.sibo.business.enums;

public enum Dashboard {
    WAIT_CONFIRM_COUNT_LOAN("1","资产借出待确认"),
    WAIT_CONFIRM_COUNT_RETURN("2","资产归还待确认"),
    REJECT("3","资产配发拒绝"),
    MINI_ASSETS("4","我的资产");

    private String code;
    private String name;

    private Dashboard(String code, String name) {
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
