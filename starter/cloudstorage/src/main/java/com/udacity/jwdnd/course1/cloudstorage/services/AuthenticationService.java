package com.udacity.jwdnd.course1.cloudstorage.services;


import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@CommonsLog
@Service
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    private HashService hashService;
    @Autowired
    private UserService userService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Login in: " +  authentication.getName());
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try{
            CloudStorageUserDetail userDetails = (CloudStorageUserDetail) userService.loadUserByUsername(username);
            String encodedSalt = userDetails.getUser().getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if (userDetails.getUser().getPassword().equals(hashedPassword)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
                token.setDetails(userDetails);
                return token;
            }

        } catch (UsernameNotFoundException usernameNotFoundException){
            log.info("User not found");
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
