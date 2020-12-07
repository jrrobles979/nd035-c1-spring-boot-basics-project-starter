package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Credential {
    private Integer credentialId;
    @Size( max = 100)
    private String url;
    @Size(min = 3, max = 30)
    private String username;
    private String key;
    private String password;
    private String decriptedPassword;

    public Credential(Integer credentialId, @Size(max = 100) String url, @Size(min = 3, max = 30) String username, String key, String password, String decriptedPassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.decriptedPassword = decriptedPassword;
    }
}
