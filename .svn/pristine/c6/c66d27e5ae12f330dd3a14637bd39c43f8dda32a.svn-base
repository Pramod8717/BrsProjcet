package com.brs.service;

import java.util.List;


import com.brs.entities.BrsBankStatementLineStg;
import com.brs.util.BankStmtLineSummary;

public interface BrsBankStmtService {

	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo, String processFlag);

	List<BrsBankStatementLineStg> saveBankList(List<BrsBankStatementLineStg> brsBankList);

	List<BrsBankStatementLineStg> getUnprocessedRecords(String processFlag);
	
	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId,String uniqueRefNo1, String bankAcNo1,Long loaderProcessId1);

	Long getLoaderProcessId(String uniqueRefNo, String bankAcNo);
	
	List<BrsBankStatementLineStg> getBrsBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
//	List<BrsBankStatementLineStg> getBrsUnreconBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	List<BrsBankStatementLineStg> getBrsUnreconBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
	
	List<BankStmtLineSummary> getBankStmtLineSummary(String bankAccountNum,String stmtNumber);
	
	void updateBankStmtRecord(String errorMsg,String bankAccNum,String date);
	
	List<BrsBankStatementLineStg> getBrsNewBankStmtData(String uniqueRefNo, String bankAcNo,Long loaderProcessId);
}
