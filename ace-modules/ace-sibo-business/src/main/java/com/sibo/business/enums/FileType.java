package com.sibo.business.enums;

/**
 * 附件类型
 */
public enum FileType {

    GENERAL("0","一般附件"),
    MEASUREMENT_PLAN("1","计量附件"),
    PERIOD_CHECK("2","期间核查附件"),
    YEAR_PLAY("3","计量计划附件"),
    YEAR_CHECK("4","核查计划附件");

    private String code;
    private String name;

    private FileType(String code, String name) {
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
