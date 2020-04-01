package com.sibo.business.enums;

/**
 * 计量状态
 */
public enum MeasueementVerificationStatu {

    UNFINISHED("0","未完成"),
    FINISHED("1","已完成");

    private String code;
    private String name;

    private MeasueementVerificationStatu(String code, String name) {
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
