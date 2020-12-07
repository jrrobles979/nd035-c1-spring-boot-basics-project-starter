package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;



    @PostConstruct
    public void postConstructor(){
        System.out.println("creating users service...");
    }

   public int createUser(CloudStorageUser userData){
       SecureRandom random = new SecureRandom();
       byte[] salt = new byte[16];
       random.nextBytes(salt);
       String encodedSalt = Base64.getEncoder().encodeToString(salt);
       String hashedPassword = hashService.getHashedValue(userData.getPassword(), encodedSalt);
       return this.userMapper.add( new CloudStorageUser( null,
                                                        userData.getUsername(),
                                                        encodedSalt,
                                                        hashedPassword,
                                                        userData.getFirstname(),
                                                        userData.getLastname())   );

   }

   public void updateUser(CloudStorageUser userData){
        this.userMapper.update(userData);
   }

   public void updatePassword(CloudStorageUser userData){
       SecureRandom random = new SecureRandom();
       byte[] salt = new byte[16];
       random.nextBytes(salt);
       String encodedSalt = Base64.getEncoder().encodeToString(salt);
       String hashedPassword = hashService.getHashedValue(userData.getPassword(), encodedSalt);
       userData.setSalt( encodedSalt );
       userData.setPassword(hashedPassword);
       this.userMapper.updatePassword(userData);
   }

    public void delete(int userId){
        this.userMapper.delete(userId);
    }

    public List<CloudStorageUser> list(){
        return this.userMapper.list();
    }

    public boolean userExist(String username){
        return this.userMapper.findByUserName(username) != null ? true : false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CloudStorageUser user = this.userMapper.findByUserName(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        CloudStorageUserDetail userDetail = new CloudStorageUserDetail(user);
        return userDetail;
    }
}
