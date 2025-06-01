package com.bank.user.service;

import com.bank.user.dto.UserRegisterDto;
import com.bank.user.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserRegisterDto userRegisterDto);
    List<User> getAllUser();
}
