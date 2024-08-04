package com.BookStore.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OTP_MASTER")
public class Otp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mobileNumber;
	private String otpCode;
	private LocalDateTime expiryTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Otp(Long id, String mobileNumber, String otpCode, LocalDateTime expiryTime) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.otpCode = otpCode;
		this.expiryTime = expiryTime;
	}

	public Otp() {
		super();
	}

	@Override
	public String toString() {
		return "Otp [id=" + id + ", mobileNumber=" + mobileNumber + ", otpCode=" + otpCode + ", expiryTime="
				+ expiryTime + "]";
	}
}