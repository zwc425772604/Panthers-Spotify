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
     //@PersistenceContext private EntityManager em;

	  @Transactional
	    public void add(String username, String email,String encPwd,int utype,char gender,String first_name, String last_name) {
	    		User user = new User();
	    		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
		    	EntityManager em = emf.createEntityManager();
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
		   em.close();
		   emf.close();
	    }

    public void remove(User user) {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.contains(user) ? user : em.merge(user));
		em.getTransaction().commit();
		em.close();
		emf.close();
    }
    /* More info to be added */
    public User editUser(User user, String pwd)
    {
		System.out.println("editUser");
		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		user.setUpassword(pwd);
		em.merge(user);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return user;
    }

    //check user is already registered or not, for login function
    public List<User> getUser(String email)
    {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
    	System.out.println("email is :" + email);
    	TypedQuery<User> query1 = em.createNamedQuery("User.findByEmail", User.class)
          		.setParameter("email", email);
    
         List<User> results = query1.getResultList();

         System.out.println("here");
       em.close();
       emf.close();
  	   
        return results;

    }
  //get friends
    public List<User> getFriend(String uemail)
    {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
 		em.getTransaction().begin();
 		
    	System.out.println("user email is :" + uemail);
    	String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
    	Query query = em.createQuery(queryString);
    	query.setParameter("uemail", uemail);
    	em.getTransaction().commit();
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
  	  em.close();
		emf.close();
        return results;
    }

  //add friends
    public List<User> addFriend(String uemail,String femail)
    {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
 		em.getTransaction().begin();
 		Friend ret = new Friend(uemail, femail);
 		em.persist(ret);
        //NameQuery are from Entity class
    	System.out.println("user email is :" + uemail);
    	String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
    	Query query = em.createQuery(queryString);
    	query.setParameter("uemail", uemail);
    	em.getTransaction().commit();
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
  	    em.close();
  	    emf.close();
        return results;
    }

  //add friends
    public boolean deleteFriend(String uemail,String femail)
    {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
 		Friend ret = new Friend(uemail, femail);

 		em.getTransaction().begin();

        //NameQuery are from Entity class
    	em.remove(em.contains(ret)? ret: em.merge(ret));	
    	em.getTransaction().commit();
  	    em.close();
  	    em.close();
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
