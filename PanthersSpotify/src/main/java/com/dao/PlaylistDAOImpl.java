package com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.model.Followplaylist;
import com.model.Playlist;
import com.model.Playlisthistory;
import com.model.Playlistsong;
import com.model.User;

@Repository("playlistDAO")
@Transactional
public class PlaylistDAOImpl implements PlaylistDAO{
	
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly=false)
	public Playlist addPlaylist(Playlist Playlist) {
		
		entityManager.persist(Playlist);
		return Playlist;
	}

	@Transactional(readOnly=false)
	public Playlist updatePlaylist(Playlist Playlist) {
		entityManager.merge(Playlist);
		return Playlist;
	}

	@Transactional(readOnly=false)
	public void deletePlaylist(Playlist Playlist) {
		entityManager.remove(entityManager.contains(Playlist) ? Playlist : entityManager.merge(Playlist));
		
	}

	@Transactional(readOnly=true)
	public Playlist getPlaylist(int playlistId) {
		TypedQuery<Playlist> query1 = entityManager.createNamedQuery("Playlist.findByPid", Playlist.class)
          		.setParameter("pid", playlistId);

         List<Playlist> results = query1.getResultList();
		return results.get(0);
	}

	@Transactional(readOnly=true)
	public List<Playlist> getPlaylists() {
		
		TypedQuery<Playlist> query1 = entityManager.createNamedQuery("Playlist.findAll", Playlist.class);

		List<Playlist> results = query1.getResultList();
		return results;
	}
	
	@Transactional(readOnly=true)
	 public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist){
	  	  TypedQuery<Playlist> query = 
	  			  (TypedQuery<Playlist>)entityManager.createQuery("SELECT playlist FROM Playlist playlist ORDER BY playlist.followers DESC" );
	  	  query.setMaxResults(numberOfPlaylist);
		  List<Playlist> result = query.getResultList();
		  
		  return result;
	  }
	
	@Transactional(readOnly=false)
	public void followPlaylist(int playlistId,String userEmail) {
		Followplaylist followPlaylist = new Followplaylist(playlistId,userEmail);
		
		entityManager.persist(followPlaylist);
	}
	
	@Transactional(readOnly=false)
	public void unfollowPlaylist(int playlistId,String userEmail) {
		Followplaylist followPlaylist = new Followplaylist(playlistId,userEmail);
		
		entityManager.remove(followPlaylist);
	}
	
	@Transactional(readOnly=false)
	public void addSongToPlaylist(int playlistId,int songId) {
		Playlistsong playlistsong = new Playlistsong(playlistId, songId);
		
		entityManager.persist(playlistsong);
	}
	
	@Transactional(readOnly=false)
	public void removeSongFromPlaylist(int playlistId,int songId) {
		Playlistsong playlistsong = new Playlistsong(playlistId, songId);
		
		entityManager.remove(playlistsong);
	}
	
	@Transactional(readOnly=true)
	public List<Playlist> findRelative(String input)
	  {
	  	  
	  	TypedQuery<Playlist> query1 = entityManager.createQuery("SELECT p from Playlist p WHERE p.pname LIKE LOWER(CONCAT(:searchTerm, '%'))", Playlist.class)
	       		.setParameter("searchTerm", input);
	  	List<Playlist> result = query1.getResultList();
	  	return result;
	  }
	
	
	@Transactional(readOnly=false)
	public void addPlaylistHistory(Playlist playlist,User user, Date date) {
		Playlisthistory playlistHistroy = new Playlisthistory(user.getEmail(),playlist.getPid());
		playlistHistroy.setCreateDay(date);
		entityManager.persist(playlistHistroy);
	}
	
	@Transactional(readOnly=false)
	public void deletePlaylistHistory(Playlist playlist,User user) {
		Playlisthistory playlistHistroy = new Playlisthistory(user.getEmail(),playlist.getPid());
		if(entityManager.contains(playlistHistroy))
		{
			entityManager.remove(playlistHistroy);
		}
	}
	
	@Transactional(readOnly=false)
	public void updatePlaylistHistory(Playlist playlist,User user,Date date) {
		Playlisthistory playlistHistroy = new Playlisthistory(user.getEmail(),playlist.getPid());
		playlistHistroy.setCreateDay(date);
		entityManager.merge(playlistHistroy);
	}
	
	@Transactional(readOnly=true)
	public List<Playlist> getHistoryPlaylists(String userEmail)
	{
		 
		String queryString = "SELECT p FROM Playlist p where p.pid in (SELECT f.playlisthistoryPK.pid from Playlisthistory f where f.playlisthistoryPK.uemail=:uemail)";
	    	Query query = entityManager.createQuery(queryString);
	    	query.setParameter("uemail", userEmail);
	    	List<Playlist>	list = (List<Playlist>)query.getResultList();
	    	return list;
	}

	@Transactional(readOnly=true)
	public Collection<Playlist> getUserPlaylists(String email) {
		Query query = entityManager.createNamedQuery("Playlist.findByOwner").setParameter("email", email);
		Collection<Playlist> result = (ArrayList<Playlist>)query.getResultList();
		return result;
	}
}
