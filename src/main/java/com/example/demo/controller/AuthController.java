package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserInterface;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImplement;
import com.example.demo.security.UserDetailsServiceImplementation;
import com.example.demo.service.AuthControllerService;
import com.example.demo.service.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthControllerService controllerService;

	@Autowired
	private UserInterface userInterface;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImplementation userDetailsServiceImplementation;
	
	@PostMapping("/signup")
	public Optional<Map> createUser(@RequestBody User user) {
		try {
			boolean serviceResponse = controllerService.signUpService(user);
			UserDetailsImplement userDetails = new UserDetailsImplement(user);

			if (serviceResponse) {
				String token = jwtUtil.generateToken(userDetails, user);
				return Optional.of(new HashMap(Map.of("token", token, "user", user)));
			}

		} catch (Exception e) {
			return Optional.ofNullable(null);
		}
		return Optional.ofNullable(null);
	}


	@PostMapping("/login")
	public Optional<Map> getCredentials(@RequestBody User user) {
		System.out.println("Hemant Patil" + " my username is " + user.getEmail());
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername(user.getEmail());
			User newUser = userInterface.findByEmail(user.getEmail());
			String token = jwtUtil.generateToken(userDetails, newUser);
			return Optional.of(new HashMap(Map.of("token", token)));
		}
	catch(Exception e)
	{
			return Optional.ofNullable(null);
		}

}
}
