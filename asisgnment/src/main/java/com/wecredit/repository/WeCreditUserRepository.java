package com.wecredit.repository;

import com.wecredit.model.WeCreditUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeCreditUserRepository extends JpaRepository<WeCreditUser, Integer> {

    WeCreditUser findByMobileNumber(String mobileNumber);
}
