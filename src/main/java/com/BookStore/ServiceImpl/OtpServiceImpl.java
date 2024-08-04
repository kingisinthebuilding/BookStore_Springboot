package com.BookStore.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.BookStore.Model.Otp;
import com.BookStore.Repository.OtpRepository;
import com.BookStore.Service.OtpService;
import com.BookStore.Service.SmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private SmsService smsService;

	@Value("${otp.length}")
	private int otpLength;

	@Value("${twilio.phone.number}")
	private String fromPhoneNumber;

	@Value("${otp.expiry.minutes}")
	private int otpExpiryMinutes;

	public String generateOtp(String mobileNumber) {
		String otpCode = generateRandomOtp();
		LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

		Otp otp = new Otp();
		otp.setMobileNumber(mobileNumber);
		otp.setOtpCode(otpCode);
		otp.setExpiryTime(expiryTime);

		otpRepository.save(otp);

		// Send OTP to mobileNumber (use an SMS service)
		smsService.sendSms(mobileNumber, "Your BookStore Verification OTP is: " + otpCode);

		return otpCode;
	}

	public boolean verifyOtp(String mobileNumber, String otpCode) {
		Optional<Otp> otpOptional = otpRepository.findByMobileNumberAndOtpCode(mobileNumber, otpCode);
		if (otpOptional.isPresent()) {
			Otp otp = otpOptional.get();
			if (otp.getExpiryTime().isAfter(LocalDateTime.now())) {
				return true;
			} else {
				otpRepository.delete(otp); // Expired OTP
			}
		}
		return false;
	}

	@Override
	public String generateRandomOtp() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder(otpLength);
		for (int i = 0; i < otpLength; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}

	public void sendSms(String to, String message) {
		Message.creator(new PhoneNumber(to), // To number
				new PhoneNumber(fromPhoneNumber), // From number (Twilio number)
				message).create();
	}

}
