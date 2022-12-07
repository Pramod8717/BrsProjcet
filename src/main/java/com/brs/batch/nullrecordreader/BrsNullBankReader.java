package com.brs.batch.nullrecordreader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.BrsError;
import com.brs.service.BrsDataService;
import com.brs.service.BrsErrorService;
import com.brs.util.AppUtility;
import com.brs.util.BrsDataUtil;

public class BrsNullBankReader implements ItemReader<BrsConsoldtStg> {
	@Autowired
	private BrsDataService service;
	
	Logger log = LoggerFactory.getLogger(BrsNullBankReader.class);
	
	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
//	List<Long> nullRecList=new ArrayList<Long>();
	
	int totalRecords=0;
	int index=0;
	
	
	public void init() {
//		nullRecList=service.getBrsRecordsWithNoBankDetails(AppUtility.voucherDate, AppUtility.voucherDate,AppUtility.bankAccountNo, AppUtility.voucherDate, AppUtility.voucherDate);
//		if(AppUtility.nullRecList!=null&&!AppUtility.nullRecList.isEmpty()) {
			totalRecords=AppUtility.nullRecList.size();
			index=0;
//		}
		log.info("BrsNullBankReader init bankAccList "+totalRecords+" Index value : "+index);
	}
	
	public void reset() {
		AppUtility.nullRecList.clear();
		totalRecords=0;
		index=0;
	}
	
	@Override
	public BrsConsoldtStg read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		try {
			if(index==0&&totalRecords==0) {
				init();
			}
		
			if(index<totalRecords) {
				Long id=AppUtility.nullRecList.get(index);
				log.info("BrsNullBankReader ID :  "+id+" Index value : "+index);
				index++;
				return service.getBrsRecordsById(id);
			}else {
				reset();
				return null;
			}
			
		}catch(Exception e) {
			Long refId=null;
			String bankAcNo="";
			String uniqueRefNo="";
			
			BrsError error=util.getErrorDetails("BrsNullBankReader", refId, bankAcNo, uniqueRefNo, "Exception");
			errorService.saveError(error);
			log.error(error.toString(), e);
			return null;
		}
	}

}
