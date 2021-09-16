package org.fade.demo.quartzdemo.persistannotationtest;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author fade
 * @date 2021/09/13
 */
public class Main {

    public static void main(String[] args) throws SchedulerException {
        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // jobs can be scheduled before start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 2);

        JobDetail job = newJob(BadJob.class)
                .withIdentity("badJob1", "group1")
                .usingJobData("denominator", "0")
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        Date ft = sched.scheduleJob(job, trigger);

        //任务每2秒执行一次 那么在BadJob1的方法中拿到的JobDataMap的数据是共享的.
        //这里要注意一个情况： 就是JobDataMap的数据共享只针对一个BadJob1任务。
        //如果在下面在新增加一个任务 那么他们之间是不共享的 比如下面

//        JobDetail job2 = newJob(BadJob.class)
//                .withIdentity("badJob2", "group1")
//                .usingJobData("denominator", "0")
//                .build();
//
//        SimpleTrigger trigger2 = newTrigger()
//                .withIdentity("trigger2", "group1")
//                .startAt(startTime)
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(2)
//                        .repeatForever())
//                .build();
//
//        //这个job2与job执行的JobDataMap不共享
//        sched.scheduleJob(job2, trigger2);

        sched.start();

        try {
            // sleep for 30 seconds
            Thread.sleep(30L * 1000L);
        } catch (Exception e) {
        }

        sched.shutdown(false);
    }

}
