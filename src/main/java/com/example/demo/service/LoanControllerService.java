package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LoanListInterface;
import com.example.demo.dao.PaymentScheduleInterface;
import com.example.demo.model.LoanList;
import com.example.demo.model.PaymentSchedule;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoanControllerService {

	@Autowired
	private LoanListInterface loanListInterface;

	@Autowired
	private PaymentScheduleInterface paymentScheduleInterface;

	// helper method for create loan
	public boolean createLoanService(Map<String, Object> loan) {

		// it will get the loanDetails from the user and converted to Map.
		Map loanList = (Map) loan.get("loanDetails");

		// Get all the paymentCycles from the api and store into list.
		List paymentSchedule = (List) loan.get("paymentCycles");

		// Load the loanDetails into the database.
		LoanList loanLists = new LoanList(loanList);
		loanListInterface.save(loanLists);

		System.out.println(loanLists.getId() + " " + "id");

		// It will fetch each PaymentSchedule from list of PaymentSchedules
		// Loaded it into the database By attaching LoanId
		// to maintain Foreign Key relationship with loanDetails Table.
		for (int i = 0; i < paymentSchedule.size(); i++) {

			Map paymentReciept = (Map) paymentSchedule.get(i);
			paymentReciept.put("loanId", loanLists.getId());
			PaymentSchedule payment = new PaymentSchedule(paymentReciept);
			paymentScheduleInterface.save(payment);
			System.out.println(i);

		}
		System.out.println(loanList);

		return true;

	}

	// It will fetch the loan details alongwith paymentcycles from the database
	// and convert it into the api format.
	public List<Map> fetchLoanDetailsService(Map<String, Long> customer) {

		List<Map> loanApi = new ArrayList<Map>();

		// it converts object into Map.
		ObjectMapper mapper = new ObjectMapper();

		Long customerId = Long.parseLong(String.valueOf(customer.get("customerId")));

		// Get the list of list of customer loans.
		List<LoanList> loan = loanListInterface.findByCustomerId(customerId);

		if (loan == null) {
			return null;
		}

		for (int loanIndex = 0; loanIndex < loan.size(); loanIndex++) {

			// converted the loanlist object to map.
			Map<String, Object> jsonObject = mapper.convertValue(loan.get(loanIndex), Map.class);

			List<PaymentSchedule> paymentList = paymentScheduleInterface.findByLoanId((Long) jsonObject.get("id"));

			// It will create paymentcycles lists to each each loan.
			for (PaymentSchedule payment : paymentList) {

				if (jsonObject.get("paymentCycles") == null) {
					jsonObject.put("paymentCycles", new ArrayList());
					ArrayList paymentCycle = (ArrayList) jsonObject.get("paymentCycles");
					paymentCycle.add(payment);
				} else {
					ArrayList paymentCycle = (ArrayList) jsonObject.get("paymentCycles");
					paymentCycle.add(payment);
				}
			}

			loanApi.add(jsonObject);

		}

		return loanApi;
	}

}
