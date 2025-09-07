package com.ECommerce.FarmEcom.dto;

// This class will capture login request data
public class LoginAuthRequest {

    private String email;     // user email
    private String password;  // user password

    // default constructor (needed for JSON to object conversion)
    public LoginAuthRequest() {
    }

    // constructor with fields
    public LoginAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
