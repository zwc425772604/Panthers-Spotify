package com.dao;

import java.util.List;

import com.model.Releasesong;
import com.model.Song;

public interface SongDAO {
	public Song addSong(Song Song);
	public Song updateSong(Song Song);
	public void deleteSong(Song Song);
	public Song getSong(int SongId);
	public List<Song> getSongs();
	public List<Song> findRelative(String input);
	public Releasesong addRelease(Releasesong releaseSong);
	public List<Releasesong> getAllSongsByStatus(String status);
}
