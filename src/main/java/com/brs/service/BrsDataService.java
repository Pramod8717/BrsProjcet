package com.brs.service;

import java.util.List;

import com.brs.batch.unreconreader.BrsInfo;
import com.brs.batch.updateunqref.UpdateUnqRef;
import com.brs.entities.BrsConsoldtStg;
import com.brs.util.BatchStatusInterf;
import com.brs.util.BrsConsoldtStgDTO;

public interface BrsDataService {

	BrsConsoldtStg getBrsConsoldtStgData();

	List<BrsConsoldtStg> getOtherRecords(String paymentmode, String uniqueRefNo,String backAccountNo);

	List<BrsConsoldtStg> getBrsRecordsByChequeId(long chequeId);

	List<BrsConsoldtStg> getBrsRecordstoUpdateRefId(long chequeId, String bankAcNum);

	List<BrsConsoldtStg> updateBrsData(List<BrsConsoldtStg> brsList);
	
	List<BrsConsoldtStg> getDayWiseBrsConsoldtStgRecords(String bankAccountNo,String date);
	
	List<BrsConsoldtStg> getBrsRecords(String uniqueRefNo,String bankAccountNo,String uniqueRefNo1,String bankAccountNo1);
	
	List<BrsConsoldtStg> getBrsRecords(String uniqueRefNo,String bankAccountNo);
	
	List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo,String bankAccountNo);
	
//	List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo,String bankAccountNo,String uniqueRefNo1,String bankAccountNo1);
	
//	List<BrsConsoldtStg> getBrsUnreconRecords(String uniqueRefNo,String bankAccountNo,String uniqueRefNo1,String bankAccountNo1,String uniqueRefNo2,String bankAccountNo2);
	
	BrsConsoldtStg getBrsConsoldtStgData(String bankAccountNo,String voucherDate);
	
	BrsConsoldtStg getBrsConsoldtStgData(String bankAccountNo);
	
	BrsConsoldtStg getBrsConsoldtStgData1(String voucherDate);
	
	List<String> getBankAccountNumberList();
	
	BrsConsoldtStg getBrsUnreconRecords();
	
	void updateStatusOfUnreconciledRecord(String status,String bankAccountNo,String voucherDate);
	
	void updateBrsRecord(String errorMsg,String bankAccNum,String date);
	
	BrsConsoldtStg getBrsRecordsWithNoUnqRef(String bankAccNum,Long cheqId);
	
	List<UpdateUnqRef> getBrsRecordsWithNoUnqRefSummary(String bankAccNum,String date,String date1);
	
	
	
	
	
	BrsConsoldtStg getBrsRecordsById(Long id);
	
	List<Long> getBrsRecordsWithNoBankDetails(String date,String date1,String bankAccNum,String date3,String date4);
	
	BrsConsoldtStg getBrsRecordsWithBnkNumAndUnqRef(String bacnkAccNum,String uniqueRef);
	
//	List<BrsInfo> getBrsRecordsWithBnkNumAndUnqRefSummary(String status,String bankAccNum,String date);

	List<BrsConsoldtStg> getBrsRecordsWithBnkNumAndUnqRefSummary(String status,String bankAccNum,String date);
	
	List<BrsInfo> getBrsUnreconciledRecordsWithBnkNumAndUnqRefSummary(String status,String bankAccNum,String date,String date1);
	
	void saveAllAndFlush(List<BrsConsoldtStg> list);
	
	Long getCountOfInProcessRecord(String bankAccNum,String date);

	// Created-----------
//	List<BatchStatusInterf> getStatusofJob(String bank_acnt_no, String voucherdate, String processFlag, String status,
//			String countst, String errorMsg);
//	Old  Created from Pramod
	List<BatchStatusInterf> getStatusJob();
	
	
//	New  Created from Pramod
//	List<BatchStatusInterf> getStatusJob1(String bankAccountNo,String voucherDate);
	List<BrsConsoldtStg> getStatusJob1(String bankAccountNo,String voucherDate);
	
	List<BrsConsoldtStg> getStatusJob2(String bankAccountNo,String voucherDate);
	
	List<BatchStatusInterf> getChequeStatus(String bankAccountNo,String voucherDate);
	//List<BrsConsoldtStg> getdetailsAll();
}