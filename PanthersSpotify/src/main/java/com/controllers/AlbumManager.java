package com.controllers;

 
import com.model.User;
import com.model.Album;
import com.model.Playlist;
import com.model.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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
public class AlbumManager {
//    @PersistenceContext private EntityManager em;
	
    
	public List<Album> getAllAlbums(){
		EntityManager em = EMF.createEntityManager();
    	List<Album> list = new ArrayList<Album>();	
    	Query query = em.createQuery("SELECT a FROM Album a");
    	list = (List<Album>)query.getResultList();
    	em.close();
    	return list;
    }
    
}