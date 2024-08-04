package com.BookStore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookStore.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);

	Optional<User> findByUsername(String username);

	Optional<User> findByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByMobileNumber(String mobileNumber);
}
