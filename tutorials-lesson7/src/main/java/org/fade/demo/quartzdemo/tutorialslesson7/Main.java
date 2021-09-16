package org.fade.demo.quartzdemo.tutorialslesson7;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;

/**
 * @author fade
 * @date 2021/09/14
 */
public class Main {

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // do something
            JobDetail job = newJob(DumbJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData("jobSays", "Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();
            // 每40秒重复执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                    .build();
            MyJobListener myJobListener = new MyJobListener();
            MyTriggerListener myTriggerListener = new MyTriggerListener();
            scheduler.getListenerManager().addJobListener(myJobListener, KeyMatcher.keyEquals(new JobKey("myJob", "group1")));
//            scheduler.getListenerManager().addJobListenerMatcher("MyJobListener", KeyMatcher.keyEquals(new JobKey("myJob", "group1")));
            scheduler.getListenerManager().addTriggerListener(myTriggerListener, KeyMatcher.keyEquals(new TriggerKey("trigger1", "group1")));
            scheduler.scheduleJob(job, trigger);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
