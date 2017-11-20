/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;


import com.model.Album;
import com.model.Friend;
import com.model.Playlist;
import com.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Repository
@Transactional
public class UserManager {
//	@Autowired
     @PersistenceContext private EntityManager em;

	  @Transactional
	    public void add(String username, String email,String encPwd,int utype,char gender,String first_name, String last_name) {
	    		User user = new User();
	    
	        user.setUname(username);
	        user.setEmail(email);
	        user.setUpassword(encPwd);
	        user.setUtype(utype);
	        user.setGender(gender);
	        user.setFirstName(first_name);
	        user.setLastName(last_name);


	        final String dir = System.getProperty("user.dir");

	        File f1 = new File(dir+"/Users");
	        if(f1.exists())
	        {
	        		System.out.println("here");
	        }
	        File userDir = new File(f1, email);
	        boolean userSuccess = userDir.mkdirs();



		   em.getTransaction().begin();
		    em.persist(user);
		   em.getTransaction().commit();
		  
	    }

    public void remove(User user) {
		em.remove(em.contains(user) ? user : em.merge(user));		
    }
    
    public User editUserAccount(User user, Character gender, String firstName, String middleName, String lastName) {
    	user.setGender(gender);
    	user.setFirstName(firstName);
    	user.setMiddleName(middleName);
    	user.setLastName(lastName);
    	em.merge(user);
    	return user;
    }
    
    /* More info to be added */
    public User editUserPassword(User user, String pwd)
    {
		System.out.println("editUser");		
		user.setUpassword(pwd);
		em.merge(user);
		return user;
    }

    //check user is already registered or not, for login function
    public List<User> getUser(String email)
    {
    	 
    	System.out.println("email is :" + email);
    	TypedQuery<User> query1 = em.createNamedQuery("User.findByEmail", User.class)
          		.setParameter("email", email);

         List<User> results = query1.getResultList();
         System.out.println("here");
       
  	   
        return results;
    }
    
    public User getUserByUsername(String username) {
    	EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
 		EntityManager em = entityManagerFactory.createEntityManager();
    	TypedQuery<User> query1 = em.createNamedQuery("User.findByUname", User.class)
          		.setParameter("uname",username); 
        List<User> results = query1.getResultList();
        em.close();
  	    entityManagerFactory.close();
        return results.get(0);
    }
  //get friends
    @Transactional
    public List<User> getFriend(String uemail)
    {
  
    	System.out.println("user email is :" + uemail);
    	String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
    	Query query = em.createQuery(queryString);
    	query.setParameter("uemail", uemail);
    	
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    	List<User> results = query.getResultList();
    
  	    for(int i=0;i<results.size();i++)
  	    {
  	    		System.out.println(results.get(i).getEmail());
  	    }
  	    if(results.size()==0)
  	    {
  	    	System.out.println("no friends");
  	    }
        return results;
    }

  //add friends
    public List<User> addFriend(String uemail,String femail)
    {
 		
 		Friend ret = new Friend(uemail, femail);
 		em.persist(ret);
        //NameQuery are from Entity class
    	System.out.println("user email is :" + uemail);
    	String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
    	Query query = em.createQuery(queryString);
    	query.setParameter("uemail", uemail);
    	
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    	List<User> results = query.getResultList();
  	    for(int i=0;i<results.size();i++)
  	    {
  	    		System.out.println(results.get(i).getEmail());
  	    }
  	    if(results.size()==0)
  	    {
  	    	System.out.println("no friends");
  	    }
        return results;
    }

    //add friends
    public boolean deleteFriend(String uemail,String femail)
    {
 
 		Friend ret = new Friend(uemail, femail);
 		
        //NameQuery are from Entity class
    	em.remove(em.contains(ret)? ret: em.merge(ret));	
    	

        return true;
    }

    @SuppressWarnings("unchecked")
	public List<User> getUsersByType(int usertype){
    	EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
		EntityManager em = entityManagerFactory.createEntityManager();
    	List<User> list = new ArrayList<User>();
    	Query query = em.createNamedQuery("User.findByUtype",User.class).setParameter("utype", usertype);
    	list = (List<User>)query.getResultList();
    	em.close();
    	entityManagerFactory.close();
    	return list;
    }


}
