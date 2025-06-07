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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public SignupResponse signUp(@RequestBody UserSignup userSignup){
        return authenticationService.signup(userSignup);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLogin userLogin) throws NoSuchAlgorithmException {
        return authenticationService.login(userLogin);
    }

}
