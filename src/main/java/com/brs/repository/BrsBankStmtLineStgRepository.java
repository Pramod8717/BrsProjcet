package com.brs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brs.entities.BrsBankStatementLineStg;
import com.brs.util.BankStmtLineSummary;

@Repository
public interface BrsBankStmtLineStgRepository extends JpaRepository<BrsBankStatementLineStg, Long> {

	
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = ?", nativeQuery = true)
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 a where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = ? and loader_process_id =(select max(loader_process_id) from uat_xxcd_bank_stmt_line_stg_1 where BANK_ACCOUNT_NUM = a.BANK_ACCOUNT_NUM and unique_reference = a.unique_reference)", nativeQuery = true)
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG a where unique_reference = ? and BANK_ACCOUNT_NUM = ? and process_flag = ? and loader_process_id =(select max(loader_process_id) from uat_xxcd_bank_stmt_line_stg where BANK_ACCOUNT_NUM = a.BANK_ACCOUNT_NUM and unique_reference = a.unique_reference)", nativeQuery = true)
	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo, Long loaderProcessId, String processFlag, String bankAcNo1, Long loaderProcessId1);

//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 where process_flag = ?", nativeQuery = true)
	
	//SQL FORMAT
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where process_flag = ? txn_date = to_date(to_char(sysdate-48)) ", nativeQuery = true)
//	List<BrsBankStatementLineStg> getUnprocessedRecords(String processFlag);
//	
	
	//MYSQL FORMAT
	
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where process_flag = ? txn_date = str_to_date(current_date - 48,'%dd-%mm-%YY') ", nativeQuery = true)
	List<BrsBankStatementLineStg> getUnprocessedRecords(String processFlag);
			
	//SQL FORMAT	
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where process_flag = ? and BANK_ACCOUNT_NUM = ? and txn_date= TO_DATE(?, 'DD-MM-YY') ", nativeQuery = true)
//	List<BrsBankStatementLineStg> getUnprocessedRecords(String processFlag,String bankAccNum,String date);
	
	//MYSQL FORMAT
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where process_flag = ? and BANK_ACCOUNT_NUM = ? and txn_date= str_to_date(?, current_date - 48,'%dd-%mm-%YY') ", nativeQuery = true)
	List<BrsBankStatementLineStg> getUnprocessedRecords(String processFlag,String bankAccNum,String date);
	
	
		
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 a where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'N' and loader_process_id = ? "
//			+ "UNION "
//			+ "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 b where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'Y' and loader_process_id = ? ", nativeQuery = true)
	
	//SQL FORMAT
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG a where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'N' and loader_process_id =(select max(loader_process_id) from uat_xxcd_bank_stmt_line_stg where BANK_ACCOUNT_NUM = a.BANK_ACCOUNT_NUM and unique_reference = a.unique_reference) "
//			+ "UNION "
//			+ "select * from UAT_XXCD_BANK_STMT_LINE_STG b where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'Y' and loader_process_id =(select max(loader_process_id) from uat_xxcd_bank_stmt_line_stg where BANK_ACCOUNT_NUM = b.BANK_ACCOUNT_NUM and unique_reference = b.unique_reference) ", nativeQuery = true)
//	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId,String uniqueRefNo1, String bankAcNo1,Long loaderProcessId1);
	
	
	
	
//	@Query(value = "select max(loader_process_id) as loader_process_id from uat_xxcd_bank_stmt_line_stg_1 where unique_reference = ? and BANK_ACCOUNT_NUM = ? ", nativeQuery = true)
	@Query(value = "select max(loader_process_id) as loader_process_id from uat_xxcd_bank_stmt_line_stg where unique_reference = ? and BANK_ACCOUNT_NUM = ? and id > 0", nativeQuery = true)
	Long getLoaderProcessId(String uniqueRefNo, String bankAcNo);
	
	
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'N' and loader_process_id = ? ", nativeQuery = true)
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'N' and loader_process_id = ? ", nativeQuery = true)
	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG_1 where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'Y' and loader_process_id = ? ", nativeQuery = true)
//	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag = 'Y' and loader_process_id = ? ", nativeQuery = true)
//	List<BrsBankStatementLineStg> getBrsUnreconBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag IN ('N','Y') and loader_process_id = ? and id >0", nativeQuery = true)
	List<BrsBankStatementLineStg> getBrsUnreconBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	@Query(value = "select bank_account_num,statement_number,txn_date,txn_type,Sum(Amount) as amount from uat_xxcd_bank_stmt_line_stg b where bank_account_num= ? and statement_number= ? and loader_process_id =(select max(loader_process_id) from uat_xxcd_bank_stmt_line_stg where bank_account_num = b.bank_account_num and statement_number=b.statement_number) group by bank_account_num,txn_date,statement_number,txn_date,txn_type order by bank_account_num,txn_date,txn_type", nativeQuery = true)
	List<BankStmtLineSummary> getBankStmtLineSummary(String bankAccountNum,String stmtNumber);
	
	@Query(value = "select * from UAT_XXCD_BANK_STMT_LINE_STG where unique_reference = ? and BANK_ACCOUNT_NUM = ? and  process_flag ='N' and loader_process_id = ? and id >0", nativeQuery = true)
	List<BrsBankStatementLineStg> getBrsNewBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	
	@Transactional
	@Modifying
	@Query(value = "update uat_xxcd_bank_stmt_line_stg set process_flag='Y' , ERROR_MESG= ? where BANK_ACCOUNT_NUM = ? and TXN_DATE = TO_DATE(?, 'DD-MM-YY') and process_flag='N' ", nativeQuery = true)
	void updateBankStmtRecord(String errorMsg,String bankAccNum,String date);
	
	

}
