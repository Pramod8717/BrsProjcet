package com.brs.batch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.hibernate.sql.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
//import org.thymeleaf.spring5.SpringTemplateEngine;

import com.brs.entities.BrsBankStmtHeaderStg;
import com.brs.entities.EmailDetails;
import com.brs.service.BrsBankStmtHeaderService;
import com.brs.service.BrsBankStmtService;
import com.brs.service.BrsDataService;
import com.brs.service.BrsEbsStgService;
import com.brs.service.BrsErrorService;
import com.brs.service.BrsUpdateStgService;
import com.brs.util.AppConstants;
import com.brs.util.AppUtility;
import com.brs.util.BankStmtLineSummary;
import com.brs.util.BatchStatusInterf;
import com.brs.util.BrsDataUtil;
import com.opencsv.CSVWriter;
 
@Component
public class JobExecListener implements JobExecutionListener {
//	@Autowired
//	private  SpringTemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	Logger log = LoggerFactory.getLogger(JobExecListener.class);

	@Autowired
	private BrsBankStmtService bnkService;

	@Autowired
	private BrsEbsStgService ebsService;

	@Autowired
	private BrsUpdateStgService updateStgService;

	@Autowired
	private BrsDataService brsStgService;

	@Autowired
	private BrsDataUtil util;

	@Autowired
	private BrsErrorService errorService;

	@Autowired
	private BrsBankStmtHeaderService bnkHdrService;

	@Autowired
	private AppUtility appUtility;

	SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-YY");

	List<BatchStatusInterf> statusJobList = new ArrayList();
	File file;

	long beforeUsedMem = 0;
	long afterUsedMem = 0;
	long actualMemUsed = 0;

	private final long MEGABYTE = 1024L * 1024L;

	public long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("     ");
		log.info(":::::::::::::::::Before job Process execution:::::::::::::::::::");
		log.info("     ");

		log.info("TOTAL MEMORY : " + Runtime.getRuntime().totalMemory() + " MB : "
				+ bytesToMegabytes(Runtime.getRuntime().totalMemory()));
		log.info("FREE MEMORY : " + Runtime.getRuntime().freeMemory() + " MB : "
				+ bytesToMegabytes(Runtime.getRuntime().freeMemory()));
		beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		log.info("BEFORE USED MEMORY : " + beforeUsedMem + " MB : " + bytesToMegabytes(beforeUsedMem));

//		log.info("Bank account num : "+AppUtility.bankAccountNo+" Date : "+AppUtility.voucherDate);

//		appUtility.initializeAppData();
		/**
		 * 
		 * try {
		 * if(AppUtility.bankAccountNo!=null&&!"".equals(AppUtility.bankAccountNo)&&!"null".equals(AppUtility.bankAccountNo)&&
		 * AppUtility.voucherDate!=null&&!"".equals(AppUtility.voucherDate)&&!"null".equals(AppUtility.voucherDate))
		 * {
		 * brsStgService.updateStatusOfUnreconciledRecord("R",AppUtility.bankAccountNo,AppUtility.voucherDate);
		 * }else { List<String> bankAccList=bnkHdrService.getListOfBankAccountNumber();
		 * if(bankAccList!=null&&!bankAccList.isEmpty()) { log.info("bankAccList
		 * "+bankAccList.size()); for(String bankAccNm:bankAccList) {
		 * brsStgService.updateStatusOfUnreconciledRecord("R",bankAccNm,null); } } }
		 * 
		 * 
		 * 
		 * if(AppUtility.bankAccountNo!=null&&!"".equals(AppUtility.bankAccountNo)&&!"null".equals(AppUtility.bankAccountNo))
		 * { log.info("Execution as per the bank accoun number and voucher date");
		 * checkAndUpdateBrsDataDateAndStmtwise(AppUtility.bankAccountNo,
		 * AppUtility.voucherDate); }else { log.info("Execution for all bank account");
		 * List<String> bankAccountNumList=brsStgService.getBankAccountNumberList();
		 * if(bankAccountNumList!=null&&!bankAccountNumList.isEmpty()) {
		 * log.info("bankAccountNumList : "+bankAccountNumList.size()); for(String
		 * bankAccNum:bankAccountNumList) {
		 * checkAndUpdateBrsDataDateAndStmtwise(bankAccNum, AppUtility.voucherDate); } }
		 * 
		 * }
		 * 
		 * }catch(Exception e) { e.printStackTrace(); Long refId=null; String
		 * bankAcNo=""; String uniqueRefNo=""; BrsError error=util.getErrorDetails("Book
		 * Read", refId, bankAcNo, uniqueRefNo, "Exception");
		 * errorService.saveError(error); log.error(error.toString(), e); }
		 * 
		 **/

		/**
		 * List<BrsEbsStg> unreconciledEbsBookList =
		 * ebsService.getUnreconciledBookEntries("Book", AppConstants.UNRECONCILED); if
		 * (unreconciledEbsBookList != null && !unreconciledEbsBookList.isEmpty()) { for
		 * (BrsEbsStg ebs : unreconciledEbsBookList) { if (ebs.getUniqueRef() == null ||
		 * "".equals(ebs.getUniqueRef())) { BrsConsotUpdateStg brsUpdate =
		 * updateStgService.getBrsUpdateStgData(ebs.getCheqId()); List<BrsConsoldtStg>
		 * brsConsolStgList = brsStgService.getBrsRecordstoUpdateRefId(ebs.getCheqId(),
		 * ebs.getBankAcNo()); if (brsUpdate != null) { util.updateEbsUniqueRef(ebs,
		 * brsUpdate); }
		 * 
		 * if (brsConsolStgList != null && !brsConsolStgList.isEmpty()) { for
		 * (BrsConsoldtStg brs : brsConsolStgList) { util.updateUniqueRef(brs,
		 * brsUpdate); } brsStgService.updateBrsData(brsConsolStgList); } } }
		 * ebsService.saveEbsList(unreconciledEbsBookList); }
		 * 
		 **/
		log.info("     ");
		log.info(":::::::::::::::::Main job Process execution::::::::::::::::::: ");
		log.info("     ");
	}

	private void checkAndUpdateBrsDataDateAndStmtwise(String bankAccNum, String statementDate) {
		log.info("BANK ACCOUNT NUMBER : " + bankAccNum + " STATEMENT DATE : " + statementDate + " / " + new Date());
		BrsBankStmtHeaderStg bankHeader = bnkHdrService.getBankHeaderList(bankAccNum, statementDate);
		if (bankHeader != null) {
			String date = fmt.format(bankHeader.getStmtDate());
			log.info("BEFORE STMT DT : " + date + " STMT NUM : " + bankHeader.getStatementNo());

			Date stmtDt = new Date(bankHeader.getStmtDate().getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(stmtDt);
			cal.add(Calendar.DATE, -1);
			stmtDt = cal.getTime();
			String stmtDtInStr = fmt.format(stmtDt);

			log.info("After STMT DT : " + stmtDtInStr);

			BrsBankStmtHeaderStg previousDayBankHeader = bnkHdrService.getBankHeaderList(bankAccNum, stmtDtInStr);
			if (previousDayBankHeader != null) {
				log.info("Opening Balance : " + bankHeader.getCtrlBeginBalance() + " PREVIOUS DAY CLOSING BALANCE : "
						+ previousDayBankHeader.getCtrlEndBalance());
				if (previousDayBankHeader.getCtrlEndBalance().equals(bankHeader.getCtrlBeginBalance())) {

					List<BankStmtLineSummary> bankStmtLineSummary = bnkService.getBankStmtLineSummary(bankAccNum,
							bankHeader.getStatementNo());
					if (bankStmtLineSummary != null && !bankStmtLineSummary.isEmpty()) {
						Double totalCrAmount = 0d;
						Double totalDrAmount = 0d;

						for (BankStmtLineSummary obj : bankStmtLineSummary) {
							if (obj.getTxn_type().equalsIgnoreCase("CR")) {
								totalCrAmount = totalCrAmount + obj.getAmount();
							} else if (obj.getTxn_type().equalsIgnoreCase("DR")) {
								totalDrAmount = totalDrAmount + obj.getAmount();
							}
						}
						log.info("HDR TBL TOTAL CR AMOUNT : " + bankHeader.getCtrlTotalCr() + " TOTAL DR AMOUNT : "
								+ bankHeader.getCtrlTotalDr());
						log.info("LIN TBL TOTAL CR AMOUNT : " + totalCrAmount + " TOTAL DR AMOUNT : " + totalDrAmount);

						if (!bankHeader.getCtrlTotalCr().equals(totalCrAmount)
								|| !bankHeader.getCtrlTotalDr().equals(totalDrAmount)) {
							log.info("Bank Line and Bank HDR total CR/DR AMt not matched!!");
							brsStgService.updateBrsRecord(AppConstants.ERR_MSG_BANK_HDR_LINE_CRDR_NM, bankAccNum, date);
						}
					} else {
						log.info("Bank Line not found!!");
						brsStgService.updateBrsRecord(AppConstants.ERR_MSG_BANK_LINE_NF, bankAccNum, date);
					}
				} else {
					log.info("Bank Header Opening/Closing balance not matched!!");
					brsStgService.updateBrsRecord(AppConstants.ERR_MSG_BANK_HDR_OPN_BAL_CLO_BAl, bankAccNum, date);
				}
			} else {
				log.info("Previouse Day Bank Header not found!!");
				brsStgService.updateBrsRecord(AppConstants.ERR_MSG_BANK_HDR_NF_FOR_PREV_DAY, bankAccNum, date);
			}
		} else {
			log.info("Bank Header not found!!");
			brsStgService.updateBrsRecord(AppConstants.ERR_MSG_BANK_HDR_NF, bankAccNum, null);
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("     ");
		log.info("::::::::::::::::after job Process execution::::::::::::::::");
		log.info("     ");
//		log.info("Bank account num : "+AppUtility.bankAccountNo+" Date : "+AppUtility.voucherDate);

		afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		log.info("AFTER USED MEMORY : " + afterUsedMem + " MB : " + bytesToMegabytes(afterUsedMem));

		actualMemUsed = afterUsedMem - beforeUsedMem;

		log.info("ACTUAL USED MEMORY : " + actualMemUsed + " MB : " + bytesToMegabytes(actualMemUsed));

		

		
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Fetching Data ::::");
			statusJobList = brsStgService.getStatusJob();
//			for (BatchStatusInterf b : statusJobList) {
//				System.out.println("ACC NO : " + b.getBank_account_no());
//			}
			
			StringBuilder msgBody= new StringBuilder();
			
		
			msgBody.append("<html>" +
						"<body>" +
					"<table>" +
						"<tr style=\"background-color:#8C0000; color: #fff; font-family: Arial; font-size:14px; font-weight: bold;\">" +
					"<th style=\"padding: 10px;\">Bank Account Number</th>" +
						"<th style=\"padding: 10px;\">Vouchar Date</th>" +
					"<th style=\"padding: 10px;\">Process Flag</th>" +
						"<th style=\"padding: 10px;\">Status</th>" +
					"<th style=\"padding: 10px;\">Error Mesg</th>" +
						"</tr>");
			
			
		    for(BatchStatusInterf bitr : statusJobList){
		    	
		    	msgBody.append("<tr style=\"font-family: Arial; font-size: 12px;\"><td style=\"padding: 10px;\">");
		       
		        msgBody.append(bitr.getBank_account_no())
		           .append("</td><td style=\"padding: 10px;\">")
		           .append(bitr.getVoucherdate())
		           .append("</td><td style=\"padding: 10px;\">")
		           .append(bitr.getProcess_flag())
		           .append("</td><td style=\"padding: 10px;\">")
		           .append(bitr.getStatus())
		           .append("</td><td style=\"padding: 10px;\">")
		           .append(bitr.getError_mesg());
		        
		        msgBody.append("</td></tr>");
		       

		    }
		    
		    msgBody.append("</table>" +
	                "</body>" +
	                "</html>");
		    
		    
		    String messageBody = msgBody.toString();
			
			EmailDetails emailobj=new EmailDetails();
			emailobj.setRecipient("pramods.shinde@cloverinfotech.com"); // 
			System.out.println("Mail Sending ::::::::::::::::");
			emailobj.setSubject("Batch running status");
			emailobj.setMsgBody(messageBody);
//			emailobj.setAttachment(null);
			System.out.println("MessageBody ::::::::::::: "+messageBody);
			try {
				sendMail(emailobj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			log.info("JOB STATUS IS NOT COMPLETED");
		}
		

//		file = createCSV();
		System.out.println("PATH ===========" + file.toString());

//		String str = sendMail();
	//	System.out.println("RESPONSE ===== " + str);

		/*
		 * if (file.exists()) { String str = sendMail();
		 * System.out.println("RESPONSE ===== " + str); } else {
		 * System.out.println("FILE NOT EXISTS....."); }
		 */

		log.info("-----------------------------------------");

//		appUtility.resetAppData();
		/**
		 * List<BrsBankStatementLineStg> finalBrsBnkStmtList = new
		 * ArrayList<BrsBankStatementLineStg>(); List<BrsEbsStg>
		 * finalEbsListForBankStmt=new ArrayList<BrsEbsStg>();
		 * 
		 * if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
		 * 
		 * List<BrsBankStatementLineStg> brsBnkStmtList =
		 * bnkService.getUnprocessedRecords("N");
		 * 
		 * if (brsBnkStmtList != null && !brsBnkStmtList.isEmpty()) {
		 * 
		 * log.info("UnProcessed Bank statmenets : "+brsBnkStmtList.size());
		 * 
		 * Map<String, List<BrsBankStatementLineStg>> mpBnk = new HashMap<>();
		 * 
		 * for (BrsBankStatementLineStg brsbnk : brsBnkStmtList) { String bankAcNum =
		 * brsbnk.getBankAcNo(); String uniqueRef = brsbnk.getUniqueRef();
		 * 
		 * if(mpBnk.containsKey(bankAcNum.concat("/").concat(uniqueRef))) {
		 * mpBnk.get(bankAcNum.concat("/").concat(uniqueRef)).add(brsbnk); }else {
		 * List<BrsBankStatementLineStg> list=new ArrayList<BrsBankStatementLineStg>();
		 * list.add(brsbnk); mpBnk.put(bankAcNum.concat("/").concat(uniqueRef), list); }
		 * }
		 * 
		 * log.info("Unique number wise bank statements "+mpBnk.size());
		 * Set<Map.Entry<String, List<BrsBankStatementLineStg>>> ess = mpBnk.entrySet();
		 * for (Map.Entry<String, List<BrsBankStatementLineStg>> it : ess) {
		 * 
		 * String key = it.getKey(); String[] splitArry = key.split("/"); String
		 * bankAcNum = splitArry[0]; String uniqueRef = splitArry[1];
		 * 
		 * List<BrsBankStatementLineStg> brsBnkStmtList2 = new
		 * ArrayList<BrsBankStatementLineStg>();
		 * if(it.getValue()!=null&&!it.getValue().isEmpty()) {
		 * brsBnkStmtList2.addAll(it.getValue()); }
		 * 
		 * String unmatchNum = util.getBnkcRefId(); log.info("UNMATCH NUMBER in After
		 * Batch process : {}", unmatchNum);
		 * 
		 * List<BrsEbsStg> ebsListForBankStmt=new ArrayList<BrsEbsStg>();
		 * ebsListForBankStmt=util.getBrsEBSListForBank(brsBnkStmtList2,
		 * AppConstants.UNRECONCILED, "Bank Contra", "", unmatchNum);
		 * 
		 * if(brsBnkStmtList2!=null&&!brsBnkStmtList2.isEmpty()) {
		 * finalBrsBnkStmtList.addAll(brsBnkStmtList2); }
		 * 
		 * if(ebsListForBankStmt!=null&&!ebsListForBankStmt.isEmpty()) {
		 * finalEbsListForBankStmt.addAll(ebsListForBankStmt); }
		 * 
		 * } }
		 * 
		 * if(finalBrsBnkStmtList!=null&&!finalBrsBnkStmtList.isEmpty()) {
		 * bnkService.saveBankList(finalBrsBnkStmtList); }
		 * 
		 * if(finalEbsListForBankStmt!=null&&!finalEbsListForBankStmt.isEmpty()) {
		 * ebsService.saveEbsList(finalEbsListForBankStmt); } }
		 * 
		 **/

//		AppUtility.bankAccountNo=null;
//		AppUtility.voucherDate=null;
//		
//		log.info("Bank account num : "+AppUtility.bankAccountNo+" Date : "+AppUtility.voucherDate);

		log.info("     ");
		log.info(":::::::::::::::::::::::job execution completed::::::::::::::::::::::::::");
		log.info("     ");
	}

	public File createCSV() {
		// LocalDateTime dateTime = LocalDateTime.now();
		// String fileName = "statusJob" + dateTime.toString() + ".csv";
		String fileName = "statusJob.csv";
		File file = new File("C:\\Users\\PramodShinde\\Downloads\\BRS\\src\\main\\webapp\\files\\" + fileName);
		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);

			// adding header to csv
			// String[] header = { "Name", "Class", "Marks" };
			String[] header = { "bank_account_no", "voucherdate", "process_flag", "status", "error_mesg" };
			writer.writeNext(header);

			for (int i = 0; i < statusJobList.size(); i++) {
				String[] data = { statusJobList.get(i).getBank_account_no(), statusJobList.get(i).getVoucherdate(),
						statusJobList.get(i).getProcess_flag(), statusJobList.get(i).getStatus(),
						statusJobList.get(i).getError_mesg() };
				writer.writeNext(data);
			}

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public String sendMail(EmailDetails details) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
//			String html = templateEngine.process("job-status.html", null);
//			mimeMessageHelper.setText(html, true);
			
			mimeMessageHelper.setText(details.getMsgBody(), true);
			mimeMessageHelper.setSubject(details.getSubject());

			
//			ClassPathResource path=new ClassPathResource("C:\\Users\\PramodShinde\\Downloads\\BRS\\src\\main\\resources\\templates");
			
			
//			// Adding the attachment
//			FileSystemResource filesr = new FileSystemResource(ResourceUtils.getFile("job-status.html"));

			//

			// Sending the mail
			javaMailSender.send(mimeMessage);
			System.out.println("Sent:::::::::::");
			return "Mail sent Successfully";
			
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}


	public String sendMail() {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo("");
			mimeMessageHelper.setText("Status Job Text");
			mimeMessageHelper.setSubject("Status Job Subject");

			// Adding the attachment
			FileSystemResource filesr = new FileSystemResource(file);

			mimeMessageHelper.addAttachment(filesr.getFilename(), filesr);

			// Sending the mail
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}

	
}
