package com.sibo.business.calculate;

import com.sibo.business.calculate.impl.EngineSpeedImpl;
import com.sibo.business.calculate.impl.TemperatureImpl;
import com.sibo.business.enums.TempletedCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单工厂方法
 */
public class AnalysisFactoryTest {

    private static AnalysisFactoryTest factory = new AnalysisFactoryTest();

    private static Map<String,AnalysisFactory> analysisMap = new HashMap<String,AnalysisFactory>();
    static{
        analysisMap.put(TempletedCode.GENERAL.getCode(),new TemperatureImpl());
        analysisMap.put(TempletedCode.MEASUREMENT_PLAN.getCode(),new EngineSpeedImpl());
    }

    public AnalysisFactory creator(String sourceType ){
        return (AnalysisFactory)analysisMap.get(sourceType);
    }

    public static AnalysisFactoryTest getInstance(){
        return factory;
    }

}
