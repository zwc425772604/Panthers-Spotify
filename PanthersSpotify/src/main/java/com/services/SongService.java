package com.services;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Album;
import com.model.Releasesong;
import com.model.Song;
import com.model.Squeue;
import com.model.User;

public interface SongService {
	public Song addSong(String stitle, Time stime, Date releaseDate,Album aid,int monthlyPlayed,String genre,String stype,String surl);
	public Song updateSong(String stitle, Time stime, Date releaseDate,Album aid,int monthlyPlayed,String genre,String stype,String surl);
	public void removeSong(Song Song);
	public Song getSong(int sid);
	public List<Song> getAllSongs();
	public List<Song> findRelative(String input);
	public Song uploadSong(User user, String songTitle, String songTime, String releaseDay, String songGenre, String songType, CommonsMultipartFile file);
	public List<Releasesong> getAllSongsByStatus(String status);
	public Collection<Releasesong> getSongArtists(Song song);
	public List<Song> getHistorySongs(String userEmail);
	public List<Song> addHistorySong(Song song,User user,Date date);
	public List<Song> deleteHistorySong(Song song,User user);
	public void addSongToQueue(Collection<Squeue> que, int sid, String email);
	public void addPlaylistToQueue(Collection<Squeue> que, int pid, String email);
	public Song getNowPlay(Collection<Squeue> que);
	public Song setNowPlay(Collection<Squeue> que, int sid);
	public Song nextSongInQueue(Collection<Squeue> que);
	public Song preSongInQueue(Collection<Squeue> que);
	public Collection<Squeue> shuffleQueue(Collection<Squeue> que);
	public Collection<Squeue> removeAllQueue(Collection<Squeue> que, String email);
	public Collection<Squeue> nextUp(Collection<Squeue> que);
}
