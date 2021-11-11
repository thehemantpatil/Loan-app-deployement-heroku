package com.example.demo.modal;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class LoanList {

	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private Long customerId;
	private String loanReason;
	private Date tradeDate;
	private Date maturityDate;
	private String paymentFrequency;
	private String paymentTerm;
	private double interestRate;
	private double loanAmount;
	private double totalAmount;
	
	public LoanList() {
		
	}
	public LoanList(Map loan) {

		super();

		this.tradeDate = new Date();
		String maturityDate = loan.get("maturityDate").toString();
		int year = Integer.parseInt(maturityDate.substring(0, 4));
		int month = Integer.parseInt(maturityDate.substring(5, 7));
		int day = Integer.parseInt(maturityDate.substring(8, 10));

		System.out.println(year + " " + "year");

		Date maturity = new Date(year - 1900, month - 1, day);

		System.out.println(loan.get("customerId") + " " + loan.get("customerId").getClass().getSimpleName());
		System.out.println(loan.get("maturityDate").getClass().getSimpleName());
		
		this.customerId = Long.parseLong(String.valueOf(loan.get("customerId")));
		this.loanReason = (String) loan.get("loanReason");
		this.maturityDate = maturity;
		this.paymentFrequency = (String) loan.get("paymentFrequency");
		this.paymentTerm = (String) loan.get("paymentTerm");
		this.interestRate = Double.parseDouble(String.valueOf(loan.get("interestRate")));
		this.loanAmount = Double.parseDouble(String.valueOf(loan.get("loanAmount")));
		this.totalAmount = Double.parseDouble(String.valueOf(loan.get("totalAmount")));
	}

	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getLoanReason() {
		return loanReason;
	}

	public void setLoanReason(String loanReason) {
		this.loanReason = loanReason;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}
 
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	
	@Override
	public String toString() {
		return "LoanList [id=" + id + ", customerId=" + customerId + ", loanReason=" + loanReason + ", tradeDate="
				+ tradeDate + ", maturityDate=" + maturityDate + ", paymentFrequency=" + paymentFrequency
				+ ", paymentTerm=" + paymentTerm + ", interestRate=" + interestRate + ", loanAmount=" + loanAmount
				+ "]";
	}	

}
