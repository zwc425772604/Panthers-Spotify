package com.dao;

import java.sql.Date;
import java.util.List;

import com.model.Playlist;
import com.model.User;

public interface PlaylistDAO {
	public Playlist addPlaylist(Playlist Playlist);
	public Playlist updatePlaylist(Playlist Playlist);
	public void deletePlaylist(Playlist Playlist);
	public Playlist getPlaylist(int playlistId);
	public List<Playlist> getPlaylists();
	public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist);
	public void followPlaylist(int playlistId,String userEmail);
	public void unfollowPlaylist(int playlistId,String userEmail);
	public void addSongToPlaylist(int playlistId,int songId);
	public void removeSongFromPlaylist(int playlistId,int songId);
	public List<Playlist> findRelative(String input);
	public void addPlaylistHistory(Playlist playlist,User user, Date date);
	public void deletePlaylistHistory(Playlist playlist,User user);
	public void updatePlaylistHistory(Playlist playlist,User user, Date date);
	public List<Playlist> getHistoryPlaylists(String userEmail);
}
