package com.bank.user.service.impl;

import com.bank.user.dto.UserRegisterDto;
import com.bank.user.entity.User;
import com.bank.user.repository.UserRepo;
import com.bank.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        User user = User.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .email(userRegisterDto.getEmail())
                .mobNumber(userRegisterDto.getMobNumber())
                .build();
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }
}
