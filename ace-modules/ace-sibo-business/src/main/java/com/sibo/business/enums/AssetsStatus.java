package com.sibo.business.enums;

public enum AssetsStatus {

    ENTERING("0","录入"),
    CONFIRM_CHANGE("1","已派发待确认"),
    CHANGE_CONFIRMATION("2","接收已确认"),
    CHANGE_FINISH("3","变更完成"),
    REJECT("4","拒绝"),
    RECEIVE("5","领用"),
    RETURN("6","归还待确认"),
    RETURN_CONFIRM("7","归还已确认"),
    INSTRUMENT_LOAN("8","仪器借出提交"),
    INSTRUMENT_LOAN_CONFIRM("9","仪器借出确认"),
    INSTRUMENT_LOAN_REJECT("10","仪器借出拒绝"),
    INSTRUMENT_LOAN_RETURN("11","仪器归还"),
    INSTRUMENT_RETURN_CONFIRM("12","仪器归还确认"),
    INSTRUMENT_RETURN_REJECT("13","仪器归还拒绝"),
    IN_STORAGE("14","归库"),
    RESET("15","重置"),
    HOLD("100","暂存");


    private String code;
    private String name;

    private AssetsStatus(String code, String name) {
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
    public static AssetsStatus getByCode(String code) {
        for (AssetsStatus menuActionType : AssetsStatus.values()) {
            if (menuActionType.getCode().equals(code.trim())) {
                return menuActionType;
            }
        }
        return null;
    }
}
