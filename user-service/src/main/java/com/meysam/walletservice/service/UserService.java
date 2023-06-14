package com.meysam.walletservice.service;

import com.meysam.common.entity.User;
import com.meysam.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User createUser(User user);
  UserResponseModel getUserByUserID(String id);
}
