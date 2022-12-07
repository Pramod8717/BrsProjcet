package com.brs.batch.bnkstmtline;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.brs.batch.BrsDataItemProcessor;
import com.brs.entities.BrsBankStatementLineStg;
import com.brs.entities.BrsEbsStg;
import com.brs.service.BrsBankStmtService;
import com.brs.service.BrsEbsStgService;
import com.brs.service.BrsErrorService;
import com.brs.util.BrsDataUtil;

public class BankStmtLineProcessor implements ItemProcessor<BrsBankStatementLineStg, List<Object>> {
	Logger log = LoggerFactory.getLogger(BankStmtLineProcessor.class);
	
	@Autowired
	private BrsBankStmtService brsBankStmtService;
	
	@Autowired
	private BrsEbsStgService brsEbsService;

	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
	
	
	@Override
	public List<Object> process(BrsBankStatementLineStg item) throws Exception {
		long start1 = System.currentTimeMillis();
		
		List<BrsBankStatementLineStg> bankStmtList=null;// = new ArrayList<BrsBankStatementLineStg>();
		List<BrsEbsStg> brsEbsList=null ;//= new ArrayList<BrsEbsStg>();
		
		try {
			
			long step002=0;
			
			log.info("Input to ItemProcessor {}", item.getUniqueRef() +" | "+item.getBankAcNo()+" | "+item.getTxnDate()+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			
			bankStmtList=brsBankStmtService.getBrsNewBankStmtData(item.getUniqueRef(), item.getBankAcNo(), item.getLoaderProcessId());
			long step001 = System.currentTimeMillis();
			
			String unmatchRef =util.getBnkcRefId();
			log.info("BANK CONTRA, UNMATCH NUMBER : {}", unmatchRef +" | "+bankStmtList.size());
			
			brsEbsList=util.getBrsEBSListForBank(bankStmtList, "Unreconciled", "Bank Contra", "", unmatchRef);
			
			step002 = System.currentTimeMillis();
			
			if (brsEbsList != null && !brsEbsList.isEmpty()) {
				brsEbsService.saveEbsList(brsEbsList);
			}
			
			if (bankStmtList != null && !bankStmtList.isEmpty()) {
				brsBankStmtService.saveBankList(bankStmtList);
			}
			long end = System.currentTimeMillis();
			
			log.info("[CASE 9 ] Time requried "+ item.getUniqueRef() +" | "+item.getBankAcNo()+" | "+item.getTxnDate()+" | TTP = "+(end-start1)+" | Time from start to step001"+(step001-start1)+" |Time from step001 to step002="+(step002-step001)+"|Time from step002 to step003="+(end-step002)+" | Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			
			return null;
		
		}catch(Exception e) {
			e.printStackTrace();
			log.info("Inside Exception : "+ item.getUniqueRef() +" | "+item.getBankAcNo()+" | "+item.getTxnDate()+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			return null;
		}
	}

}
