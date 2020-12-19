package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.io.File;
import java.net.URL;

public class TestConstants {
    public static final String DEFAULT_USERNAME = "jrobles";
    public static final String DEFAULT_PASSWORD = "123456";

    public static final String LOGIN_TITLE = "Login";
    public static final String HOME_TITLE = "Home";
    public static final String INVALID_PASSWORD = "Invalid username or password";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String SIGNUP_SUCCESS="You successfully signed up! Please continue to the login page.";
    public static final String SIGNUP_ERROR_USER_ALREADY_EXIST="User already exist";
    public static final String SIGNUP_INVALID="ups something went wrong";

    private static final String DEFAULT_URL_RESULT_OK = "http://localhost:{port}/result?success";
    public static String getDefaultUrlResultOk(Integer port){
        return DEFAULT_URL_RESULT_OK.replace("{port}",port.toString());
    }
    private static final String DEFAULT_URL_RESULT_ERROR = "http://localhost:{port}/result?error";

    public static String getDefaultUrlResultError(Integer port){
        return DEFAULT_URL_RESULT_ERROR.replace("{port}",port.toString());
    }

    public static final String DEFAULT_FILE = "Mazda2020.jpg";



    public static final long timeOutInSecs= 30;


   // public static final String DEFAULT_NOTE_TITLE = "NOTE TITLE";
    // public static final String DEFAULT_NOTE_CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu vestibulum tortor. Etiam nec sapien urna. Mauris placerat libero ac purus tincidunt, id convallis odio fermentum";

    public static Note generateNote(){
        Note note= new Note(null,
                "NOTE TITLE",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu vestibulum tortor. Etiam nec sapien urna. Mauris placerat libero ac purus tincidunt, id convallis odio fermentum");
        return note;
    }

    public static Credential generateCredential(){
        Credential credential = new Credential(null,"https://www.facebook.com/",
                "defaultuser","","abcdefg","");
        return credential;
    }




}
