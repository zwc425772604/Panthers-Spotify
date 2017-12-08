package com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Album;
import com.model.Albumhistory;
import com.model.Followplaylist;
import com.model.Friend;
import com.model.Playlist;
import com.model.Playlisthistory;
import com.model.Playlistsong;
import com.model.Song;
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

         Playlist ret = query1.getSingleResult();
		return ret;
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
	
	
	
	
	@Transactional(readOnly=true)
	 public List<Playlist> getFollowedPlaylist(String userEmail){
	  	 String queryString = "SELECT playlist FROM Playlist playlist WHERE playlist.pid in(SELECT f.followplaylistPK.pid from Followplaylist f where f.followplaylistPK.uemail=:uemail)"; 	  
	  	Query query = entityManager.createQuery(queryString);
	  	query.setParameter("uemail", userEmail);
    		List<Playlist>	list = (List<Playlist>)query.getResultList();
		  
		  return list;
	  }
	
	@Transactional(readOnly=false)
	public void followPlaylist(int playlistId,String userEmail) {
		Followplaylist followPlaylist = new Followplaylist(playlistId,userEmail);
		
		if(!entityManager.contains(followPlaylist))
		{
			System.out.println("here follow");
			entityManager.persist(followPlaylist);
		}
	}
	
	@Transactional(readOnly=false)
	public void unfollowPlaylist(int playlistId,String userEmail) {

		Query query = entityManager.createNamedQuery("Followplaylist.findByUemailPid")
				.setParameter("uemail",userEmail)
				.setParameter("pid",playlistId);
		Followplaylist followplaylist = (Followplaylist)query.getSingleResult();
		entityManager.remove(entityManager.contains(followplaylist) ? followplaylist : entityManager.merge(followplaylist));
	}
	
	@Transactional(readOnly=false)
	public String addSongToPlaylist(int playlistId,int songId) {
		/*
		Playlistsong playlistsong = new Playlistsong(playlistId, songId);
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		playlistsong.setCreateDate(date);
		if(entityManager.contains(playlistsong))
		{
			entityManager.merge(playlistsong);
		}
		else
		{
			entityManager.persist(playlistsong);
		}
		*/
		Query query = entityManager.createNamedQuery("Playlistsong.findByPK");
		query.setParameter("pid", playlistId);
		query.setParameter("sid", songId);
		ArrayList<Playlistsong> result = (ArrayList<Playlistsong>) query.getResultList();
		if(result.size()>0) {
			return null;
		}
		Playlistsong playlistsong = new Playlistsong(playlistId, songId);
		java.util.Date now = new java.util.Date();
		playlistsong.setCreateDate(now);
		entityManager.persist(playlistsong);
		return "ok";
	}
	
	@Transactional(readOnly=false)
	public void removeSongFromPlaylist(int playlistId,int songId) {
		
		Query query = entityManager.createNamedQuery("Playlistsong.findBySidPid")
				.setParameter("sid",songId)
				.setParameter("pid",playlistId);
		Playlistsong playlistsong = (Playlistsong)query.getSingleResult();
		entityManager.remove(entityManager.contains(playlistsong) ? playlistsong : entityManager.merge(playlistsong));
	}
	
	@Transactional(readOnly=true)
	public List<Song> getSongInPlaylist(int playlistId) {
		
		 //String queryString = "SELECT song FROM Song song WHERE song.sid in(SELECT f.playlistsongPK.sid from Playlistsong f where f.playlistsongPK.pid=:pid)"; 	  
		String queryString = "SELECT s FROM Song s,Playlistsong p WHERE s.sid=p.playlistsongPK.sid and p.playlistsongPK.pid=:pid order by p.createDate";
		
		Query query = entityManager.createQuery(queryString);
		  
		  query.setParameter("pid", playlistId);
		  List<Song>	list = (List<Song>)query.getResultList();
		  
		  return list;	
	}
	
	@Transactional(readOnly=true)
	public List<Playlist> findRelative(String input)
	  {
	  	  
		TypedQuery<Playlist> query1 = entityManager.createQuery("SELECT p from Playlist p WHERE p.pname LIKE CONCAT('%',:searchTerm,'%')", Playlist.class)
	       		.setParameter("searchTerm", input);
	  	List<Playlist> result = query1.getResultList();
	  	return result;
	  }
	
	
	@Transactional(readOnly=false)
	public void addPlaylistHistory(Playlist playlist,User user, Date date) {
		Query query = entityManager.createNamedQuery("Playlisthistory.findByPidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("pid",playlist.getPid());
		Playlisthistory playlistHistroy=null;
		try
		{
			playlistHistroy = (Playlisthistory)query.getSingleResult();
			playlistHistroy.setCreateDay(date);
			entityManager.merge(playlistHistroy);
		}
		catch(NoResultException e)
		{
			playlistHistroy = new Playlisthistory(user.getEmail(),playlist.getPid());
			playlistHistroy.setCreateDay(date);
			entityManager.persist(playlistHistroy);
		}
		
	}
	
	@Transactional(readOnly=false)
	public void deletePlaylistHistory(Playlist playlist,User user) {
		Query query = entityManager.createNamedQuery("Playlisthistory.findByPidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("pid",playlist.getPid());
		Playlisthistory playlistHistroy = null;
		try
		{
			playlistHistroy = (Playlisthistory)query.getSingleResult();
			entityManager.remove(playlistHistroy);
		}
		catch(NoResultException e)
		{
			System.out.println("error message");
		}
	}
	
	@Transactional(readOnly=false)
	public void updatePlaylistHistory(Playlist playlist,User user,Date date) {
		Query query = entityManager.createNamedQuery("Playlisthistory.findByPidUemail")
				.setParameter("uemail",user.getEmail())
				.setParameter("pid",playlist.getPid());
		Playlisthistory playlistHistroy = (Playlisthistory)query.getSingleResult();
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
	
	@Transactional(readOnly=true)
	 public List<Playlist> getTopGenrePlaylist(String genre){
		
		String queryString = "SELECT a FROM Playlist a where a.genre=:genre ORDER BY a.followers DESC";
		Query query = entityManager.createQuery(queryString).setParameter("genre", genre);
		
		//query.setMaxResults(numAlbum);
		List<Playlist> playlists = (ArrayList<Playlist>)query.getResultList();
		  
		  return playlists;
	  }
	
	
}
