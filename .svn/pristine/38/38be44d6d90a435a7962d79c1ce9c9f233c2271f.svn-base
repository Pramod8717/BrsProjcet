package com.brs.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brs.batch.unreconreader.BrsInfo;
import com.brs.batch.updateunqref.UpdateUnqRef;
import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.BrsError;
import com.brs.repository.BrsErrorRepository;
import com.brs.service.BrsDataService;
import com.brs.service.BrsErrorService;
import com.brs.util.AppUtility;
import com.brs.util.BrsDataUtil;

@Component
public class BrsDataItemReader implements ItemReader<BrsConsoldtStg> {

	@Autowired
	private BrsDataService service;
	
	
	
	Logger log = LoggerFactory.getLogger(BrsDataItemReader.class);
	
	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
//	List<BrsInfo> newRecList=new CopyOnWriteArrayList<BrsInfo>();
	List<BrsConsoldtStg> newRecList=new CopyOnWriteArrayList<BrsConsoldtStg>();	
	volatile AtomicInteger totalRecords=new AtomicInteger(0);
	volatile AtomicInteger index=new AtomicInteger(0);
	
	public synchronized void init() {
		newRecList=service.getBrsRecordsWithBnkNumAndUnqRefSummary("N",AppUtility.bankAccountNo, AppUtility.voucherDate);
//		if(AppUtility.newRecList!=null&&!AppUtility.newRecList.isEmpty()) {
			totalRecords.set(newRecList.size());
			index.set(0);
//		}
		log.info("BrsDataItemReader init totalRecords "+totalRecords+" Index value : "+index.get());
	}
	
	public void reset() {
		newRecList.clear();
		totalRecords.set(0);
		index.set(0);
	}
		

	@Override
	public synchronized BrsConsoldtStg read() {
		try {
		//	synchronized (newRecList) {
				if(index.get()==0&&totalRecords.get()==0) {
					init();
					if(totalRecords.get() ==0) {
						return null;
					}
				}
			//}
			
		
			if(index.get()<totalRecords.get()) {
//				BrsInfo brsInfo= newRecList.get(index);
				BrsConsoldtStg obj=newRecList.get(index.getAndIncrement());
				log.info("BrsDataItemReader Bank Account Num/Unique Ref :"+obj.getBankAcNo()+" / "+obj.getUniqueRef()+"  Index value : "+index.get()+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
//				index++;
				
				return obj;
//				return service.getBrsRecordsWithBnkNumAndUnqRef(brsInfo.getBank_Account_No(), brsInfo.getunique_reference());
			}else {
				reset();
				return null;
			}
			
//			if(newRecList.size()>0) {
//				return newRecList.remove(0);
//			}
//			return null;
			
		}catch(Exception e) {
			Long refId=null;
			String bankAcNo="";
			String uniqueRefNo="";
			
			BrsError error=util.getErrorDetails("BrsDataItemReader", refId, bankAcNo, uniqueRefNo, "Exception");
			errorService.saveError(error);
			log.error(error.toString(), e);
			return null;
		}
	}
	
	

}
