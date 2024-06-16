package simplewebserver.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Integrate a scheduling library to run periodic tasks.
 * */
public class ScheduledTask implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Scheduled task executed: " + context.getFireTime());
    }

    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(ScheduledTask.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
