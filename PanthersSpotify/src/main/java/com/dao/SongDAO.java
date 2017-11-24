package com.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.model.Releasesong;
import com.model.Song;
import com.model.Squeue;
import com.model.User;

public interface SongDAO {
	public Song addSong(Song Song);
	public Song updateSong(Song Song);
	public void deleteSong(Song Song);
	public Song getSong(int SongId);
	public List<Song> getSongs();
	public List<Song> findRelative(String input);
	public Releasesong addRelease(Releasesong releaseSong);
	public List<Releasesong> getAllSongsByStatus(String status);
	public void addSongHistory(Song song,User user, Date date) ;
	public void deleteSongHistory(Song song,User user);
	public void updateSongHistory(Song song,User user, Date date);
	public List<Song> getHistorySongs(String userEmail);
	public Squeue addSongToQueue(int sid, String email);
	public Song setNowPlay(Collection<Squeue> que, int sid);
	public int removeAllQueue(String email);
}
