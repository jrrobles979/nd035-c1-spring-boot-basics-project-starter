package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageUser;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CommonsLog
@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private UserService userService;



    @GetMapping()
    public String signupView() {
        return "signup";
    }


    @PostMapping()
    public String signupUser(@ModelAttribute CloudStorageUser user, Model model) {
        log.info("Register user: " + user.getUsername());
        String signupError = null;
        if( userService.userExist(user.getUsername()) ){
            signupError = "User already exist";
        }
        if (signupError == null){
            int success = userService.createUser(user);
            log.info("new user id:" +  success);
            if (success < 0){
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }


}
