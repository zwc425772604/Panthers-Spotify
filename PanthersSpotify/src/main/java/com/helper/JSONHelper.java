package com.helper;

import java.text.SimpleDateFormat;
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
import com.model.Concert;
import com.model.Payment;
import com.model.Playlist;
import com.model.Song;
import com.model.SongQueue;
import com.model.User;
import com.services.AlbumService;
import com.services.PlaylistService;
import com.services.SongService;
import com.services.UserService;

public class JSONHelper {
	public static String singlePlaylistToJSON(Playlist playlist) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
			jsonObject = new JSONObject();
			jsonObject.put("playlistName", playlist.getPname());
			jsonObject.put("playlistId", playlist.getPid());
			arr.put(jsonObject);	
		return arr.toString();
	}

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
			jsonObject.put("userBan", u.getIsBan());
			jsonObject.put("userPhotoUrl", u.getPhotoUrl());
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
	
	public static String albumListToJSON(List<Album> albums) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject jsonObject;
		for (Album p : albums) {
			jsonObject = new JSONObject();
			jsonObject.put("albumID", p.getAid());
			jsonObject.put("albumName", p.getAname());
			jsonObject.put("albumGenre", p.getGenre());
			jsonObject.put("albumDescription", p.getDes());
			jsonObject.put("albumNumFollowers", p.getFollowers());
			jsonObject.put("albumReleaseDate", p.getReleaseDate());
			jsonObject.put("albumNumberSongs", p.getNSongs());
			jsonObject.put("albumTimeLength", p.getTimelength());
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
			//jsonObject.put("artistRoyalty", u.getArtist().getRoyalty());
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
			jsonObject.put("songAlbum",s.getAlbumId().getAname());
			songArr.put(jsonObject);
		}
		arr.put(songArr);
		JSONArray albumArr = new JSONArray();
		for (Album a : albums) {
			jsonObject = new JSONObject();
			jsonObject.put("albumTitle", a.getAname());
			jsonObject.put("albumURL", a.getPhotoUrl());
			jsonObject.put("albumID", a.getAid());
			albumArr.put(jsonObject);
		}
		arr.put(albumArr);
		JSONArray playlistArr = new JSONArray();
		for (Playlist p : playlists) {
			jsonObject = new JSONObject();
			jsonObject.put("playlistTitle", p.getPname());
			jsonObject.put("playlistID", p.getPid());
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
			jsonObject.put("songPath", s.getSurl());
			jsonObject.put("songReleaseDate", s.getReleaseDay()); 
			jsonObject.put("songMonthlyPlayed", s.getMonthlyPlayed());
			jsonObject.put("albumId", s.getAlbumId().getAid());  
			jsonObject.put("albumName", s.getAlbumId().getAname());
			
			JSONArray artists = new JSONArray();
			List<User> users = songService.getArtistsCollection(sid);
			for (User user : users) {
				JSONObject ob = new JSONObject();
				ob.put("name", user.getUserName());
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
				ob.put("name", user.getUserName());
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
		jsonObject.put("albumDate", album.getReleaseDate());
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
	
	public static String getOneArtistRoyalty(User artist,UserService userService)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("artistName", artist.getFullName());
		jsonObject.put("artistRoyalty", userService.getArtistInfo(artist).getRoyalty());
		return jsonObject.toString();
	}
	
	public static String getAllArtistRoyalty(List<User> artists,UserService userService)
	{
		JSONObject jsonObject = new JSONObject();
		JSONArray artistsInfo = new JSONArray();
		for (User artist : artists) {
			JSONObject ob = new JSONObject();
			ob.put("artistName", artist.getFullName());
			ob.put("artistRoyalty", userService.getArtistInfo(artist).getRoyalty());
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
			if (preSong.getSqueuePK() !=null) {
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
	public static String getGenres(String[] input) throws JSONException
	{
		JSONObject ob = new JSONObject();
		JSONArray genres = new JSONArray();
		for(int i=0;i<input.length;i++)
		{
			JSONObject obj = new JSONObject();
			obj.put("genre"+i, input[i]);
			genres.put(obj);
		}
		ob.put("genres", genres);
		return ob.toString();
	}
	
	public static JSONObject queueToJSON(SongQueue que) throws JSONException {
		JSONObject ob = new JSONObject();
		JSONObject song = new JSONObject();
		song.put("title", que.getSong().getStitle());
		song.put("duration", que.getSong().getDuration());
		song.put("sid", que.getSong().getSid());
                song.put("songPath", que.getSong().getSurl());
                song.put("lyricPath", que.getSong().getLyricsUrl());
		ob.put("song",song);
		JSONObject album = new JSONObject();
		if(que.getSong().getAlbumId()!=null) {
		album.put("name", que.getSong().getAlbumId().getAname());
		album.put("id",que.getSong().getAlbumId().getAid());
			
		}
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
	
	public static String getArtistInfo(String bio, int followers)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("artistBio", bio);
		jsonObject.put("artistFollowers", followers);
		return jsonObject.toString();
	}
	
	public static String getPayment(Payment payment, User user)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("holdName", payment.getHoldName());
		jsonObject.put("cardNumber", payment.getCardNum());
		jsonObject.put("cvv", payment.getCvv());
		jsonObject.put("expDate", payment.getExpirationDate());
		jsonObject.put("userEmail", payment.getUemail());
		jsonObject.put("balance", payment.getBalance());
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdfDate.format(user.getUpgradeDate());
		jsonObject.put("upgradeDate",strDate);
		return jsonObject.toString();
	}
    
	public static String getConcertInfo(List<Concert> cs) {
		JSONObject ob = new JSONObject();
		JSONArray concerts = new JSONArray();
		for(Concert c: cs) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cid", c.getCid());
			jsonObject.put("date", c.getCtime());
			jsonObject.put("address", c.getAddress());
			jsonObject.put("name", c.getCname());
			concerts.put(jsonObject);
		}
		ob.put("concertList", concerts);
		return ob.toString();
		
	}
	
	public static String getAllArtistsRoyalty(List ret,UserService userService)
	{
		JSONObject jsonObject = new JSONObject();
		JSONArray artistsInfo = new JSONArray();
//		for (User artist : artists) {
//			JSONObject ob = new JSONObject();
//			ob.put("artistName", artist.getFullName());
//			ob.put("artistRoyalty", userService.getArtistInfo(artist).getRoyalty());
//			artistsInfo.put(ob);
//		}
		double subtractSum = 0;
		List<User> users = userService.getAllUsers();
		for(int i=0;i<ret.size();i++)
		{
			JSONObject ob = new JSONObject();
			Object[] artistInfo = (Object[])ret.get(i);
			
			String monthlyPlayedString = artistInfo[1].toString();
			double royalty = Double.parseDouble(monthlyPlayedString);
			if(royalty==0)
			{
				continue;
			}
			String artistEmail = (String)artistInfo[0];
			for(int j=0;j<users.size();j++)
			{
				if(users.get(j).getEmail().equals(artistEmail))
				{
					ob.put("artistName", users.get(j).getUserName());
					break;
				}
			}
			//Artist a = userService.getArtistInfo(u);
			
			
			
			subtractSum = subtractSum + royalty;
			ob.put("artistRoyalty", royalty);
			artistsInfo.put(ob);
			//userDAO.setRoyalty(a, royalties);
		}
		jsonObject.put("allArtistRoyalty", artistsInfo);
		int numPremium = userService.getAllPremium().size();
		jsonObject.put("numPremium", numPremium);
		jsonObject.put("premiumBenefit", numPremium*10);
		jsonObject.put("revenue", numPremium*10-subtractSum);
		
		return jsonObject.toString();
	}
	
	public static String statisticsToJSON(List ret,UserService userService,PlaylistService playlistService,SongService songService,AlbumService albumService){
		JSONObject jsonObject = new JSONObject();
		List<User> users1 = userService.getAllUsers();
  		int numUsers = users1.size();
  		List<User> users2 = userService.getAllPremium();
  		int numPremiums = users2.size();
  		List<User> users3 = userService.getAllArtist();
  		int numArtists = users3.size();
  		int numBasics = numUsers-numPremiums-numArtists-1;
  		List<Playlist> p = playlistService.getAllPlaylists();
  		int numPlaylists = p.size();
  		List<Song> s = songService.getAllSongs();
  		int numSongs = s.size();
  		List<Album> a = albumService.getAllAlbums();
  		int numAlbums = a.size();
  		jsonObject.put("numberUsers", numUsers);
		jsonObject.put("numPremiums", numPremiums);
		jsonObject.put("numArtist", numArtists);
		jsonObject.put("numBasics",numBasics);
		jsonObject.put("numPlaylists",numPlaylists);
		jsonObject.put("numSongs",numSongs);
		jsonObject.put("numAlbums",numAlbums);
		
		JSONArray artistsInfo = new JSONArray();
		for(int i=0;i<ret.size();i++)
		{
			JSONObject ob = new JSONObject();
			Object[] artistInfo = (Object[])ret.get(i);
			String genre = null;
			String numGenres = null;
			if (artistsInfo.length()>0) {
				genre = artistInfo[0].toString();
				numGenres = artistInfo[1].toString();
			}
			ob.put("genre", genre);
			ob.put("numGenres", numGenres);
			artistsInfo.put(ob);
			//userDAO.setRoyalty(a, royalties);
		}
		jsonObject.put("genreInfo", artistsInfo);
		
  		
		return jsonObject.toString();
	}
	
}
