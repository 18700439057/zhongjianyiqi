package com.sibo.business.enums;

public enum WorkType {
    WAIT_WORK("1","待办工作"),
    HAVE_WORK("2","已办工作");

    private String code;
    private String name;

    private WorkType(String code, String name) {
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
