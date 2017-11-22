package com.services;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Album;
import com.model.Releasesong;
import com.model.Song;
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
}
