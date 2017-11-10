/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import com.model.Playlist;
import com.model.Song;

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
public class SongManager {
//    @PersistenceContext private EntityManager em;
    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public void add(Song song) {
	    userTransaction.begin();
	    em.persist(song);
	    em.flush();
	    userTransaction.commit();
	    
    	System.out.println("want to add song");
    }
  
    
}