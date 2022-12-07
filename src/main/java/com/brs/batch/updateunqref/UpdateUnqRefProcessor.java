package com.brs.batch.updateunqref;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.brs.entities.BrsBankStatementLineStg;
import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.BrsConsotUpdateStg;
import com.brs.entities.BrsEbsStg;
import com.brs.entities.BrsValidateStg;
import com.brs.repository.BrsConsoldtStgRepository;
import com.brs.service.BrsDataService;
import com.brs.service.BrsEbsStgService;
import com.brs.service.BrsErrorService;
import com.brs.service.BrsUpdateStgService;
import com.brs.service.BrsValidateStgService;
import com.brs.util.AppConstants;
import com.brs.util.AppUtility;
import com.brs.util.BrsDataUtil;

public class UpdateUnqRefProcessor implements ItemProcessor<BrsConsoldtStg, List<BrsConsoldtStg>> {

	Logger log = LoggerFactory.getLogger(UpdateUnqRefProcessor.class);
	@Autowired
	private BrsUpdateStgService brsUpdateStgService;

	@Autowired
	private BrsEbsStgService brsEbsService;

	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
	@Autowired
	private BrsValidateStgService brsValidateService;
	
	@Autowired
	private BrsDataService brsDataService;
	
	@Autowired
	private BrsConsoldtStgRepository repo;
	
	@Override
	public List<BrsConsoldtStg> process(BrsConsoldtStg brsData) throws Exception {
		
		Long chequeId=brsData.getCheqId();
		String bankAcNo=brsData.getBankAcNo();
		String uniqueRefNo =null;
//		log.info("UpdateUnqRefProcessor Bank Account Num/chequeId :  "+bankAcNo+" / "+chequeId);
		List<BrsConsoldtStg> bookStmtList = new ArrayList<BrsConsoldtStg>();
		List<BrsEbsStg> brsEbsList = new ArrayList<BrsEbsStg>();
		List<BrsValidateStg> brsValidateList=null;
		
		boolean processedRecord=false;
		String processedUnmatchNum="";
		if(brsData.getProcessFlag().equals("R")) {
			processedRecord=true;
			processedUnmatchNum=brsValidateService.getUnmatchRefNumFromValidateStg(bankAcNo, uniqueRefNo);
//			System.out.println("processedUnmatchNum "+processedUnmatchNum);
		}
		bookStmtList = new ArrayList<BrsConsoldtStg>();
		bookStmtList.add(brsData);
		
		BrsConsotUpdateStg brsUpdateStg = brsUpdateStgService.getBrsUpdateStgData(chequeId);
		if (brsUpdateStg != null) {
			uniqueRefNo = util.updateUniqueRef(brsData, brsUpdateStg);
			
			if (uniqueRefNo == null) {

				brsData.setStatus(AppConstants.UNRECONCILED);
				brsData.setErrorMsg(AppConstants.UNQREFNOTADDEDERR);

				
				String unmatchRef ="";
				
				if(processedRecord&&processedUnmatchNum!=null&&!"".equals(processedUnmatchNum)) {
					unmatchRef=processedUnmatchNum;
				}else {
					unmatchRef = util.getBkcRefId();
				}
				
				brsEbsList = util.getBrsEBSList(bookStmtList, AppConstants.UNRECONCILED,AppConstants.UNQREFNOTADDEDERR, "", unmatchRef);

				if (brsEbsList != null && !brsEbsList.isEmpty()) {
					brsEbsService.saveEbsList(brsEbsList);
				}
				
				
				brsValidateList=util.getBrsValidateStgList(bookStmtList, AppConstants.UNRECONCILED, AppConstants.UNQREFNOTADDEDERR, "",unmatchRef);
				if (brsValidateList != null && !brsValidateList.isEmpty()) {
					brsValidateService.saveValidateList(brsValidateList);
				}
				
			}
		}else {
			brsData.setStatus(AppConstants.UNRECONCILED);
			brsData.setErrorMsg(AppConstants.UNQREFNOTFOUNDERR);

			
			String unmatchRef ="";
			if(processedRecord&&processedUnmatchNum!=null&&!"".equals(processedUnmatchNum)) {
				unmatchRef=processedUnmatchNum;
			}else {
				unmatchRef = util.getBkcRefId();
			}
			
			brsEbsList = util.getBrsEBSList(bookStmtList, AppConstants.UNRECONCILED, AppConstants.UNQREFNOTFOUNDERR,"", unmatchRef);

			if (brsEbsList != null && !brsEbsList.isEmpty()) {
				brsEbsService.saveEbsList(brsEbsList);
			}
			
			
			brsValidateList=util.getBrsValidateStgList(bookStmtList, AppConstants.UNRECONCILED, AppConstants.UNQREFNOTFOUNDERR, "",unmatchRef);
			if (brsValidateList != null && !brsValidateList.isEmpty()) {
				brsValidateService.saveValidateList(brsValidateList);
			}
		}
//		repo.saveAllAndFlush(bookStmtList);
		
		return bookStmtList;
	}

}
