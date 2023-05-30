package com.meysam.Service;

import com.meysam.Model.User;
import com.meysam.Model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User createUser(User user);
  UserResponseModel getUserByUserID(String id);
}
