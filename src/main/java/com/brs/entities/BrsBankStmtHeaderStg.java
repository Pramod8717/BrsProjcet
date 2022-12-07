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
@Table(name = "UAT_XXCD_BANK_STMT_HDR_STG")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class BrsBankStmtHeaderStg {
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "BANK_ACCOUNT_NUM")
	private String bankAcNo;

	@Column(name = "STATEMENT_NUMBER")
	private String statementNo;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "STATEMENT_DATE")
	private Date stmtDate;
	
	@Column(name = "OU_NAME")
	private String ou_name;
	
	@Column(name = "CONTROL_BEGIN_BALANCE")
	private Double ctrlBeginBalance;
	
	@Column(name = "CONTROL_TOTAL_CR")
	private Double ctrlTotalCr;
	
	@Column(name = "CONTROL_TOTAL_DR")
	private Double ctrlTotalDr;
	
	@Column(name = "CONTROL_END_BALANCE")
	private Double ctrlEndBalance;
	
	@Column(name = "HEADER_ID")
	private Long headerId;

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

	public BrsBankStmtHeaderStg(Long id, String bankAcNo, String statementNo, Date stmtDate, String ou_name,
			Double ctrlBeginBalance, Double ctrlTotalCr, Double ctrlTotalDr, Double ctrlEndBalance, Long headerId,
			Date creationDate, Date lastUpdateDate, String processFlag, Long stgIntProcessId, Long loaderProcessId,
			String errorMessage) {
		super();
		this.id = id;
		this.bankAcNo = bankAcNo;
		this.statementNo = statementNo;
		this.stmtDate = stmtDate;
		this.ou_name = ou_name;
		this.ctrlBeginBalance = ctrlBeginBalance;
		this.ctrlTotalCr = ctrlTotalCr;
		this.ctrlTotalDr = ctrlTotalDr;
		this.ctrlEndBalance = ctrlEndBalance;
		this.headerId = headerId;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.processFlag = processFlag;
		this.stgIntProcessId = stgIntProcessId;
		this.loaderProcessId = loaderProcessId;
		this.errorMessage = errorMessage;
	}

	public BrsBankStmtHeaderStg() {
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

	public Date getStmtDate() {
		return stmtDate;
	}

	public void setStmtDate(Date stmtDate) {
		this.stmtDate = stmtDate;
	}

	public String getOu_name() {
		return ou_name;
	}

	public void setOu_name(String ou_name) {
		this.ou_name = ou_name;
	}

	public Double getCtrlBeginBalance() {
		return ctrlBeginBalance;
	}

	public void setCtrlBeginBalance(Double ctrlBeginBalance) {
		this.ctrlBeginBalance = ctrlBeginBalance;
	}

	public Double getCtrlTotalCr() {
		return ctrlTotalCr;
	}

	public void setCtrlTotalCr(Double ctrlTotalCr) {
		this.ctrlTotalCr = ctrlTotalCr;
	}

	public Double getCtrlTotalDr() {
		return ctrlTotalDr;
	}

	public void setCtrlTotalDr(Double ctrlTotalDr) {
		this.ctrlTotalDr = ctrlTotalDr;
	}

	public Double getCtrlEndBalance() {
		return ctrlEndBalance;
	}

	public void setCtrlEndBalance(Double ctrlEndBalance) {
		this.ctrlEndBalance = ctrlEndBalance;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
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

	@Override
	public String toString() {
		return "BrsBankStmtHeaderStg [id=" + id + ", bankAcNo=" + bankAcNo + ", statementNo=" + statementNo
				+ ", stmtDate=" + stmtDate + ", ou_name=" + ou_name + ", ctrlBeginBalance=" + ctrlBeginBalance
				+ ", ctrlTotalCr=" + ctrlTotalCr + ", ctrlTotalDr=" + ctrlTotalDr + ", ctrlEndBalance=" + ctrlEndBalance
				+ ", headerId=" + headerId + ", creationDate=" + creationDate + ", lastUpdateDate=" + lastUpdateDate
				+ ", processFlag=" + processFlag + ", stgIntProcessId=" + stgIntProcessId + ", loaderProcessId="
				+ loaderProcessId + ", errorMessage=" + errorMessage + "]";
	}
	
	
	
}
