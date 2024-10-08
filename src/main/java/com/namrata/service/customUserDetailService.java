package com.namrata.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.namrata.model.CustomUserDetails;

import com.namrata.model.User;
import com.namrata.repository.UserRepository;

@Service
public class customUserDetailService implements UserDetailsService {

	
	@Autowired
	
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	  Optional<User> user= userRepository.findUserByEmail(email);
	  user.orElseThrow(()->new UsernameNotFoundException("User not found"));
		return user.map(CustomUserDetails::new).get();
		
	}

}
