package com.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Album;
import com.model.Artist;
import com.model.Playlist;
import com.model.Song;
import com.model.SongQueue;
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
			jsonObject.put("artistUrl", u.getPhotoUrl());
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
	public static JSONArray new_pendingSongsToJSON1(List<Song> songs, SongService songService) throws JSONException {
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
		return arr;
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
			ob.put("name", user.getUserName());
			ob.put("aemail", user.getEmail());
			artists.put(ob);
		}
		jsonObject.put("songArtist", artists);
		JSONArray songsInAlbum = new JSONArray();
		List<Song> songs = songService.getSongs(album);

		for (Song song : songs) {
			JSONObject ob = new JSONObject();
			ob.put("songTitle", song.getStitle());
			ob.put("songId", song.getSid());
			songsInAlbum.put(ob);
		}
		jsonObject.put("songsInAlbum", songsInAlbum);
		return jsonObject.toString();
	}
	
	public static String getOneArtistRoyalty(User artist)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("artistName", artist.getFullName());
		jsonObject.put("artistRoyalty", artist.getArtist().getRoyalty());
		return jsonObject.toString();
	}
	
	public static String getAllArtistRoyalty(List<User> artists)
	{
		JSONObject jsonObject = new JSONObject();
		JSONArray artistsInfo = new JSONArray();
		for (User artist : artists) {
			JSONObject ob = new JSONObject();
			jsonObject.put("artistName", artist.getFullName());
			jsonObject.put("artistRoyalty", artist.getArtist().getRoyalty());
			artistsInfo.put(ob);
		}
		jsonObject.put("allArtistRoyalty", artistsInfo);
		
		return jsonObject.toString();
	}
	
	public static JSONObject songQueueToJSON(Collection<SongQueue> que) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Iterator<SongQueue> it = (Iterator<SongQueue>)que.iterator();
		JSONObject nowPlay = new JSONObject();
		JSONArray ary = new JSONArray();
		SongQueue preSong = new SongQueue();
		while(it.hasNext()) {
			SongQueue sq = it.next();
			if (sq.getIsPlay()) {
				break;
			}
			preSong = sq;
			if (it.hasNext()==false)
				preSong = new SongQueue();
		}
		it = (Iterator<SongQueue>)que.iterator();
		if (it.hasNext()) {
			while(it.hasNext()) {
				SongQueue sq = it.next();
				if (sq.getIsPlay()) {
					nowPlay = queueToJSON(sq);
					while(it.hasNext()) {
						SongQueue temp = it.next();
						JSONObject obj = new JSONObject();
						obj = queueToJSON(temp);
						ary.put(obj);
					}
				}
			}
			JSONObject pre = new JSONObject();
			if (preSong.getUser() !=null) {
				pre = queueToJSON(preSong);
			}
			jsonObject.put("previous", pre);
			jsonObject.put("nowPlay", nowPlay);
			jsonObject.put("nextUp", ary);
			JSONObject test = jsonObject.getJSONObject("nowPlay");
			if (test.length()==0) {
				System.out.println("now play is null");
			}
		}
		return jsonObject;
	}
	
	public static JSONObject queueToJSON(SongQueue que) throws JSONException {
		JSONObject ob = new JSONObject();
		JSONObject song = new JSONObject();
		song.put("title", que.getSong().getStitle());
		song.put("duration", que.getSong().getDuration());
		song.put("sid", que.getSong().getSid());
		ob.put("song",song);
		JSONObject album = new JSONObject();
		album.put("name", que.getSong().getAlbumId().getAname());
		album.put("id",que.getSong().getAlbumId().getAid());
		ob.put("album", album);
		JSONArray artists = new JSONArray();
		for (User u : que.getArtistsCollection()) {
			JSONObject obj = new JSONObject();
			obj.put("name", u.getUserName());
			obj.put("email", u.getEmail());
			artists.put(obj);
		}
		ob.put("artists", artists);
		return ob;
	}

}
