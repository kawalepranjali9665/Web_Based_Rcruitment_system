
  package com.app.service;
  
  import com.app.pojos.User;
  
  public interface UserService { public int generateOtp();
  
  public User findByEmail(String email);
  
  void updatePassword(User user);
  
  }
 