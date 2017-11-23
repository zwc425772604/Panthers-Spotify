package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Album;
import com.model.Song;
import com.model.User;

@Repository("albumDAO")
@Transactional
public class AlbumDAOImpl implements AlbumDAO{
	
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly=false)
	public Album addAlbum(Album Album) {
		
		entityManager.persist(Album);
		return Album;
	}

	@Transactional(readOnly=false)
	public Album updateAlbum(Album Album) {
		entityManager.merge(Album);
		return Album;
	}

	@Transactional(readOnly=false)
	public void deleteAlbum(Album Album) {
		entityManager.remove(entityManager.contains(Album) ? Album : entityManager.merge(Album));
		
	}

	@Transactional(readOnly=true)
	public Album getAlbum(int AlbumId) {
		
		TypedQuery<Album> query1 = entityManager.createNamedQuery("User.findByAid", Album.class)
				.setParameter("aid", AlbumId);

		List<Album> results = query1.getResultList();
		return results.get(0);
	}

	@Transactional(readOnly=true)
	public List<Album> getAlbums() {
		TypedQuery<Album> query1 = entityManager.createNamedQuery("Album.findAll", Album.class);

		List<Album> results = query1.getResultList();
		return results;
	}
	
	@Transactional(readOnly=true)
	 public List<Album> getTopFollowedAlbum(int numberOfAlbum){
	  	  TypedQuery<Album> query = 
	  			  (TypedQuery<Album>)entityManager.createQuery("SELECT Album FROM Album Album ORDER BY Album.followers DESC" );
	  	  query.setMaxResults(numberOfAlbum);
		  List<Album> result = query.getResultList();
		  
		  return result;
	  }
	
	@Transactional(readOnly=true)
	public List<Album> findRelative(String input)
	  {
	  	  
	  	TypedQuery<Album> query1 = entityManager.createNamedQuery("SELECT s from Album s WHERE s.aname LIKE LOWER(CONCAT(:searchTerm, '%'))", Album.class)
	       		.setParameter("searchTerm", input);
	  	List<Album> result = query1.getResultList();
	  	return result;
	  }
}