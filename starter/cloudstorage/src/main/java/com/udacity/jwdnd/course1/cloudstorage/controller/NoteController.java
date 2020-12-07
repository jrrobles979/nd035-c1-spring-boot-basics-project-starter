package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CloudStorageUserDetail;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;


    @PostMapping
    @RequestMapping("/notes")
    public String save(Authentication auth, Note note){
        CloudStorageUserDetail userDetail = (CloudStorageUserDetail) auth.getDetails();
        int res = -1;
        if( note.getNoteId() == null){
            res = noteService.createNote(  new Note(null, note.getTitle(), note.getDescription() ), userDetail.getUser().getUserId() );
        }else {
            res = noteService.saveChanges(note);
        }
        String  goTo ="redirect:/result?error";
        if(res > 0){
            goTo ="redirect:/result?success";
        }
        return goTo;
    }

    @GetMapping("/notes/delete")
    public String delete(Authentication auth, @RequestParam("id") int noteId ){

        if(noteId  > 0 ){
            noteService.delete(noteId);
            return "redirect:/result?success";
        }
        return "redirect:/result?error&errMsg=Invalid%20note%20id";
    }



}
