package com.dao;

import java.util.List;

import com.model.Album;

public interface AlbumDAO {
	public Album addAlbum(Album Album);
	public Album updateAlbum(Album Album);
	public void deleteAlbum(Album Album);
	public Album getAlbum(int AlbumId);
	public List<Album> getAlbums();
	public List<Album> getTopFollowedAlbum(int numberOfAlbum);
	public List<Album> findRelative(String input);
}
