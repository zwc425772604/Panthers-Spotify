/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import com.model.Playlist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.Query;

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

	@Transactional
    public List<Playlist> add(String playlist_name,User user, String description,String pic,java.sql.Date date) {
		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
   	    EntityManager em = emf.createEntityManager();	
		BufferedImage image = null;
    		URL url = null;
    	    
    	
    		Playlist playlist = new Playlist();
   		
   		playlist.setPname(playlist_name);
   		
   		playlist.setPowner(user);
   		int check = 0;
   		
   		if(pic!=null)
   		{
   			if(check==1)// mainly problem constrain
   			{
	   			try {
					url= new URL(pic);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	   			try {
					image = ImageIO.read(url);
					ImageIO.write(image, "jpg",new File("/Users/yangxiang/eclipse-workspace/panthers_spotify/"+user.getEmail()+"/"+"out.jpg"));
		   			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   			}
   			
   			File readFile = new File("pic");
   			try {
				image = ImageIO.read(readFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   			File inputFile = new File("/Users/yangxiang/eclipse-workspace/panthers_spotify/"+user.getEmail()+"/"+"ouput.jpg");
   			try {
   				ImageIO.write(image, "png", inputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
   		
   			playlist.setPhotoUrl(inputFile.getAbsolutePath());
   		}
   		else
   		{
   			playlist.setPhotoUrl(pic);
   		}
   		playlist.setDes(description);
   		playlist.setFollowers(0);
   		playlist.setNSongs(0);
   		playlist.setCreateDate(date);
    	
   		EntityTransaction userTransaction = em.getTransaction();
	    userTransaction.begin();
	    em.persist(playlist);
	    em.flush();
	    userTransaction.commit();
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
  
}

