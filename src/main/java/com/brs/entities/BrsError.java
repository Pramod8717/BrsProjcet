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
@Table(name = "UAT_XXCD_BRS_ERROR")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class BrsError {
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	Long id;
	
	@Column(name = "BANK_ACCOUNT_NO")
	String bankAccountNo;
	
	@Column(name = "UNIQUE_REFERENCE")
	String uniqueRef;
	
	@Column(name = "REMARK")
	String remark;
	
	@Column(name = "REF_ID")
	Long refId;
	
	@Column(name = "TBL_TYP")
	String tblTyp;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	public BrsError(Long id, String bankAccountNo, String uniqueRef, String remark, Long refId, String tblTyp,
			Date creationDate) {
		super();
		this.id = id;
		this.bankAccountNo = bankAccountNo;
		this.uniqueRef = uniqueRef;
		this.remark = remark;
		this.refId = refId;
		this.tblTyp = tblTyp;
		this.creationDate = creationDate;
	}

	public BrsError() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getUniqueRef() {
		return uniqueRef;
	}

	public void setUniqueRef(String uniqueRef) {
		this.uniqueRef = uniqueRef;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getTblTyp() {
		return tblTyp;
	}

	public void setTblTyp(String tblTyp) {
		this.tblTyp = tblTyp;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "BrsError [id=" + id + ", bankAccountNo=" + bankAccountNo + ", uniqueRef=" + uniqueRef + ", remark="
				+ remark + ", refId=" + refId + ", tblTyp=" + tblTyp + ", creationDate=" + creationDate + "]";
	}
	
	
	
	

}
