
package com.brs.util;

import java.util.Date;

public class BrsConsoldtStgDTO {

	private String bankAcNo;
	private Date voucherDate;
	private String processFlag;
	private String status;
	private String errorMsg;
	private String cheque_status;
	public BrsConsoldtStgDTO() {
		// TODO Auto-generated constructor stub
	}

	public BrsConsoldtStgDTO(String bankAcNo, Date voucherDate, String processFlag, String status, String errorMsg) {
		this.bankAcNo = bankAcNo;
		this.voucherDate = voucherDate;
		this.processFlag = processFlag;
		this.status = status;
		this.errorMsg = errorMsg;
		this.cheque_status = cheque_status;
	}

	public BrsConsoldtStgDTO(String chequeStatus) {
		// TODO Auto-generated constructor stub
	}

	public String getCheque_status() {
		return cheque_status;
	}

	public void setCheque_status(String cheque_status) {
		this.cheque_status = cheque_status;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

//	@Override
//	public String toString() {
//		return "BrsConsoldtStgDTO [bankAcNo=" + bankAcNo + ", voucherDate=" + voucherDate + ", processFlag="
//				+ processFlag + ", status=" + status + ", errorMsg=" + errorMsg + "]";
//	}
	

}