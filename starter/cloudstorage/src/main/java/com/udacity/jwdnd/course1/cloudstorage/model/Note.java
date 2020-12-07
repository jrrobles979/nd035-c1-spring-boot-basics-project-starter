package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class Note {
    private Integer noteId;
    @Size(min = 3, max = 20)
    private String title;
    @Size(max = 1000)
    private String description;

    public Note(Integer noteId, @Size(min = 3, max = 20) String title, @Size(max = 1000) String description) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
    }
}
