package com.project.swapskill_backend.Service.SecurityService;

import com.project.swapskill_backend.Model.UserAuthenticationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;


@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

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
}
