package com.BookStore.Service;

import org.springframework.http.ResponseEntity;

public interface LoginService {

	ResponseEntity<String> login(String username, String password, String email);
}