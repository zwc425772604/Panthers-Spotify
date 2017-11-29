package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model.User;
import com.model.UserType;
import com.helper.CheckPayment;
import com.helper.JSONHelper;
import com.helper.Security;
import com.helper.StringToDateHelper;
//import com.helper.StringToDateHelper;
import com.model.Album;
import com.model.Payment;
import com.model.Song;
import com.model.SongQueue;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.ReleasesongPK;
import com.services.AlbumService;
import com.services.PlaylistService;
import com.services.SongService;
import com.services.UserService;

@Controller
public class ServletController {
	@Autowired(required = true)
	@Qualifier("userService")
	private UserService userService;

	@Autowired(required = true)
	@Qualifier("songService")
	private SongService songService;

	@Autowired(required = true)
	@Qualifier("playlistService")
	private PlaylistService playlistService;

	@Autowired(required = true)
	@Qualifier("albumService")
	private AlbumService albumService;

	@Autowired
	private Environment environment;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap map) {

		return "index";
	}

	/* user login */
	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public ModelAndView userLogin(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String encPwd = Security.oldEncryptPassword(password);
		User user = userService.getUser(email);
		if (user.equals(null)) {
			mav.setViewName("index");
			mav.addObject("error_message", "This email does not register on our site!");
		} else if (Security.matchPassword(password, user.getUserPassword())|| user.getUserPassword().equals(encPwd)) {
			session.setAttribute("user", user);
			
			int userTypeInt = user.getUserType();
			UserType userType = UserType.BASIC;
			UserType[] types = { UserType.BASIC, UserType.PREMIUM, UserType.ARTIST, UserType.ADMIN };
			for (UserType type : types) {
				if (type.getTypeCode() == userTypeInt) {
					userType = type;
				}
			}
			
			user.setSongQueueCollection(userService.getQueue(email));
			Collection<SongQueue> songQueue = new ArrayList<SongQueue>();
			Collection<Playlist> playlists = new ArrayList<Playlist>();
			Collection<Playlist> followedPlaylist = new ArrayList<Playlist>();
			switch (userType) {
			case BASIC:
				user.setSongQueueCollection(userService.getQueue(email));
				songQueue = user.getSongQueueCollection();
				songService.setArtistsCollection(songQueue);
				
				playlists = user.getUserPlaylistCollection();
				followedPlaylist = playlistService.getFollowPlaylists(email);
				playlists.addAll(followedPlaylist);
				session.setAttribute("songQueue", songQueue);
				session.setAttribute("user_playlist", playlists);
				mav.setViewName("main");
				mav.addObject("username", user.getUserName());
				break;
			case PREMIUM:
				user.setSongQueueCollection(userService.getQueue(email));
				songQueue = user.getSongQueueCollection();
				songService.setArtistsCollection(songQueue);
				playlists = user.getUserPlaylistCollection();
				followedPlaylist = playlistService.getFollowPlaylists(email);
				playlists.addAll(followedPlaylist);
				
				session.setAttribute("songQueue", songQueue);
				session.setAttribute("user_playlist", playlists);
				mav.setViewName("main");
				mav.addObject("username", user.getUserName());
				break;
			case ARTIST:
				mav.addObject("username", user.getUserName());
				mav.setViewName("artistMainPage");
				break;
			case ADMIN:
				mav.addObject("username", user.getUserName());
				mav.setViewName("admin");
				break;
			default:
				mav.addObject("error_message", "Incorrect email or password!");
				mav.setViewName("index");
				break;
			}
		} else {
			mav.addObject("error_message", "Incorrect email or password!");
			mav.setViewName("index");
		}
		return mav;
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView mainPage(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") == null) {
			mav.setViewName("index");
			return mav;
		}
		mav.setViewName("main");
		return mav;
	}

	/* user logout */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView userLogout(ModelAndView mav, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		mav.setViewName("index");
		return mav;
	}

	/* display sign up page */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView displaySignUp(ModelAndView mav) {
		mav.addObject("signUpMessage", "Welcome to Panthers Spotify!");
		mav.setViewName("signUp");
		return mav;
	}

	/* user sign up */ /* user type: 0=basic 1=premium 2=artist 3=admin */
	@RequestMapping(value = "/userSignUp", method = RequestMethod.POST)
	public @ResponseBody String userSignUp(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String encPassword = Security.encryptPassword(password);
		String email = request.getParameter("email");
		boolean isEmailRegistered = userService.isEmailRegistered(email);
		if (isEmailRegistered) {
			return "failed";
		}
		char gender = request.getParameter("gender").charAt(0);
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		int userType = Integer.parseInt(environment.getProperty("user.basic"));
		userService.addUser(username, email, encPassword, userType, gender, firstName, middleName, lastName, dob);
		String message = "Congratulation, sign up successfully. Please return to homepage for login.";
		return message; // handle in SignUp.jsp
	}

	/* create Playlist */
	@RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
	public ModelAndView createPlaylist(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file,
			HttpServletRequest request, HttpSession session) {
		String playlistName = request.getParameter("playlist_name");
		String description = request.getParameter("playlist_description");
		User user = (User) session.getAttribute("user");
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		List<Playlist> userPlaylist = playlistService.addPlaylist(playlistName, user, description, file, date);
		mav.addObject("user_playlist", userPlaylist);
		session.setAttribute("user_playlist", userPlaylist);
		mav.setViewName("main");
		return mav;
	}

	@RequestMapping(value = "/followSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String followSpecificPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		User user = (User) session.getAttribute("user");
		System.out.println("upper1");
		System.out.println("upper2");
		System.out.println("upper3");
		Playlist ret = playlistService.followPlaylist(playlistID, user);
		System.out.println("lower");
		List<Playlist> userPlaylist = (List<Playlist>) (session.getAttribute("user_playlist"));
		userPlaylist.add(ret);
		session.setAttribute("user_playlist", userPlaylist);
		System.out.println("playlist id to be follow is : " + playlistID);
		return "ok";
	}

	@RequestMapping(value = "/unfollowSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String unfollowSpecificPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		User user = (User) session.getAttribute("user");
		Playlist ret = playlistService.unfollowPlaylist(playlistID, user);
		List<Playlist> userPlaylist = (List<Playlist>) (session.getAttribute("user_playlist"));
		userPlaylist.remove(ret);
		session.setAttribute("user_playlist", userPlaylist);
		System.out.println("here is unfollow");
		System.out.println("playlist id to be unfollow is : " + playlistID);
		return "ok";
	}

	/* edit specific playlist */
	@RequestMapping(value = "/editPlaylistDetails", method = RequestMethod.POST)
	public ModelAndView editPlaylist(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file,
			HttpServletRequest request, HttpSession session) {

		String playlist_name = request.getParameter("playlist_name");
		String description = request.getParameter("playlist_description");
		User user = (User) session.getAttribute("user");
		
		Playlist playlistOne = (Playlist) session.getAttribute("selectedPlaylist");
		// System.out.println(path+" "+filename);
		// java.sql.Date date = new
		// java.sql.Date(Calendar.getInstance().getTimeInMillis());
		int pid = playlistOne.getPid();
		List<Playlist> user_playlist = playlistService.updatePlaylist(pid, description, file, playlist_name, user);
		mav.addObject("user_playlist", user_playlist);
		session.setAttribute("user_playlist", user_playlist);
		mav.setViewName("main");
		return mav;
	}

	/* remove specific playlist */
	@RequestMapping(value = "/removeSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String removeSpecificPlaylist(ModelAndView mav, HttpServletRequest request,
			HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		Playlist playlist = playlistService.getPlaylist(playlistID);
		User user = (User) session.getAttribute("user");
		playlistService.removePlaylist(playlist);
		// playlistManager.remove(playlist_id);
		List<Playlist> user_playlist = (List<Playlist>) (session.getAttribute("user_playlist"));
		user_playlist.remove(playlist);
		session.setAttribute("user_playlist", user_playlist);
		mav.addObject("user_playlist", user_playlist);
		System.out.println("remove success");
		return "ok";
	}

	/* get specific playlist */
	@RequestMapping(value = "/getSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String getSpecificPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlist_id"));
		Playlist playlist = playlistService.getPlaylist(playlistID);
		String pname = playlist.getPname();
		System.out.println("playlist name is :" + pname);
		System.out.println("playlist num songs :" + playlist.getNSongs());
		session.setAttribute("selectedPlaylist", playlist);
		session.setAttribute("selectedPlaylistNumSongs", playlist.getNSongs());
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		session.setAttribute("list", list);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list,songService);
		JSONArray playlistSongJSON1 = JSONHelper.new_pendingSongsToJSON1(list,songService);
		session.setAttribute("jsonList", playlistSongJSON1);
		System.out.println("playlistSong json " + playlistSongJSON1);
		return playlistSongJSON;
	}
	/* add song to database */

	@RequestMapping(value = "/addSongToDatabase", method = RequestMethod.POST)
	public ModelAndView addSongToDatabase(ModelAndView mav, HttpServletRequest request, HttpSession session)
			throws ServletException, IOException {
		String songTitle = request.getParameter("song_title");
		String songTime = request.getParameter("song_time");
		String releaseDay = request.getParameter("release_day");
		String songGenre = request.getParameter("song_genre");
		String songType = request.getParameter("song_type");
		String songUrl = request.getParameter("song_file");

		Album album = albumService.getAlbum(0);
		// Song songTime and release Day to be Fixed
		Song song = new Song(songTitle, null, null, 0, songGenre, songType, songUrl);
		song = songService.addSong(songTitle, null, null, album, 0, songGenre, songType, songUrl);
		mav.setViewName("admin");
		return mav;
	}

	/* Load song from database */
	@RequestMapping(value = "/loadSong", method = RequestMethod.POST)
	public @ResponseBody String loadSongs(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		List<Song> songs = songService.getAllSongs();
		session.setAttribute("songs", songs);
		System.out.println("loadsongs" + songs.size());
		return "ok";
	}

	@RequestMapping(value = "/loadAlbum", method = RequestMethod.POST)
	public @ResponseBody String loadAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		List<Album> albums = albumService.getAllAlbums();
		session.setAttribute("album_list", albums);
		System.out.println("loadAlbum" + albums.size());
		return "ok";
	}

	@RequestMapping(value = "/editUserAccount", method = RequestMethod.POST)
	public ModelAndView editUserAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		char gender = request.getParameter("gender").charAt(0);
		String firstName = request.getParameter("firstName");
		// String middleName = request.getParameter("middleName"); Do we have to have
		// middle name??????
		String lastName = request.getParameter("lastName");
		// String iPublic = request.getParameter("isPublic"); need this button
		boolean isPublic = true;
		user = userService.updateUser(user, user.getUserName(), user.getUserType(), gender, firstName, lastName,
				isPublic);

		session.setAttribute("user", user);
		mav.setViewName("main");
		mav.addObject("username", user.getUserName());
		return mav;
	}

	@RequestMapping(value = "/editUserPassword", method = RequestMethod.POST)
	public ModelAndView editUserPassword(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String pwd = request.getParameter("password");
		String encPwd = Security.encryptPassword(pwd);
		user = userService.editUserPassword(user, encPwd);
		session.setAttribute("user", user);
		mav.setViewName("main");
		mav.addObject("username", user.getUserName());
		return mav;
	}

	@RequestMapping(value = "/deleteUserAccount", method = RequestMethod.POST)
	public ModelAndView deleteUserAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		System.out.println("deleting user account");
		User user = (User) session.getAttribute("user");
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		userService.removeUser(user);
		mav.setViewName("index");
		return mav;
	}

	// errors in this method
	@RequestMapping(value = "/getOverviewPlaylist", method = RequestMethod.POST)
	public ModelAndView getOverviewPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		// Get Top Follower Playlist
		int NUMBER_OF_TOP_FOLLOWED = 4;
		List<Playlist> userPlaylist = (List<Playlist>) (playlistService.getTopFollowedPlaylist(NUMBER_OF_TOP_FOLLOWED));

		session.setAttribute("overviewPlaylist", userPlaylist);
		mav.setViewName("main");
		return mav;
	}

	@RequestMapping(value = "/getUserFriendList", method = RequestMethod.POST)
	public @ResponseBody String getUserFriendList(ModelAndView mav, HttpServletRequest request, HttpSession session) {

		System.out.println("Getting user friendlist");
		User user = (User) session.getAttribute("user");
		List<User> temp = null;
		try {
			temp = userService.getFriend(user.getEmail());
		} catch (NullPointerException e) {
			return "empty";
		}
		if (temp.isEmpty()) {
			System.out.println("it is empty");
		} else {
			session.setAttribute("userFriendList", temp);
		}
		String friendsList = JSONHelper.userListToJSON(temp);
		System.out.println("friendsList " + friendsList.length());
		return friendsList;
	}

	@RequestMapping(value = "/findFriend", method = RequestMethod.POST)
	public @ResponseBody String findFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String userEmail = request.getParameter("userEmail");
		System.out.println("userEmail is " + userEmail);

		User temp = userService.getUser(userEmail);
		if (temp == null) {
			System.out.println("GG");
			return "null";
		} else {
			System.out.println("user found");
			session.setAttribute("selectedFriend", temp);
		}
		return "ok";
	}

	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public @ResponseBody String addFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		System.out.println("HI");
		User user = (User) session.getAttribute("user");
		String femail = request.getParameter("femail");// friend email
		List<User> temp = userService.addFriend(user.getEmail(), femail);
		session.setAttribute("userFriendList", temp);
		String friendJSON = JSONHelper.userListToJSON(temp);
		return friendJSON;
	}

	@RequestMapping(value = "/deleteFriend", method = RequestMethod.POST)
	public @ResponseBody String deleteFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		System.out.println("Delete Friend");
		User user = (User) session.getAttribute("user");
		String femail = request.getParameter("femail");// friend email
		System.out.println("Friend to be deleted" + femail);
		// boolean temp = userService.deleteFriend(user.getEmail(),femail);
		// remove the
		List<User> newFriendlist = userService.deleteFriend(user.getEmail(), femail);
		session.setAttribute("userFriendList", newFriendlist);
		String friendJSON = JSONHelper.userListToJSON(newFriendlist);
		return friendJSON;
	}

	@RequestMapping(value = "/getUserPage", method = RequestMethod.POST)
	public @ResponseBody String getUserPage(ModelAndView mav, HttpServletRequest request, HttpSession session) {

		String username = request.getParameter("username");
		String email = request.getParameter("userEmail");
		User user = userService.getUser(email);
		// User user = (users.size() != 0 )?users.get(0):null;
		session.setAttribute("selectedFriend", user);
		return "ok";
	}

	@RequestMapping(value = "/addSongToPlaylist", method = RequestMethod.POST)
	public @ResponseBody String addSongToPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		User user = (User) session.getAttribute("user");
		playlistService.addSongToPlaylist(playlistID, songID);
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		Playlist p = playlistService.getPlaylist(playlistID);
		p.setNSongs(p.getNSongs()+1);
		playlistService.updateSpecificPlaylist(p);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list, songService);
		System.out.println("addsongtoplaylist");
		return playlistSongJSON;
	}

	@RequestMapping(value = "/removeSongToPlaylist", method = RequestMethod.POST)
	public @ResponseBody String removeSongToPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		User user = (User) session.getAttribute("user");
		playlistService.removeSongFromPlaylist(playlistID, songID);
		
		Playlist p = playlistService.getPlaylist(playlistID);
		p.setNSongs(p.getNSongs()-1);
		playlistService.updateSpecificPlaylist(p);
		
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list, songService);
		System.out.println("removesongtoplaylist");
		return playlistSongJSON;
	}

	@RequestMapping(value = "/loadUserTables/{userType}", method = RequestMethod.POST)
	public @ResponseBody String loadAllUsers(@PathVariable int userType, HttpServletRequest request,
			HttpSession session) throws JSONException {
		System.out.println("user type" + userType);
		List<User> users = userService.getUsersByType(userType);
		String userJsonArray;
		userJsonArray = userType == 2 ? JSONHelper.artistListToJSON(users) : JSONHelper.userListToJSON(users);
		System.out.println("basicUserJsonArray is :" + userJsonArray);
		return userJsonArray;
	}

	@RequestMapping(value = "/loadAllPlaylists", method = RequestMethod.POST)
	public @ResponseBody String loadAllPlaylists(HttpServletRequest request, HttpSession session) throws JSONException {
		List<Playlist> playlists = playlistService.getAllPlaylists();
		String playlistsJsonArray = JSONHelper.playlistListToJSON(playlists);
		System.out.println("playlistJsonArray is :" + playlistsJsonArray);
		return playlistsJsonArray;
	}

	/* fire from admin.js admin action */
	@RequestMapping(value = "/deleteSelectedUserAccount", method = RequestMethod.POST)
	public @ResponseBody String deleteSelectedUserAccount(HttpServletRequest request, HttpSession session)
			throws JSONException {
		String userID = request.getParameter("userID");
		User li = userService.getUser(userID);
		userService.removeUser(li);
		System.out.println("removevvv");
		return "remove success";
	}

	/* fire from admin.js admin action */// did it
	@RequestMapping(value = "/deleteSelectedPlaylist", method = RequestMethod.POST)
	public @ResponseBody String deleteSelectedUserPlaylist(ModelAndView mav, HttpServletRequest request,
			HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		// Playlist playlist = playlistManager.getPlaylist(playlistID);
		// User user = (User) session.getAttribute("user");
		// playlistManager.removePlaylist(playlistID,user);
		// //playlistManager.remove(playlist_id);
		// List<Playlist> user_playlist = (List<Playlist>)
		// (session.getAttribute("user_playlist"));
		// user_playlist.remove(playlist);
		// session.setAttribute("user_playlist", user_playlist);
		// mav.addObject("user_playlist", user_playlist);
		// System.out.println("remove success");
		return "ok";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody String search(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String input = request.getParameter("input");
		List<Playlist> retPlaylist = playlistService.findRelative(input);
		List<Song> retSong = songService.findRelative(input);
		List<Album> retAlbum = albumService.findRelative(input);
		String searchJson;
		if ((retPlaylist.isEmpty() && retSong.isEmpty() && retAlbum.isEmpty())) {
			searchJson = "empty";
		} else if (input.length() == 0) {
			searchJson = "";
		} else {
			searchJson = JSONHelper.searchToJSON(retSong, retAlbum, retPlaylist);
		}
		return searchJson;
	}

	// admin action, add artist
	@RequestMapping(value = "/addArtistToDatabase", method = RequestMethod.POST)
	public ModelAndView addNewArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String artistName = request.getParameter("artistName");
		String artistEmail = request.getParameter("artistEmail");
		String artistPassword = request.getParameter("artistPassword");
		String encPwd = Security.encryptPassword(artistPassword);
		String artistFirstName = request.getParameter("artistFirstName");
		String artistMiddleName = request.getParameter("artistMiddleName");
		String artistLastName = request.getParameter("artistLastName");
		String artistBiography = request.getParameter("artistBiography");
		String artistDOB = request.getParameter("artistDOB");
		char gender = request.getParameter("gender").charAt(0);
		int userType = 2;
		userService.addUser(artistName, artistEmail, encPwd, userType, gender, artistFirstName, artistMiddleName,
				artistLastName, artistDOB);
		mav.setViewName("admin");
		System.out.println("adding artist successfully");
		return mav;
	}

	@RequestMapping(value = "/submitSongForUploading", method = RequestMethod.POST)
	public ModelAndView submitSongForUploading(ModelAndView mav,
			@RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("user");
		String songTitle = request.getParameter("song_title");
		String songTime = request.getParameter("song_time");
		String releaseDay = request.getParameter("release_day");
		String songGenre = request.getParameter("song_genre");
		String songType = request.getParameter("song_type");

		songService.uploadSong(user, songTitle, songTime, releaseDay, songGenre, songType, file);

		mav.setViewName("artistMainPage");
		return mav;
	}

	@RequestMapping(value = "/loadPendingSongs", method = RequestMethod.POST)
	public @ResponseBody String loadPendingSongs(HttpServletRequest request, HttpSession session) throws JSONException {
		List<Releasesong> releaseSongs = songService.getAllSongsByStatus("pending");
		List<Song> songs = new ArrayList<Song>();
		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		for (Releasesong rs : releaseSongs) {
			ReleasesongPK rpk = rs.getReleasesongPK();
			int sid = rpk.getSid();
			Song s = songService.getSong(sid);
			songs.add(s);
			// use hashmap<String, arraylist> to store the artist in a song
			if (map.get(sid) == null) {
				map.put(sid, new ArrayList<String>());
			}
			map.get(sid).add(rpk.getUemail()); // add the artist email to the map
		}
		System.out.println("map size " + map.size());
		System.out.println("map" + map.values());
		String pendingSongsJsonArray = JSONHelper.pendingSongsToJSON(songs, map);
		return pendingSongsJsonArray;
	}

	@RequestMapping(value = "/loadArtists", method = RequestMethod.POST)
	public @ResponseBody String loadArtists(HttpServletRequest request, HttpSession session) throws JSONException {
		List<User> artists = userService.getAllArtist();
		String artistsArray = JSONHelper.artistListToJSON(artists);
		return artistsArray;
	}

	@RequestMapping(value = "/getSpecificArtist", method = RequestMethod.POST)
	public @ResponseBody String getSpecificArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String email = request.getParameter("email");
		User user = userService.getUser(email);
		// User user = (users.size() != 0 )?users.get(0):null;
		session.setAttribute("selectedArtist", user);
		System.out.println(user.getUserName());
		return "ok";
	}

	@RequestMapping(value = "/preSong", method = RequestMethod.POST)
	public @ResponseBody String getPreSong(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> que = (Collection<SongQueue>) session.getAttribute("songQueue");
		Song song = songService.preSongInQueue(que);
		session.setAttribute("songQueue", que);
		if (song == null)
			return "end";
		else {
			session.setAttribute("nowPlay", song);
			return "ok";
		}
	}

	@RequestMapping(value = "/nextSong", method = RequestMethod.POST)
	public @ResponseBody String getNextSong(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> que = (Collection<SongQueue>) session.getAttribute("songQueue");
		Song song = songService.nextSongInQueue(que);
		session.setAttribute("songQueue", que);
		if (song != null) {
			session.setAttribute("nowPlay", song);
			return "ok";
		} else {
			return "end";
		}
	}

	@RequestMapping(value = "/shuffle", method = RequestMethod.POST)
	public @ResponseBody String shuffleQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> que = (Collection<SongQueue>) session.getAttribute("songQueue");
		que = songService.shuffleQueue(que);
		session.setAttribute("songQueue", que);
		return "ok";
	}

	@RequestMapping(value = "/playPlaylist", method = RequestMethod.GET)
	public @ResponseBody String getNewsongQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> orig = (Collection<SongQueue>) session.getAttribute("songQueue");
		String email = ((User) session.getAttribute("user")).getEmail();
		Collection<SongQueue> newSq = songService.removeAllQueue(orig, email);
		songService.addPlaylistToQueue(newSq, Integer.parseInt(request.getParameter("pid")), email);
		session.setAttribute("songQueue", newSq);
		return "ok";
	}

	@RequestMapping(value = "/addSongToQueue", method = RequestMethod.GET)
	public @ResponseBody String addSongToQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> que = (Collection<SongQueue>) session.getAttribute("songQueue");
		String email = ((User) session.getAttribute("user")).getEmail();
		int sid = Integer.parseInt(request.getParameter("sid"));
		songService.addSongToQueue(que, sid, email);
		session.setAttribute("songQueue", que);
		return "ok";
	}

	@RequestMapping(value = "/addPlaylistToQueue", method = RequestMethod.GET)
	public @ResponseBody String addPlaylistToQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Collection<SongQueue> que = (Collection<SongQueue>) session.getAttribute("songQueue");
		String email = ((User) session.getAttribute("user")).getEmail();
		songService.addPlaylistToQueue(que, Integer.parseInt(request.getParameter("pid")), email);
		session.setAttribute("songQueue", que);
		return "ok";
	}

	@RequestMapping(value = "/getSpecificAlbum", method = RequestMethod.POST)
	public @ResponseBody String getSpecificAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String albumIDString = request.getParameter("albumID");
		int albumID = Integer.parseInt(albumIDString);
		Album album = albumService.getAlbum(albumID);
		session.setAttribute("selectedAlbum", album);
		return "ok";
	}

	@RequestMapping(value = "/approveSongByAdmin", method = RequestMethod.POST)
	public @ResponseBody String approveSong(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		String status = "approved";
		songService.updateReleaseSong(songID, status);
		return "approved";
	}

	@RequestMapping(value = "/removeSongByAdmin", method = RequestMethod.POST)
	public @ResponseBody String removeSongByAdmin(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		String status = "rejected";
		songService.updateReleaseSong(songID, status);
		// need to delete the song from file system
		return "rejected";
	}
	
	@RequestMapping(value = "/addPaymentForArtist", method = RequestMethod.POST)
	public @ResponseBody String addPayment(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String cardNum = request.getParameter("cardNum");
		boolean isValidCardNum = CheckPayment.verify(cardNum);
		if (isValidCardNum) {
			String holderName = request.getParameter("holderName");
			int cvv = Integer.parseInt(request.getParameter("cvv"));
			String expirationDate = request.getParameter("expirationDate");
			java.util.Date expDate = StringToDateHelper.parseToMonthYear(expirationDate);
			User user = (User) session.getAttribute("user");
			Payment payment = new Payment();
			payment.setCardNum(cardNum);
			payment.setCvv(cvv);
			payment.setExpirationDate(expDate);
			payment.setHoldName(holderName);
			
			userService.addPayment(payment);
			return "ok";
		} else {
			return "invalid card number";
		}
		
	}

	@RequestMapping(value = "/upgrade", method = RequestMethod.POST)
	public @ResponseBody String upgrade(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String cardNum = request.getParameter("cardNum");
		System.out.println("cardNum is " + cardNum);
		boolean isValidCardNum = CheckPayment.verify(cardNum);
		if (isValidCardNum) {
			String holderName = request.getParameter("holderName");
			int cvv = Integer.parseInt(request.getParameter("cvv"));
			String expirationDate = request.getParameter("expirationDate");
			java.util.Date expDate = StringToDateHelper.parseToMonthYear(expirationDate);
			User user = (User) session.getAttribute("user");
			Payment payment = new Payment();
			payment.setCardNum(cardNum);
			payment.setCvv(cvv);
			payment.setExpirationDate(expDate);
			payment.setHoldName(holderName);
			
			userService.addPayment(payment);
			userService.upgrade(user);
			return "ok";
		} else {
			return "invalid card number";
		}
		
	}
	
	
	
	@RequestMapping(value = "/editPaymentAccount", method = RequestMethod.POST)
	public @ResponseBody String editPaymentAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String cardNum = request.getParameter("cardNum");
		System.out.println("cardNum is " + cardNum);
		boolean isValidCardNum = CheckPayment.verify(cardNum);
		if (isValidCardNum) {
			String holderName = request.getParameter("holderName");
			int cvv = Integer.parseInt(request.getParameter("cvv"));
			String expirationDate = request.getParameter("expirationDate");
			java.util.Date expDate = StringToDateHelper.parseToMonthYear(expirationDate);
			User user = (User) session.getAttribute("user");
			Payment payment = new Payment();
			payment.setCardNum(cardNum);
			payment.setCvv(cvv);
			payment.setExpirationDate(expDate);
			payment.setHoldName(holderName);
			
			userService.updatePayment(payment);
			return "ok";
		} else {
			return "invalid card number";
		}
		
	}

	@RequestMapping(value = "/downgrade", method = RequestMethod.POST)
	public @ResponseBody String downgrade(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		// at the end of the month do it
		User user = (User) session.getAttribute("user");
		userService.removePayment(user.getPayment());// need to test
		userService.downgrade(user);
		return null;
	}
	
	@RequestMapping(value = "/getSpecificAlbumInfo", method = RequestMethod.POST)
	public @ResponseBody String getSpecificAlbumInfo(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Object albumObj = session.getAttribute("selectedAlbum");
		String albumInfo = null;
		if(albumObj != null) {
			Album album = (Album) albumObj;
			albumInfo = JSONHelper.albumInformation(album, albumService, songService);
		}
		return albumInfo;
	}
	
	@RequestMapping(value = "/displayCheckRoyalty", method = RequestMethod.POST)
	public @ResponseBody String checkRoyalty(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		List<User> artists = userService.getAllArtist();
		double factor = Double.parseDouble(environment.getProperty("artist.royalty.factor"));
		for(int i=0;i<artists.size();i++)
		{
			userService.setArtistRoylties(artists.get(i).getArtist(), factor);
		}
		
		String artistRoyaltyInfo = JSONHelper.getAllArtistRoyalty(artists);
		return artistRoyaltyInfo;
	}
	
	@RequestMapping(value = "/payOneCheckRoyalty", method = RequestMethod.POST)
	public @ResponseBody String payOneCheckRoyalty(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		User user = (User) session.getAttribute("user");//admin page only
		String artistEmail = request.getParameter("artistEmail");
		String artistRoyalty = request.getParameter("royalty");
		double artistRoyal = Double.parseDouble(artistRoyalty);
		List<User> artists = userService.getAllArtist();
		
		
		User ar = userService.getUser(artistEmail);
		Payment artistPayment = ar.getPayment();
		artistPayment.setBalance(artistPayment.getBalance()+artistRoyal);
		userService.updatePayment(artistPayment);
		
		Payment adminPayment = user.getPayment();
		adminPayment.setBalance(adminPayment.getBalance()-artistRoyal);
		userService.updatePayment(adminPayment);
		
		List<Song> songs = userService.getReleaseSong(ar.getArtist());
		for(int i=0;i<songs.size();i++)
		{
			songService.updateMontlySong(0, songs.get(i));
		}
		for(int i=0;i<artists.size();i++)
		{
			if(artists.get(i).getArtist().getRoyalty()==0)
			{
				artists.remove(i);
			}
		}
		
		String artistRoyaltyInfo = JSONHelper.getAllArtistRoyalty(artists);
		return artistRoyaltyInfo;
	}
	
	@RequestMapping(value = "/payAllCheckRoyalty", method = RequestMethod.POST)
	public @ResponseBody String payAllCheckRoyalty(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		User user = (User) session.getAttribute("user");//admin page only
		List<User> artists = userService.getAllArtist();
		
		for(int j=0;j<artists.size();j++)
		{
			User ar = artists.get(j);
			Payment artistPayment = ar.getPayment();
			artistPayment.setBalance(artistPayment.getBalance()+ar.getArtist().getRoyalty());
			userService.updatePayment(artistPayment);
			
			Payment adminPayment = user.getPayment();
			adminPayment.setBalance(adminPayment.getBalance()-ar.getArtist().getRoyalty());
			userService.updatePayment(adminPayment);
			
			List<Song> songs = userService.getReleaseSong(ar.getArtist());
			for(int i=0;i<songs.size();i++)
			{
				songService.updateMontlySong(0, songs.get(i));
			}
		}
		//return main page
		return "ok";
	}
	
}