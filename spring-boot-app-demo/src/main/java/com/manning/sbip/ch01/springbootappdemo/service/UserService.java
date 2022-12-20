package com.manning.sbip.ch01.springbootappdemo.service;

import com.manning.sbip.ch01.springbootappdemo.dto.UserDto;
import com.manning.sbip.ch01.springbootappdemo.entity.ApplicationUser;

public interface UserService {

    ApplicationUser createUser(final UserDto dto);
    ApplicationUser findByUsername(final String username);
}
