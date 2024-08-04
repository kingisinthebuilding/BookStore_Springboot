package com.BookStore.Service;

public interface OtpService {

	public String generateOtp(String mobileNumber);
	
	public boolean verifyOtp(String mobileNumber, String otpCode);
	
	public String generateRandomOtp();
}