package com.sibo.business.enums;

/**
 * 计量审查类型
 */
public enum MetrologicalReviewType {

    MEASURE("0","计量"),
    INSPECT("1","核查");

    private String code;
    private String name;

    private MetrologicalReviewType(String code, String name) {
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
