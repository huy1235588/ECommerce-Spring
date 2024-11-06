package org.ha.ecommerce.service;

import org.ha.ecommerce.model.UserModel;
import org.ha.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
