/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
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
     // @PersistenceContext private EntityManager em;
// 	 @PersistenceUnit(unitName = "pan")
//     private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "pan" );
//     EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public void add(String username, String email,String encPwd,int utype,char gender,String first_name, String last_name) {
    		User user = new User();
    		 EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
    		EntityManager em = entityManagerFactory.createEntityManager();
        user.setUname(username);
        user.setEmail(email);
        user.setUpassword(encPwd);
        user.setUtype(utype);
        user.setGender(gender);
        user.setFirstName(first_name);
        user.setLastName(last_name);
	   em.getTransaction().begin();
	    em.persist(user);
	   em.getTransaction().commit();
	   em.close();
	   entityManagerFactory.close();
    }

    public void remove(User user) {
       
    }
    
    //check user is already registered or not, for login function
    public List<User> getUser(String email, String password, EntityManager em)
    {
//    	 EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    	EntityManager em = entityManagerFactory.createEntityManager();
//    	entityManagerFactory = Persistence.createEntityManagerFactory( "pan" );
//    	EntityManager em = entityManagerFactory.createEntityManager();
        //NameQuery are from Entity class
    	System.out.println("email is :" + email);
         List results =
        em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email).getResultList();
//        List<User> results = query1.getResultList();
//        em.close();
//        entityManagerFactory.close();
        return results;

    }
    
    
}
