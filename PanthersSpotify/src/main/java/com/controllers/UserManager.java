/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class UserManager {
//    @PersistenceContext private EntityManager em;
    
    
    @Transactional
    public void add(String username, String encPwd,String email,int utype,char gender,String first_name, String last_name,EntityManager em) {
    		User user = new User();
        user.setUname(username);
        user.setEmail(email);
        user.setUpassword(encPwd);
        user.setUtype(utype);
        user.setGender(gender);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        
        
        File testfile1 = new File("PanthersSpotify");
        if(testfile1.exists())
        {
        		System.out.println("ok");
        		System.out.println(testfile1.getAbsolutePath());
        }
        else
        {
        		System.out.println("fail");
        }
        
        
        File testfile = new File("/Users/yangxiang/eclipse-workspace/panthers_spotify/"+email);//modify it
        
        
        testfile.mkdirs();
        
        
        EntityTransaction userTransaction = em.getTransaction();
	    userTransaction.begin();
	    em.persist(user);
	    userTransaction.commit();
    }

    public void remove(User user) {
       
    }
    
    //check user is already registered or not, for login function
    public List<User> getUser(String email, String password,EntityManager em)
    {
        //NameQuery are from Entity class
         TypedQuery<User> query1 =
        em.createNamedQuery("User.findByEmail", User.class)
                 .setParameter("email", email);
        List<User> results = query1.getResultList();
        return results;

    }
    
    
}
