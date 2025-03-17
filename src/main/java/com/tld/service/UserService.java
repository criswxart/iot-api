package com.tld.service;

import java.util.Optional;

import com.tld.model.Users;

public interface UserService {
	
	 Users registerUser(Users user);

	 Optional<Users> findByUsername(String username);

	 void updatePassword(Users user, String newPassword);
}
