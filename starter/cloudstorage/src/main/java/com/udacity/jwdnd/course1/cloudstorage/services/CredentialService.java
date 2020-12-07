package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;


    @PostConstruct
    public void postConstruct(){
        System.out.println("creating credentials service...");
    }

    public int add(Credential credential, int userId){
        String key = RandomStringUtils.random(16, true, true);
        String encriptedPassword = encryptionService.encryptValue( credential.getPassword(), key );
        return this.credentialMapper.add( new Credential( null,
                                                            credential.getUrl(),
                                                            credential.getUsername(),
                                                            key,
                                                            encriptedPassword,
                                            "") ,
                                                            userId);
    }

    public int update(Credential credential){
        String key = RandomStringUtils.random(16, true, true);
        String encriptedPassword = encryptionService.encryptValue( credential.getPassword(), key );
        return this.credentialMapper.update(new Credential( credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                key,
                encriptedPassword,
                ""));
    }

    public void delete(int credentialId){
            this.credentialMapper.delete(credentialId);
    }

    public List<Credential> listByUser(int userId){
        return this.credentialMapper.listByUser(userId).stream().map(this::decriptPassword).collect(Collectors.toList()) ;
    }

    private Credential decriptPassword(Credential credential){
        String decripted = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setDecriptedPassword(decripted);
        return credential;
    }

    public Credential find(int credentialId){
        return this.credentialMapper.findById(credentialId);
    }

}
