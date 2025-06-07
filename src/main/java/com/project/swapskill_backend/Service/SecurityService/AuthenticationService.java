package com.project.swapskill_backend.Service.SecurityService;

import com.project.swapskill_backend.DTO.Request.UserLogin;
import com.project.swapskill_backend.DTO.Request.UserSignup;
import com.project.swapskill_backend.DTO.Response.LoginResponse;
import com.project.swapskill_backend.DTO.Response.SignupResponse;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;


@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;



    public String verify(UserAuthenticationModel userAuthenticationModel) throws NoSuchAlgorithmException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userAuthenticationModel.getUsername(), userAuthenticationModel.getPassword()
            ));

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(userAuthenticationModel.getUsername());
            }
        } catch (BadCredentialsException e) {
            return "Invalid user";
        } catch (Exception e) {
            return "Authentication Failed" + e.getMessage();
        }
        return "Authentication Failed";
    }

    public SignupResponse signup(UserSignup userSignup) {
        UserAuthenticationModel userAuthenticationModel = new UserAuthenticationModel();
        userAuthenticationModel.setId(UUID.randomUUID());
        userAuthenticationModel.setUsername(userSignup.getUsername());
        userAuthenticationModel.setPassword(userSignup.getPassword());
        userAuthenticationModel.setEmail(userSignup.getEmail());
        userAuthenticationModelRepo.save(userAuthenticationModel);
        SignupResponse signupResponse = new SignupResponse();
        signupResponse.setSignupResponse("User registerd");
        return signupResponse;
    }

    public LoginResponse login(UserLogin userLogin) throws NoSuchAlgorithmException {
        LoginResponse loginResponse = new LoginResponse();
        if(userAuthenticationModelRepo.findByUsername(userLogin.getUsername())!= null){
            UserAuthenticationModel userAuthenticationModel = new UserAuthenticationModel();
            userAuthenticationModel.setUsername(userLogin.getUsername());
            userAuthenticationModel.setPassword(userLogin.getPassword());
            String result = this.verify(userAuthenticationModel);
            loginResponse.setJwtToken(result);
        }
        return loginResponse;
    }
}
