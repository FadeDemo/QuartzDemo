package org.fade.demo.quartzdemo.quartzspring;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fade
 * @date 2021/11/24
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Scheduler scheduler = context.getBean("scheduler", Scheduler.class);
        try {
            scheduler.start();
            Thread.sleep(5000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
