package com.example.demo.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dao.UserInterface;
import com.example.demo.service.LoanControllerService;


@RestController
@RequestMapping("loan")
public class LoanController {

	@Autowired
	private LoanControllerService loanService;

	@Autowired
	private UserInterface userInterface;


	@GetMapping("/fetch-loan") 
	public Optional<List<Map>> fetchLoanDetails(@RequestParam Map<String, Long> customer) {
		List<Map> loanApi = loanService.fetchLoanDetailsService(customer);
		return Optional.ofNullable(loanApi);
	}

	@PostMapping("/create-loan")
	public boolean createLoan(@RequestBody Map<String, Object> loan) {
		boolean serviceResponse = loanService.createLoanService(loan);
		return true;
	}

}
