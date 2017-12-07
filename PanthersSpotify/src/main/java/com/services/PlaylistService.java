package com.services;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Playlist;
import com.model.Song;
import com.model.User;

public interface PlaylistService {
	public List<Playlist> addPlaylist(String playlistName,User user, String description,CommonsMultipartFile file,Date date);
	public List<Playlist> updatePlaylist(int pid, String des,CommonsMultipartFile file, String pname, User user);
	public List<Playlist> removePlaylist(Playlist Playlist);
	public Playlist getPlaylist(int pid);
	public List<Playlist> getAllPlaylists();
	public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist);
	public Playlist followPlaylist(int playlistId,User user);
	public Playlist unfollowPlaylist(int playlistId,User user);
	public String addSongToPlaylist(int playlistId,int songId);
	public void removeSongFromPlaylist(int playlistId,int songId);
	public List<Playlist> findRelative(String input);
	public List<Playlist> getHistoryPlaylists(String userEmail);
	public List<Playlist> addHistoryPlaylist(Playlist playlist,User user,Date date);
	public List<Playlist> deleteHistoryPlaylist(Playlist playlist,User user);
	public Collection<Playlist> getUserPlaylists(String email);
	public Collection<Playlist> getFollowPlaylists(String userEmail);
	public List<Song> getSongInPlaylist(int playlistId);
	public void updateSpecificPlaylist(Playlist p);
	public List<Playlist> getTopGenrePlaylist(String genre);
	public Time setPlaylistTimeLength(Playlist playlist,List<Song> s);
}
