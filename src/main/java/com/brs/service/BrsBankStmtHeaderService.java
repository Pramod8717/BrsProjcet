package com.brs.service;


import java.util.List;

import com.brs.entities.BrsBankStmtHeaderStg;

public interface BrsBankStmtHeaderService {
	
	
	BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum,String stmtDate);
	
	List<String> getListOfBankAccountNumber();

}
