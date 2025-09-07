package com.ECommerce.FarmEcom.service;

import com.ECommerce.FarmEcom.model.User;
import com.ECommerce.FarmEcom.repository.UserRepository;
import com.ECommerce.FarmEcom.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This service is used by Spring Security to load user details for authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor-based dependency injection
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security calls this method when a user tries to log in
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 🔹 Treat username as email
        System.out.println(">>> Inside loadUserByUsername for: " + username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}
