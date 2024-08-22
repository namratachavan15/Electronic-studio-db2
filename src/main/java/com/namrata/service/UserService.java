package com.namrata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namrata.model.User;
import com.namrata.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
    	User user = userRepository.findByEmail(email);
        logger.info("Fetched User: {}", user);
        return user;
    }
}
