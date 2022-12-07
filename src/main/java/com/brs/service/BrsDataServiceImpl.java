package com.brs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.brs.batch.unreconreader.BrsInfo;
import com.brs.batch.updateunqref.UpdateUnqRef;
import com.brs.entities.BrsConsoldtStg;
import com.brs.repository.BrsConsoldtStgRepository;
import com.brs.util.AppUtility;
import com.brs.util.BatchStatusInterf;
import com.brs.util.BrsConsoldtStgDTO;

@Service
public class BrsDataServiceImpl implements BrsDataService {

	Logger log = LoggerFactory.getLogger(BrsDataServiceImpl.class);

	@Autowired
	private BrsConsoldtStgRepository repo;

//	@Value("${bankAccountNo}")
//	public static String bankAccountNo=null;

//	@Value("${voucherDate}")
//	public static String voucherDate=null;

	/**
	 * This method in BRS process is responsible for reading unprocessed(New)
	 * records one by one
	 */
	@Override
	public BrsConsoldtStg getBrsConsoldtStgData() {
//		log.info("BANK ACCOUNT NUM : "+bankAccountNo+" VOUCHER DATE : "+voucherDate);

		if ((AppUtility.bankAccountNo != null && !"".equals(AppUtility.bankAccountNo))
				&& (AppUtility.voucherDate != null && !"".equals(AppUtility.voucherDate))) {
//			log.info("1");
			return repo.getBrsConsoldtStgData(AppUtility.bankAccountNo, AppUtility.voucherDate);
		} else if ((AppUtility.bankAccountNo != null && !"".equals(AppUtility.bankAccountNo))
				&& (AppUtility.voucherDate == null || "".equals(AppUtility.voucherDate))) {
//			log.info("2V");
			return repo.getBrsConsoldtStgData(AppUtility.bankAccountNo);
		} else if ((AppUtility.voucherDate != null && !"".equals(AppUtility.voucherDate))
				&& (AppUtility.bankAccountNo == null || "".equals(AppUtility.bankAccountNo))) {
//			log.info("3B");
			return repo.getBrsConsoldtStgData1(AppUtility.voucherDate);
		} else {
//			log.info("4D");
			return repo.getBrsConsoldtStgData();
		}

//		return repo.getBrsConsoldtStgData();
	}

	@Override
	public List<BrsConsoldtStg> getOtherRecords(String paymentMode, String uniqueRefNo, String backAccountNo) {
		return repo.getBrsChequeRecords(uniqueRefNo, backAccountNo);
	}

	@Override
	public List<BrsConsoldtStg> getBrsRecordsByChequeId(long chequeId) {
		return repo.getBrsRecordsByChequeId(chequeId);
	}

	@Override
	public List<BrsConsoldtStg> getBrsRecordstoUpdateRefId(long chequeId, String bankAcNum) {
		return repo.getBrsRecordstoUpdateRefId(chequeId, bankAcNum);
	}

	@Override
	public List<BrsConsoldtStg> updateBrsData(List<BrsConsoldtStg> brsList) {
		return repo.saveAllAndFlush(brsList);
	}

	@Override
	public List<BrsConsoldtStg> getDayWiseBrsConsoldtStgRecords(String bankAccountNo, String date) {
		return repo.getDayWiseBrsConsoldtStgRecords(bankAccountNo, date);
	}

	@Override
	public List<BrsConsoldtStg> getBrsRecords(String uniqueRefNo, String bankAccountNo, String uniqueRefNo1,
			String bankAccountNo1) {
		return repo.getBrsRecords(uniqueRefNo, bankAccountNo, uniqueRefNo1, bankAccountNo1);
	}

	@Override
	public List<BrsConsoldtStg> getBrsRecords(String uniqueRefNo, String bankAccountNo) {
		return repo.getBrsRecords(uniqueRefNo, bankAccountNo);
	}

	@Override
	public List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo, String bankAccountNo) {
		return repo.getBrsUnreconRecords(uniqueRefNo, bankAccountNo, uniqueRefNo, bankAccountNo);
	}

	@Override
	public BrsConsoldtStg getBrsConsoldtStgData(String bankAccountNo, String voucherDate) {
		return repo.getBrsConsoldtStgData(bankAccountNo, voucherDate);
	}

	@Override
	public BrsConsoldtStg getBrsConsoldtStgData(String bankAccountNo) {
		return repo.getBrsConsoldtStgData(bankAccountNo);
	}

	@Override
	public BrsConsoldtStg getBrsConsoldtStgData1(String voucherDate) {
		return repo.getBrsConsoldtStgData1(voucherDate);
	}

	@Override
	public List<String> getBankAccountNumberList() {
		return repo.getBankAccountNumberList();
	}

	@Override
	public BrsConsoldtStg getBrsUnreconRecords() {
//		log.info("Processing Unprocessed records for reconcilation :---------------------");

		if ((AppUtility.bankAccountNo != null && !"".equals(AppUtility.bankAccountNo)
				&& !"null".equals(AppUtility.bankAccountNo))
				&& (AppUtility.voucherDate != null && !"".equals(AppUtility.voucherDate)
						&& !"null".equals(AppUtility.voucherDate))) {
//			log.info("U1");
			return repo.getBrsUnreconRecords2(AppUtility.bankAccountNo, AppUtility.voucherDate, AppUtility.voucherDate);
		} else if ((AppUtility.bankAccountNo != null && !"".equals(AppUtility.bankAccountNo))
				&& (AppUtility.voucherDate == null || "".equals(AppUtility.voucherDate)
						|| "null".equals(AppUtility.voucherDate))) {
//			log.info("U2V");
			return repo.getBrsUnreconRecords1(AppUtility.bankAccountNo);
		} else if ((AppUtility.voucherDate != null && !"".equals(AppUtility.voucherDate))
				&& (AppUtility.bankAccountNo == null || "".equals(AppUtility.bankAccountNo)
						|| "null".equals(AppUtility.bankAccountNo))) {
//			log.info("U3B");
			return repo.getBrsUnreconRecords3(AppUtility.voucherDate, AppUtility.voucherDate);
		} else {
//			log.info("U4D");
			return repo.getBrsUnreconRecords();
		}
	}

	@Override
	public void updateStatusOfUnreconciledRecord(String status, String bankAccountNo, String voucherDate) {
//		log.info("updateStatusOfUnreconciledRecord :---------------------");

		if ((bankAccountNo != null && !"".equals(bankAccountNo) && !"null".equals(bankAccountNo))
				&& (voucherDate != null && !"".equals(voucherDate) && !"null".equals(voucherDate))) {
//			log.info("RP1");
			repo.updateStatusOfUnreconciledRecord(status, bankAccountNo, voucherDate, voucherDate);

		} else if ((bankAccountNo != null && !"".equals(bankAccountNo))
				&& (voucherDate == null || "".equals(voucherDate) || "null".equals(voucherDate))) {
//			log.info("RP2V");
			repo.updateStatusOfUnreconciledRecord1(status, bankAccountNo);

		} else if ((voucherDate != null && !"".equals(voucherDate))
				&& (bankAccountNo == null || "".equals(bankAccountNo) || "null".equals(bankAccountNo))) {
//			log.info("RP3B");
			repo.updateStatusOfUnreconciledRecord(status, voucherDate, voucherDate);
		} else {
//			log.info("RP4D");
			repo.updateStatusOfUnreconciledRecord(status);
		}
	}

	@Override
	public void updateBrsRecord(String errorMsg, String bankAccNum, String date) {
//		log.info("updateBrsRecord :---------------------");
//		if((bankAccNum!=null&&!"".equals(bankAccNum)&&!"null".equals(bankAccNum))&&(date!=null&&!"".equals(date)&&!"null".equals(date))) {
//			log.info("DWU1");
		repo.updateBrsRecord(errorMsg, bankAccNum, date);

//			
//		}else if((bankAccNum!=null&&!"".equals(bankAccNum))&&(date==null||"".equals(date)||"null".equals(date))) {
////			log.info("DWU2V");
//			repo.updateBrsRecord(errorMsg,bankAccNum);
//			
//		}else if((date!=null&&!"".equals(date))&&(bankAccNum==null||"".equals(bankAccNum)||"null".equals(bankAccNum))) {
////			log.info("DWU3B");
//			repo.updateBrsRecord1(errorMsg,date);
//		}else {
////			log.info("DWU4D");
//			repo.updateBrsRecord(errorMsg,bankAccNum);
//		}
	}

//	@Override
//	public List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo, String bankAccountNo, String uniqueRefNo1,String bankAccountNo1) {
//		return repo.getBrsUnreconRecords(uniqueRefNo, bankAccountNo, uniqueRefNo1, bankAccountNo1);
//	}

//	@Override
//	public List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo, String bankAccountNo, String uniqueRefNo1,String bankAccountNo1, String uniqueRefNo2,String bankAccountNo2) {
//		return repo.getBrsUnreconRecords(uniqueRefNo, bankAccountNo, uniqueRefNo1, bankAccountNo1, uniqueRefNo2, bankAccountNo2);
//	}

	@Override
	public BrsConsoldtStg getBrsRecordsWithNoUnqRef(String bankAccNum, Long cheqId) {
		return repo.getBrsRecordsWithNoUnqRef(bankAccNum, cheqId);
	}

	@Override
	public List<UpdateUnqRef> getBrsRecordsWithNoUnqRefSummary(String bankAccNum, String date, String date1) {
		if (bankAccNum != null && !"".equals(bankAccNum) && !"null".equals(bankAccNum) && date != null
				&& !"".equals(date) && !"null".equals(date)) {
			return repo.getBrsRecordsWithNoUnqRefSummary(bankAccNum, date, date1);
		}
		return repo.getBrsRecordsWithNoUnqRefSummary();
	}

	// Created ---------------
	@Override
	public List<BatchStatusInterf> getStatusJob() {
		return repo.getStatusofJob();
	}
	
	// Created ---------------1
//	@Override
//	public BatchStatusInterf getStatusJob1(String bacnkAccNum, String voucherDate) {
//		return repo.getStatusofJob1( bacnkAccNum,  voucherDate);
//	}
	
//	@Override
//	public List<BatchStatusInterf> getStatusJob1(String bacnkAccNum, String voucherDate) {
//		return repo.getStatusofJob1(bacnkAccNum, voucherDate);
//	}
	
	@Override
	public List<BrsConsoldtStg> getStatusJob1(String bankAccountNo, String voucherDate) {
		List<BrsConsoldtStg> list = repo.getStatusofJob1(bankAccountNo, voucherDate);
		return list;
	}
	@Override
	public List<BrsConsoldtStg> getStatusJob2(String bankAccountNo, String voucherDate) {
		List<BrsConsoldtStg> list = repo.getStatusofJob2(bankAccountNo, voucherDate);
		return list;
	}
	@Override
	public List<BatchStatusInterf> getChequeStatus(String bankAccountNo, String voucherDate) {
		return repo.getChequeStatus(bankAccountNo, voucherDate);
	}
	
	
//	@Override
//	public BrsConsoldtStg getBrsRecordsWithBnkNumAndUnqRef(String bacnkAccNum, String uniqueRef) {
//		return repo.getBrsRecordsWithBnkNumAndUnqRef(bacnkAccNum, uniqueRef);
//	}

	

	@Override
	public BrsConsoldtStg getBrsRecordsById(Long id) {
		return repo.getBrsRecordsById(id);
	}

	@Override
	public List<Long> getBrsRecordsWithNoBankDetails(String date, String date1, String bankAccNum, String date3,
			String date4) {
		if (date != null && !"".equals(date) && !"null".equals(date) && bankAccNum != null && !"".equals(bankAccNum)
				&& !"null".equals(bankAccNum)) {
			return repo.getBrsRecordsWithNoBankDetails(date, date1, bankAccNum, date3, date4);
		}
		return repo.getBrsRecordsWithNoBankDetails();
	}

	@Override
	public BrsConsoldtStg getBrsRecordsWithBnkNumAndUnqRef(String bacnkAccNum, String uniqueRef) {
		return repo.getBrsRecordsWithBnkNumAndUnqRef(bacnkAccNum, uniqueRef);
	}

//	@Override
//	public List<BrsInfo> getBrsRecordsWithBnkNumAndUnqRefSummary(String status, String bankAccNum, String date) {
//		if(date!=null&&!"".equals(date)&&!"null".equals(date)&&
//				bankAccNum!=null&&!"".equals(bankAccNum)&&!"null".equals(bankAccNum)) {
//			return repo.getBrsRecordsWithBnkNumAndUnqRefSummary(status, bankAccNum, date);
//		}
//		return repo.getBrsRecordsWithBnkNumAndUnqRefSummary(status);
//	}

	@Override
	public List<BrsConsoldtStg> getBrsRecordsWithBnkNumAndUnqRefSummary(String status, String bankAccNum, String date) {
		if (date != null && !"".equals(date) && !"null".equals(date) && bankAccNum != null && !"".equals(bankAccNum)
				&& !"null".equals(bankAccNum)) {
			return repo.getBrsRecordsWithBnkNumAndUnqRefSummary(status, bankAccNum, date);
		}
		return repo.getBrsRecordsWithBnkNumAndUnqRefSummary(status);
	}

	@Override
	public List<BrsInfo> getBrsUnreconciledRecordsWithBnkNumAndUnqRefSummary(String status, String bankAccNum,
			String date, String date1) {
		if (date != null && !"".equals(date) && !"null".equals(date) && bankAccNum != null && !"".equals(bankAccNum)
				&& !"null".equals(bankAccNum)) {
			return repo.getBrsUnreconciledRecordsWithBnkNumAndUnqRefSummary(status, bankAccNum, date, date1);
		}
		return repo.getBrsUnreconciledRecordsWithBnkNumAndUnqRefSummary(status);
	}

	@Override
	public void saveAllAndFlush(List<BrsConsoldtStg> list) {
		repo.saveAllAndFlush(list);
	}

	@Override
	public Long getCountOfInProcessRecord(String bankAccNum, String date) {
		return repo.getCountOfInProcessRecord(bankAccNum, date);
	}
//
//	@Override
//	public List<BatchStatusInterf> getdetailsAll() {
//		// TODO Auto-generated method stub
//		return repo.findAll();
//	}

}
