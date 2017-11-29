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

import com.model.Album;
import com.model.Releasesong;
import com.model.Song;
import com.model.SongQueue;
import com.model.Songhistory;
import com.model.User;

@Repository("songDAO")
@Transactional
public class SongDAOImpl implements SongDAO {

	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly = false)
	public Song addSong(Song song) {

		entityManager.persist(song);
		return song;
	}

	@Transactional(readOnly = false)
	public Song updateSong(Song song) {
		entityManager.merge(song);
		return song;
	}

	@Transactional(readOnly = false)
	public void deleteSong(Song song) {
		entityManager.remove(entityManager.contains(song) ? song : entityManager.merge(song));

	}

	@Transactional(readOnly = true)
	public Song getSong(int songId) {
		TypedQuery<Song> query1 = entityManager.createNamedQuery("Song.findBySid", Song.class).setParameter("sid",
				songId);

		Song ret = query1.getSingleResult();
		return ret;
	}
	
	@Transactional(readOnly = true)
	public List<Song> getSongs(Album album) {
		TypedQuery<Song> query1 = entityManager
				.createQuery("SELECT s from Song s WHERE s.aid=:aid", Song.class)
				.setParameter("aid", album);
		List<Song> result = query1.getResultList();
		return result;
	}

	@Transactional(readOnly = true)
	public List<Song> getSongs() {
		TypedQuery<Song> query1 = entityManager.createNamedQuery("Song.findAll", Song.class);

		List<Song> results = query1.getResultList();
		return results;
	}

	@Transactional(readOnly = true)
	public List<Song> findRelative(String input) {

		TypedQuery<Song> query1 = entityManager
				.createQuery("SELECT s from Song s WHERE s.stitle LIKE LOWER(CONCAT(:searchTerm, '%'))", Song.class)
				.setParameter("searchTerm", input);
		List<Song> result = query1.getResultList();
		return result;
	}

	@Transactional(readOnly = false)
	public Releasesong addRelease(Releasesong releaseSong) {

		entityManager.persist(releaseSong);
		return releaseSong;
	}

	@Transactional(readOnly = true)
	public List<Releasesong> getAllSongsByStatus(String status) {
		TypedQuery<Releasesong> query1 = entityManager.createNamedQuery("Releasesong.findByStatus", Releasesong.class)
				.setParameter("status", status);
		List<Releasesong> result = query1.getResultList();
		return result;
	}

	@Transactional(readOnly = false)
	public void deleteRequestSong(int songId) {

		TypedQuery<Song> query1 = entityManager.createNamedQuery("DELETE FROM Song WHERE sid=:sid;", Song.class)
				.setParameter("sid", songId);
	}

	@Transactional(readOnly = false)
	public void updateRequestSong(int songId, String uemail) {

		TypedQuery<Song> query1 = entityManager
				.createNamedQuery("UPDATE Song SET status = :status WHERE uemail = :uemail and sid=:sid;", Song.class)
				.setParameter("status", "pass").setParameter("uemail", uemail).setParameter("sid", songId);
	}

	@Transactional(readOnly = false)
	public void addSongHistory(Song song, User user, Date date) {
		Songhistory songHistroy = new Songhistory(user.getEmail(), song.getSid());
		songHistroy.setCreateDay(date);
		entityManager.persist(songHistroy);
	}

	@Transactional(readOnly = false)
	public void deleteSongHistory(Song song, User user) {
		Songhistory songHistroy = new Songhistory(user.getEmail(), song.getSid());
		if (entityManager.contains(songHistroy)) {
			entityManager.remove(songHistroy);
		}
	}

	@Transactional(readOnly = false)
	public void updateSongHistory(Song song, User user, Date date) {
		Songhistory songHistroy = new Songhistory(user.getEmail(), song.getSid());
		songHistroy.setCreateDay(date);
		entityManager.merge(songHistroy);
	}

	@Transactional(readOnly = true)
	public List<Song> getHistorySongs(String userEmail) {

		String queryString = "SELECT s FROM Song s where s.sid in (SELECT f.songhistoryPK.pid from Songhistory f where f.songhistoryPK.uemail=:uemail)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("uemail", userEmail);
		List<Song> list = (List<Song>) query.getResultList();
		return list;
	}

	@Transactional(readOnly = false)
	public SongQueue addSongToQueue(int sid, String email) {
		SongQueue newSq = new SongQueue(email, sid);
		newSq.setIsPlay(false);
		try {
			entityManager.persist(newSq);
		} catch (Exception e) {
			return null;
		}
		return newSq;
	}

	@Transactional(readOnly = false)
	public Song setNowPlay(Collection<SongQueue> que, int sid) {
		for (int i = 0; i < que.size(); i++) {
			SongQueue temp = ((ArrayList<SongQueue>) que).get(i);
			if (temp.getSqueuePK().getSid() == sid) {
				((ArrayList<SongQueue>) que).get(i).setIsPlay(true);
				entityManager.merge(((ArrayList<SongQueue>) que).get(i));
			} else if (temp.getIsPlay() && temp.getSqueuePK().getSid() != sid) {
				((ArrayList<SongQueue>) que).get(i).setIsPlay(false);
				entityManager.merge(((ArrayList<SongQueue>) que).get(i));
			}
		}
		return getSong(sid);

	}

	@Transactional(readOnly = false)
	public int removeAllQueue(String email) {
		Query query = entityManager.createNamedQuery("SongQueue.deleteByUemail").setParameter("uemail", email);
		int count = query.executeUpdate();
		return count;
	}

	@Transactional(readOnly = true)
	public List<Releasesong> getAllSongsByID(int songID) {
		TypedQuery<Releasesong> query = entityManager.createNamedQuery("Releasesong.findBySid", Releasesong.class)
				.setParameter("sid", songID);
		List<Releasesong> result = query.getResultList();
		return result;
	}

	@Transactional(readOnly = false)
	public void updateReleaseSong(Releasesong rs) {
		entityManager.merge(rs);

	}
	
	@Transactional(readOnly = true)
	public Collection<User> getSongArtists(Integer sid) {
		String queryString = "select u from User u where u.email in (SELECT r.releasesongPK.uemail FROM Releasesong r WHERE r.releasesongPK.sid = :sid)";
		Query query = entityManager.createQuery(queryString).setParameter("sid", sid);
		Collection<User> users = (ArrayList<User>)query.getResultList();
		return users;
	}

}
