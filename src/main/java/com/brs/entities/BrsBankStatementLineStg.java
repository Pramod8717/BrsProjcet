
package com.brs.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Table(name = "UAT_XXCD_BANK_STMT_LINE_STG_1")
@Table(name = "UAT_XXCD_BANK_STMT_LINE_STG")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class BrsBankStatementLineStg {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "BANK_ACCOUNT_NUM")
	private String bankAcNo;

	@Column(name = "STATEMENT_NUMBER")
	private String statementNo;

	@Column(name = "LINE_NUMBER")
	private Long lineNo;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "TXN_DATE")
	private Date txnDate;

	@Column(name = "BANK_TRX_NUMBER")
	private String bankTrxNum;

	@Column(name = "AMOUNT")
	private Double amount;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Column(name = "TXN_TEXT")
	private String txnText;

	@Column(name = "CUSTOMER_TEXT")
	private String customerText;

	@Column(name = "BANK_ACCOUNT_TEXT")
	private String bankAccountText;

	@Column(name = "LINE_ID")
	private Long lineId;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "PROCESS_FLAG")
	private String processFlag;

	@Column(name = "STG_INT_PROCESS_ID")
	private Long stgIntProcessId;

	@Column(name = "LOADER_PROCESS_ID")
	private Long loaderProcessId;

	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "UNIQUE_REFERENCE")
	private String uniqueRef;

	@Column(name = "MATCH_REFERENCE")
	private String matchRef;

	@Column(name = "UNMATCH_REFERENCE")
	private String unmatchRef;

	@Column(name = "PROCESS_STATUS")
	private String processStatus;

	@Column(name = "ERROR_MESG")
	private String errorMsg;

	public BrsBankStatementLineStg(Long id, String bankAcNo, String statementNo, Long lineNo, String txnType,
			Date txnDate, String bankTrxNum, Double amount, Date effectiveDate, String txnText, String customerText,
			String bankAccountText, Long lineId, Date creationDate, Date lastUpdateDate, String processFlag,
			Long stgIntProcessId, Long loaderProcessId, String errorMessage, String uniqueRef, String matchRef,
			String unmatchRef, String processStatus, String errorMsg) {
		super();
		this.id = id;
		this.bankAcNo = bankAcNo;
		this.statementNo = statementNo;
		this.lineNo = lineNo;
		this.txnType = txnType;
		this.txnDate = txnDate;
		this.bankTrxNum = bankTrxNum;
		this.amount = amount;
		this.effectiveDate = effectiveDate;
		this.txnText = txnText;
		this.customerText = customerText;
		this.bankAccountText = bankAccountText;
		this.lineId = lineId;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.processFlag = processFlag;
		this.stgIntProcessId = stgIntProcessId;
		this.loaderProcessId = loaderProcessId;
		this.errorMessage = errorMessage;
		this.uniqueRef = uniqueRef;
		this.matchRef = matchRef;
		this.unmatchRef = unmatchRef;
		this.processStatus = processStatus;
		this.errorMsg = errorMsg;
	}

	public BrsBankStatementLineStg() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	public String getStatementNo() {
		return statementNo;
	}

	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}

	public Long getLineNo() {
		return lineNo;
	}

	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public String getBankTrxNum() {
		return bankTrxNum;
	}

	public void setBankTrxNum(String bankTrxNum) {
		this.bankTrxNum = bankTrxNum;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTxnText() {
		return txnText;
	}

	public void setTxnText(String txnText) {
		this.txnText = txnText;
	}

	public String getCustomerText() {
		return customerText;
	}

	public void setCustomerText(String customerText) {
		this.customerText = customerText;
	}

	public String getBankAccountText() {
		return bankAccountText;
	}

	public void setBankAccountText(String bankAccountText) {
		this.bankAccountText = bankAccountText;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public Long getStgIntProcessId() {
		return stgIntProcessId;
	}

	public void setStgIntProcessId(Long stgIntProcessId) {
		this.stgIntProcessId = stgIntProcessId;
	}

	public Long getLoaderProcessId() {
		return loaderProcessId;
	}

	public void setLoaderProcessId(Long loaderProcessId) {
		this.loaderProcessId = loaderProcessId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUniqueRef() {
		return uniqueRef;
	}

	public void setUniqueRef(String uniqueRef) {
		this.uniqueRef = uniqueRef;
	}

	public String getMatchRef() {
		return matchRef;
	}

	public void setMatchRef(String matchRef) {
		this.matchRef = matchRef;
	}

	public String getUnmatchRef() {
		return unmatchRef;
	}

	public void setUnmatchRef(String unmatchRef) {
		this.unmatchRef = unmatchRef;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "BrsBankStatementLineStg [id=" + id + ", bankAcNo=" + bankAcNo + ", statementNo=" + statementNo
				+ ", lineNo=" + lineNo + ", txnType=" + txnType + ", txnDate=" + txnDate + ", bankTrxNum=" + bankTrxNum
				+ ", amount=" + amount + ", effectiveDate=" + effectiveDate + ", txnText=" + txnText + ", customerText="
				+ customerText + ", bankAccountText=" + bankAccountText + ", lineId=" + lineId + ", creationDate="
				+ creationDate + ", lastUpdateDate=" + lastUpdateDate + ", processFlag=" + processFlag
				+ ", stgIntProcessId=" + stgIntProcessId + ", loaderProcessId=" + loaderProcessId + ", errorMessage="
				+ errorMessage + ", uniqueRef=" + uniqueRef + ", matchRef=" + matchRef + ", unmatchRef=" + unmatchRef
				+ ", processStatus=" + processStatus + ", errorMsg=" + errorMsg + "]";
	}

	
	
	
	
}
