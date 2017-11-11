/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;
import com.model.Playlist;
import com.model.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
	
    @Transactional
    public Song add(Song song, EntityManager em)throws IOException  {
    		EntityTransaction userTransaction = em.getTransaction();
    		song = changeUrl(song);
	    userTransaction.begin();
	    em.persist(song);
	    em.flush();
	    userTransaction.commit();	    
	    return song;
    }
    
    public Song changeUrl(Song song) throws IOException {
    	String fileName = song.getSurl().substring(song.getSurl().lastIndexOf("\\") + 1);
    	final File f = new File(SongManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	Path input = (Path)Paths.get(song.getSurl());  
    	Files.copy(input,(new File(f.toString() + "\\songs\\" + fileName)).toPath(), StandardCopyOption.REPLACE_EXISTING);
    	song.setSurl(fileName);
    	return song;
    }
  
    
}