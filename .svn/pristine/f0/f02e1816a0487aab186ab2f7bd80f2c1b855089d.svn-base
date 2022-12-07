package com.brs.batch.bnkstmthdr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.brs.entities.BrsBankStmtHeaderStg;
import com.brs.repository.BrsBankStmtHeaderRepository;
import com.brs.service.BrsBankStmtHeaderService;
import com.brs.service.BrsBankStmtService;
import com.brs.service.BrsDataService;
import com.brs.util.AppConstants;
import com.brs.util.BankStmtLineSummary;
import com.brs.util.BrsReaderUtility;

public class BankStmtHdrProcessor implements ItemProcessor<BrsReaderUtility, List<Object>> {

	SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-YY");
	
	Logger log = LoggerFactory.getLogger(BankStmtHdrProcessor.class);
	
	@Autowired
	private BrsBankStmtHeaderService bnkHdrService;
	
	@Autowired
	private BrsBankStmtService bnkService;
	
	@Autowired
	private BrsDataService brsStgService;
	
	@Autowired
	BrsBankStmtHeaderRepository hdrRepo;
	
	@Override
	public List<Object> process(BrsReaderUtility brsReaderUtility) throws Exception {
		long start1 = System.currentTimeMillis();
		if(brsReaderUtility==null) {
			return null;
		}
		
		BrsBankStmtHeaderStg bankHeader=bnkHdrService.getBankHeaderList(brsReaderUtility.getBankAccountNo(),brsReaderUtility.getExecutionDate());
		boolean updateFlag=false;
		String errorMsg="";
		
		boolean resetFlag=false;
		
		
		log.info("BankStmtHdrProcessor BANK / DT / TYPE : "+brsReaderUtility.getBankAccountNo()+" / "+brsReaderUtility.getExecutionDate()+" / "+brsReaderUtility.getType());
		if(bankHeader!=null&&bankHeader.getId()!=null) {
			String bankAccNum=bankHeader.getBankAcNo();
			String date=fmt.format(bankHeader.getStmtDate());
			log.info("BEFORE STMT DT : "+date +" STMT NUM : "+bankHeader.getStatementNo());
			
			Date stmtDt=new Date(bankHeader.getStmtDate().getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(stmtDt);
			cal.add(Calendar.DATE, -1);
			stmtDt=cal.getTime();
			String stmtDtInStr=fmt.format(stmtDt);
			
			log.info("After STMT DT : "+stmtDtInStr);
			
			BrsBankStmtHeaderStg previousDayBankHeader=bnkHdrService.getBankHeaderList(bankAccNum,stmtDtInStr);
			if(previousDayBankHeader!=null) {
				log.info("Opening Balance : "+bankHeader.getCtrlBeginBalance() +" PREVIOUS DAY CLOSING BALANCE : "+previousDayBankHeader.getCtrlEndBalance());
				if(previousDayBankHeader.getCtrlEndBalance().equals(bankHeader.getCtrlBeginBalance())) {
					
					List<BankStmtLineSummary> bankStmtLineSummary=bnkService.getBankStmtLineSummary(bankAccNum, bankHeader.getStatementNo());
					if(bankStmtLineSummary!=null&&!bankStmtLineSummary.isEmpty()) {
						Double totalCrAmount=0d;
						Double totalDrAmount=0d;
						
						for(BankStmtLineSummary obj:bankStmtLineSummary) {
							if(obj.getTxn_type().equalsIgnoreCase("CR")) {
								totalCrAmount=totalCrAmount+obj.getAmount();
							}else if(obj.getTxn_type().equalsIgnoreCase("DR")) {
								totalDrAmount=totalDrAmount+obj.getAmount();
							}
						}
						log.info("HDR TBL TOTAL CR AMOUNT : "+bankHeader.getCtrlTotalCr()+" TOTAL DR AMOUNT : "+bankHeader.getCtrlTotalDr());
						log.info("LIN TBL TOTAL CR AMOUNT : "+totalCrAmount+" TOTAL DR AMOUNT : "+totalDrAmount);
						
						if(!bankHeader.getCtrlTotalCr().equals(totalCrAmount)||!bankHeader.getCtrlTotalDr().equals(totalDrAmount)) {
							updateFlag=true;
							errorMsg=AppConstants.ERR_MSG_BANK_HDR_LINE_CRDR_NM;
						}else {
							log.info("Bank Line and Bank HDR total CR/DR AMT matched... CHECKING/RESETING UNRECONCILED RECORD STATUS .");
							Long countOfInProcessRec=brsStgService.getCountOfInProcessRecord(brsReaderUtility.getBankAccountNo(), brsReaderUtility.getExecutionDate());
							
							log.info("countOfInProcessRec : "+countOfInProcessRec+" - " +brsReaderUtility.getBankAccountNo()+" / "+brsReaderUtility.getExecutionDate());
							if(countOfInProcessRec!=null&&countOfInProcessRec==0) {
								if("R".equals(brsReaderUtility.getType())) {
									bankHeader.setProcessFlag("Y");
									hdrRepo.save(bankHeader);
								}
							}else {
								resetFlag=true;
							}
						}
							
					}else {
						updateFlag=true;
						errorMsg=AppConstants.ERR_MSG_BANK_LINE_NF;
					}
				}else {
					updateFlag=true;
					errorMsg=AppConstants.ERR_MSG_BANK_HDR_OPN_BAL_CLO_BAl;
				}
			}else {
				updateFlag=true;
				errorMsg=AppConstants.ERR_MSG_BANK_HDR_NF_FOR_PREV_DAY;
			}
			
//			if("R".equals(brsReaderUtility.getType())) {
//				bankHeader.setProcessFlag("Y");
//				hdrRepo.save(bankHeader);
//			}
			
		}else {
			updateFlag=true;
			errorMsg=AppConstants.ERR_MSG_BANK_HDR_NF;
		}
		
		log.info(" update flag: "+updateFlag+" reset flag : "+resetFlag+" | "+errorMsg );
		if(updateFlag&&"V".equals(brsReaderUtility.getType())) {
			brsStgService.updateBrsRecord(errorMsg, brsReaderUtility.getBankAccountNo(), brsReaderUtility.getExecutionDate());
			bnkService.updateBankStmtRecord(errorMsg, brsReaderUtility.getBankAccountNo(), brsReaderUtility.getExecutionDate());
		}else if(resetFlag&&"R".equals(brsReaderUtility.getType())) {
			log.info("Updating status to reconciled");
			brsStgService.updateStatusOfUnreconciledRecord("R",brsReaderUtility.getBankAccountNo(),brsReaderUtility.getExecutionDate());
		}else {
			log.info("No Update");
		}
		
		long end = System.currentTimeMillis();
		log.info("[Case 8]BankStmtHdrProcessor BANK / DT / TYPE : "+brsReaderUtility.getBankAccountNo()+" / "+brsReaderUtility.getExecutionDate()+" / "+brsReaderUtility.getType()+"| TTP = "+(end-start1)+" |Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
		
		return null;
	}

}
