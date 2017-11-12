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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    	EntityManager em = entityManagerFactory.createEntityManager();
    	song = changeUrl(song);
    	em.getTransaction().begin();
	    em.persist(song);
	    em.flush();
	    em.getTransaction().commit();	  
	    em.close();
	    return song;
    }
    
    public Song changeUrl(Song song) throws IOException {
//    	EntityManager em = entityManagerFactory.createEntityManager();
    	String fileName = song.getSurl().substring(song.getSurl().lastIndexOf("\\") + 1);
    	final File f = new File(SongManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	Path input = (Path)Paths.get(song.getSurl());  
    	Files.copy(input,(new File(f.toString() + "\\songs\\" + fileName)).toPath(), StandardCopyOption.REPLACE_EXISTING);
    	song.setSurl(fileName);
    	return song;
    }
    
}