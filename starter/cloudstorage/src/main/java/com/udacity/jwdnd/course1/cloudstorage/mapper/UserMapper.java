package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("Insert into USERS(username, salt, password, firstname, lastname) " +
            "values(#{username}, #{salt}, #{password}, #{firstname}, #{lastname});")
    //@Options(useGeneratedKeys = true, keyProperty = "userId")
    int add(CloudStorageUser user);

    @Update("UPDATE USERS " +
            "SET salt=#{salt}, " +
            "password=#{password}, " +
            "WHERE userid=#{id}; ")
    void updatePassword(CloudStorageUser user);

    @Update("UPDATE USERS " +
            "SET firstname=#{firstname}, " +
            "lastname=#{lastname}, " +
            "WHERE userid=#{userid}")
    void update(CloudStorageUser user);

    @Delete("DELETE FROM USERS WHERE userid=#{userId};")
    void delete(int userId);

    @Select("SELECT * FROM USERS WHERE username=#{username};")
    CloudStorageUser findByUserName(String username);

    @Select("SELECT username, firstname, lastname FROM USERS;")
    List<CloudStorageUser> list();
}
