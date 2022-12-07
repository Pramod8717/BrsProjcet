package com.brs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brs.entities.BrsError;
import com.brs.repository.BrsErrorRepository;

@Service
public class BrsErrorServiceImpl implements BrsErrorService {
	
	@Autowired
	BrsErrorRepository repo;

	@Override
	public List<BrsError> saveErrorList(List<BrsError> brsErrorList) {
		return repo.saveAllAndFlush(brsErrorList);
	}

	@Override
	public BrsError saveError(BrsError brsError) {
		return repo.save(brsError);
	}
	
	
}
