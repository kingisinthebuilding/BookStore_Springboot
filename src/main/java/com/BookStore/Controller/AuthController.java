package com.BookStore.Controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BookStore.Model.User;
import com.BookStore.Repository.UserRepository;
import com.BookStore.Service.LoginService;
import com.BookStore.Service.OtpService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		System.out.println("Starting registration process for: " + user.toString());

		// Check if the user already exists by email, username, or mobile number
		boolean emailExists = userRepository.existsByEmail(user.getEmail());
		boolean usernameExists = userRepository.existsByUsername(user.getUsername());
		boolean mobileNumberExists = userRepository.existsByMobileNumber(user.getMobileNumber());

		System.out.println("Email exists: " + emailExists);
		System.out.println("Username exists: " + usernameExists);
		System.out.println("Mobile number exists: " + mobileNumberExists);

		if (emailExists || usernameExists || mobileNumberExists) {
			return ResponseEntity.status(409)
					.body("User is already registered with provided email, username, or mobile number.");
		}

		// Proceed with registration
		user.setVerified(false);
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password
		userRepository.save(user);
		System.out.println("USER saved: " + user.toString());

		String otp = otpService.generateOtp(user.getMobileNumber());
		System.out.println("OTP generated: " + otp);
		// Send OTP to the user's mobile number using an SMS service

		return ResponseEntity.ok("User registered. OTP sent to mobile number.");
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam String mobileNumber, @RequestParam String otpCode) {
		System.out.println("mobileNumber " + mobileNumber);
		System.out.println("otpCode " + otpCode);
		boolean isVerified = otpService.verifyOtp(mobileNumber, otpCode);
		if (isVerified) {
			User user = userRepository.findByMobileNumber(mobileNumber)
					.orElseThrow(() -> new RuntimeException("User not found"));
			user.setVerified(true);
			userRepository.save(user);
			return ResponseEntity.ok("OTP verified. User is now verified.");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password,
			@RequestParam String email) {
		ResponseEntity<String> userDetails = loginService.login(username, password, email);
		System.out.println("UserDetails " + userDetails);
		return userDetails;
	}
}