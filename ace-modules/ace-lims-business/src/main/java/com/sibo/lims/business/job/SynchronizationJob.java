package com.sibo.lims.business.job;

import com.sibo.lims.business.biz.VSysAccountBiz;
import com.sibo.lims.business.biz.VSysOrgBiz;
import com.sibo.lims.business.biz.VSysUserBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步用户部门定时任务
 */
@Slf4j
@Component
public class SynchronizationJob {

    @Autowired
    private VSysUserBiz vSysUserBiz;

    @Autowired
    private VSysOrgBiz vSysOrgBiz;

    @Autowired
    private VSysAccountBiz vSysAccountBiz;

//    @Scheduled(cron = "0 30 0 * * ?")
    public void syncUserByLims() throws Exception{
        vSysUserBiz.queryUser();
    }

//    @Scheduled(cron = "0 0 1 * * ?")
    public void syncDeptByLims() throws Exception{
        vSysOrgBiz.queryDept();
    }

//    @Scheduled(cron = "0 30 1 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void syncAccountByLims() throws Exception{
        vSysAccountBiz.queryAccount();
    }
}
