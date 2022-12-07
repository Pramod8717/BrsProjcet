
package com.brs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Table(name = "UAT_XXCD_BRS_CONS_UPDATE_STG_1")
@Table(name = "UAT_XXCD_BRS_CONS_UPDATE_STG")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class BrsConsotUpdateStg {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "CHEQ_ID")
	private Long cheqId;

	@Column(name = "CHEQUENUM")
	private String chequeNum;

	@Column(name = "UTR_NO")
	private String utrNo;

	@Column(name = "PAYSLIPNO")
	private String payslipNo;

	@Column(name = "PROCESS_FLAG")
	private String processFlag;
	
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

	public BrsConsotUpdateStg(Long id, Long cheqId, String chequeNum, String utrNo, String payslipNo,
			String processFlag, Long jeBatchId, String periodName, Long jeHeaderId, Long jeLineNum,
			Long chartOfAccountId, String funtionalCurrencyCode, Long codeCombinationId) {
		super();
		this.id = id;
		this.cheqId = cheqId;
		this.chequeNum = chequeNum;
		this.utrNo = utrNo;
		this.payslipNo = payslipNo;
		this.processFlag = processFlag;
		this.jeBatchId = jeBatchId;
		this.periodName = periodName;
		this.jeHeaderId = jeHeaderId;
		this.jeLineNum = jeLineNum;
		this.chartOfAccountId = chartOfAccountId;
		this.funtionalCurrencyCode = funtionalCurrencyCode;
		this.codeCombinationId = codeCombinationId;
	}

	public BrsConsotUpdateStg() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCheqId() {
		return cheqId;
	}

	public void setCheqId(Long cheqId) {
		this.cheqId = cheqId;
	}

	public String getChequeNum() {
		return chequeNum;
	}

	public void setChequeNum(String chequeNum) {
		this.chequeNum = chequeNum;
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

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
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

	@Override
	public String toString() {
		return "BrsConsotUpdateStg [id=" + id + ", cheqId=" + cheqId + ", chequeNum=" + chequeNum + ", utrNo=" + utrNo
				+ ", payslipNo=" + payslipNo + ", processFlag=" + processFlag + ", jeBatchId=" + jeBatchId
				+ ", periodName=" + periodName + ", jeHeaderId=" + jeHeaderId + ", jeLineNum=" + jeLineNum
				+ ", chartOfAccountId=" + chartOfAccountId + ", funtionalCurrencyCode=" + funtionalCurrencyCode
				+ ", codeCombinationId=" + codeCombinationId + "]";
	}
	
	
	

}
