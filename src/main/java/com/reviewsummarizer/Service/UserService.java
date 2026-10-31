package com.reviewsummarizer.Service;

import java.util.ArrayList;
import java.util.HashMap;
import  com.reviewsummarizer.model.User;
import  java.util.Map;
import  java.util.List;

public class UserService {
    private  Map<Integer,User> userDb;


    public  UserService(){
        userDb = new HashMap<>();
    }

    public  User createUser(User userReq){
        userDb.put(userReq.getId(),userReq);
        return  userReq;
    }

    public  User getUserById(Integer id){
        return userDb.getOrDefault(id,null);

    }

    public  List<User> getAllUseer(){
        List<User> ul=new ArrayList<>();

        for(User user:userDb.values()){
            ul.add(user);
        }
        return ul;


    }


}
