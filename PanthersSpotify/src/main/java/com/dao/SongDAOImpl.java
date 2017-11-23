package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.User;

@Repository("songDAO")
@Transactional
public class SongDAOImpl implements SongDAO{
	
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly=false)
	public Song addSong(Song Song) {
		
		entityManager.persist(Song);
		return Song;
	}

	@Transactional(readOnly=false)
	public Song updateSong(Song Song) {
		entityManager.merge(Song);
		return Song;
	}

	@Transactional(readOnly=false)
	public void deleteSong(Song Song) {
		entityManager.remove(entityManager.contains(Song) ? Song : entityManager.merge(Song));
		
	}

	@Transactional(readOnly=true)
	public Song getSong(int SongId) {
		TypedQuery<Song> query1 = entityManager.createNamedQuery("Song.findBySid", Song.class)
          		.setParameter("sid", SongId);

         List<Song> results = query1.getResultList();
         return results.get(0);
	}

	@Transactional(readOnly=true)
	public List<Song> getSongs() {
		TypedQuery<Song> query1 = entityManager.createNamedQuery("Song.findAll", Song.class);

         List<Song> results = query1.getResultList();
		return results;
	}
	
	@Transactional(readOnly=true)
	public List<Song> findRelative(String input)
	  {
	  	  
	  	TypedQuery<Song> query1 = entityManager.createNamedQuery("SELECT s from Song s WHERE s.stitle LIKE LOWER(CONCAT(:searchTerm, '%'))", Song.class)
	       		.setParameter("searchTerm", input);
	  	List<Song> result = query1.getResultList();
	  	return result;
	  }
	
	@Transactional(readOnly=false)
	public Releasesong addRelease(Releasesong releaseSong) {
	
		entityManager.persist(releaseSong);
		return releaseSong;
	}
	@Transactional(readOnly=true)
	public List<Releasesong> getAllSongsByStatus(String status)
	{
		TypedQuery<Releasesong> query1 = entityManager.createNamedQuery("Releasesong.findByStatus", Releasesong.class)
				.setParameter("status", status);
			List<Releasesong> result = query1.getResultList();
			return result;
	}
	
	@Transactional(readOnly=false)
	public void deleteRequestSong(int songId)
	  {
	  	  
	  	TypedQuery<Song> query1 = entityManager.createNamedQuery("DELETE FROM Song WHERE sid=:sid;", Song.class)
	       		.setParameter("sid", songId);
	  }
	
	@Transactional(readOnly=false)
	public void updateRequestSong(int songId,String uemail)
	{
	  	  
	  	TypedQuery<Song> query1 = entityManager.createNamedQuery("UPDATE Song SET status = :status WHERE uemail = :uemail and sid=:sid;", Song.class)
	  			.setParameter("status", "pass")
	  			.setParameter("uemail", uemail)
	  			.setParameter("sid", songId);
	}
}