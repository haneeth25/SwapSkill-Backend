package com.project.swapskill_backend.Service.SecurityService;

import com.project.swapskill_backend.Config.UserPrincipal;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthenticationModel userAuthenticationModel = userAuthenticationModelRepo.findByUsername(username);
        return new UserPrincipal(userAuthenticationModel);
    }

}
