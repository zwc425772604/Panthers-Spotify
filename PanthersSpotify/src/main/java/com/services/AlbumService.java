package com.services;


import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Album;
import com.model.User;

public interface AlbumService {
	public List<Album> addAlbum(String AlbumName,User user, String description,CommonsMultipartFile file,Date date);
	public List<Album> updateAlbum(int pid, String des,CommonsMultipartFile file, String pname, User user);
	public List<Album> removeAlbum(Album Album);
	public Album getAlbum(int pid);
	public List<Album> getAllAlbums();
	public List<Album> getTopFollowedAlbum(int numberOfAlbum);
	public List<Album> findRelative(String input);
	public List<Album> addHistoryAlbum(Album album,User user,Date date);
	public List<Album> deleteHistoryAlbum(Album album,User user);
	public List<Album> getHistoryAlbums(String userEmail);
	public List<User> getArtistsCollection(int aid);
	public List<Album> getTopGenreAlbum(String genre,int pageNum,int numRet);
	public List<Album> getNewsRelease(Date date);
	public Collection<Album> getAllAlbumArtist(String artistEmail);
}