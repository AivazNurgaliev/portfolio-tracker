package com.ourproject.portfoliotracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String password) {
        passwordEncoder.encode(password);
        while (passwordEncoder.upgradeEncoding(password)) {
            passwordEncoder.encode(password);
        }
        return password;
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
