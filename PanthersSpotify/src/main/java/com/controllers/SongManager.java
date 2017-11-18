
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import com.model.Album;
import com.model.Playlist;
import com.model.Song;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.transaction.Transactional;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class SongManager {
//	@Autowired
//	@PersistenceContext private EntityManager em;
	@PersistenceUnit(unitName = "pan")
    private EntityManagerFactory entityManagerFactory;
//    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public Song add(Song song) throws IOException {
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
   	    EntityManager em = emf.createEntityManager();
    	song = changeUrl(song);
    	em.getTransaction().begin();
	    em.persist(song);
	    em.flush();
	    em.getTransaction().commit();	  
	    em.close();
	    emf.close();
	    return song;
    }
    
    public Song changeUrl(Song song) throws IOException {
    	String fileName = song.getSurl().substring(song.getSurl().lastIndexOf("\\") + 1);
    	final File f = new File(SongManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	Path input = (Path)Paths.get(song.getSurl());  
    	Files.copy(input,(new File(f.toString() + "\\songs\\" + fileName)).toPath(), StandardCopyOption.REPLACE_EXISTING);
    	song.setSurl(fileName);
    	try {
	    	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
	 	    AudioFormat format = audioInputStream.getFormat();
	 	    long frames = audioInputStream.getFrameLength();
	 	    double durationInSeconds = (frames+0.0) / format.getFrameRate();  
	 	    System.out.println(durationInSeconds);
    	}catch(Exception ex) {
    		
    	}
    	
    	return song;
    }
    
    public List<Song> getAllSongs(){
    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
   	    EntityManager em = emf.createEntityManager();
    
    	List<Song> list = new ArrayList<Song>();	
    	Query query = em.createQuery("SELECT s FROM Song s");
    	list = (List<Song>)query.getResultList();
    	em.close();
    	emf.close();
    	return list;
    }
    
    public Song getSong(int sid,EntityManager em)
    {
    	TypedQuery<Song> query1 = em.createNamedQuery("Song.findByPid", Song.class)
         		.setParameter("sid", sid);
  	    List<Song> result = query1.getResultList();
  	    return result.get(0);
    }
    
    public List<Song> findRelative(String input)
	  {
		  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  	  EntityManager em = emf.createEntityManager();
	  	  
	  	TypedQuery<Song> query1 = em.createNamedQuery("SELECT p from Song p WHERE p.stitle LIKE LOWER(CONCAT(:searchTerm, '%'))", Song.class)
	       		.setParameter("searchTerm", input);
	  	List<Song> result = query1.getResultList();
	  	return result;
	  }
}
    
