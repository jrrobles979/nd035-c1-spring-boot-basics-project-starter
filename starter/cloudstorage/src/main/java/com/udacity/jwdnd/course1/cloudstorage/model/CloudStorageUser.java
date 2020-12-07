package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CloudStorageUser {
    private Integer userId;
    @Size(min = 3, max = 20)
    private String username;
    private String salt;
    private String password;
    @Size(min = 3, max = 20)
    private String firstname;
    @Size(min = 3, max = 20)
    private String lastname;
    private boolean enabled = true;

    public CloudStorageUser(Integer userId, @Size(min = 3, max = 20) String username, String salt, String password, @Size(min = 3, max = 20) String firstname, @Size(min = 3, max = 20) String lastname) {
        this.userId = userId;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
