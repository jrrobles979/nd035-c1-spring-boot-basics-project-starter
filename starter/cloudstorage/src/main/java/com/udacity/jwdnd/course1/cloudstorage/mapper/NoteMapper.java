package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) " +
            "VALUES(#{newNote.title},#{newNote.description},#{userId})")
    int add(Note newNote, int userId);

    @Update("UPDATE NOTES " +
            "SET notetitle=#{title}, " +
            "notedescription=#{description} " +
            "WHERE noteid=#{noteId};")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    void delete(int noteId);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> listByUserId(int userId);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId}")
    Note findByNoteId(int noteId);


}
