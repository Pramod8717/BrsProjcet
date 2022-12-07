package com.brs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brs.entities.BrsBankStmtHeaderStg;

@Repository
public interface BrsBankStmtHeaderRepository extends JpaRepository<BrsBankStmtHeaderStg, Long> {
	// date_sub(curdate(),interval 81 day)
	// SQL FORMAT
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_HDR_STG b where bank_account_num = ? and statement_date= to_date(?,'DD-MM-YY') and loader_process_id =(select max(loader_process_id) from UAT_XXCD_BANK_STMT_HDR_STG where BANK_ACCOUNT_NUM = b.BANK_ACCOUNT_NUM and statement_date=b.statement_date ) ", nativeQuery = true)
//	BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum,String stmtDate);
	
	// MYSQL FORMAT
	@Query(value = "select * from UAT_XXCD_BANK_STMT_HDR_STG b where bank_account_num = ? and statement_date= str_to_date(?, current_date - 48,'%dd-%mm-%YY') and loader_process_id =(select max(loader_process_id) from UAT_XXCD_BANK_STMT_HDR_STG where BANK_ACCOUNT_NUM = b.BANK_ACCOUNT_NUM and statement_date=b.statement_date ) ", nativeQuery = true)
	BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum,String stmtDate);
	
	
	
	// SQL FORMAT
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_HDR_STG b where bank_account_num = ? and statement_date= to_date(to_char(sysdate-48)) and loader_process_id =(select max(loader_process_id) from UAT_XXCD_BANK_STMT_HDR_STG where BANK_ACCOUNT_NUM = b.BANK_ACCOUNT_NUM and statement_date=b.statement_date ) ", nativeQuery = true)
//	BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum);
	
	// MYSQL FORMAT
	@Query(value = "select * from UAT_XXCD_BANK_STMT_HDR_STG b where bank_account_num = ? and statement_date= str_to_date(current_date - 48,'%dd-%mm-%YY') and loader_process_id =(select max(loader_process_id) from UAT_XXCD_BANK_STMT_HDR_STG where BANK_ACCOUNT_NUM = b.BANK_ACCOUNT_NUM and statement_date=b.statement_date ) ", nativeQuery = true)
	BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum);
	
	
	// SQL FORMAT
//	@Query(value = "select DISTINCT a.bank_account_num from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO order by a.bank_account_num ", nativeQuery = true)
//	List<String> getListOfBankAccountNumber();
	
	
	
	// MYSQL FORMAT
	@Query(value = "select DISTINCT a.bank_account_num from UAT_XXCD_BANK_STMT_HDR_STG a,uat_xxcd_bank_stmt_line_stg b,UAT_XXCD_BRS_CONSOLDT_STG c where a.bank_account_num is not null and a.bank_account_num=b.bank_account_num and b.bank_account_num=c.BANK_ACCOUNT_NO order by a.bank_account_num ", nativeQuery = true)
	
	List<String> getListOfBankAccountNumber();
	
	
}
