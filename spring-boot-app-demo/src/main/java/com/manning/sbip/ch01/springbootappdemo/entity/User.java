package com.manning.sbip.ch01.springbootappdemo.entity;

import com.manning.sbip.ch01.springbootappdemo.validations.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    private String userName;
    @Password
    private String password;
}
