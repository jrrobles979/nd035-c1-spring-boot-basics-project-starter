package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class CloudStorageFile {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private byte[] fileData;

    public CloudStorageFile(Integer fileId, String fileName, String contentType, String fileSize, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }
}
