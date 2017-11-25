package com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Releasesong;
import com.model.Song;
import com.model.Songhistory;
import com.model.Squeue;
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
	  	  
	  	TypedQuery<Song> query1 = entityManager.createQuery("SELECT s from Song s WHERE s.stitle LIKE LOWER(CONCAT(:searchTerm, '%'))", Song.class)
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
	
	@Transactional(readOnly=false)
	public void addSongHistory(Song song,User user, Date date) {
		Songhistory songHistroy = new Songhistory(user.getEmail(),song.getSid());
		songHistroy.setCreateDay(date);
		entityManager.persist(songHistroy);
	}
	
	@Transactional(readOnly=false)
	public void deleteSongHistory(Song song,User user) {
		Songhistory songHistroy = new Songhistory(user.getEmail(),song.getSid());
		if(entityManager.contains(songHistroy))
		{
			entityManager.remove(songHistroy);
		}
	}
	
	@Transactional(readOnly=false)
	public void updateSongHistory(Song song,User user,Date date) {
		Songhistory songHistroy = new Songhistory(user.getEmail(),song.getSid());
		songHistroy.setCreateDay(date);
		entityManager.merge(songHistroy);
	}
	
	@Transactional(readOnly=true)
	public List<Song> getHistorySongs(String userEmail)
	{
		 
		String queryString = "SELECT s FROM Song s where s.sid in (SELECT f.songhistoryPK.pid from Songhistory f where f.songhistoryPK.uemail=:uemail)";
	    	Query query = entityManager.createQuery(queryString);
	    	query.setParameter("uemail", userEmail);
	    	List<Song>	list = (List<Song>)query.getResultList();
	    	return list;
	}
	
	@Transactional(readOnly=false)
	public Squeue addSongToQueue(int sid, String email) {
		Squeue newSq = new Squeue(email,sid);
		newSq.setIsPlay(false);
		try {
			entityManager.persist(newSq);
		}catch (Exception e){
			return null;
		}
		return newSq;
	}


	@Transactional(readOnly=false)
	public Song setNowPlay(Collection<Squeue> que, int sid) {
		for (int i = 0; i<que.size(); i++) {
			Squeue temp = ((ArrayList<Squeue>) que).get(i);
			if (temp.getSqueuePK().getSid() == sid) {
				((ArrayList<Squeue>) que).get(i).setIsPlay(true);
				entityManager.merge(((ArrayList<Squeue>) que).get(i));
			}else if(temp.getIsPlay() && temp.getSqueuePK().getSid() != sid) {
				((ArrayList<Squeue>) que).get(i).setIsPlay(false);
				entityManager.merge(((ArrayList<Squeue>) que).get(i));
			}
		}
		return getSong(sid);
		
	}

	@Transactional(readOnly=false)
	public int removeAllQueue(String email) {
		Query query = entityManager.createNamedQuery("Squeue.deleteByUemail").setParameter("uemail", email);
		//query.setParameter("uemail", email);
		int count = query.executeUpdate();
		return count;
		//return query.executeUpdate();		
	}
}