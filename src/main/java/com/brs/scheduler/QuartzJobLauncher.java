package com.brs.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.brs.config.BatchConfiguration;

public class QuartzJobLauncher extends QuartzJobBean {
	
	Logger log = LoggerFactory.getLogger(QuartzJobLauncher.class);

	private String jobName;
	private JobLauncher jobLauncher;
	private JobLocator jobLocator;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public JobLocator getJobLocator() {
		return jobLocator;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
//				.toJobParameters();
		
		JobParametersBuilder jobBuilder= new JobParametersBuilder();
		jobBuilder.addString("bankAccountNo", null);
		jobBuilder.addString("date",null);
		jobBuilder.addString("time", format.format(Calendar.getInstance().getTime()));
		JobParameters jobParameters =jobBuilder.toJobParameters();

		try {
			Job job = jobLocator.getJob(jobName);
			JobExecution jobExecution = jobLauncher.run(job, jobParameters);
			
			log.info("STATUS : "+jobExecution.getStatus());

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException | NoSuchJobException e) {
			e.printStackTrace();
		}
	}

}