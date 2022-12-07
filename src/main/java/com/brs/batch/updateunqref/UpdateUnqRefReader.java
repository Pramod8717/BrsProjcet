package com.brs.batch.updateunqref;

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

public class UpdateUnqRefReader implements ItemReader<BrsConsoldtStg> {
	
	Logger log = LoggerFactory.getLogger(UpdateUnqRefReader.class);
	
	@Autowired
	private BrsDataService service;
	
	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
//	List<UpdateUnqRef> updateUnqRefList=new ArrayList<UpdateUnqRef>();
	
	int totalRecords=0;
	int index=0;
	
	public void init() {
//		updateUnqRefList=service.getBrsRecordsWithNoUnqRefSummary(AppUtility.bankAccountNo, AppUtility.voucherDate, AppUtility.voucherDate);
//		if(AppUtility.updateUnqRefList!=null&&!AppUtility.updateUnqRefList.isEmpty()) {
			totalRecords=AppUtility.updateUnqRefList.size();
			index=0;
//		}
		log.info("UpdateUnqRefReader init bankAccList "+totalRecords+" Index value : "+index);
	}
	
	public void reset() {
		AppUtility.updateUnqRefList.clear();
		totalRecords=0;
		index=0;
	}
	
	@Override
	public BrsConsoldtStg read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		try {
			
			if(index==0&&totalRecords==0) {
				init();
				if(totalRecords==0) {
					return null;
				}
			}
		
			if(index<totalRecords) {
				UpdateUnqRef updateUnqRef=AppUtility.updateUnqRefList.get(index);
				log.info("UpdateUnqRefReader Bank Account Num/CHEQ_ID :  "+updateUnqRef.getBank_Account_No()+" / "+updateUnqRef.getCheq_Id()+" Index value : "+index);
				index++;
				return service.getBrsRecordsWithNoUnqRef(updateUnqRef.getBank_Account_No(), updateUnqRef.getCheq_Id());
			}else {
				reset();
				return null;
			}
			
			
			
			
		}catch(Exception e) {
			Long refId=null;
			String bankAcNo="";
			String uniqueRefNo="";
			
			BrsError error=util.getErrorDetails("UpdateUnqRefReader", refId, bankAcNo, uniqueRefNo, "Exception");
			errorService.saveError(error);
			log.error(error.toString(), e);
			return null;
		}
	}

}
