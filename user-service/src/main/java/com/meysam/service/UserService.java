package com.meysam.service;

import com.meysam.model.User;
import com.meysam.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User createUser(User user);
  UserResponseModel getUserByUserID(String id);
}
