package com.brs.service;

import java.util.List;

import com.brs.entities.BrsError;

public interface BrsErrorService {
	List<BrsError> saveErrorList(List<BrsError> brsErrorList);
	
	BrsError saveError(BrsError brsError);
}
