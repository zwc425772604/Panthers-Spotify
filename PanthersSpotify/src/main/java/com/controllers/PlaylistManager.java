/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;

import PlaylistInterface.PlaylistInterface;

import com.model.Playlist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
public class PlaylistManager implements PlaylistInterface {
//	@Autowired
//   @PersistenceContext private EntityManager em;
//    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//	 @PersistenceUnit(unitName = "pan")
//	    private EntityManagerFactory entityManagerFactory;
//    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public List<Playlist> add(String playlistName,User user, String description,String photoUrl,Date date) {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
    	EntityManager em = emf.createEntityManager();
    		Playlist playlist = new Playlist(playlistName,description,photoUrl,0,0,date,user);
   		String dir = System.getProperty("user.dir");
   		File f = new File(photoUrl);
   		File userFile = new File(dir+"/"+user.getEmail());
   		BufferedImage image=null;
   		try {
			image = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		em.getTransaction().begin();
	    em.persist(playlist);
	    em.flush();
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	    user_playlist.add(playlist);
	    return user_playlist;
    }
    
    //remove playlist
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
    
  public List<Playlist> edit(int pid, String des, String photoUrl, String pname, User user) {
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  EntityManager em = emf.createEntityManager();
	    //TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
      	//	.setParameter("pid", pid);
	    
	    Playlist playlist = em.find(Playlist.class, pid);
	    
	    em.getTransaction().begin();
	    if(des!=null)
	    		playlist.setDes(des);
	    if(photoUrl!=null)
	    		playlist.setPhotoUrl(photoUrl);
	    if(pname!=null)
	    		playlist.setPname(pname);
	    em.getTransaction().commit();
	    
	    
	    //UPDATE playlist
	    //SET description = des, photoUrl= photoUrl, pname = pname
	    //		WHERE pid = 1;
	    
	    
  	System.out.println("want to edit playlist");
  	em.close();
  	emf.close();
  	List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
  	Playlist p=null;
  	for(int i=0;i<user_playlist.size();i++)
  	{
  		p=user_playlist.get(i);
  		if(p.getPid()==pid)
  		{
  			break;
  		}
  	}
  	if(des!=null)
		p.setDes(des);
	if(photoUrl!=null)
			p.setPhotoUrl(photoUrl);
	if(pname!=null)
			p.setPname(pname);
  	return user_playlist;
  	
  	
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
  
  //add song to playlist
  public void addSongPlaylist(int playlist_id,int song_id)
  {
  	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager(); 
  	  Playlist playlist = em.find(Playlist.class, playlist_id);
	  	  em.getTransaction().begin();
	  	  em.remove(playlist);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  System.out.println("want to remove playlist");
	  	  em.close();
	  	  emf.close();
  }
  
}

