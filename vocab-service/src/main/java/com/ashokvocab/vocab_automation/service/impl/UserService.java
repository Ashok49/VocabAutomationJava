package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.User;
import com.ashokvocab.vocab_automation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByGoogleUserId(String userid) {
        return userRepository.findByUserid(userid);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void saveUserIfNotExists(String googleUserId, String email, String name, String picture) {
        Optional<User> existingUser = userRepository.findByUserid(googleUserId);
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setUserid(googleUserId);
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPicture(picture);
            userRepository.save(newUser);
        }
    }
}
