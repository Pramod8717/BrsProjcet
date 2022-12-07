package com.brs.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
//@Table(name = "UAT_XXCD_BRS_VALIDATE_STG_1")
@Table(name = "UAT_XXCD_BRS_VALIDATE_STG")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class BrsValidateStg {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "BANK_ACCOUNT_NO")
	private String bankAcNo;

//	@Column(name = "SYSTEM")
	@Column(name = "`SYSTEM`")
	private String system;

	@Column(name = "VOUCHERID")
	private String voucherId;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "VOUCHERDATE")
	private Date voucherDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "VALUEDATE")
	private Date valueDate;

	@Column(name = "BRANCHID")
	private String branchId;

	@Column(name = "PROPOSALID")
	private String proposalId;

	@Column(name = "AGREEMENTNO")
	private String agreementNo;

	@Column(name = "GL_CODE")
	private String glCode;

	@Column(name = "GL_DESCRIPTION")
	private String glDescription;

	@Column(name = "DRAMT")
	private Double drAmt;

	@Column(name = "CRAMT")
	private Double crAmt;

	@Column(name = "NARRATION")
	private String narration;

	@Column(name = "STAGE_ID")
	private String stageId;

	@Column(name = "CHEQ_ID")
	private Long cheqId;

	@Column(name = "RECEIPT_NO")
	private String receipt_No;

	@Column(name = "RECEIPTNO")
	private String receiptNo;

	@Column(name = "CHEQUENUM")
	private String chequeNum;

	@Column(name = "PAYMENTMODE")
	private String paymentMode;

	@Column(name = "UTR_NO")
	private String utrNo;

	@Column(name = "PAYSLIPNO")
	private String payslipNo;

	@Column(name = "CHEQUE_STATUS")
	private String chequeStatus;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "REALIZATIONDATE")
	private Date realizationDate;

	@Column(name = "PROCESS_FLAG")
	private String processFlag;

	@Column(name = "SOURCE_SYSTEM")
	private String sourceSystem;
	
	@Column(name = "UNIQUE_REFERENCE")
	private String uniqueRef;

	@Column(name = "MATCH_REFERENCE")
	private String matchRef;

	@Column(name = "UNMATCH_REFERENCE")
	private String unmatchRef;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "CREATED_BY")
	private Short createdBy;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	@Column(name = "LAST_UPDATED_BY")
	private Short lastUpdatedBy;
		
	@Column(name = "ERROR_MESG")
	private String errorMsg;
	
	@Column(name = "PROCESS_STATUS")
	private String processStatus;
	
	@Column(name = "JE_BATCH_ID")
	private Long jeBatchId;

	@Column(name = "PERIOD_NAME")
	private String periodName;
	
	@Column(name = "JE_HEADER_ID")
	private Long jeHeaderId;
	
	@Column(name = "JE_LINE_NUM")
	private Long jeLineNum;
	
	@Column(name = "CHART_OF_ACCOUNTS_ID")
	private Long chartOfAccountId;
	
	@Column(name = "FUNCTIONAL_CURRENCY_CODE")
	private String funtionalCurrencyCode;
	
	@Column(name = "CODE_COMBINATION_ID")
	private Long codeCombinationId;
	
	@Column(name = "STATUS")
	private String status;

	public BrsValidateStg(Long id, String bankAcNo, String system, String voucherId, Date voucherDate, Date valueDate,
			String branchId, String proposalId, String agreementNo, String glCode, String glDescription, Double drAmt,
			Double crAmt, String narration, String stageId, Long cheqId, String receipt_No, String receiptNo,
			String chequeNum, String paymentMode, String utrNo, String payslipNo, String chequeStatus,
			Date realizationDate, String processFlag, String sourceSystem, String uniqueRef, String matchRef,
			String unmatchRef, Date creationDate, Short createdBy, Date lastUpdateDate, Short lastUpdatedBy,
			String errorMsg, String processStatus, Long jeBatchId, String periodName, Long jeHeaderId, Long jeLineNum,
			Long chartOfAccountId, String funtionalCurrencyCode, Long codeCombinationId, String status) {
		super();
		this.id = id;
		this.bankAcNo = bankAcNo;
		this.system = system;
		this.voucherId = voucherId;
		this.voucherDate = voucherDate;
		this.valueDate = valueDate;
		this.branchId = branchId;
		this.proposalId = proposalId;
		this.agreementNo = agreementNo;
		this.glCode = glCode;
		this.glDescription = glDescription;
		this.drAmt = drAmt;
		this.crAmt = crAmt;
		this.narration = narration;
		this.stageId = stageId;
		this.cheqId = cheqId;
		this.receipt_No = receipt_No;
		this.receiptNo = receiptNo;
		this.chequeNum = chequeNum;
		this.paymentMode = paymentMode;
		this.utrNo = utrNo;
		this.payslipNo = payslipNo;
		this.chequeStatus = chequeStatus;
		this.realizationDate = realizationDate;
		this.processFlag = processFlag;
		this.sourceSystem = sourceSystem;
		this.uniqueRef = uniqueRef;
		this.matchRef = matchRef;
		this.unmatchRef = unmatchRef;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.errorMsg = errorMsg;
		this.processStatus = processStatus;
		this.jeBatchId = jeBatchId;
		this.periodName = periodName;
		this.jeHeaderId = jeHeaderId;
		this.jeLineNum = jeLineNum;
		this.chartOfAccountId = chartOfAccountId;
		this.funtionalCurrencyCode = funtionalCurrencyCode;
		this.codeCombinationId = codeCombinationId;
		this.status = status;
	}

	public BrsValidateStg() {
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

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getGlDescription() {
		return glDescription;
	}

	public void setGlDescription(String glDescription) {
		this.glDescription = glDescription;
	}

	public Double getDrAmt() {
		return drAmt;
	}

	public void setDrAmt(Double drAmt) {
		this.drAmt = drAmt;
	}

	public Double getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(Double crAmt) {
		this.crAmt = crAmt;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public Long getCheqId() {
		return cheqId;
	}

	public void setCheqId(Long cheqId) {
		this.cheqId = cheqId;
	}

	public String getReceipt_No() {
		return receipt_No;
	}

	public void setReceipt_No(String receipt_No) {
		this.receipt_No = receipt_No;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getChequeNum() {
		return chequeNum;
	}

	public void setChequeNum(String chequeNum) {
		this.chequeNum = chequeNum;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getUtrNo() {
		return utrNo;
	}

	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}

	public String getPayslipNo() {
		return payslipNo;
	}

	public void setPayslipNo(String payslipNo) {
		this.payslipNo = payslipNo;
	}

	public String getChequeStatus() {
		return chequeStatus;
	}

	public void setChequeStatus(String chequeStatus) {
		this.chequeStatus = chequeStatus;
	}

	public Date getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(Date realizationDate) {
		this.realizationDate = realizationDate;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Short getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Short createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Short getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Short lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public Long getJeBatchId() {
		return jeBatchId;
	}

	public void setJeBatchId(Long jeBatchId) {
		this.jeBatchId = jeBatchId;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public Long getJeHeaderId() {
		return jeHeaderId;
	}

	public void setJeHeaderId(Long jeHeaderId) {
		this.jeHeaderId = jeHeaderId;
	}

	public Long getJeLineNum() {
		return jeLineNum;
	}

	public void setJeLineNum(Long jeLineNum) {
		this.jeLineNum = jeLineNum;
	}

	public Long getChartOfAccountId() {
		return chartOfAccountId;
	}

	public void setChartOfAccountId(Long chartOfAccountId) {
		this.chartOfAccountId = chartOfAccountId;
	}

	public String getFuntionalCurrencyCode() {
		return funtionalCurrencyCode;
	}

	public void setFuntionalCurrencyCode(String funtionalCurrencyCode) {
		this.funtionalCurrencyCode = funtionalCurrencyCode;
	}

	public Long getCodeCombinationId() {
		return codeCombinationId;
	}

	public void setCodeCombinationId(Long codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BrsValidateStg [id=" + id + ", bankAcNo=" + bankAcNo + ", system=" + system + ", voucherId=" + voucherId
				+ ", voucherDate=" + voucherDate + ", valueDate=" + valueDate + ", branchId=" + branchId
				+ ", proposalId=" + proposalId + ", agreementNo=" + agreementNo + ", glCode=" + glCode
				+ ", glDescription=" + glDescription + ", drAmt=" + drAmt + ", crAmt=" + crAmt + ", narration="
				+ narration + ", stageId=" + stageId + ", cheqId=" + cheqId + ", receipt_No=" + receipt_No
				+ ", receiptNo=" + receiptNo + ", chequeNum=" + chequeNum + ", paymentMode=" + paymentMode + ", utrNo="
				+ utrNo + ", payslipNo=" + payslipNo + ", chequeStatus=" + chequeStatus + ", realizationDate="
				+ realizationDate + ", processFlag=" + processFlag + ", sourceSystem=" + sourceSystem + ", uniqueRef="
				+ uniqueRef + ", matchRef=" + matchRef + ", unmatchRef=" + unmatchRef + ", creationDate=" + creationDate
				+ ", createdBy=" + createdBy + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", errorMsg=" + errorMsg + ", processStatus=" + processStatus + ", jeBatchId=" + jeBatchId
				+ ", periodName=" + periodName + ", jeHeaderId=" + jeHeaderId + ", jeLineNum=" + jeLineNum
				+ ", chartOfAccountId=" + chartOfAccountId + ", funtionalCurrencyCode=" + funtionalCurrencyCode
				+ ", codeCombinationId=" + codeCombinationId + ", status=" + status + "]";
	}
	
	
	
	

}
