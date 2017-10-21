/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Repository
public class UserDAOImplementation {
    @PersistenceContext private EntityManager em;
   
   
    public void add(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

   
    public void remove(User user) {
       
    }
    
}
