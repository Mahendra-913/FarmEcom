package com.ECommerce.FarmEcom.controller;

import com.ECommerce.FarmEcom.auth.AuthRequest;
import com.ECommerce.FarmEcom.auth.AuthResponse;
import com.ECommerce.FarmEcom.model.User;
import com.ECommerce.FarmEcom.repository.UserRepository;
import com.ECommerce.FarmEcom.security.JwtUtil;
import jakarta.servlet.ServletOutputStream;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //  Login endpoint
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        // Authenticate user
        System.out.println("control flow");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Fetch role from DB
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + authRequest.getEmail()));

        System.out.println(">>> Authentication status: " + authentication.isAuthenticated());

        // Generate token
        String token = jwtUtil.generateToken(userDetails, user.getRole());
        System.out.println(token);
        return new AuthResponse(token);
    }

    //  Register endpoint
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully with email: " + user.getEmail();
    }
}


    //ye code bhi 403 error de rha h


//gemini is in game now
//
//package com.ECommerce.FarmEcom.controller;
//
//import com.ECommerce.FarmEcom.auth.AuthRequest;
//import com.ECommerce.FarmEcom.auth.AuthResponse;
//import com.ECommerce.FarmEcom.model.User;
//import com.ECommerce.FarmEcom.repository.UserRepository;
//import com.ECommerce.FarmEcom.security.JwtUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping
//public class AuthController {
//
//    // ... aapke baaki variables ...
//
//    public AuthController(AuthenticationManager authenticationManager,
//                          JwtUtil jwtUtil,
//                          UserRepository userRepository,
//                          PasswordEncoder passwordEncoder) {
//        // ... aapka constructor ...
//    }
//
//    //  Login endpoint
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody AuthRequest authRequest) {
//        //  LOG #1: Check if the controller method is being called
//        System.out.println(">>> [AuthController] /login endpoint hit. Attempting to authenticate user: " + authRequest.getEmail());
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
//            );
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            User user = userRepository.findByEmail(authRequest.getEmail())
//                    .orElseThrow(() -> new RuntimeException("User not found: " + authRequest.getEmail()));
//
//            //  LOG #2: Check if authentication was successful
//            System.out.println(">>> [AuthController] Authentication successful for user: " + authRequest.getEmail());
//
//            String token = jwtUtil.generateToken(userDetails, user.getRole());
//            return new AuthResponse(token);
//
//        } catch (Exception e) {
//            // LOG #3: VERY IMPORTANT - See what error is thrown on failure
//            System.err.println(">>> [AuthController] Authentication failed! Reason: " + e.getMessage());
//            throw e; // Re-throw the exception so Spring can handle it
//        }
//    }
//
//    // ... register method ...
//}