package com.sibo.business.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 投资计划导出表头
 */
public enum InvestmentHeadersEnums {
    PROJECTNAME("projectName","项目名称"),
    PLANSUMINVESTMENT("planSumInvestment","计划投资(万元)"),
    PRACTICALINVESTMENT("practicalInvestment","累计投资(万元)"),
    PROJECTPROGRESS("projectProgress","项目进度"),
    INVESTMENTCATEGORY("investmentCategory","投资类别"),
    INVESTMENTNATURE("investmentNature","投资性质"),
    CAPITALSOURCE("capitalSource","资金来源"),
    CONSTRUCTIONCONTENT("constructionContent","建设内容"),
    CONSTRUCTIONNECESSITY("constructionNecessity","建设必要性"),
    PROJECTLEADER("projectLeader","项目负责人"),
    RESPONSIBLEDEPARTMENT("responsibleDepartment","责任部门"),
    CRTUSERNAME("crtUserName","创建人名称"),
    KYSTARTTIME("kyStartTime","可研方案开始时间"),
    KY_ENDTIME("kyEndTime","可研方案结束时间"),
    ZB_STARTTIME("zbStartTime","招标开始时间"),
    ZB_ENDTIME("zbEndTime","招标结束时间"),
    HT_STARTTIME("htStartTime","合同签订开始时间"),
    HT_ENDTIME("htEndTime","合同签订结束时间"),
    SS_STARTTIME("ssStartTime","实施开始时间"),
    SS_ENDTIME("ssEndTime","实施结束时间"),
    GN_STARTTIME("gnStartTime","功能验收开始时间"),
    GN_ENDTIME("gnEndTime","功能验收结束时间"),
    JG_STARTTIME("jgStartTime","竣工验收开始时间"),
    JG_ENDTIME("jgEndTime","竣工验收结束时间"),
    REMARK("remark","备注");
    private String code;
    private String name;

    private InvestmentHeadersEnums(String code, String name) {
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
    public static JSONArray  toJson() {
        JSONArray jsonArray = new JSONArray();
        for (InvestmentHeadersEnums e : InvestmentHeadersEnums.values()) {
            JSONObject object = new JSONObject();
            object.put("value", e.getCode());
            object.put("name", e.getName());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    public static String [] getKeys() {
        String [] str = new String[InvestmentHeadersEnums.values().length];
        int i = 0;
        for (InvestmentHeadersEnums costHeadersEnums : InvestmentHeadersEnums.values()) {
            str[i] = costHeadersEnums.getName();
            i++;
        }

        return str;
    }
}
