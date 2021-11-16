package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.LoanListInterface;
import com.example.demo.dao.PaymentScheduleInterface;
import com.example.demo.dao.UserInterface;
import com.example.demo.model.LoanList;
import com.example.demo.model.PaymentSchedule;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthControllerService {

	@Autowired
	private UserInterface userInterface;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// helper method for Signup.
	public boolean signUpService(User user) {
		
		/*
		 * return true if successfully registered. if email already exists will returns
		 * false.
		 */
		if (userInterface.findByEmail(user.getEmail()) == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userInterface.save(user);
			return true;
		}
		
		return false;
	}

	
}
