package com.manning.sbip.ch01.springbootappdemo.service.impl;

import com.manning.sbip.ch01.springbootappdemo.dto.UserDto;
import com.manning.sbip.ch01.springbootappdemo.entity.ApplicationUser;
import com.manning.sbip.ch01.springbootappdemo.repository.ApplicationUserRepository;
import com.manning.sbip.ch01.springbootappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ApplicationUser createUser(final UserDto userDto) {
        final ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName(userDto.getFirstName());
        applicationUser.setLastName(userDto.getLastName());
        applicationUser.setEmail(userDto.getEmail());
        applicationUser.setUsername(userDto.getUsername());
        applicationUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(applicationUser);
    }

    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
