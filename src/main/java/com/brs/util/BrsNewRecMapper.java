package com.brs.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.brs.entities.BrsConsoldtStg;

public class BrsNewRecMapper implements RowMapper<BrsConsoldtStg> {

	@Override
	public BrsConsoldtStg mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BrsConsoldtStg pojo=new BrsConsoldtStg();
		pojo.setId(rs.getLong("ID"));
		pojo.setBankAcNo(rs.getString("BANK_ACCOUNT_NO"));
		pojo.setSystem(rs.getString("SYSTEM"));
		pojo.setVoucherId(rs.getString("VOUCHERID"));
		pojo.setVoucherDate(rs.getDate("VOUCHERDATE"));
		pojo.setValueDate(rs.getDate("VALUEDATE"));
		pojo.setBranchId(rs.getString("BRANCHID"));
		pojo.setProposalId(rs.getString("PROPOSALID"));
		pojo.setAgreementNo(rs.getString("AGREEMENTNO"));
		pojo.setGlCode(rs.getString("GL_CODE"));
		pojo.setGlDescription(rs.getString("GL_DESCRIPTION"));
		pojo.setDrAmt(rs.getDouble("DRAMT"));
		pojo.setCrAmt(rs.getDouble("CRAMT"));
		pojo.setNarration(rs.getString("NARRATION"));
		pojo.setStageId(rs.getString("STAGE_ID"));
		pojo.setCheqId(rs.getLong("CHEQ_ID"));
		pojo.setReceipt_No(rs.getString("RECEIPT_NO"));
		pojo.setReceipt_No(rs.getString("RECEIPTNO"));
		pojo.setChequeNum(rs.getString("CHEQUENUM"));
		pojo.setPaymentMode(rs.getString("PAYMENTMODE"));
		pojo.setUtrNo(rs.getString("UTR_NO"));
		pojo.setPayslipNo(rs.getString("PAYSLIPNO"));
		pojo.setChequeStatus(rs.getString("CHEQUE_STATUS"));
		pojo.setRealizationDate(rs.getDate("REALIZATIONDATE"));
		pojo.setProcessFlag(rs.getString("PROCESS_FLAG"));
		pojo.setSourceSystem(rs.getString("SOURCE_SYSTEM"));
		pojo.setUniqueRef(rs.getString("UNIQUE_REFERENCE"));
		pojo.setStatus(rs.getString("STATUS"));
		pojo.setErrorMsg(rs.getString("ERROR_MESG"));
		pojo.setJeBatchId(rs.getLong("JE_BATCH_ID"));
		pojo.setPeriodName(rs.getString("PERIOD_NAME"));
		pojo.setJeHeaderId(rs.getLong("JE_HEADER_ID"));
		pojo.setJeLineNum(rs.getLong("JE_LINE_NUM"));
		pojo.setChartOfAccountId(rs.getLong("CHART_OF_ACCOUNTS_ID"));
		pojo.setFuntionalCurrencyCode(rs.getString("FUNCTIONAL_CURRENCY_CODE"));
		pojo.setCodeCombinationId(rs.getLong("CODE_COMBINATION_ID"));
		pojo.setCreatedBy(rs.getShort("CREATED_BY"));
		pojo.setCreationDate(rs.getDate("CREATION_DATE"));
		pojo.setLastUpdateBy(rs.getShort("LAST_UPDATE_BY"));
		pojo.setLastUpdateDate(rs.getDate("LAST_UPDATE_DATE"));
		
		return pojo;
		
	}

}
