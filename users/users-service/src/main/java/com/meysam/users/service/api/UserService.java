package com.meysam.users.service.api;

import com.meysam.common.model.dto.UserWalletsDto;
import com.meysam.common.model.entity.User;

public interface UserService/* extends UserDetailsService*/ {
  User createUser(User user);
  User findByUserName(String username);
  UserWalletsDto getUserWallets(String username);
//  UserResponseModel getUserByUserID(String id);
}
