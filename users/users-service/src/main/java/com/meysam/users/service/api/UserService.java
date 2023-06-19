package com.meysam.users.service.api;

import com.meysam.common.model.dto.UserWalletsDto;
import com.meysam.common.model.entity.User;

import java.math.BigDecimal;

public interface UserService/* extends UserDetailsService*/ {
  User createUser(User user);
  User findByUserName(String username);
  User findById(BigDecimal id);
  UserWalletsDto getUserWallets(String username);
//  UserResponseModel getUserByUserID(String id);
}
