package com.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.model.Album;
import com.model.User;

public interface AlbumDAO {
	public Album addAlbum(Album Album);
	public Album updateAlbum(Album Album);
	public void deleteAlbum(Album Album);
	public Album getAlbum(int AlbumId);
	public List<Album> getAlbums();
	public List<Album> getTopFollowedAlbum(int numberOfAlbum);
	public List<Album> findRelative(String input);
	public void addAlbumHistory(Album album,User user, Date date);
	public void deleteAlbumHistory(Album album,User user);
	public void updateAlbumHistory(Album album,User user, Date date);
	public List<Album> getHistoryAlbums(String userEmail);
	public Collection<User> getAlbumArtists(int aid);
	public List<Album> getTopGenreAlbum(String genre,int pageNum,int numRet);
	public List<Album> getNewsRelease(Date date);
	public Collection<Album> getAllAlbumArtist(String artistEmail);
}
