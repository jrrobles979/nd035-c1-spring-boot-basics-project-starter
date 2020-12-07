package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageFile;
import com.udacity.jwdnd.course1.cloudstorage.services.CloudStorageUserDetail;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CommonsLog
@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/files")
    public String saveFile(Authentication authentication, MultipartFile fileUpload) throws IOException {

        CloudStorageUserDetail userDetail = (CloudStorageUserDetail) authentication.getDetails();
        String goTo = "redirect:/result?error";
        try{
            if(fileUpload != null && !fileUpload.isEmpty()){
                int res = fileService.save( fileUpload  , userDetail.getUser().getUserId() );
                if(res>0) {
                    goTo =  "redirect:/result?success";
                }
            }
        } catch (IOException ioException){
            log.error("There was an error reading the file");
        }

        return goTo;
    }

    @GetMapping("/files/delete")
    public String delete(Authentication auth, @RequestParam("id") int fileId ){

        if(fileId  > 0 ){
            fileService.delete(fileId);
            return "redirect:/result?success";
        }
        return "redirect:/result?error&errMsg=Invalid%file%20id";
    }

    /*@GetMapping("/files/download")
    public byte[] download(Authentication auth, @RequestParam("id") int fileId ) throws Exception{
        byte[] file = null;
        if(fileId  > 0 ){
            file =  fileService.downloadFile(fileId);
        }
        return file;
    }*/

    @GetMapping("/files/download")
    public HttpEntity<byte[]> download(Authentication auth, @RequestParam("id") int fileId) throws IOException {

        CloudStorageFile file = null;
        if(fileId  > 0 ){
            file =  fileService.downloadFile(fileId);
        }
        if (file == null)
        {
            return null;
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType( MediaType.valueOf(file.getContentType()) );
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getFileName().replace(" ", "_"));
        header.setContentLength(Long.valueOf(file.getFileSize()));

        return new HttpEntity<byte[]>(file.getFileData(), header);
    }



}
