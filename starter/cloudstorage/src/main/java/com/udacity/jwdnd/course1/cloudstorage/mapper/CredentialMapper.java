package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) " +
            "VALUES(#{newCredential.url},#{newCredential.username},#{newCredential.key},#{newCredential.password},#{userId}) ")
    //@Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int add(Credential newCredential, int userId);

    @Update("UPDATE CREDENTIALS " +
            "SET " +
            "url=#{url}, " +
            "username=#{username}, " +
            "key=#{key}, " +
            "password=#{password} " +
            "WHERE credentialid=#{credentialId}")
    int update(Credential credentialToUpdate);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    void delete(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> listByUser(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    Credential findById(int credentialId);
}
