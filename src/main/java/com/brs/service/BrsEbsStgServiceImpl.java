package com.brs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brs.entities.BrsEbsStg;
import com.brs.repository.BrsEbsStgRepository;

@Service
public class BrsEbsStgServiceImpl implements BrsEbsStgService {

	@Autowired
	private BrsEbsStgRepository repo;

	@Override
	public List<BrsEbsStg> getBrsEbsStgData(String uniqueRefNo, String bankAcNo, String stmtType) {
		return repo.getBrsEbsStgData(uniqueRefNo, bankAcNo, stmtType);
	}

	@Override
	public List<BrsEbsStg> saveEbsList(List<BrsEbsStg> brsEbsList) {
		return repo.saveAllAndFlush(brsEbsList);
	}

	@Override
	public List<BrsEbsStg> getUnreconciledBookEntries(String stmtType, String status, String bankAcNum,
			String uniqueRefNo) {
		return repo.getUnreconciledBookEntries(stmtType, status, bankAcNum, uniqueRefNo);
	}

	@Override
	public List<BrsEbsStg> getUnreconciledBookEntries(String stmtType, String status) {
		return repo.getUnreconciledBookEntries(stmtType, status);
	}

	@Override
	public BrsEbsStg getIdFromEbsEntry(String stmtType, String status, String bankAccountNo, String uniqueRef) {
		return repo.getIdFromEbsEntry(stmtType, status, bankAccountNo, uniqueRef);
	}
}
