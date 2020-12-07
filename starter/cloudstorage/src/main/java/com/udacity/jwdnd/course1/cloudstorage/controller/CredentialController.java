package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CloudStorageUserDetail;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @PostMapping
    @RequestMapping("/credentials")
    public String save(Authentication authentication, Credential credential){
        CloudStorageUserDetail userDetail = (CloudStorageUserDetail) authentication.getDetails();
        int res = -1;
        if( credential.getCredentialId() == null){
            res = credentialService.add( credential, userDetail.getUser().getUserId() );
        }else {
            res = credentialService.update(credential);
        }
        String  goTo ="redirect:/result?error";
        if(res > 0){
            goTo ="redirect:/result?success";
        }
        return goTo;

    }

    @GetMapping("/credentials/delete")
    public String delete(Authentication authentication, @RequestParam("id") int credentialId){

        if(credentialId  > 0 ){
            credentialService.delete(credentialId);
            return "redirect:/result?success";
        }

        return "redirect:/result?error&errMsg=Invalid%20credential%20id";
    }


}
