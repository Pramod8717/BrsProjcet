package com.brs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brs.entities.BrsConsotUpdateStg;
import com.brs.repository.BrsUpdateStgRepository;

@Service
public class BrsUpdateStgServiceImpl implements BrsUpdateStgService{
	
	@Autowired
	BrsUpdateStgRepository repo;

	@Override
	public BrsConsotUpdateStg getBrsUpdateStgData(long chequeId) {
		return repo.getBrsUpdateStgData(chequeId);
	}

}
