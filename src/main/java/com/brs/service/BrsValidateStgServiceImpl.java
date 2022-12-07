package com.brs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brs.entities.BrsValidateStg;
import com.brs.repository.BrsValidateStgRepository;
@Service
public class BrsValidateStgServiceImpl implements BrsValidateStgService{
	@Autowired
	private BrsValidateStgRepository repo;
	@Override
	public List<BrsValidateStg> saveValidateList(List<BrsValidateStg> brsValidateList) {
		return repo.saveAllAndFlush(brsValidateList);
	}
	@Override
	public BrsValidateStg getIdFromValidateTbl(String status, String bankAccountNo, String uniqueRef) {
		return repo.getIdFromValidateTbl(status, bankAccountNo, uniqueRef);
	}
	@Override
	public String getUnmatchRefNumFromValidateStg(String bankAccountNo, String uniqueRef) {
		return repo.getUnmatchRefNumFromValidateStg(bankAccountNo, uniqueRef);
	}

}
