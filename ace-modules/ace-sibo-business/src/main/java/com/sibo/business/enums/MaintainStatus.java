package com.sibo.business.enums;

public enum MaintainStatus {

    WAIT_SUBMIT("0","待提交"),
    CONFIRMATION("1","确认中"),
    MAINTENANCE("2","维修中"),
    COMPLETE_MAINTENANCE("3","维修完成"),
    COMPLETE_MAINTENANCE_CONFIRM("4","维修完成通过"),
    COMPLETE_MAINTENANCE_REJECT("5","维修完成拒绝");

    private String code;
    private String name;

    private MaintainStatus(String code, String name) {
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
    public static MaintainStatus getByCode(String code) {
        for (MaintainStatus menuActionType : MaintainStatus.values()) {
            if (menuActionType.getCode().equals(code.trim())) {
                return menuActionType;
            }
        }
        return null;
    }
}
