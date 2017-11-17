package com.helper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Playlist;
import com.model.Song;
import com.model.User;

public class JSONHelper {
//	public static String playlistToJSON(List<Playlist> playlist) throws JSONException
//	{
//		JSONArray playlistJsonArray = new JSONArray();
//		for (Playlist p : playlist)
//		{
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("playlistID", p.getPid());
//			jsonObject.put("playlistName", p.getPname());
//			jsonObject.put("playlistOwner", p.getPowner().getEmail());
//			JSONArray songArray = new JSONArray();
//			for (Song s : p.getSongCollection())
//			{
//				JSONObject songObject = new JSONObject();
//				songObject.put("songID", s.getSid());
//				songObject.put("songTitle", s.getStitle());
//				songArray.put(songObject);
//			}
//			jsonObject.put("songs", songArray);
//			playlistJsonArray.put(songArray);
//			
//		}
//		return playlistJsonArray.toString();
//	}
	
	public static String userListToJSON(List<User> users) throws JSONException
	{
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (User u : users)
		{
			jsonObject = new JSONObject();
			jsonObject.put("userID", u.getEmail());
			jsonObject.put("username", u.getUname());
			jsonObject.put("userType", u.getUtype());
			jsonObject.put("userFirstName", u.getFirstName());
			jsonObject.put("userLastName", u.getLastName());
			arr.put(jsonObject);	
		}
		return arr.toString();
	}
	
	public static String playlistListToJSON(List<Playlist> playlists) throws JSONException
	{
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (Playlist p : playlists)
		{
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
	

}
