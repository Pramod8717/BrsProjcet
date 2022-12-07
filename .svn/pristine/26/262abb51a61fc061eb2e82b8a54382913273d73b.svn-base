package com.brs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brs.entities.BrsBankStmtHeaderStg;
import com.brs.repository.BrsBankStmtHeaderRepository;

@Service
public class BrsBankStmtHeaderServiceImpl implements BrsBankStmtHeaderService {

	@Autowired
	private BrsBankStmtHeaderRepository repo;
	
	@Override
	public BrsBankStmtHeaderStg getBankHeaderList(String bankAccountNum, String stmtDate) {
		
		if(stmtDate!=null) {
			return repo.getBankHeaderList(bankAccountNum, stmtDate);
		}else {
			return repo.getBankHeaderList(bankAccountNum);
		}
		
	}

	@Override
	public List<String> getListOfBankAccountNumber() {
		return repo.getListOfBankAccountNumber();
	}

}
