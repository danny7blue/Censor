package network;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
//public class HelloJob implements Job{
//public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        //打印当前的执行时间 例如 2017-11-23 00:00:00
//        Date date = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("现在的时间是："+ sf.format(date));
//        //具体的业务逻辑
//        System.out.println("Hello Quartz");
//    }
//}
class HelloJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 打印当前的执行时间，格式为2017-01-01 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time Is : " + sf.format(date));
        System.out.println("Hello World");
    }

}
class HelloScheduler {
    public static void main(String[] args) throws InterruptedException {
        // 打印当前的时间，格式为2017-01-01 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Time Is : " + sf.format(date));
        // 创建一个JobDetail实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob").build();
        CronTrigger trigger = (CronTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
        // 创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sfact.getScheduler();
            scheduler.start();
            System.out.println("scheduled time is :"
                    + sf.format(scheduler.scheduleJob(jobDetail, trigger)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        //scheduler执行两秒后挂起
        Thread.sleep(2000L);
        //shutdown(true)表示等待所有正在执行的job执行完毕之后，再关闭scheduler
        //shutdown(false)即shutdown()表示直接关闭scheduler
        try {
            scheduler.shutdown(false);
            System.out.println("scheduler is shut down? " + scheduler.isShutdown());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}