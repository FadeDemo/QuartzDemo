/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
 
package org.quartz.examples.example13;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to test/show the clustering features of JDBCJobStore (JobStoreTX or JobStoreCMT).
 * <p>
 * All instances MUST use a different properties file, because their instance Ids must be different, however all other
 * properties should be the same.
 * </p>
 * <p>
 * If you want it to clear out existing jobs & triggers, pass a command-line argument called "clearJobs".
 * </p>
 * <p>
 * You should probably start with a "fresh" set of tables (assuming you may have some data lingering in it from other
 * tests), since mixing data from a non-clustered setup with a clustered one can be bad.
 * </p>
 * <p>
 * Try killing one of the cluster instances while they are running, and see that the remaining instance(s) recover the
 * in-progress jobs. Note that detection of the failure may take up to 15 or so seconds with the default settings.
 * </p>
 * <p>
 * Also try running it with/without the shutdown-hook plugin registered with the scheduler.
 * (org.quartz.plugins.management.ShutdownHookPlugin).
 * </p>
 * <p>
 * <i>Note:</i> Never run clustering on separate machines, unless their clocks are synchronized using some form of
 * time-sync service (such as an NTP daemon).
 * </p>
 * 
 * @see SimpleRecoveryJob
 * @see SimpleRecoveryStatefulJob
 * @author James House
 */
public class ClusterExample {

  private static final Logger log = LoggerFactory.getLogger(ClusterExample.class);

  public void run(boolean inClearJobs, boolean inScheduleJobs) throws Exception {
    // todo 理解为什么这里的集群instance2会有输出？
    //  为什么tutorials-lesson11的集群只有NodeA有输出，
    //  当NodeA挂了，其余Node才会有输出？
    //  官方文档上说的集群的运行是负载均衡的，体现在哪里？

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();

    if (inClearJobs) {
      log.warn("***** Deleting existing jobs/triggers *****");
      sched.clear();
    }

    log.info("------- Initialization Complete -----------");

    if (inScheduleJobs) {

      log.info("------- Scheduling Jobs ------------------");

      String schedId = sched.getSchedulerInstanceId();

      int count = 1;
      // Job1 SimpleRecoveryJob
      JobDetail job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId) // put triggers in group
                                                                                            // named after the cluster
                                                                                            // node instance just to
                                                                                            // distinguish (in logging)
                                                                                            // what was scheduled from
                                                                                            // where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();

      SimpleTrigger trigger = newTrigger().withIdentity("triger_" + count, schedId)
          .startAt(futureDate(1, IntervalUnit.SECOND))
          .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInSeconds(5)).build();

      log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
      sched.scheduleJob(job, trigger);

      count++;

      // Job2 SimpleRecoveryJob
      job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId) // put triggers in group named after
                                                                                  // the cluster node instance just to
                                                                                  // distinguish (in logging) what was
                                                                                  // scheduled from where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();

      trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(2, IntervalUnit.SECOND))
          .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInSeconds(5)).build();

      log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
      sched.scheduleJob(job, trigger);

      count++;

      // Job3 SimpleRecoveryStatefulJob
      job = newJob(SimpleRecoveryStatefulJob.class).withIdentity("job_" + count, schedId) // put triggers in group named
                                                                                          // after the cluster node
                                                                                          // instance just to
                                                                                          // distinguish (in logging)
                                                                                          // what was scheduled from
                                                                                          // where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();

      trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(1, IntervalUnit.SECOND))
          .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInSeconds(3)).build();

      log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
      sched.scheduleJob(job, trigger);

      count++;

      // Job4 SimpleRecoveryJob
      job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId) // put triggers in group named after
                                                                                  // the cluster node instance just to
                                                                                  // distinguish (in logging) what was
                                                                                  // scheduled from where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();

      trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(1, IntervalUnit.SECOND))
          .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInSeconds(4)).build();

      log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " & repeat: " + trigger.getRepeatCount()
                + "/" + trigger.getRepeatInterval());
      sched.scheduleJob(job, trigger);

      count++;

      // Job5 SimpleRecoveryJob
      job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId) // put triggers in group named after
                                                                                  // the cluster node instance just to
                                                                                  // distinguish (in logging) what was
                                                                                  // scheduled from where
          .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went
                             // down...
          .build();

      trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(1, IntervalUnit.SECOND))
          .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInMilliseconds(4500L)).build();

      log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " & repeat: " + trigger.getRepeatCount()
                + "/" + trigger.getRepeatInterval());
      sched.scheduleJob(job, trigger);
    }

    // jobs don't start firing until start() has been called...
    log.info("------- Starting Scheduler ---------------");
    sched.start();
    log.info("------- Started Scheduler ----------------");

    log.info("------- Waiting for one hour... ----------");
    try {
      Thread.sleep(3600L * 1000L);
    } catch (Exception e) {
      //
    }

    log.info("------- Shutting Down --------------------");
    sched.shutdown();
    log.info("------- Shutdown Complete ----------------");
  }

  public static void main(String[] args) throws Exception {
    boolean clearJobs = false;
    boolean scheduleJobs = true;

    for (String arg : args) {
      if ("clearJobs".equalsIgnoreCase(arg)) {
        clearJobs = true;
      } else if ("dontScheduleJobs".equalsIgnoreCase(arg)) {
        scheduleJobs = false;
      }
    }

    ClusterExample example = new ClusterExample();
    example.run(clearJobs, scheduleJobs);
  }
}
