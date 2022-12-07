package com.brs.scheduler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brs.BrsProjectTestApplication;
import com.brs.batch.BrsDataItemProcessor;
import com.brs.batch.BrsDataItemWriter;
import com.brs.config.BatchConfiguration;
import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.EmailDetails;
import com.brs.repository.BrsConsoldtStgRepository;
import com.brs.service.BrsDataServiceImpl;
import com.brs.service.EmailService;
import com.brs.util.AppUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SchedulerController {
	private static final String SCHEDULED_TASKS = "scheduledTasks";

	@Autowired
	private EmailService emailService;

	@Autowired
	private ScheduledAnnotationBeanPostProcessor postProcessor;

	@Autowired
	private SchedulerConfig schedulerConfiguration;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yy");

	@Autowired
	private BrsConsoldtStgRepository repo;

	@Autowired
	private BrsDataItemProcessor brsProcess;

	@Autowired
	private BrsDataItemWriter brsWriter;

	Logger log = LoggerFactory.getLogger(SchedulerController.class);

	@GetMapping(value = "/")
	public String test() {
		return "hello";
	}

	@GetMapping(value = "/stop")
	public String stopSchedule() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		postProcessor.postProcessBeforeDestruction(schedulerConfiguration, SCHEDULED_TASKS);
		return "Job Stopped";
	}

	@GetMapping(value = "/start")
	public String startSchedule() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		JobParametersBuilder jobBuilder = new JobParametersBuilder();
		jobBuilder.addString("bankAccountNo", null);
		jobBuilder.addString("date", null);
		jobBuilder.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
		JobParameters jobParameters = jobBuilder.toJobParameters();

//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
		schedulerConfiguration.jobLauncher.run(job, jobParameters);

		return "Job execution has been started.";
	}

	@GetMapping(value = "/list")
	public String listSchedules() throws JsonProcessingException {
		Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
		if (!setTasks.isEmpty()) {
			return setTasks.toString();
		} else {
			return "Currently no scheduler tasks are running";
		}
	}

	@GetMapping("/execute/{bankAccountNo}/{uniqueReference}")
	public String executeBrsProcess(@PathVariable String bankAccountNo, @PathVariable String uniqueReference)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		log.info("executeBrsProcess, bankAccountNo : " + bankAccountNo + " uniqueReference : " + uniqueReference);

		List<BrsConsoldtStg> otherRecordsList = repo.getBrsChequeRecords(uniqueReference, bankAccountNo);
		log.info("otherRecordsList : " + otherRecordsList.size());

		if (!otherRecordsList.isEmpty()) {
			List<BrsConsoldtStg> bookList = brsProcess.process(otherRecordsList.get(0));
			if (!bookList.isEmpty()) {
				brsWriter.writeBRS(bookList);
			}
		}

		return "BRS process has been started.";
	}

//	@GetMapping("/executebrs/{bankAccountNo}/{date}")
//	public String executeBrsProcessDayWise(@PathVariable String bankAccountNo, @PathVariable String date)
//			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
//			JobParametersInvalidException {
//		log.info("executeBrsProcessDayWise, bankAccountNo : " + bankAccountNo + " date : " + date);
//		System.out.println("bankAccountNo"+ bankAccountNo);
//		if ((bankAccountNo == null || "".equals(bankAccountNo) || "null".equals(bankAccountNo))
//				&& (date == null || "".equals(date) || "null".equals(date))) {
//			
//			
//			return "Please add bank account and date. ";
//			
//		}
//
//		try {
//
//			JobParametersBuilder jobBuilder = new JobParametersBuilder();
//			jobBuilder.addString("bankAccountNo", bankAccountNo);
//			jobBuilder.addString("date", date);
//			jobBuilder.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
//			JobParameters jobParameters = jobBuilder.toJobParameters();
//
//			schedulerConfiguration.jobLauncher.run(job, jobParameters);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("Exception occurred durning sarting batch programme!!");
//		}
//		System.out.println("Job is Completed::::::::::::");
//		return "BRS process has been started.";
//	}
//	
	
	
	
	@GetMapping("/executebrs/{bankAccountNo}/{voucherDate}")
	public String executeBrsProcessDayWise(@PathVariable String bankAccountNo, @PathVariable String voucherDate)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		log.info("executeBrsProcessDayWise, bankAccountNo : " + bankAccountNo + " voucherDate : " + voucherDate);
		System.out.println("bankAccountNo"+ bankAccountNo);
		if ((bankAccountNo == null || "".equals(bankAccountNo) || "null".equals(bankAccountNo))
				&& (voucherDate == null || "".equals(voucherDate) || "null".equals(voucherDate))) {
			
			
			return "Please add bank account and date. ";
			
		}else {
			
			
			
		}

		try {

			JobParametersBuilder jobBuilder = new JobParametersBuilder();
			jobBuilder.addString("bankAccountNo", bankAccountNo);
			jobBuilder.addString("voucherDate", voucherDate);
			jobBuilder.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
			JobParameters jobParameters = jobBuilder.toJobParameters();

			schedulerConfiguration.jobLauncher.run(job, jobParameters);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception occurred durning sarting batch programme!!");
		}
		System.out.println("Job is Completed::::::::::::");
		return "BRS process has been started.";
	}
	
	
	
	
	
	


	@GetMapping("/executebrsdaywise/{date}")
	public String executeBrsProcessDayWise(@PathVariable String date) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.info("executebrsdaywise date : " + date);
		

		if ((date == null || "".equals(date) || "null".equals(date))) {
			return "Please add date. ";
		}

		try {
			Date executionDate = fmt.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return "Please pass date in dd-MM-yy format.";
		}

		try {
			JobParametersBuilder jobBuilder = new JobParametersBuilder();
			jobBuilder.addString("bankAccountNo", null);
			jobBuilder.addString("date", date);
			jobBuilder.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
			JobParameters jobParameters = jobBuilder.toJobParameters();
			schedulerConfiguration.jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception occurred durning sarting batch programme!!");
		}
		return "BRS process has been started.";
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {
		
		String status = emailService.sendSimpleMail(details);

		return status;
	}

	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}

}