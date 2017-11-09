/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import com.model.Playlist;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class PlaylistDAO {
//    @PersistenceContext private EntityManager em;
    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public void add(Playlist playlist) {
	    //userTransaction.begin();
	    em.persist(playlist);
	   // userTransaction.commit();
    	System.out.println("want to add playlist");
    }

    public void remove(User user) {
       
    }
    
    //check user is already registered or not, for login function
    public List<User> getUser(String email, String password)
    {
        //NameQuery are from Entity class
         TypedQuery<User> query1 =
        em.createNamedQuery("User.findByEmail", User.class)
                 .setParameter("email", email);
        List<User> results = query1.getResultList();
        return results;

    }
    
    
}
