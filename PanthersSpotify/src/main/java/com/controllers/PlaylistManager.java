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
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class PlaylistManager {
//    @PersistenceContext private EntityManager em;
    
    
    @Transactional
    public List<Playlist> add(String playlist_name,User user, String description,String pic,java.sql.Date date,EntityManager em) {
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
	    
	    
	    List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	    user_playlist.add(playlist);
	    return user_playlist;
    }

    public void remove(int pid,EntityManager em) {
  	  Playlist playlist = em.find(Playlist.class, pid);  
  	  em.getTransaction().begin();
  	  em.remove(playlist);
  	  em.flush();
  	  em.getTransaction().commit();
  	  System.out.println("want to remove playlist");
  }
  public void edit(int pid, String des, String photoUrl, String pname,EntityManager em) {
  	
	    TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
      		.setParameter("pid", pid);
	    List<Playlist> results = query1.getResultList();
	    Playlist p = results.get(0);
	    p.setDes(des);
	    p.setPhotoUrl(photoUrl);
	    p.setPname(pname);
	    
	    Query query2 
	    		= em.createQuery("UPDATE playlist SET description = des, photoUrl= photoUrl, pname = pname WHERE pid = :pid",Playlist.class)
	    		.setParameter("pid", pid);
	    query2.executeUpdate();
	    //UPDATE playlist
	    //SET description = des, photoUrl= photoUrl, pname = pname
	    //		WHERE pid = 1;
	    
  	System.out.println("want to edit playlist");
  }

  //get the playlist for specific pid
  public Playlist getPlaylist(int pid,EntityManager em)
  {
  	TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
       		.setParameter("pid", pid);
	    List<Playlist> result = query1.getResultList();
	    return result.get(0);
  }
  
}

