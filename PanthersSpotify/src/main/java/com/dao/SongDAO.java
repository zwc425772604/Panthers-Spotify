package com.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.model.Album;
import com.model.Releasesong;
import com.model.Song;
import com.model.SongQueue;
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
	public SongQueue addSongToQueue(int sid, String userEmail);
	public Song setNowPlay(Collection<SongQueue> que, int sid);
	public int removeAllQueue(String userEmail);
	public List<Releasesong> getAllSongsByID(int songID);
	public void updateReleaseSong(Releasesong rs);
	public Collection<User> getSongArtists(Integer sid);
	public List<Song> getSongs(Album album);
	public List<Song> getSongByArtist(String artistEmail, String status);
	public Releasesong getSongInRelease(int sid, String uemail);
	public List<Song> getRemoveRequestSongs(String status);
	public void addToHistory(String email, int sid);
	public List getGenreSongs();
}
