package com.brs.batch.unreconreader;

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

public class BrsDataUnreconItemReader implements ItemReader<BrsConsoldtStg> {
	
	Logger log = LoggerFactory.getLogger(BrsDataUnreconItemReader.class);
	
	@Autowired
	private BrsDataService service;
	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
//	List<BrsInfo> unreconRecList=new ArrayList<BrsInfo>();
	
	int totalRecords=0;
	int index=0;
	
	public void init() {
//		unreconRecList=service.getBrsUnreconciledRecordsWithBnkNumAndUnqRefSummary("R",AppUtility.bankAccountNo, AppUtility.voucherDate, AppUtility.voucherDate);
//		if(AppUtility.unreconRecList!=null&&!AppUtility.unreconRecList.isEmpty()) {
			totalRecords=AppUtility.unreconRecList.size();
			index=0;
//		}
		log.info("BrsDataUnreconItemReader init totalRecords "+totalRecords+" Index value : "+index);
	}
	
	public void reset() {
		AppUtility.unreconRecList.clear();
		totalRecords=0;
		index=0;
	}
	
	@Override
	public BrsConsoldtStg read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		try {
//			BrsConsoldtStg brsdata = service.getBrsUnreconRecords();
//			if (brsdata != null) {
//				return brsdata;
//			} else {
//				return null;
//			}
			
			if(index==0&&totalRecords==0) {
				init();
				if(totalRecords==0) {
					return null;
				}
			}
		
			if(index<totalRecords) {
				BrsInfo brsInfo=AppUtility.unreconRecList.get(index);
				log.info("BrsDataUnreconItemReader Bank Account Num/Unique Ref :  "+brsInfo.getBank_Account_No()+" / "+brsInfo.getunique_reference()+" Index value : "+index);
				index++;
				return service.getBrsRecordsWithBnkNumAndUnqRef(brsInfo.getBank_Account_No(), brsInfo.getunique_reference());
			}else {
				reset();
				return null;
			}
		
		}catch(Exception e) {
			Long refId=null;
			String bankAcNo="";
			String uniqueRefNo="";
			
			BrsError error=util.getErrorDetails("BrsDataUnreconItemReader", refId, bankAcNo, uniqueRefNo, "Exception");
			errorService.saveError(error);
			log.error(error.toString(), e);
			return null;
		}
		
		
	}

}
