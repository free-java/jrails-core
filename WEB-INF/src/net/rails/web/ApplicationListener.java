package net.rails.web;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rails.Define;
import net.rails.ext.AbsGlobal;
import net.rails.support.Support;
import net.rails.support.job.worker.DefaultScheduleWorker;
import net.rails.support.job.worker.JobObject;
import net.rails.support.job.worker.JobWorker;

@WebListener
public class ApplicationListener implements ServletContextListener {

	private Logger log;
	private AbsGlobal g;
	private ServletContextEvent context;

	public ApplicationListener() {
		super();
		log = LoggerFactory.getLogger(ApplicationListener.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.debug("ApplicationListener Destroyed!");
		shutdownScheduler();
	}

	@Override
	public void contextInitialized(final ServletContextEvent context) {
		this.context = context;
		log.debug("ApplicationListener Initialized!");
		if (Define.CONFIG_PATH == null) {
			String webinf = new File(String.format("%s/WEB-INF/",context.getServletContext().getRealPath("/"))).getAbsolutePath();
			Define.CONFIG_PATH = String.format("%s/config/",webinf);
			log.debug("Set Define.CONFIG_PATH = {}",Define.CONFIG_PATH);
		}
		if (Define.VIEW_PATH == null) {
			String webinf = new File(String.format("%s/WEB-INF/",context.getServletContext().getRealPath("/"))).getAbsolutePath();
			Define.VIEW_PATH = String.format("%s/view/",webinf);
			log.debug("Set Define.VIEW_PATH = {}",Define.VIEW_PATH);
		}
		File logPropFile = new File(String.format("%s/log4j.properties",Define.CONFIG_PATH));
		if (logPropFile.exists()) {
			log.debug("Use /config/log4j.properties");
			try {
				PropertyConfigurator.configure(logPropFile.toURI().toURL());
			} catch (MalformedURLException e) {
				log.error(e.getMessage(), e);
			}
		}
		g = new AbsGlobal() {

			@Override
			public void setUserId(Object userId) {

			}

			@Override
			public Object getUserId() {
				return null;
			}

			@Override
			public void setSessionId(Object sessionId) {

			}

			@Override
			public Object getSessionId() {
				return null;
			}

			@Override
			public String getRealPath() {
				return context.getServletContext().getRealPath("/");
			}

		};
		startJobs();
	}

	private void startJobs() {
		if (!Support.env().getRoot().containsKey("jobs")) {
			return;
		}
		Object o = Support.env().get("jobs");
		DefaultScheduleWorker scheduleWorker = null;
		try {
			log.debug("Starting Jobs");
			if (o instanceof List) {
				scheduleWorker = Support.job().defaultSchedule(g);
			} else {
				scheduleWorker = (DefaultScheduleWorker) Class.forName(o.toString()).getConstructor(AbsGlobal.class)
						.newInstance(g);
			}
			List<JobObject> jobs = scheduleWorker.getScheduleJobs();
			for (JobObject jobWorker : jobs) {
				String jobName = jobWorker.getJobName();
				String jobClass = jobWorker.getClassify();
				String cronExpression = jobWorker.getCronExpression();
				log.debug("Starting: {}",jobName);
				log.debug("Class: {}",jobClass);
				log.debug("Cron Expression: {}",cronExpression);
				org.quartz.Job job = (org.quartz.Job) Class.forName(jobClass).newInstance();
				JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, jobWorker.getJobGroup())
						.build();
				jobDetail.getJobDataMap().put("AbsGlobal", g);
				Trigger trigger = TriggerBuilder.newTrigger()
						.withIdentity(jobWorker.getTriggerName(), jobWorker.getTriggerGroup())
						.withSchedule(CronScheduleBuilder.cronSchedule(jobWorker.getCronExpression())).build();

				JobListener jobListener = scheduleWorker.getJobListener();
				TriggerListener triggerListener = scheduleWorker.getTriggerListener();
				if(jobListener != null){
					JobWorker.GLOBAL_SCHEDULER.getListenerManager().addJobListener(scheduleWorker.getJobListener());
				}
				if(triggerListener != null){
					JobWorker.GLOBAL_SCHEDULER.getListenerManager().addTriggerListener(scheduleWorker.getTriggerListener());
				}
				JobWorker.GLOBAL_SCHEDULER.start();
				JobWorker.GLOBAL_SCHEDULER.scheduleJob(jobDetail, trigger);
			}
			log.debug("Started Jobs");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void shutdownScheduler() {
		if (!Support.env().getRoot().containsKey("jobs")) {
			return;
		}
		try {
			if(JobWorker.GLOBAL_SCHEDULER != null){
				JobWorker.GLOBAL_SCHEDULER.shutdown(true);
			}
			log.debug("Scheduler Shutdown Status: {}",JobWorker.GLOBAL_SCHEDULER.isShutdown());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
