package org.ha.ecommerce.service;

import org.ha.ecommerce.model.UserModel;
import org.ha.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel getUserByEmailIgnoreCase(String email) {
        return userRepository.getUserByEmailIgnoreCase(email);
    }

    public UserModel getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
