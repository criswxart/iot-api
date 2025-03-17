package com.tld.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tld.jpa.repository.UserRepository;
import com.tld.model.Users;
import com.tld.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users registerUser(Users user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
       return userRepository.save(user);
    	
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public void updatePassword(Users user, String newPassword) {
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
