package com.ECommerce.FarmEcom.dto;

// This class will return JWT token to client after successful login
public class LoginAuthResponse {

    private String token;  // JWT token string

    // default constructor
    public LoginAuthResponse() {
    }

    // constructor with token
    public LoginAuthResponse(String token) {
        this.token = token;
    }

    // getter and setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
