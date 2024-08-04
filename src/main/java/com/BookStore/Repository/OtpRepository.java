package com.BookStore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookStore.Model.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
	Optional<Otp> findByMobileNumberAndOtpCode(String mobileNumber, String otpCode);
}