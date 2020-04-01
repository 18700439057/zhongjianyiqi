package com.sibo.business.enums;

/**
 * 设备从属
 */
public enum EquipmentSubordinate {
    FOLLOW_THE_PERSONNEL("0","跟随人员"),
    FOLLOW_THE_DEPARTMENT("1","跟随部门")
    ;

    private String code;
    private String name;

    private EquipmentSubordinate(String code, String name) {
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
