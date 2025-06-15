package com.project.swapskill_backend.Controller;

import com.project.swapskill_backend.DTO.Request.UserLogin;
import com.project.swapskill_backend.DTO.Request.UserSignup;
import com.project.swapskill_backend.DTO.Response.LoginResponse;
import com.project.swapskill_backend.DTO.Response.SignupResponse;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import com.project.swapskill_backend.Service.SecurityService.AuthenticationService;
import com.project.swapskill_backend.Service.SecurityService.CustomUserDetailService;
import com.project.swapskill_backend.Service.SecurityService.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public SignupResponse signUp(@RequestBody UserSignup userSignup){
        log.info("Received request at '/signup' endpoint.");
        return authenticationService.signup(userSignup);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLogin userLogin) throws NoSuchAlgorithmException {
        log.info("Received request at '/login' endpoint.");
        return authenticationService.login(userLogin);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> request) throws NoSuchAlgorithmException{
        authenticationService.forgetPassword(request.get("email"));
        return ResponseEntity.ok("Reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request){
        return ResponseEntity.ok(authenticationService.resetPassword(request));
    }

}
