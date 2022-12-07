package com.brs.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.brs.entities.BrsBankStatementLineStg;

public class BankStmtLineMapper implements RowMapper<BrsBankStatementLineStg> {

	@Override
	public BrsBankStatementLineStg mapRow(ResultSet rs, int rowNum) throws SQLException {
		BrsBankStatementLineStg obj=new BrsBankStatementLineStg();
		
		obj.setAmount(rs.getDouble("AMOUNT"));
		obj.setBankAccountText(rs.getString("BANK_ACCOUNT_TEXT"));
		obj.setBankAcNo(rs.getString("BANK_ACCOUNT_NUM"));
		
		obj.setBankTrxNum(rs.getString("BANK_TRX_NUMBER"));
		obj.setCreationDate(rs.getDate("CREATION_DATE"));
		obj.setCustomerText(rs.getString("CUSTOMER_TEXT"));
		obj.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
		obj.setErrorMessage(rs.getString("ERROR_MESSAGE"));
		obj.setErrorMsg(rs.getString("ERROR_MESG"));
		obj.setId(rs.getLong("ID"));
		obj.setLastUpdateDate(rs.getDate("LAST_UPDATE_DATE"));
		obj.setLineId(rs.getLong("LINE_NUMBER"));
		obj.setLineNo(rs.getLong("BANK_ACCOUNT_NUM"));
		obj.setLoaderProcessId(rs.getLong("LOADER_PROCESS_ID"));
		obj.setMatchRef(rs.getString("MATCH_REFERENCE"));
		obj.setProcessFlag(rs.getString("PROCESS_FLAG"));
		obj.setProcessStatus(rs.getString("PROCESS_STATUS"));
		obj.setStatementNo(rs.getString("STATEMENT_NUMBER"));
		obj.setStgIntProcessId(rs.getLong("STG_INT_PROCESS_ID"));
		obj.setTxnDate(rs.getDate("TXN_DATE"));
		obj.setTxnText(rs.getString("TXN_TEXT"));
		obj.setTxnType(rs.getString("TXN_TYPE"));
		obj.setUniqueRef(rs.getString("UNIQUE_REFERENCE"));
		obj.setUnmatchRef(rs.getString("UNMATCH_REFERENCE"));
		
		
		return obj;
	}

}
