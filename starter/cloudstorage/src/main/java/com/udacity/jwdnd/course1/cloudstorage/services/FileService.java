package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    @PostConstruct
    public void postConstruct(){
        System.out.println("creating files service...");
    }



    public int save(MultipartFile file, int userId) throws IOException {


            CloudStorageFile newFile = new CloudStorageFile(null,
                                                                file.getOriginalFilename(),
                                                                file.getContentType(),
                                                                String.valueOf(file.getSize()),
                                                                file.getBytes() );
            return this.fileMapper.add(newFile, userId);
    }

    public void delete(int fileId){
        this.fileMapper.delete(fileId);
    }

    public CloudStorageFile find(int fileId){
        return this.fileMapper.findById(fileId);
    }

    public List<CloudStorageFile> listByUser(int userId){
        return this.fileMapper.listByUser(userId);
    }

    public CloudStorageFile downloadFile(int fileId){
        return this.fileMapper.downloadFile(fileId);
    }


}
