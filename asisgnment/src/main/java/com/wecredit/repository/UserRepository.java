package com.wecredit.repository;

import com.wecredit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByMobileNumber(String phoneNumber);
}