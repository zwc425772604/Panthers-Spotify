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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class PlaylistManager {
//	@Autowired
//   @PersistenceContext private EntityManager em;
//    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//	 @PersistenceUnit(unitName = "pan")
//	    private EntityManagerFactory entityManagerFactory;
//    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public List<Playlist> add(String playlist_name,User user, String description,String pic,java.sql.Date date) {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
    		Playlist playlist = new Playlist();
   		
   		playlist.setPname(playlist_name);
   		
   		playlist.setPowner(user);
   		playlist.setDes(description);
   		playlist.setFollowers(0);
   		playlist.setNSongs(0);
   		playlist.setCreateDate(date);
    	
    		
   		em.getTransaction().begin();
	    em.persist(playlist);
	    em.flush();
	    em.getTransaction().commit();
	    
	    
	    List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	    user_playlist.add(playlist);
	    em.close();
	    emf.close();
	    return user_playlist;
    }

    public void remove(int pid) {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
  	  Playlist playlist = em.find(Playlist.class, pid);  
  	  em.getTransaction().begin();
  	  em.remove(playlist);
  	  em.flush();
  	  em.getTransaction().commit();
  	  System.out.println("want to remove playlist");
  	  em.close();
  	  emf.close();
  	  
  }
    public void removePlaylist(int pid)
    {
    	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	  EntityManager em = emf.createEntityManager(); 
    	  Playlist playlist = em.find(Playlist.class, pid);
	  	  em.getTransaction().begin();
	  	  em.remove(playlist);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  System.out.println("want to remove playlist");
	  	  em.close();
	  	  emf.close();
    }
    
  public void edit(int pid, String des, String photoUrl, String pname) {
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  EntityManager em = emf.createEntityManager();
	    TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
      		.setParameter("pid", pid);
	    List<Playlist> results = query1.getResultList();
	    Playlist p = results.get(0);
	    p.setDes(des);
	    p.setPhotoUrl(photoUrl);
	    p.setPname(pname);
	    //UPDATE playlist
	    //SET description = des, photoUrl= photoUrl, pname = pname
	    //		WHERE pid = 1;
	    
  	System.out.println("want to edit playlist");
  	em.close();
  	emf.close();
  }

  //get the playlist for specific pid
  public Playlist getPlaylist(int pid)
  {
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  EntityManager em = emf.createEntityManager();
  	TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
       		.setParameter("pid", pid);
	    List<Playlist> result = query1.getResultList();
	    em.close();
	    emf.close();
	    return result.get(0);
  }
  
}

