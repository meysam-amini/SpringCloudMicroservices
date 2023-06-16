package com.meysam.common.service.api;

import com.meysam.common.model.entity.User;

public interface UserService/* extends UserDetailsService*/ {
  User createUser(User user);
  User findByUserName(String username);
//  UserResponseModel getUserByUserID(String id);
}
