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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class UserDAOImplementation {
//    @PersistenceContext private EntityManager em;
     EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
   @Transactional
    public void add(User user) {
    userTransaction.begin();
    em.persist(user);
    userTransaction.commit();
   
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
