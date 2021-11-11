package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.LoanList;
import com.example.demo.modal.LoanListInterface;
import com.example.demo.modal.PaymentSchedule;
import com.example.demo.modal.PaymentScheduleInterface;
import com.example.demo.modal.User;
import com.example.demo.modal.UserInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoanController {

	@Autowired
	private UserInterface userInterface;

	@Autowired
	private LoanListInterface loanListInterface;

	@Autowired
	private PaymentScheduleInterface paymentScheduleInterface;
	
	@GetMapping("url-checking")
	public String urlChecking() {
			return "Coming";
	}
	@GetMapping("fetch-loan")
	public Optional<List<Map>> fetchLoanDetails(@RequestParam Map<String, Long> customer) {
		List<Map> loanApi = new ArrayList<Map>();

		ObjectMapper mapper = new ObjectMapper();
		Long customerId = Long.parseLong(String.valueOf(customer.get("customerId")));
		List<LoanList> loan = loanListInterface.findByCustomerId(customerId);
		System.out.println(loan);

		if (loan == null) {
			return Optional.ofNullable(null);
		}

		for (int loanIndex = 0; loanIndex < loan.size(); loanIndex++) {
			Map<String, Object> jsonObject = mapper.convertValue(loan.get(loanIndex), Map.class);
			List<PaymentSchedule> paymentList = paymentScheduleInterface.findByLoanId((Long) jsonObject.get("id"));
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
		System.out.println(loanApi);
		return Optional.ofNullable(loanApi);
	}

//	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("create-loan")
	public boolean createLoan(@RequestBody Map<String, Object> loan) {

		Map loanList = (Map) loan.get("loanDetails");
		List paymentSchedule = (List) loan.get("paymentCycles");

		LoanList loanLists = new LoanList(loanList);
		loanListInterface.save(loanLists);

		System.out.println(loanLists.getId() + " " + "id");

		for (int i = 0; i < paymentSchedule.size(); i++) {
			Map paymentReciept = (Map) paymentSchedule.get(i);
			paymentReciept.put("loanId", loanLists.getId());
			PaymentSchedule payment = new PaymentSchedule(paymentReciept);
			paymentScheduleInterface.save(payment);
			System.out.println(i);
		}

		return true;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("signup")
	public Optional<User> createUser(@RequestBody User user) {
		System.out.println("signUp coming");
		System.out.println(user.toString());
		if (userInterface.findByEmail(user.getEmail()) == null) {
			userInterface.save(user);
			return Optional.of(user);
		}
		return Optional.ofNullable(null);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("login")
	public Optional<User> getCredentials(@RequestBody User user) {

		User userDetails = userInterface.findByEmail(user.getEmail());

		if (userDetails != null && userDetails.getPassword().equals(user.getPassword())) {
			System.out.println(userDetails.getPassword() + " " + user.getPassword());
			return Optional.of(userDetails);
		}
		return Optional.ofNullable(null);
	}

}
