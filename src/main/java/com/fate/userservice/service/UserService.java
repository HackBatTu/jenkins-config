package com.fate.userservice.service;

import com.fate.userservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);

    List<User> getAllUser();

    User getUser(Long userId);
}
