package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@CommonsLog
@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @PostConstruct
    public void postConstruct(){
        System.out.println("creating notes service...");
    }

    public int createNote(Note note, int userId){
        return this.noteMapper.add(note,userId);
    }

    public int saveChanges(Note note){
        return this.noteMapper.update(note);
    }

    /*public int save(NoteData note){
        int res = -1;
        if ( note.getNoteId() == null) {
           res = this.noteMapper.addNote(note);
        }else {
           res = this.noteMapper.updateNote(note);
        }
        return res;
    }*/

    public void delete(int noteId){
        this.noteMapper.delete(noteId);
    }

    public Note find(int noteId){
        return this.noteMapper.findByNoteId(noteId);
    }

    public List<Note> listByUser(int userId){
        log.info("get notes for:" + userId);
        /**TODO: remove this **/
        List<Note> notes = this.noteMapper.listByUserId(userId);
        System.out.println(notes);

        return this.noteMapper.listByUserId(userId);
    }

}
