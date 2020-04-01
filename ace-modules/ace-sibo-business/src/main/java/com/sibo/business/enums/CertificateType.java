package com.sibo.business.enums;

/**
 * 证书类型
 */
public enum CertificateType {
    INNER("1","内部证书"),
    EXTERTOR("2","外部证书")
            ;

    private String code;
    private String name;

    private CertificateType(String code, String name) {
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
