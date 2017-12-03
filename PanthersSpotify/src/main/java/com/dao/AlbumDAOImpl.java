package com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Album;
import com.model.Albumhistory;
import com.model.Playlist;
import com.model.Playlisthistory;
import com.model.Song;
import com.model.Songhistory;
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
		
		TypedQuery<Album> query1 = entityManager.createNamedQuery("Album.findByAid", Album.class)
				.setParameter("aid", AlbumId);

		Album ret = query1.getSingleResult();
		return ret;
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
	  	  
		TypedQuery<Album> query1 = entityManager.createQuery("SELECT s from Album s WHERE s.aname LIKE LOWER(CONCAT(:searchTerm, '%'))", Album.class)
				.setParameter("searchTerm", input);
	  	List<Album> result = query1.getResultList();
	  	return result;
	}
	
	
	@Transactional(readOnly=false)
	public void addAlbumHistory(Album album,User user, Date date) {
		Query query = entityManager.createNamedQuery("Albumhistory.findByAidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("aid",album.getAid());
		Albumhistory albumHistroy = null;
		
		try
		{
			albumHistroy = (Albumhistory)query.getSingleResult();
			albumHistroy.setCreateDay(date);
			entityManager.merge(albumHistroy);
		}
		catch(NoResultException e)
		{
			albumHistroy = new Albumhistory(user.getEmail(),album.getAid());
			albumHistroy.setCreateDay(date);
			entityManager.persist(albumHistroy);
		}
	}
	
	@Transactional(readOnly=false)
	public void deleteAlbumHistory(Album album,User user) {
		Query query = entityManager.createNamedQuery("Albumhistory.findByAidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("aid",album.getAid());
		Albumhistory albumHistroy = null;
		try
		{
			albumHistroy = (Albumhistory)query.getSingleResult();
			entityManager.remove(albumHistroy);
		}
		catch(NoResultException e)
		{
			System.out.println("error message");
		}
	}
	
	@Transactional(readOnly=false)
	public void updateAlbumHistory(Album album,User user,Date date) {
		Query query = entityManager.createNamedQuery("Albumhistory.findByAidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("aid",album.getAid());
		Albumhistory albumHistroy = (Albumhistory)query.getSingleResult();
		albumHistroy.setCreateDay(date);
		entityManager.merge(albumHistroy);
	}
	
	@Transactional(readOnly=true)
	public List<Album> getHistoryAlbums(String userEmail)
	{
		 
		String queryString = "SELECT a FROM Album a where a.aid in (SELECT f.albumhistoryPK.aid from Albumhistory f where f.albumhistoryPK.uemail=:uemail)";
	    	Query query = entityManager.createQuery(queryString);
	    	query.setParameter("uemail", userEmail);
	    	List<Album>	list = (List<Album>)query.getResultList();
	    	return list;
	}
	
	@Transactional(readOnly = true)
	public Collection<User> getAlbumArtists(int aid) {
		String queryString = "select u from User u where u.email in (SELECT r.releasealbumPK.uemail FROM Releasealbum r WHERE r.releasealbumPK.aid = :aid)";
		Query query = entityManager.createQuery(queryString).setParameter("aid", aid);
		Collection<User> users = (ArrayList<User>)query.getResultList();
		return users;
	}
	
	@Transactional(readOnly = true)
	public Collection<Album> getAllAlbumArtist(String artistEmail) {
		String queryString = "select u from Album u where u.aid in (SELECT r.releasealbumPK.aid FROM Releasealbum r WHERE r.releasealbumPK.uemail = :uemail)";
		Query query = entityManager.createQuery(queryString).setParameter("uemail", artistEmail);
		Collection<Album> users = (ArrayList<Album>)query.getResultList();
		return users;
	}
	
	@Transactional(readOnly=true)
	 public List<Album> getTopGenreAlbum(String genre,int pageNum,int numRet){
		
		String queryString = "SELECT a FROM Album a where a.genre=:genre ORDER BY a.followers DESC";
		Query query = entityManager.createQuery(queryString).setParameter("genre", genre);
		query.setFirstResult((pageNum-1)*numRet);
		query.setMaxResults(numRet);
		List<Album> albums = (ArrayList<Album>)query.getResultList();
		  
		  return albums;
	  }
	
	@Transactional(readOnly=true)
	 public List<Album> getNewsRelease(Date date){
		
		String queryString = "SELECT a FROM Album a where a.releaseDate>:releaseDate order by a.followers desc";
		Query query = entityManager.createQuery(queryString).setParameter("releaseDate", date);
		
		//query.setMaxResults(numAlbum);
		List<Album> albums = (ArrayList<Album>)query.getResultList();
		  
		  return albums;
	  }
}