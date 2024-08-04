package com.BookStore.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BookStore.Model.User;
import com.BookStore.Repository.UserRepository;
import com.BookStore.Service.LoginService;
import com.BookStore.Util.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<String> login(String username, String password, String email) {
	    try {
	        Optional<User> userOptional = userRepository.findByEmail(email);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();

	            // Check if the username and password matches
	            if (user.getUsername().equals(username) && passwordEncoder.matches(password, user.getPassword())) {
	                // Generate JWT token
	                String token = jwtUtil.generateToken(username); // Use only username for token
	                return ResponseEntity.ok("Login successful. Token: " + token);
	            } else {
	                // Authentication failed
	                return ResponseEntity.status(401).body("Invalid username or password");
	            }
	        } else {
	            // User not found
	            return ResponseEntity.status(404).body("User not found");
	        }
	    } catch (Exception e) {
	        // Log the exception details for debugging
	        // For example, using a logger: logger.error("An error occurred during login", e);
	        return ResponseEntity.status(500).body("An error occurred during login: " + e.getMessage());
	    }
	}
}