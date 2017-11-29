package com.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Album;
import com.model.Playlist;
import com.model.Song;
import com.model.User;
import com.services.AlbumService;
import com.services.SongService;

public class JSONHelper {
	// public static String playlistToJSON(List<Playlist> playlist) throws
	// JSONException
	// {
	// JSONArray playlistJsonArray = new JSONArray();
	// for (Playlist p : playlist)
	// {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put("playlistID", p.getPid());
	// jsonObject.put("playlistName", p.getPname());
	// jsonObject.put("playlistOwner", p.getPowner().getEmail());
	// JSONArray songArray = new JSONArray();
	// for (Song s : p.getSongCollection())
	// {
	// JSONObject songObject = new JSONObject();
	// songObject.put("songID", s.getSid());
	// songObject.put("songTitle", s.getStitle());
	// songArray.put(songObject);
	// }
	// jsonObject.put("songs", songArray);
	// playlistJsonArray.put(songArray);
	//
	// }
	// return playlistJsonArray.toString();
	// }

	public static String userListToJSON(List<User> users) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (User u : users) {
			jsonObject = new JSONObject();
			jsonObject.put("userID", u.getEmail());
			jsonObject.put("username", u.getUserName());
			jsonObject.put("userType", u.getUserType());
			jsonObject.put("userFirstName", u.getFirstName());
			jsonObject.put("userLastName", u.getLastName());
			arr.put(jsonObject);
		}
		return arr.toString();
	}

	public static String playlistListToJSON(List<Playlist> playlists) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (Playlist p : playlists) {
			jsonObject = new JSONObject();
			jsonObject.put("playlistID", p.getPid());
			jsonObject.put("playlistName", p.getPname());
			jsonObject.put("playlistOwner", p.getPowner().getEmail());
			jsonObject.put("playlistNumSongs", p.getNSongs());
			jsonObject.put("playlistNumFollowers", p.getFollowers());
			jsonObject.put("playlistCreateDate", p.getCreateDate());
			arr.put(jsonObject);
		}
		return arr.toString();
	}

	public static String artistListToJSON(List<User> users) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (User u : users) {
			jsonObject = new JSONObject();
			jsonObject.put("artistName", u.getUserName());
			jsonObject.put("artistFirstName", u.getFirstName());
			jsonObject.put("artistLastName", u.getLastName());
			jsonObject.put("artistEmail", u.getEmail());
			arr.put(jsonObject);
		}
		return arr.toString();
	}

	public static String pendingSongsToJSON(List<Song> songs, HashMap<Integer, ArrayList<String>> map)
			throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (Song s : songs) {
			jsonObject = new JSONObject();
			int sid = s.getSid();
			jsonObject.put("songID", sid);
			jsonObject.put("songTitle", s.getStitle());
			jsonObject.put("songGenre", s.getGener());
			jsonObject.put("songArtist", map.get(sid));
			arr.put(jsonObject);
		}
		return arr.toString();
	}

	public static String searchToJSON(List<Song> songs, List<Album> albums, List<Playlist> playlists)
			throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		JSONArray songArr = new JSONArray();
		for (Song s : songs) {
			jsonObject = new JSONObject();
			int sid = s.getSid();
			jsonObject.put("songID", sid);
			jsonObject.put("songTitle", s.getStitle());
			songArr.put(jsonObject);
		}
		arr.put(songArr);
		JSONArray albumArr = new JSONArray();
		for (Album a : albums) {
			jsonObject = new JSONObject();
			jsonObject.put("albumTitle", a.getAname());
			albumArr.put(jsonObject);
		}
		arr.put(albumArr);
		JSONArray playlistArr = new JSONArray();
		for (Playlist p : playlists) {
			jsonObject = new JSONObject();
			jsonObject.put("playlistTitle", p.getPname());
			playlistArr.put(jsonObject);
		}
		arr.put(playlistArr);
		return arr.toString();
	}

	public static String new_pendingSongsToJSON(List<Song> songs, SongService songService) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (Song s : songs) {
			jsonObject = new JSONObject();
			int sid = s.getSid();
			jsonObject.put("songID", sid);
			jsonObject.put("songTitle", s.getStitle());
			jsonObject.put("songGenre", s.getGener());
			JSONArray artists = new JSONArray();
			List<User> users = songService.getArtistsCollection(sid);
			for (User user : users) {
				JSONObject ob = new JSONObject();
				ob.put("name", user.getFullName());
				ob.put("aemail", user.getEmail());
				artists.put(ob);
			}
			jsonObject.put("songArtist", artists);
			arr.put(jsonObject);
		}
		return arr.toString();
	}

	public static String songToJSON(Song song, Collection<User> users) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int sid = song.getSid();
		jsonObject.put("songID", sid);
		jsonObject.put("songTitle", song.getStitle());
		jsonObject.put("songGenre", song.getGener());
		JSONArray artists = new JSONArray();
		for (User user : users) {
			JSONObject ob = new JSONObject();
			ob.put("name", user.getFullName());
			ob.put("aemail", user.getEmail());
			artists.put(ob);
		}
		jsonObject.put("songArtist", artists);
		return jsonObject.toString();
	}
	
	public static String albumInformation(Album album, AlbumService albumService, SongService songService) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int aid = album.getAid();
		jsonObject.put("albumID", aid);
		jsonObject.put("albumName", album.getAname());
		jsonObject.put("albumDes", album.getDes());
		jsonObject.put("albumPhotoUrl", album.getPhotoUrl());
		JSONArray artists = new JSONArray();
		List<User> users = albumService.getArtistsCollection(aid);
		
		for (User user : users) {
			JSONObject ob = new JSONObject();
			ob.put("name", user.getFullName());
			ob.put("aemail", user.getEmail());
			artists.put(ob);
		}
		jsonObject.put("songArtist", artists);
		JSONArray songsInAlbum = new JSONArray();
		List<Song> songs = songService.getSongs(aid);
		for (Song song : songs) {
			JSONObject ob = new JSONObject();
			ob.put("songTitle", song.getStitle());
			ob.put("songGenre", song.getGener());
			songsInAlbum.put(ob);
		}
		jsonObject.put("songsInAlbum", songsInAlbum);
		return jsonObject.toString();
	}
	

}
