package com.brs.config;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.brs.batch.BrsDataItemProcessor;
import com.brs.batch.BrsDataItemReader;
import com.brs.batch.BrsDataItemWriter;
import com.brs.batch.JobExecListener;
import com.brs.batch.StepExecListner;
import com.brs.batch.bnkstmthdr.BankStmtHdrProcessor;
import com.brs.batch.bnkstmthdr.BankStmtHdrReader;
import com.brs.batch.bnkstmtline.BankStmtLineProcessor;
import com.brs.batch.nullrecordreader.BrsNullBankReader;
import com.brs.batch.resetunrecon.NoOpItemWriter;
import com.brs.batch.resetunrecon.ResetUnreconReader;
import com.brs.batch.unreconreader.BrsDataUnreconItemReader;
import com.brs.batch.unreconreader.BrsDataUnreconWriter;
import com.brs.batch.updateunqref.UpdateUnqRefProcessor;
import com.brs.batch.updateunqref.UpdateUnqRefReader;
import com.brs.entities.BrsBankStatementLineStg;
import com.brs.entities.BrsBankStmtHeaderStg;
import com.brs.entities.BrsConsoldtStg;
import com.brs.util.AppUtility;
import com.brs.util.BankStmtLineMapper;
import com.brs.util.BrsNewRecMapper;
import com.brs.util.BrsReaderUtility;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Bean
	@StepScope
	public ItemReader<BrsConsoldtStg> reader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 5] Process_New_Records_Readder : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		
		/*SQL Quries
		 * 
		 * String
		 * fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
		 * +
		 * "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		 * +
		 * "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		 * +
		 * "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
		 * +
		 * "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
		 * + "FROM uat_xxcd_brs_consoldt_stg " +
		 * "where process_flag='N' and VOUCHERDATE = to_date(to_char(sysdate-80))) ";
		 * 
		 */
		
		
		// Mysql Quries
		System.out.println("Step....11");
		String fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
	    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
	    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
	    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER()"
	    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T "
	    		+ "FROM uat_xxcd_brs_consoldt_stg "
	    		+ "where process_flag='N' and VOUCHERDATE = date_sub(curdate(),interval 81 day))AS T ";
		
		
		
		// SQL FORMAT
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//			
//		    fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
//		    		+ "FROM uat_xxcd_brs_consoldt_stg "
//		    		+ "where process_flag='N' and BANK_ACCOUNT_NO ='"+bankAccountNo+"' AND VOUCHERDATE = TO_DATE('"+date+"', 'DD-MM-YY')) ";
//			
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			log.info("reader : Manual Execution with voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//			
//			fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//			    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//			    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//			    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//			    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
//			    		+ "FROM uat_xxcd_brs_consoldt_stg "
//			    		+ "where process_flag='N' AND VOUCHERDATE = TO_DATE('"+date+"', 'DD-MM-YY')) ";
//		}
		
		//MYSQL FORMAT
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			
			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			System.out.println("Step....12");
		    fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description,dramt,cramt, "
		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T "
		    		+ "FROM uat_xxcd_brs_consoldt_stg "
		    		+ "where process_flag='N' and BANK_ACCOUNT_NO ='"+bankAccountNo+"' AND VOUCHERDATE = date_sub(curdate(),interval 81 day))AS T ";
			
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			log.info("reader : Manual Execution with voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			System.out.println("Step....13");
			fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
			    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
			    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
			    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
			    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T "
			    		+ "FROM uat_xxcd_brs_consoldt_stg "
			    		+ "where process_flag='N' AND VOUCHERDATE = date_sub(curdate(),interval 81 day))AS T ";
		}
		
		
		
		
		
		return new JdbcPagingItemReaderBuilder<BrsConsoldtStg>()
				.name("Processing new records")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.whereClause("where T = 1 ")
				.sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
				.rowMapper(new BrsNewRecMapper()).build();
	}
	
	
	@Bean
	public BrsDataItemProcessor processor() {
		return new BrsDataItemProcessor();
	}

	@Bean
	public BrsDataItemWriter writer() {
		return new BrsDataItemWriter();
	}
	
	@Bean
	public BrsDataUnreconWriter unreconwriter() {
		return new BrsDataUnreconWriter();
	}
	
	@Bean
	public JobExecListener listener() {
		return new JobExecListener();
	}
	
	@Bean
	public StepExecListner stepListener() {
		return new StepExecListner();
	}
	
	@Bean
	@StepScope
	public ItemReader<BrsReaderUtility> resetUnreconReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 1] Reste_Unrecon_Record_Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		// SQL Alies Queris Formate
		
		/*
		 * String
		 * fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo," +
		 * "to_char(a.statement_date,'DD-MM-YY') as executionDate" + ", 'R' as type " +
		 * "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
		 * +
		 * "where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
		 * +
		 * "and a.process_flag='N' and a.statement_date <to_date(to_char(sysdate-80)) order by a.bank_account_num ) "
		 * ;
		 */
		
		
		// Mysql Alies Queris Formate
		
		System.out.println("Start 1...............");
		String fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo," +
		  "date_format(a.statement_date,'%d-%m-%Y') as executionDate" + ", 'R' as type " +
		  "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
		  +"where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
		  +"and a.process_flag='N' and a.statement_date < date_sub(curdate(),interval 81 day) order by a.bank_account_num ) AS T ";
		
		System.out.println("Start 2...............");
		// SQL FORMAT
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//			fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo,"
//					+ "to_char(a.statement_date,'DD-MM-YY') as executionDate"
//					+ ", 'R' as type "
//					+ "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
//					+ "where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
//					+ "and a.process_flag='N' and BANK_ACCOUNT_NO = '"+bankAccountNo+"' and  a.statement_date < TO_DATE('"+date+"','DD-MM-YY') order by a.bank_account_num )  ";
//		
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//
//			fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo,"
//					+ "to_char(a.statement_date,'DD-MM-YY') as executionDate"
//					+ ", 'R' as type "
//					+ "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
//					+ "where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
//					+ "and a.process_flag='N' and  a.statement_date < TO_DATE('"+date+"','DD-MM-YY') order by a.bank_account_num )  ";
//		
//		}
		
		// MYSQL FORMAT
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			System.out.println("Start 3...............");
			fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo,"
					+ "date_sub(curdate(),interval 81 day) as executionDate"
					+ ", 'R' as type "
					+ "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
					+ "where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
					+ "and a.process_flag='N' and BANK_ACCOUNT_NO = '"+bankAccountNo+"' and  a.statement_date < date_sub(curdate(),interval 81 day) order by a.bank_account_num )AS T";
			System.out.println("Start 45...............");
			
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			System.out.println("Start 4...............");
			fromClause="From (select DISTINCT a.bank_account_num as bankAccountNo,"
					+ "date_sub(curdate(),interval 81 day) as executionDate"
					+ ", 'R' as type "
					+ "from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c "
					+ "where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO "
					+ "and a.process_flag='N' and  a.statement_date < date_sub(curdate(),interval 81 day) order by a.bank_account_num )AS T";
		
		}
		
		return new JdbcPagingItemReaderBuilder<BrsReaderUtility>()
				.name("Reset unreconciled record status")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.maxItemCount(20000)
				.sortKeys(Collections.singletonMap("executionDate", Order.DESCENDING))
				.beanRowMapper(BrsReaderUtility.class).build();
	}
	
	@Bean
	public NoOpItemWriter noOpWriter() {
		return new NoOpItemWriter();
	}
	
	@Bean
	@StepScope
	public ItemReader<BrsConsoldtStg> unreconReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 6] Unrecon_Records_Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		// SQL FORMAT
//		String fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//	    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//	    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//	    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//	    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
//	    		+ "FROM uat_xxcd_brs_consoldt_stg "
//	    		+ "where process_flag='R' and VOUCHERDATE < to_date(to_char(sysdate-80))) ";
//		
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//			
//		    fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
//		    		+ "FROM uat_xxcd_brs_consoldt_stg "
//		    		+ "where process_flag='R' and BANK_ACCOUNT_NO ='"+bankAccountNo+"' AND VOUCHERDATE < TO_DATE('"+date+"', 'DD-MM-YY')) ";
//			
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS duplicatecount "
//		    		+ "FROM uat_xxcd_brs_consoldt_stg "
//		    		+ "where process_flag='R' AND VOUCHERDATE < TO_DATE('"+date+"', 'DD-MM-YY')) ";
		
		
		
		// MYSQL FORMAT
		
		String fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
	    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
	    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
	    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
	    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T "
	    		+ "FROM uat_xxcd_brs_consoldt_stg "
	    		+ "where process_flag='R' and VOUCHERDATE < date_sub(curdate(),interval 81 day))AS T ";
		System.out.println("Step....14");
		
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			
			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			System.out.println("Step....15");
		    fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T "
		    		+ "FROM uat_xxcd_brs_consoldt_stg "
		    		+ "where process_flag='R' and BANK_ACCOUNT_NO ='"+bankAccountNo+"' AND VOUCHERDATE < date_sub(curdate(),interval 81 day))AS T ";
			
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			System.out.println("Step....16");
			fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
		    		+ "OVER(PARTITION BY unique_reference,bank_account_no ORDER BY unique_reference,bank_account_no) AS T"
		    		+ "FROM uat_xxcd_brs_consoldt_stg "
		    		+ "where process_flag='R' AND VOUCHERDATE < date_sub(curdate(),interval 81 day)AS T ";
			
		}
		
		return new JdbcPagingItemReaderBuilder<BrsConsoldtStg>()
				.name("Processing unreconciled records")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.whereClause("where T = 1 ")
				.sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
				.rowMapper(new BrsNewRecMapper()).build();
	}
	
	@Bean
	@StepScope
	public ItemReader<BrsReaderUtility> bnkStmtHdrReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 2] Bank_Stmt_Hdr_Validation_Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		// SQL FORMAT
//		String fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
//				+ "to_char(voucherdate,'DD-MM-YY') as executionDate, 'V' as type "
//				+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO is not null and voucherdate =to_date(to_char(sysdate-80))) ";
//		
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//		    fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
//					+ "to_char(voucherdate,'DD-MM-YY') as executionDate, 'V' as type "
//		    		+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO = '"+bankAccountNo+"' and VOUCHERDATE = TO_DATE('"+date+"','DD-MM-YY'))";
//		
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
//					+ "to_char(voucherdate,'DD-MM-YY') as executionDate, 'V' as type "
//		    		+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO is not null and VOUCHERDATE = TO_DATE('"+date+"','DD-MM-YY'))";
//		
//		}
//		
//		return new JdbcPagingItemReaderBuilder<BrsReaderUtility>()
//				.name("Bank stmt hdr validation reader")
//				.dataSource(dataSource)
//				.selectClause("select * ")
//				.fromClause(fromClause)
//				.maxItemCount(20000)
//				.sortKeys(Collections.singletonMap("executionDate", Order.DESCENDING))
//				.beanRowMapper(BrsReaderUtility.class).build();
		
		// MYSQL FORMAT
		String fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
				+ "date_sub(curdate(),interval 81 day) as executionDate, 'V' as type "
				+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO is not null and voucherdate = date_sub(curdate(),interval 81 day))AS T ";
		System.out.println("Start 5...............");
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
		    fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
					+ "date_sub(curdate(),interval 81 day) as executionDate, 'V' as type "
		    		+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO = '"+bankAccountNo+"' and VOUCHERDATE = date_sub(curdate(),interval 81 day))AS T";
		
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			System.out.println("Start 6...............");
			fromClause="From (select DISTINCT BANK_ACCOUNT_NO as bankAccountNo,"
					+ "date_sub(curdate(),interval 81 day) as executionDate, 'V' as type "
		    		+ "from UAT_XXCD_BRS_CONSOLDT_STG where BANK_ACCOUNT_NO is not null and VOUCHERDATE = date_sub(curdate(),interval 81 day))AS T";
		
		}
		
		return new JdbcPagingItemReaderBuilder<BrsReaderUtility>()
				.name("Bank stmt hdr validation reader")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.maxItemCount(20000)
				.sortKeys(Collections.singletonMap("executionDate", Order.DESCENDING))
				.beanRowMapper(BrsReaderUtility.class).build();
	}
	
	@Bean
	public BankStmtHdrProcessor bnkStmtHdrProcessor() {
		return new BankStmtHdrProcessor();
	}
		
	@Bean
	@StepScope
	public ItemReader<BrsConsoldtStg> updateUnqRefReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 3] Update_Unique_Reference_Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		// SQL FOEMAT
//		String fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//	    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//	    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//	    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//	    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS duplicatecount "
//	    		+ "FROM uat_xxcd_brs_consoldt_stg where bank_account_no is not null and unique_reference is null and cheq_id is not null "
//	    		+ "and process_flag IN ('N','R') and VOUCHERDATE <= to_date(to_char(sysdate-80))) ";
//		
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//		    fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//		    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS duplicatecount "
//		    		+ "FROM uat_xxcd_brs_consoldt_stg where unique_reference is null and cheq_id is not null "
//		    		+ "and process_flag IN ('N','R') and bank_account_no='"+bankAccountNo+"' and VOUCHERDATE <= to_date('"+date+"','DD-MM-YY')) ";
//			
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			fromClause="from (SELECT bank_account_no,system,voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
//		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
//		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
//		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by, orclglcode,ROW_NUMBER() "
//		    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS duplicatecount "
//		    		+ "FROM uat_xxcd_brs_consoldt_stg where bank_account_no is not null and unique_reference is null and cheq_id is not null "
//		    		+ "and process_flag IN ('N','R') and VOUCHERDATE <= to_date('"+date+"','DD-MM-YY')) ";
		
		
			// MYSQL FORMAT
		String fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
	    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
	    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
	    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
	    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS T "
	    		+ "FROM uat_xxcd_brs_consoldt_stg where bank_account_no is not null and unique_reference is null and cheq_id is not null "
	    		+ "and process_flag IN ('N','R') and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T ";
		System.out.println("Step....17");
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			
		    fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
		    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS T "
		    		+ "FROM uat_xxcd_brs_consoldt_stg where unique_reference is null and cheq_id is not null "
		    		+ "and process_flag IN ('N','R') and bank_account_no='"+bankAccountNo+"' and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T ";
			
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			System.out.println("Step....18");
			fromClause="from (SELECT bank_account_no,'system',voucherid,voucherdate,valuedate,branchid,proposalid,agreementno,gl_code,gl_description, dramt, cramt, "
		    		+ "narration,stage_id, cheq_id, receipt_no,receiptno,chequenum,paymentmode,utr_no,payslipno,cheque_status, realizationdate,process_flag, "
		    		+ "source_system,unique_reference,id,status,error_mesg,je_batch_id, period_name,je_header_id,je_line_num,chart_of_accounts_id,functional_currency_code, "
		    		+ "code_combination_id,creation_date,created_by,last_update_date, last_update_by,ROW_NUMBER() "
		    		+ "OVER(PARTITION BY cheq_id,bank_account_no ORDER BY cheq_id,bank_account_no) AS T "
		    		+ "FROM uat_xxcd_brs_consoldt_stg where bank_account_no is not null and unique_reference is null and cheq_id is not null "
		    		+ "and process_flag IN ('N','R') and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T ";
		}
		
		return new JdbcPagingItemReaderBuilder<BrsConsoldtStg>()
				.name("Updating unique reference")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.whereClause("where T = 1 ")
				.sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
				.rowMapper(new BrsNewRecMapper()).build();
	}
	
	@Bean
	public UpdateUnqRefProcessor updateUnqRefProcessor() {
		return new UpdateUnqRefProcessor();
	}
		
	@Bean
	@StepScope
	public ItemReader<BrsConsoldtStg> brsNullBankReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 4] BRS_Null_Record_Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		// SQL FORMAT
//		String fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where bank_account_no is null and process_flag IN ('N','R') and  VOUCHERDATE <= TO_DATE(to_char(sysdate-80)) "
//				+ "Union "
//				+ "select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null and process_flag IN ('N','R') and VOUCHERDATE <= TO_DATE(to_char(sysdate-80))) ";
//		
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//		    
//			fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null "
//					+ "and process_flag IN ('N','R') and bank_account_no='"+bankAccountNo+"' and VOUCHERDATE <= TO_DATE('"+date+"','DD-MM-YY'))";
//		
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//			fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where bank_account_no is null and process_flag IN ('N','R') and  VOUCHERDATE <= TO_DATE('"+date+"','DD-MM-YY') "
//					+ "Union "
//					+ "select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null and process_flag IN ('N','R') and VOUCHERDATE <= TO_DATE('"+date+"','DD-MM-YY') ) ";
//			
		// MYSQL FORMAT
		String fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where bank_account_no is null and process_flag IN ('N','R') and  VOUCHERDATE <= date_sub(curdate(),interval 81 day)AS T "
				+ "Union all"
				+ "select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null and process_flag IN ('N','R') and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T ";
		
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
		    
			fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null "
					+ "and process_flag IN ('N','R') and bank_account_no='"+bankAccountNo+"' and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T";
		
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			
			fromClause="From (select * from UAT_XXCD_BRS_CONSOLDT_STG where bank_account_no is null and process_flag IN ('N','R') and  VOUCHERDATE <= date_sub(curdate(),interval 81 day)AS T "
					+ "Union all"
					+ "select * from UAT_XXCD_BRS_CONSOLDT_STG where unique_reference is null and cheq_id is null and process_flag IN ('N','R') and VOUCHERDATE <= date_sub(curdate(),interval 81 day))AS T ";
			
		}
		
		return new JdbcPagingItemReaderBuilder<BrsConsoldtStg>()
				.name("BRS null record reader")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
				.rowMapper(new BrsNewRecMapper()).build();
	}
	
	@Bean
	@StepScope
	public ItemReader<BrsBankStatementLineStg> bankContraReader(@Value("#{jobParameters['bankAccountNo']}")String bankAccountNo,@Value("#{jobParameters['date']}")String date) {
		
		log.info("[STEP READER - 7] Bank contra Reader : bankAccountNo / date - "+bankAccountNo+" / "+date+" | "+new Date()+"| Thread=[name="+Thread.currentThread().getName()+" | id="+Thread.currentThread().getId()+"]");
		
		
		// SQL FORMAT
//		String fromClause="from ( select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
//				+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
//				+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS duplicatecount "
//				+ "from uat_xxcd_bank_stmt_line_stg "
//				+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and TXN_DATE=to_date(to_char(sysdate-80)) ) ";
//		
//		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			
//			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//			fromClause="from ( select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
//					+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
//					+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS duplicatecount "
//					+ "from uat_xxcd_bank_stmt_line_stg "
//					+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and BANK_ACCOUNT_NUM ='"+bankAccountNo+"' and TXN_DATE=TO_DATE('"+date+"', 'DD-MM-YY'))";
//		
//		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
//			log.info("reader : Manual Execution with voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//			
//			fromClause="from ( select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
//					+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
//					+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS duplicatecount "
//					+ "from uat_xxcd_bank_stmt_line_stg "
//					+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and TXN_DATE=TO_DATE('"+date+"', 'DD-MM-YY'))";
//		
		// MYSQL FORMAT
		String fromClause="from ( select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
				+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
				+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS T "
				+ "from uat_xxcd_bank_stmt_line_stg "
				+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and TXN_DATE=date_sub(curdate(),interval 81 day)) AS T ";
		System.out.println("Error CHK....3...");
		if((bankAccountNo!=null&&!"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			
			log.info("reader : Manual Execution with bank account / voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			fromClause="from (select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
					+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
					+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS T "
					+ "from uat_xxcd_bank_stmt_line_stg "
					+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and BANK_ACCOUNT_NUM ='"+bankAccountNo+"' and TXN_DATE=date_sub(curdate(),interval 81 day))AS T";
			System.out.println("Error CHK....1..");
		}else if((bankAccountNo==null||"".equals(bankAccountNo))&&(date!=null&&!"".equals(date))) {
			log.info("reader : Manual Execution with voucher date"+bankAccountNo+" / "+date+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			
			fromClause="from ( select BANK_ACCOUNT_NUM, STATEMENT_NUMBER,LINE_NUMBER, TXN_TYPE, TXN_DATE, BANK_TRX_NUMBER,AMOUNT,EFFECTIVE_DATE,TXN_TEXT,CUSTOMER_TEXT,BANK_ACCOUNT_TEXT,LINE_ID,CREATION_DATE, LAST_UPDATE_DATE, "
					+ "PROCESS_FLAG,STG_INT_PROCESS_ID, LOADER_PROCESS_ID, ERROR_MESSAGE,UNIQUE_REFERENCE,MATCH_REFERENCE,UNMATCH_REFERENCE,PROCESS_STATUS,ERROR_MESG,ID,ROW_NUMBER() "
					+ "OVER(PARTITION BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM ORDER BY UNIQUE_REFERENCE,BANK_ACCOUNT_NUM) AS T"
					+ "from uat_xxcd_bank_stmt_line_stg "
					+ "where UNIQUE_REFERENCE is not null and BANK_ACCOUNT_NUM is not null and PROCESS_FLAG='N' and TXN_DATE=date_sub(curdate(),interval 81 day))AS T";
			System.out.println("Error CHK....2...");
		}
		
		return new JdbcPagingItemReaderBuilder<BrsBankStatementLineStg>()
				.name("Processing bank contra")
				.dataSource(dataSource)
				.selectClause("select * ")
				.fromClause(fromClause)
				.whereClause("where T = 1 ")
				.sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
				.rowMapper(new BankStmtLineMapper()).build();
	}
	
	@Bean
	public BankStmtLineProcessor bankContraProcessor() {
		return new BankStmtLineProcessor();
	}
		
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(64);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(64);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }
	
	
	
	@Bean
	public Job createJob() {
		log.info("INSIDE JOB EXECUTION...!!!");
			
		return jobBuilderFactory.get("MyJob").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(resetStatusOfUnProcessedRecords())
				.next(checkAndUpdateBrsDataDateAndStmtwise())
				.next(updateBrsRecordsWithNoUnqRef())
				.next(updateNullValueRecord())
				.next(processNewRecords())
				.next(processUnreconciledRecords())
				.next(processBankContraRecords()).end().build();
	}

	@Bean
	public Step resetStatusOfUnProcessedRecords() {
		log.info("STEP - 1 !!!");
		return stepBuilderFactory.get("Reseting_Status").<BrsReaderUtility, List<Object>>chunk(1000).reader(resetUnreconReader(null,null))
				.processor(bnkStmtHdrProcessor()).writer(noOpWriter()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}
	
	@Bean
	public Step checkAndUpdateBrsDataDateAndStmtwise() {
		log.info("STEP - 2 !!!");
		return stepBuilderFactory.get("Validating_Records").<BrsReaderUtility, List<Object>>chunk(1000).reader(bnkStmtHdrReader(null,null))
				.processor(bnkStmtHdrProcessor()).writer(noOpWriter()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}
	
	
	@Bean
	public Step updateBrsRecordsWithNoUnqRef() {
		log.info("STEP - 3 !!!");
		return stepBuilderFactory.get("Updating_Unique_Reference").<BrsConsoldtStg, List<BrsConsoldtStg>>chunk(1000).reader(updateUnqRefReader(null,null))
				.processor(updateUnqRefProcessor()).writer(writer()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}

	
	@Bean
	public Step updateNullValueRecord() {
		log.info("STEP - 4 !!!");
		return stepBuilderFactory.get("Updating_Null_Records").<BrsConsoldtStg, List<BrsConsoldtStg>>chunk(1000).reader(brsNullBankReader(null,null))
				.processor(processor()).writer(writer()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}


	@Bean
	public Step processNewRecords() {
		log.info("STEP - 5 !!!");
		return stepBuilderFactory.get("Processing_New_Records").<BrsConsoldtStg, List<BrsConsoldtStg>>chunk(1000).reader(reader(null,null))
				.processor(processor()).writer(writer()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}
	
	@Bean
	public Step processUnreconciledRecords() {
		log.info("STEP - 6 !!!");
		return stepBuilderFactory.get("Processing_Unreconciled_Record").<BrsConsoldtStg, List<BrsConsoldtStg>>chunk(1000).reader(unreconReader(null,null))
				.processor(processor()).writer(unreconwriter()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}
	
	@Bean
	public Step processBankContraRecords() {
		log.info("STEP - 7 !!!");
		return stepBuilderFactory.get("Processing_Bank_Contra_Record").<BrsBankStatementLineStg, List<Object>>chunk(1000).reader(bankContraReader(null,null))
				.processor(bankContraProcessor()).writer(noOpWriter()).taskExecutor(taskExecutor()).listener(stepListener()).build();
	}
	
	
}
