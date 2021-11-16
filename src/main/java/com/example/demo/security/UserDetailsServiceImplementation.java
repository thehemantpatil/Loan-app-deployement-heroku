package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.dao.UserInterface;
import com.example.demo.model.User;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserInterface userInterface;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userInterface.findByEmail(username);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException(username + "Not Found");
		}

		UserDetailsImplement userDetailsImplement = new UserDetailsImplement(user);

		return userDetailsImplement;
	}

}
