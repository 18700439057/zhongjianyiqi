package com.sibo.business.job;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.biz.VCronLogBiz;
import com.sibo.business.entity.VCronLog;
import com.sibo.business.mapper.VCronLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private VCronLogBiz cronLogBiz;
    public void sayHello(){
        String strcmd = "cmd /c start D:\\up\\jar\\backup.bat";
        run_cmd(strcmd,"");
    }

    public  void run_cmd(String strcmd,String crtName) {
        //
        Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
        try {
            ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            ps.waitFor();  //等待子进程完成再往下执行。
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int i = ps.exitValue();  //接收执行完毕的返回值
        if (i == 0) {
            log.info("DB backup sucess！！！");
        } else {
            log.info("DB backup failed！！！");
        }
        saveTaskLog(crtName);

        ps.destroy();  //销毁子进程
        ps = null;
    }

    private void saveTaskLog(String crtName){
        VCronLog cronLog = new VCronLog();
        if(StringUtils.isNotEmpty(crtName)){
            cronLog.setCrtName(crtName);
        }else{
            cronLog.setCrtName("自动备份");
        }

        cronLog.setCrtTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        cronLogBiz.insertSelective(cronLog);
    }


//        public static void main(String[] args) {
//            String strcmd = "cmd /c start C:\\Users\\Administrator\\Desktop\\backup.bat";
//            run_cmd(strcmd);
//        }
}
