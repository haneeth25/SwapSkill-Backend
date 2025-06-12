package com.project.swapskill_backend.Service.SecurityService;

import com.project.swapskill_backend.DTO.PasswordResetToken;
import com.project.swapskill_backend.Exception.EmailNotFoundException;
import com.project.swapskill_backend.DTO.Request.UserLogin;
import com.project.swapskill_backend.DTO.Request.UserSignup;
import com.project.swapskill_backend.DTO.Response.LoginResponse;
import com.project.swapskill_backend.DTO.Response.SignupResponse;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import com.project.swapskill_backend.Repository.PasswordResetTokenRepo;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;
    @Autowired
    PasswordResetTokenRepo passwordResetTokenRepo;
    @Autowired
    private JavaMailSender mailSender;


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

    public void forgetPassword(String userEmail) {
        UserAuthenticationModel user = userAuthenticationModelRepo.findByEmail(userEmail);
        if(user == null){
            throw new EmailNotFoundException("User with email " + userEmail + " not found");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = passwordResetTokenRepo.findByUser(user)
                .map(existingToken -> {
                    existingToken.setToken(token);
                    existingToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(5));
                    return existingToken;
                })
                .orElseGet(() -> {
                    PasswordResetToken newToken = new PasswordResetToken();
                    newToken.setToken(token);
                    newToken.setUser(user);
                    newToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(5));
                    return newToken;
                });

        passwordResetTokenRepo.save(resetToken);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Reset your password");
            message.setText("Click the link to reset your password: " + resetLink);

            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send password reset email");
        }
    }


    public String resetPassword(Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        PasswordResetToken tokenOpt = passwordResetTokenRepo.findByToken(token).orElseThrow(()->new IllegalArgumentException("Invalid reset token"));
        if (tokenOpt.getExpiryDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset token Expired.");
        }

        UserAuthenticationModel user = tokenOpt.getUser();
        user.setPassword(newPassword);
        userAuthenticationModelRepo.save(user);

        passwordResetTokenRepo.delete(tokenOpt);

        return "Password successfully reset.";
    }

    public SignupResponse signup(UserSignup userSignup) {
        SignupResponse signupResponse = new SignupResponse();
        if(userAuthenticationModelRepo.findByEmail(userSignup.getEmail())!=null){
            signupResponse.setSignupResponse("Email exists");
            System.out.println(signupResponse.getSignupResponse());
            return signupResponse;
        }
        else if(userAuthenticationModelRepo.findByUsername(userSignup.getUsername()) != null){
            signupResponse.setSignupResponse("User exists");
            System.out.println(signupResponse.getSignupResponse());
            return signupResponse;

        }
        else {
            UserAuthenticationModel userAuthenticationModel = new UserAuthenticationModel();
            userAuthenticationModel.setId(UUID.randomUUID());
            userAuthenticationModel.setUsername(userSignup.getUsername());
            userAuthenticationModel.setPassword(userSignup.getPassword());
            userAuthenticationModel.setEmail(userSignup.getEmail());
            userAuthenticationModelRepo.save(userAuthenticationModel);
            signupResponse.setSignupResponse("User registerd");
            System.out.println(signupResponse.getSignupResponse());
            return signupResponse;
        }
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
