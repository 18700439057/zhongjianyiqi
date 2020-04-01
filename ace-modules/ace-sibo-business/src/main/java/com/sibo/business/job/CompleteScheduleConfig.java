package com.sibo.business.job;
import com.sibo.business.entity.VCron;
import com.sibo.business.mapper.VCronMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
@Component
public class CompleteScheduleConfig{

    @Autowired
    private VCronMapper cronMapper;


    @Resource(name = "jobDetail")
    private JobDetail jobDetail;

    @Resource(name = "jobTrigger")
    private CronTrigger cronTrigger;

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    /**
     * 方法名：
     * 功能：每隔10s查库，并根据查询结果决定是否重新设置定时任务
     * 描述：
     * 创建人：henry_liu
     * 创建时间：2018/10/10 14:19
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Scheduled(fixedRate = 120000)
    public void scheduleUpdateCronTrigger() throws SchedulerException {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
        //当前Trigger使用的
        String currentCron = trigger.getCronExpression();
        log.info("currentCron Trigger:{}", currentCron);
        //从数据库查询出来的
        Example example = new Example(VCron.class);
        example.createCriteria().andEqualTo("status","1");
        List<VCron> list = cronMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
//            String searchCron = list.get(0).getCron();
            for (VCron cron:list) {
                String searchCron =cron.getCron();
                log.info("searchCron  Trigger:{}", searchCron);
                if (currentCron.equals(searchCron)) {
                    // 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务
                } else {
                    //表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
                    //按新的cronExpression表达式重新构建trigger
                    trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
                    trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey()).withSchedule(scheduleBuilder).build();
                    // 按新的trigger重新设置job执行
                    scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
                    currentCron = searchCron;
                }
            }

        }
    }
}
