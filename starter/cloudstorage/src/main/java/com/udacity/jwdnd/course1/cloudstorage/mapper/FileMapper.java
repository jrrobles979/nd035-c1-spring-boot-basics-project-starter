package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) " +
            "VALUES( #{file.fileName}, #{file.contentType}, #{file.fileSize}, #{userId} , #{file.fileData});")
    int add(CloudStorageFile file, int userId);

    //@Update("")
    //void updateFile(FileData file);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId};")
    void delete(int fileId);

    @Select("SELECT fileId, filename, contenttype, filesize, userid " +
            "FROM FILES " +
            "WHERE fileid=#{fileId};")
    CloudStorageFile findById(int fileId);

    @Select("SELECT fileid,filename,contenttype,filesize,userid " +
            "FROM FILES " +
            "WHERE userid=#{userId}")
    List<CloudStorageFile> listByUser(int userId);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId};")
    CloudStorageFile downloadFile(int fileId);
}
