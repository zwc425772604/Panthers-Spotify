package com.services;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Playlist;
import com.model.User;

public interface PlaylistService {
	public List<Playlist> addPlaylist(String playlistName,User user, String description,CommonsMultipartFile file,Date date);
	public List<Playlist> updatePlaylist(int pid, String des,CommonsMultipartFile file, String pname, User user);
	public List<Playlist> removePlaylist(Playlist Playlist);
	public Playlist getPlaylist(int pid);
	public List<Playlist> getAllPlaylists();
	public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist);
	public boolean followPlaylist(int playlistId,User user);
	public boolean unfollowPlaylist(int playlistId,User user);
	public void addSongToPlaylist(int playlistId,int songId);
	public void removeSongFromPlaylist(int playlistId,int songId);
	public List<Playlist> findRelative(String input);
	public List<Playlist> getHistoryPlaylists(String userEmail);
	public List<Playlist> addHistoryPlaylist(Playlist playlist,User user,Date date);
	public List<Playlist> deleteHistoryPlaylist(Playlist playlist,User user);
	public Collection<Playlist> getUserPlaylists(String email);
	
}
