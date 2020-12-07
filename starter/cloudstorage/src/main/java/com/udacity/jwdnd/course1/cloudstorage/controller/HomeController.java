package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageFile;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Log
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private FileService fileService;



    @GetMapping()
    public String loginView(Authentication authentication, Model model) {
        CloudStorageUserDetail details = (CloudStorageUserDetail) authentication.getDetails();
        ArrayList<Note> notes = new ArrayList<>(noteService.listByUser( details.getUser().getUserId()));
        ArrayList<Credential> credentials = new ArrayList<>(credentialService.listByUser(details.getUser().getUserId()));
        ArrayList<CloudStorageFile> files = new ArrayList<>(fileService.listByUser(details.getUser().getUserId()));
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("files", files);
        return "home";
    }






}
