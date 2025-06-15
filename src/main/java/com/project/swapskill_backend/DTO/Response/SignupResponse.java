package com.project.swapskill_backend.DTO.Response;

public class SignupResponse {
    private String signupResponse;
    private String username;
    private String password;
    private String jwtToken;

    public String getSignupResponse() {
        return signupResponse;
    }

    public void setSignupResponse(String signupResponse) {
        this.signupResponse = signupResponse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
