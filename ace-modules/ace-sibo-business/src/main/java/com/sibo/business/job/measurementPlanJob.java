package com.sibo.business.job;

import com.sibo.business.biz.VAssetsParameterBiz;
import com.sibo.business.biz.VInstrumentLibraryBiz;
import com.sibo.business.biz.VMeasurementVerificationPlanBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 计量计划定时任务
 */
@Slf4j
@Component
public class measurementPlanJob {

    @Autowired
    private VMeasurementVerificationPlanBiz vMeasurementVerificationPlanBiz;

    @Autowired
    private VAssetsParameterBiz vAssetsParameterBiz;

    @Autowired
    private VInstrumentLibraryBiz instrumentLibraryBiz;

    //@Scheduled(cron = "0 0 0 1 1 ?")
    public void pushDataScheduled() throws Exception{
        vMeasurementVerificationPlanBiz.createMeasurementpaln();
    }

    //@Scheduled(cron = "0 0 0 1 1 ?")
    public void createInspectPaln() throws Exception{
        vMeasurementVerificationPlanBiz.createInspectPaln();
    }
//    @Scheduled(cron = "0/10 * * * * ?")
    public void test() throws Exception{
        vAssetsParameterBiz.dis();
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void autoCommit() throws Exception{
        instrumentLibraryBiz.autoCommit();
    }
}
