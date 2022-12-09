package com.manning.sbip.ch01.springbootappdemo.repository;

import com.manning.sbip.ch01.springbootappdemo.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(final String userName);
}
