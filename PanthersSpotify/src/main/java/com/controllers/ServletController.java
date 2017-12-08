
package com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.helper.SortTitle;
import com.helper.StringToDateHelper;
import com.helper.UploadFile;
//import com.helper.StringToDateHelper;
import com.model.Album;
import com.model.Artist;
import com.model.Concert;
import com.model.Payment;
import com.model.Song;
import com.model.SongQueue;
import com.model.Playlist;
import com.model.Playlistsong;
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
		
		if (user==null) {//||user.getIsLogin()
			mav.setViewName("index");
			mav.addObject("error_message", "This email does not register on our site!");
		}
		else if(user.getIsBan())
		{
			mav.setViewName("index");
			mav.addObject("error_message", "you are banned!!!!");
		}
		else if (Security.matchPassword(password, user.getUserPassword())|| user.getUserPassword().equals(encPwd)) {
			int userTypeInt = user.getUserType();
			user.setIsLogin(true);
			userService.updateSpecificUser(user);
			UserType userType = UserType.BASIC;
			UserType[] types = { UserType.BASIC, UserType.PREMIUM, UserType.ARTIST, UserType.ADMIN };
			for (UserType type : types) {
				if (type.getTypeCode() == userTypeInt) {
					userType = type;
				}
			}
			Collection<SongQueue> songQueue = new ArrayList<SongQueue>();
			Collection<Playlist> playlists = new ArrayList<Playlist>();
			Collection<Playlist> followedPlaylist = new ArrayList<Playlist>();
			switch (userType) {
			case BASIC:
				songQueue = userService.getQueue(email);
				songService.setArtistsCollection(songQueue);
				playlists = user.getUserPlaylistCollection();
				followedPlaylist = playlistService.getFollowPlaylists(email);
				playlists.addAll(followedPlaylist);
				session.setAttribute("user_playlist", playlists);
                                session.setAttribute("userFollowedPlaylists", followedPlaylist);
				user.setSongQueueCollection(songQueue);
				session.setAttribute("user", user);
				songService.setArtistsCollection(songQueue);
				mav.setViewName("main");
				mav.addObject("username", user.getUserName());
				break;
			case PREMIUM:
				songQueue = userService.getQueue(email);
				songService.setArtistsCollection(songQueue);
				playlists = user.getUserPlaylistCollection();
				followedPlaylist = playlistService.getFollowPlaylists(email);
				playlists.addAll(followedPlaylist);
				session.setAttribute("user_playlist", playlists);
                                session.setAttribute("userFollowedPlaylists", followedPlaylist);
				user.setSongQueueCollection(songQueue);
				session.setAttribute("user", user);
				mav.setViewName("main");
				mav.addObject("username", user.getUserName());
				break;
			case ARTIST:
                session.setAttribute("user", user);
				mav.addObject("username", user.getUserName());
				mav.setViewName("artistMainPage");
				break;
			case ADMIN:
				session.setAttribute("user", user);
				//double factor = Double.parseDouble(environment.getProperty("artist.royalty.factor"));
				//userService.setArtistsRoylties(factor);
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
		User user = (User)session.getAttribute("user");
		//user.setIsLogin(false);
		
		//userService.updateSpecificUser(user);
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
		userService.addUser(username, email, encPassword, userType, gender, firstName, middleName, lastName, dob,null);
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
		if(user.getUserType()==0||user.getUserType()==1)
			mav.setViewName("main");
		else if(user.getUserType()==3)
			mav.setViewName("admin");
		
		
		
		
		return mav;
	}
	
	/* create Album */
	@RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
	public ModelAndView createAlbum(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file,
			HttpServletRequest request, HttpSession session) {
		String albumName = request.getParameter("album_name");
		String description = request.getParameter("album_description");
		String genre = request.getParameter("genre");
		User user = (User) session.getAttribute("user");
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		
		
		List<Album> userAlbum = albumService.addAlbum(albumName, user, description, file, date);
		mav.addObject("user_album", userAlbum);
		session.setAttribute("user_album", userAlbum);
		if(user.getUserType()==2)
			mav.setViewName("artistMainPage");
		else if(user.getUserType()==3)
			mav.setViewName("admin");
			
		return mav;
	}

	@RequestMapping(value = "/followSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String followSpecificPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		User user = (User) session.getAttribute("user");
		Playlist ret = playlistService.followPlaylist(playlistID, user);
		ret.setFollowers(ret.getFollowers()+1);
		List<Playlist> userPlaylist = (List<Playlist>) (session.getAttribute("user_playlist"));
		userPlaylist.add(ret);
		session.setAttribute("user_playlist", userPlaylist);
		List<Playlist> followedPlaylist = (List<Playlist> )(session.getAttribute("userFollowedPlaylists"));
                followedPlaylist.add(ret);
                session.setAttribute("userFollowedPlaylists", followedPlaylist);
		System.out.println("playlist id to be follow is : " + playlistID);
                String jsonResult = JSONHelper.singlePlaylistToJSON(ret);
                return jsonResult;
	}

	@RequestMapping(value = "/unfollowSpecificPlaylist", method = RequestMethod.POST)
	public @ResponseBody String unfollowSpecificPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		User user = (User) session.getAttribute("user");
		Playlist ret = playlistService.unfollowPlaylist(playlistID, user);
		ret.setFollowers(ret.getFollowers()-1);
		List<Playlist> userPlaylist = (List<Playlist>) (session.getAttribute("user_playlist"));
		userPlaylist.remove(ret);
		session.setAttribute("user_playlist", userPlaylist);
                List<Playlist> followedPlaylist = (List<Playlist> )(session.getAttribute("userFollowedPlaylists"));
                followedPlaylist.remove(ret);
                session.setAttribute("userFollowedPlaylists", followedPlaylist);
		System.out.println("here is unfollow");
		System.out.println("playlist id to be unfollow is : " + playlistID);
		return "ok";
	}
	
	@RequestMapping(value = "/followSpecificArtist", method = RequestMethod.POST)
	public @ResponseBody String followSpecificArtist(HttpServletRequest request, HttpSession session) {
		String artistEmail = request.getParameter("artistEmail");
		User user = (User) session.getAttribute("user");
		userService.followArtist(artistEmail, user);
		//session.setAttribute("followedArtist", userPlaylist);
		List<Artist> artists = userService.getFollowArtists(user);
		System.out.println("artist email to be follow is : " + artistEmail);
		return "ok";
	}

	@RequestMapping(value = "/unfollowSpecificArtist", method = RequestMethod.POST)
	public @ResponseBody String unfollowSpecificArtist(HttpServletRequest request, HttpSession session) {
		String artistEmail = request.getParameter("artistEmail");
		User user = (User) session.getAttribute("user");
		userService.unfollowArtist(artistEmail, user);
		List<Artist> artists = userService.getFollowArtists(user);
		//List<Playlist> userPlaylist = (List<Playlist>) (session.getAttribute("user_playlist"));
		//userPlaylist.remove(ret);
		//session.setAttribute("user_playlist", userPlaylist);
		//System.out.println("here is unfollow");
		//System.out.println("playlist id to be unfollow is : " + playlistID);
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
		//String pname = playlist.getPname();
		session.setAttribute("selectedPlaylist", playlist);
		session.setAttribute("selectedPlaylistNumSongs", playlist.getNSongs());
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		//session.setAttribute("list", list);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list,songService);
		JSONArray playlistSongJSON1 = JSONHelper.new_pendingSongsToJSON1(list,songService);
		session.setAttribute("jsonList", playlistSongJSON1);
		System.out.println("playlistSong json " + playlistSongJSON1);
		session.setAttribute("playlistSongJSON", playlistSongJSON);
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
	
	///----------admin delete song from database-----------------------------
	@RequestMapping(value = "/deleteSongToDatabase", method = RequestMethod.POST)
	public ModelAndView deleteSongToDatabase(ModelAndView mav, HttpServletRequest request, HttpSession session)
			throws ServletException, IOException {
		String songtmp = request.getParameter("songId");
		int songId = Integer.parseInt(songtmp);
		Song deleteSong = songService.getSong(songId);
		songService.removeSong(deleteSong);
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
                String albumsJSON = JSONHelper.albumListToJSON(albums);
		session.setAttribute("album_list", albums);
                System.out.println("albumsJSON " + albumsJSON);
		return albumsJSON;
	}

	@RequestMapping(value = "/editUserAccount", method = RequestMethod.POST)
	public ModelAndView editUserAccount(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file,
			HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		char gender = request.getParameter("gender").charAt(0);
		String firstName = request.getParameter("firstName");
		// String middleName = request.getParameter("middleName"); Do we have to have
		// middle name??????
		String lastName = request.getParameter("lastName");
		String bio = null;
		String selectedArtist = null;
		if(user.getUserType()==2)
		{
			bio = request.getParameter("artistBio");
			userService.editArtist(user, bio);
			boolean isPublic = true;
			if(user.getPhotoUrl()!=null)
			{
				String photoUrl1= user.getPhotoUrl() != null? user.getPhotoUrl().substring(1,user.getPhotoUrl().length()):user.getPhotoUrl();
				String dir1 = System.getProperty("user.dir");
				String userPath1 = dir1+"/src/main/webapp/WEB-INF/resources/data/Users/"+photoUrl1;
				File userFile1 = new File(userPath1);
				if(userFile1.exists())
				{
					userFile1.delete();
				}
				else
				{
					System.out.println("error in delete photo");
				}
			}
			
			
			String filename = file.getOriginalFilename();
			String dir = System.getProperty("user.dir");
		    	String userPath = dir+"/src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail();
		    	File userFile = new File(userPath);
		    	String photoUrl = UploadFile.upload(userPath,filename,file);
		    	if(photoUrl==null)
		    	{
		    		photoUrl=user.getPhotoUrl();
		    		userFile.delete();
		    	}
		    	
		    	
			user = userService.updateUser(user, user.getUserName(), user.getUserType(), gender, firstName, lastName,
				isPublic,user.getPhotoUrl());
		}
		else if(user.getUserType()==3)
		{
			bio = request.getParameter("artistBio");
			selectedArtist = request.getParameter("artistEmail");
			User selectedUser = userService.getUser(selectedArtist);
			userService.editArtist(selectedUser, bio);
			boolean isPublic = true;
			
			if(user.getPhotoUrl()!=null)
			{
				String photoUrl1= selectedUser.getPhotoUrl() != null? selectedUser.getPhotoUrl().substring(1,selectedUser.getPhotoUrl().length()):selectedUser.getPhotoUrl();
				String dir1 = System.getProperty("user.dir");
				String userPath1 = dir1+"/src/main/webapp/WEB-INF/resources/data/"+photoUrl1;
				File userFile1 = new File(userPath1);
				if(userFile1.exists())
				{
					userFile1.delete();
				}
				else
				{
					System.out.println("error in delete photo");
				}
			}
			
			String filename = file.getOriginalFilename();
			String dir = System.getProperty("user.dir");
		    	String userPath = dir+"/src/main/webapp/WEB-INF/resources/data/"+user.getEmail();
		    	File userFile = new File(userPath);
		    	String photoUrl = UploadFile.upload(userPath,filename,file);
		    	if(photoUrl==null)
		    	{
		    		photoUrl=selectedUser.getPhotoUrl();
		    		userFile.delete();
		    	}
		    	photoUrl = "/Users/"+selectedUser.getEmail()+"/"+filename;
			user = userService.updateUser(selectedUser, selectedUser.getUserName(), selectedUser.getUserType(), gender, firstName, lastName,
				isPublic,selectedUser.getPhotoUrl());
		}
		else
		{
			boolean isPublic = true;
			System.out.println("edit here");
			if(user.getPhotoUrl()!=null)
			{
				String photoUrl1= user.getPhotoUrl() != null? user.getPhotoUrl().substring(1,user.getPhotoUrl().length()):user.getPhotoUrl();
				String dir1 = System.getProperty("user.dir");
				String userPath1 = dir1+"/src/main/webapp/WEB-INF/resources/data/"+photoUrl1;
				System.out.println(userPath1);
				File userFile1 = new File(userPath1);
				if(userFile1.exists())
				{
					userFile1.delete();
					System.out.println("delete it");
				}
				else
				{
					System.out.println("error in delete photo");
				}
			}
			
			String filename = file.getOriginalFilename();
			String dir = System.getProperty("user.dir");
		    	String userPath = dir+"/src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail();
		    	File userFile = new File(userPath);
		    	String photoUrl = UploadFile.upload(userPath,filename,file);
		    	if(photoUrl==null)
		    	{
		    		userFile.delete();
		    	}
		    	else
		    	{
		    		photoUrl = "/Users/"+user.getEmail()+"/"+filename;
		    	}
			user = userService.updateUser(user, user.getUserName(), user.getUserType(), gender, firstName, lastName,
				isPublic,photoUrl);

			
			
		}
		
		session.setAttribute("user", user);
		if(user.getUserType()==2)
		{
			mav.setViewName("artistMainPage");
		}
		else if(user.getUserType()==3)
		{
			mav.setViewName("admin");
		}
		else
		{
			mav.setViewName("main");
		}
		mav.addObject("username", user.getUserName());
		return mav;
	}

	@RequestMapping(value = "/editUserPassword", method = RequestMethod.POST)
	public @ResponseBody String editUserPassword(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String pwd = request.getParameter("password");
		String token = request.getParameter("token");
		System.out.println(token + " " + user.getToken());
		if(token.equals(user.getToken()))
		{
			String encPwd = Security.encryptPassword(pwd);
			user = userService.editUserPassword(user, encPwd);
			session.setAttribute("user", user);
		}
		else
		{
			return "invalid error";
		}		
		
		mav.addObject("username", user.getUserName());
		return "ok";
	}
	
	
	@RequestMapping(value = "/privateSession", method = RequestMethod.POST)
	public @ResponseBody String privateSession(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		System.out.println("herere");
		if(user.getPrivateSession())
		{
			user.setPrivateSession(false);
		}
		else
		{
			user.setPrivateSession(true);
		}		
		userService.updateSpecificUser(user);
		System.out.println(user.getPrivateSession());
		String status = user.getPrivateSession()?"true":"false";
		return status;
	}
	
	@RequestMapping(value = "/editUserSetting", method = RequestMethod.POST)
	public @ResponseBody String editUserSetting(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String privacy = request.getParameter("privacy");
		String language = request.getParameter("lang");
		
		user.setLanguage(language);	
		user.setIsPublic((privacy.equals("Public")? true:false));		
		
		userService.updateSpecificUser(user);
		return "ok";
	}
	
	//this is in the change password place
	@RequestMapping(value = "/sendToken", method = RequestMethod.POST)
	public ModelAndView sendToken(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		userService.editUserToken(user);
		mav.setViewName("main");
		mav.addObject("username", user.getUserName());
		return mav;
	}

	@RequestMapping(value = "/deleteUserAccount", method = RequestMethod.POST)
	public ModelAndView deleteUserAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		System.out.println("deleting user account");
		User user = (User) session.getAttribute("user");
		if (session.getAttribute("user") != null&&user.getUserType()!=3) {
			session.removeAttribute("user");
		}
		
		if(user.getUserType()!=3)
		{
			userService.removeUser(user);
			mav.setViewName("index");
		}
		else
			mav.setViewName("admin");
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
		/*
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		User user = (User) session.getAttribute("user");
		playlistService.addSongToPlaylist(playlistID, songID);
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		Playlist p = playlistService.getPlaylist(playlistID);
		Time t = playlistService.setPlaylistTimeLength(p, list);
		//Song song = songService.getSong(songID);
		p.setTimelength(p.getTimelength());
		p.setNSongs(p.getNSongs()+1);
		p.setTimelength(t);
		playlistService.updateSpecificPlaylist(p);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list, songService);
		System.out.println("addsongtoplaylist");
		return playlistSongJSON;
		*/
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		User user = (User) session.getAttribute("user");
		String res = playlistService.addSongToPlaylist(playlistID, songID);
		if (res==null) {
			return "fail";
		}
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		Playlist p = playlistService.getPlaylist(playlistID);
		Time t = playlistService.setPlaylistTimeLength(p, list);
		//Song song = songService.getSong(songID);
		p.setTimelength(p.getTimelength());
		p.setNSongs(p.getNSongs()+1);
		p.setTimelength(t);
		playlistService.updateSpecificPlaylist(p);
		return "ok";
	}

	@RequestMapping(value = "/removeSongFromPlaylist", method = RequestMethod.POST)
	public @ResponseBody String removeSongToPlaylist(HttpServletRequest request, HttpSession session) {
		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		User user = (User) session.getAttribute("user");
		playlistService.removeSongFromPlaylist(playlistID, songID);
		
		Playlist p = playlistService.getPlaylist(playlistID);
		p.setNSongs(p.getNSongs()-1);
		
		
		List<Song> list = playlistService.getSongInPlaylist(playlistID);
		
		
		Time t = playlistService.setPlaylistTimeLength(p, list);
		p.setTimelength(t);
		playlistService.updateSpecificPlaylist(p);
		String playlistSongJSON = JSONHelper.new_pendingSongsToJSON(list, songService);
		System.out.println("removesongtoplaylist");
		return playlistSongJSON;
	}

	@RequestMapping(value = "/loadUserTables/{userType}", method = RequestMethod.POST)
	public @ResponseBody String loadAllUsers(@PathVariable int userType, HttpServletRequest request,
			HttpSession session) throws JSONException {
		
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
	
	///----------------------------admin page display all albums
	@RequestMapping(value = "/loadAllAlbum", method = RequestMethod.POST)
	public @ResponseBody String loadAllAlbum(HttpServletRequest request, HttpSession session) throws JSONException {
		List<Album> albums = albumService.getAllAlbums();
		
		String albumsJsonArray = JSONHelper.albumListToJSON(albums);
		//System.out.println("playlistJsonArray is :" + albumsJsonArray);
		return albumsJsonArray;
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
		Playlist playlist = playlistService.getPlaylist(playlistID);
		playlistService.removePlaylist(playlist);
		List<Playlist> playlists = playlistService.getAllPlaylists();
		String playlistsJsonArray = JSONHelper.playlistListToJSON(playlists);
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
		return playlistsJsonArray;
	}

	@RequestMapping(value = "/searchAlbum", method = RequestMethod.POST)
	public @ResponseBody String searchAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String input = request.getParameter("input");
		List<Album> retAlbum = albumService.findRelative(input);
		List<Playlist> retPlaylist = playlistService.findRelative(input);
		List<User> retArtist = userService.findRelative(input);
		session.setAttribute("album_list", retAlbum);
		session.setAttribute("artist_list", retArtist);
		session.setAttribute("SearchingPlaylist", retPlaylist);

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
		System.out.println(searchJson);	
		Object gg = session.getAttribute("album_list");
		System.out.println(gg);
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
				artistLastName, artistDOB,artistBiography);
		mav.setViewName("admin");
		System.out.println("adding artist successfully");
		return mav;
	}

	@RequestMapping(value = "/addUserToDatabase", method = RequestMethod.POST)
	public ModelAndView addUserToDatabase(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String artistPassword = request.getParameter("password");
		String encPwd = Security.encryptPassword(artistPassword);
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");		
		String DOB = request.getParameter("DOB");
		char gender = request.getParameter("gender").charAt(0);
		int userType = request.getParameter("userType").equals("Basic")?0:1;
		userService.addUser(name, email, encPwd, userType, gender, firstName, middleName,
				lastName, DOB, null);
		mav.setViewName("admin");
		System.out.println("adding user successfully");
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
		//String albumIdtmp = request.getParameter("song_album_id");
		//int albumId = Integer.parseInt(albumIdtmp);
		Album album = albumService.getAlbum(0);
		
		songService.uploadSong(user, songTitle, songTime, releaseDay, songGenre, songType, file,album);

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
	
	@RequestMapping(value = "/loadApprovedSongs", method = RequestMethod.POST)
	public @ResponseBody String loadApprovedSongs(HttpServletRequest request, HttpSession session) throws JSONException {
		List<Releasesong> releaseSongs = songService.getAllSongsByStatus("approved");
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
	
	//----------artist remove request song
		@RequestMapping(value = "/removeRequestSongForUploading", method = RequestMethod.POST)
		public ModelAndView removeRequestSongForUploading(ModelAndView mav, HttpServletRequest request, HttpSession session)
				throws ServletException, IOException {
			User user = (User) session.getAttribute("user");
			String songtmp = request.getParameter("songId");
			int songId = Integer.parseInt(songtmp);
			songService.uploadRemoveSong(user, songId);
			List<Song> s = songService.getStatusSongs("removePending");
			String retSongs = JSONHelper.new_pendingSongsToJSON(s,songService);
			mav.setViewName("artistMainPage");
			return mav;
		}

	@RequestMapping(value = "/loadArtists", method = RequestMethod.POST)
	public @ResponseBody String loadArtists(HttpServletRequest request, HttpSession session) throws JSONException {
		List<User> artists = userService.getAllArtist();
		String artistsArray = JSONHelper.artistListToJSON(artists);
		return artistsArray;
	}
    
	@RequestMapping(value = "/editArtistBio", method = RequestMethod.POST)
	public ModelAndView editArtistBio(ModelAndView mav, HttpServletRequest request, HttpSession session) throws JSONException {
		User user = (User) session.getAttribute("user");
		String bio = request.getParameter("bio");
		String username = request.getParameter("username");
		userService.editArtist(user, bio);
		user = userService.getUser(user.getEmail());
		userService.updateUser(user, username,user.getUserType(),'M',null,null,user.getIsPublic(),user.getPhotoUrl());
		user = userService.getUser(user.getEmail());
		session.setAttribute("user", user);
		mav.setViewName("artistMainPage");
		return mav;
	}
	
	@RequestMapping(value = "/getSpecificArtist", method = RequestMethod.POST)
	public @ResponseBody String getSpecificArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String email = request.getParameter("email");
		User artist = userService.getUser(email);
		// User user = (users.size() != 0 )?users.get(0):null;
		session.setAttribute("selectedArtist", artist);
		User user = (User)session.getAttribute("user");
		List<Artist> followingArtist = userService.getFollowArtists(user);
		List<String> followingArtistEmail = new ArrayList();
		for(Artist a: followingArtist) {
			followingArtistEmail.add(a.getArtistEmail());
		}
		session.setAttribute("userFollowingArtist", followingArtistEmail);
		Artist artistObj = userService.getArtistInfo(artist);
		session.setAttribute("selectedArtistFollowers", artistObj.getFollowers());
		return "ok";
	}
	
	@RequestMapping(value = "/loadFollowingArtist", method = RequestMethod.POST)
	public @ResponseBody String loadFollowingArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Artist> followingArtist = userService.getFollowArtists(user);
		List<User> artistUserList = new ArrayList<User>();
		for(Artist a : followingArtist) {
			artistUserList.add(userService.getUser(a.getArtistEmail()));
		}
		
		String artistsArray = JSONHelper.artistListToJSON(artistUserList);
		return artistsArray;
	}
	

	@RequestMapping(value = "/preSong", method = RequestMethod.POST)
	public @ResponseBody String getPreSong(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Collection<SongQueue> que = user.getSongQueueCollection();
		Song song = songService.preSongInQueue(que);
		if(user.getPrivateSession() == false) {
			songService.addToHistory(user.getEmail(), song.getSid());
		}
		user.setSongQueueCollection(que);
		session.setAttribute("user", user);
		/*
		if (song == null) {
			JSONObject result = JSONHelper.songQueueToJSON(que);
			session.setAttribute("queueJSON", result);
			return result.toString();
		}
		else {
			session.setAttribute("nowPlay", song);
			return "ok";
		}
		*/
		JSONObject result = JSONHelper.songQueueToJSON(que);
		session.setAttribute("queueJSON", result);
		return result.toString();
	}

	@RequestMapping(value = "/nextSong", method = RequestMethod.POST)
	public @ResponseBody String getNextSong(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		boolean isShuffle = request.getParameter("isShuffle").equals("true");
		JSONObject json = new JSONObject();
		Collection<SongQueue> que = new ArrayList<SongQueue>();
		User user = (User)session.getAttribute("user");
		
		if (isShuffle) {
			que = (Collection<SongQueue>) session.getAttribute("preQueue");
		}else {
			que = user.getSongQueueCollection();
		}
		Song song = songService.nextSongInQueue(que);
		if(user.getPrivateSession() == false) {
			songService.addToHistory(user.getEmail(), song.getSid());
		}
		if(isShuffle)
			session.setAttribute("preQueue", que);
		else {
			user.setSongQueueCollection(que);
			session.setAttribute("user", user);
		}
		/*
		if (song != null) {
			session.setAttribute("nowPlay", song);
			return "ok";
		} else {
			return "end";
		}
		*/
		JSONObject result = JSONHelper.songQueueToJSON(que);
		session.setAttribute("queueJSON", result);
		return result.toString();
	}

	@RequestMapping(value = "/shuffle", method = RequestMethod.POST)
	public @ResponseBody String shuffleQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Collection<SongQueue> que = userService.getQueue(user.getEmail());
		//JSONObject preQue = JSONHelper.songQueueToJSON(que);
		songService.setArtistsCollection(que);
		session.setAttribute("preQueue", que);
		boolean isShuffle = (request.getParameter("isShuffle").equals("true"));
		que = isShuffle ? songService.shuffleQueue(que) : que;
		user.setSongQueueCollection(que);
		JSONObject result = JSONHelper.songQueueToJSON(que);
		session.setAttribute("queueJSON", result);
		session.setAttribute("user", user);
		return result.toString();
	}

	@RequestMapping(value = "/playPlaylist", method = RequestMethod.POST)
	public @ResponseBody String getNewSongQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Date now = new Date();
		System.out.println("start time: "+now);
		Collection<SongQueue> orig = user.getSongQueueCollection();
		String email = user.getEmail();
		List<SongQueue> newSq = (List<SongQueue>) songService.removeAllQueue(orig, email);
        int pid = Integer.parseInt(request.getParameter("pid"));
       
        
        
      //----------------------------------------------------------------------------------------
        Playlist p = playlistService.getPlaylist(pid);
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		/*if(user.getPrivateSession()==false)
		{
			playlistService.addHistoryPlaylist(p, user, date);
		}*/
		
        List<Song> s = playlistService.getSongInPlaylist(pid);
        Song specificSong = new Song();
        if(s.size()>0)
        	specificSong = s.get(0);
        
        //songService.updateMontlySong(specificSong.getMonthlyPlayed()+1, specificSong);
        //-------------------------794---------------------------------------------------------------
        if(user.getPrivateSession()==false)
		{
        	songService.addToHistory(email, specificSong.getSid());
		}
		
        songService.addPlaylistToQueue(newSq, pid, email);
		String sid = request.getParameter("sid");
		int index = newSq.get(0).getSong().getSid();
		if (sid != null)
			index = Integer.parseInt(sid);
		if(newSq.size()>0) {
			songService.setNowPlay(newSq, index);
			songService.setArtistsCollection(newSq);
		}
		user.setSongQueueCollection(newSq);
		JSONObject result = JSONHelper.songQueueToJSON(newSq);
		session.setAttribute("queueJSON", result);
		session.setAttribute("user", user);
		now = new Date();
		System.out.println("ended time: "+now);
		return result.toString();
	}

	@RequestMapping(value = "/addSongToQueue", method = RequestMethod.GET)
	public @ResponseBody String addSongToQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Collection<SongQueue> que = user.getSongQueueCollection();
		String email = user.getEmail();
		int sid = Integer.parseInt(request.getParameter("sid"));
		String res = songService.addSongToQueue(que, sid, email);
		if (res.equals("ok")) {
			user.setSongQueueCollection(que);
			JSONObject result = JSONHelper.songQueueToJSON(que);
			session.setAttribute("queueJSON", result);
			session.setAttribute("user", user);
			return res;
		}else {
			return res;
		}
	}

	@RequestMapping(value = "/addPlaylistToQueue", method = RequestMethod.GET)
	public @ResponseBody String addPlaylistToQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Collection<SongQueue> que = user.getSongQueueCollection();
		String email = user.getEmail();
		songService.addPlaylistToQueue(que, Integer.parseInt(request.getParameter("pid")), email);
		user.setSongQueueCollection(que);
		JSONObject result = JSONHelper.songQueueToJSON(que);
		session.setAttribute("queueJSON", result);
		session.setAttribute("user", user);
		return result.toString();
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

	//--------------edit delete song from database---admin-----
	@RequestMapping(value = "/removeSongByAdmin", method = RequestMethod.POST)
	public @ResponseBody String removeSongByAdmin(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		int songID = Integer.parseInt(request.getParameter("songID").trim());
		
		String status = "rejected";
		songService.updateReleaseSong(songID, status);
		
		Song deleteSong = songService.getSong(songID);
		songService.removeSong(deleteSong);
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
			payment.setUemail(user.getEmail());
			payment.setUser(user);
			
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
		System.out.println("it is "+isValidCardNum);
		if (isValidCardNum) {
			String holderName = request.getParameter("holderName");
			int cvv = Integer.parseInt(request.getParameter("cvv"));
			String expirationDate = request.getParameter("expirationDate");
			java.util.Date expDate = StringToDateHelper.parseToMonthYear(expirationDate);
			User user = (User) session.getAttribute("user");
			Payment payment = new Payment();
			payment.setUser(user);
			payment.setUemail(user.getEmail());
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
		userService.removePayment(user.getEmail());// need to test
		user.setPayment(null);
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
	
	@RequestMapping(value = "/displayArtistCheckRoyalty", method = RequestMethod.POST)
	public @ResponseBody String displayArtistCheckRoyalty(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String artistEmail = request.getParameter("artistEmail");
		User user = userService.getUser(artistEmail);
		double factor = Double.parseDouble(environment.getProperty("artist.royalty.factor"));
		
			userService.setArtistRoylties(userService.getArtistInfo(user), factor);
//			List<Song> songs = userService.getReleaseSong(userService.getArtistInfo(user));
//			for(int j=0;j<songs.size();j++)
//			{
//				songService.updateMontlySong(0, songs.get(j));
//			}
		
		
		String artistRoyaltyInfo = JSONHelper.getOneArtistRoyalty(user,userService);
		return artistRoyaltyInfo;
	}
	
	
	
	@RequestMapping(value = "/displayCheckRoyalty", method = RequestMethod.POST)
	public @ResponseBody String checkRoyalty(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		List<User> artists = userService.getAllArtist();
		double factor = Double.parseDouble(environment.getProperty("artist.royalty.factor"));
		
		List ret = userService.setArtistsRoylties(factor);
		String artistRoyaltyInfo = JSONHelper.getAllArtistsRoyalty(ret,userService);
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
		
		List<Song> songs = userService.getReleaseSong(userService.getArtistInfo(ar));
		for(int i=0;i<songs.size();i++)
		{
			songService.updateMontlySong(0, songs.get(i));
		}
		for(int i=0;i<artists.size();i++)
		{
			if(userService.getArtistInfo(artists.get(i)).getRoyalty()==0)
			{
				artists.remove(i);
			}
		}
		
		String artistRoyaltyInfo = JSONHelper.getAllArtistRoyalty(artists,userService);
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
	
	@RequestMapping(value = "/getGenre", method = RequestMethod.POST)
	public @ResponseBody String getGenre(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String display = environment.getProperty("genre.type");
		String[] splitted = display.split(",");
		
		String genres = JSONHelper.getGenres(splitted);
		return genres;
	}
	
	@RequestMapping(value = "/getSpecificGenre", method = RequestMethod.POST)
	public @ResponseBody String getSpecificGenre(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String genre = request.getParameter("genre");
		String display = environment.getProperty("displayAmount");
		int displayAmount = Integer.parseInt(display);
		
		List<Album> retAlbums = albumService.getTopGenreAlbum(genre,1,displayAmount);
		List<Playlist> retPlaylist = playlistService.getTopGenrePlaylist(genre);
		return "ok";
	}
	
	@RequestMapping(value = "/getPageGenre", method = RequestMethod.POST)
	public @ResponseBody String getPageGenre(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		String genre = request.getParameter("genre");
		String pageNum = request.getParameter("pageNum");
		String display = environment.getProperty("displayAmount");
		
		int displayAmount = Integer.parseInt(display);
		int pageNumInt = Integer.parseInt(pageNum);
		List<Album> retAlbums = albumService.getTopGenreAlbum(genre,pageNumInt,displayAmount);
		List<Playlist> retPlaylist = playlistService.getTopGenrePlaylist(genre);
		System.out.println(retAlbums.size());
		session.setAttribute("album_list", retAlbums);
		session.setAttribute("selectedGenre",genre);
		return "ok";
	}
	
	@RequestMapping(value = "/getNewsRelease", method = RequestMethod.POST)
	public @ResponseBody String getNewsRelease(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Calendar cal = Calendar.getInstance();
		  cal.set(2017,6, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		List<Album> retAlbums = albumService.getNewsRelease(date);
		//System.out.println(retAlbums.size());
		session.setAttribute("album_list", retAlbums);
		return "ok";
	}
	
	@RequestMapping(value = "/getSongQueue", method = RequestMethod.POST)
	public @ResponseBody String getSongQueue(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Collection<SongQueue> que = user.getSongQueueCollection();
		JSONObject result = JSONHelper.songQueueToJSON(que);
		session.setAttribute("queueJSON", result);
		return result.toString();
		
		/*
		
		JSONObject obj = (JSONObject) session.getAttribute("queueJSON");
		return obj.toString();
		*/
	}
	
	@RequestMapping(value = "/getArtistInfo", method = RequestMethod.POST)
	public @ResponseBody String getArtistInfo(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String artistEmail = request.getParameter("artistEmail");
		User user = userService.getUser(artistEmail);
		
		String retArtistInfo = JSONHelper.getArtistInfo(user.getArtist().getBio(),user.getArtist().getFollowers());
		
		return retArtistInfo;
	}
	
	@RequestMapping(value = "/getArtistAlbum", method = RequestMethod.POST)
	public @ResponseBody String getArtistAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String artistEmail = request.getParameter("artistEmail");
		List<Album> retAlbum = (List<Album>)albumService.getAllAlbumArtist(artistEmail);
		
		session.setAttribute("album_list", retAlbum);
		return "ok";
	}
	
	@RequestMapping(value = "/getUserHistory", method = RequestMethod.POST)
	public @ResponseBody String getUserHistory(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String email = (String)((User)session.getAttribute("selectedFriend")).getEmail();
		System.out.println(email);
		if(email.equals("himself")) {
			User user = (User)session.getAttribute("user");
			email = user.getEmail();
		}
		
		User friend = userService.getUser(email);
		System.out.println(friend.getIsPublic());
		if(!friend.getIsPublic()) {
			return "private";
		}
		List<Song> history = songService.getHistorySongs(email);
		String JSON = JSONHelper.new_pendingSongsToJSON(history, songService);
		
		
		return JSON;
	}
	
	@RequestMapping(value = "/getCurrentUserHistory", method = RequestMethod.POST)
	public @ResponseBody String getCurrentUserHistory(ModelAndView mav, HttpServletRequest request, HttpSession session) {		
		String email;
		User user = (User)session.getAttribute("user");
		email = user.getEmail();
		List<Song> history = songService.getHistorySongs(email);
		String JSON = JSONHelper.new_pendingSongsToJSON(history, songService);	
		return JSON;
	}
        
        @RequestMapping(value = "/getFriendPlaylist", method = RequestMethod.POST)
	public @ResponseBody String getFriendPlaylist(HttpServletRequest request, HttpSession session) {		
		String email = (String)((User)session.getAttribute("selectedFriend")).getEmail();
                User user = userService.getUser(email);
                System.out.println("user is " + user);
                List<Playlist> playlist = (List<Playlist>)user.getUserPlaylistCollection();
		String playlistJSON = JSONHelper.playlistListToJSON(playlist);	
		return playlistJSON;
	}
	
	@RequestMapping(value = "/getAllSongsForArtist/{status}", method = RequestMethod.POST)
	public @ResponseBody String getAllSongsForArtist(@PathVariable String status, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");//admin page only
		
		List<Song> songs = songService.getSongByArtist(user.getEmail(), status);
		
		String JSON = JSONHelper.new_pendingSongsToJSON(songs, songService);
		
		
		return JSON;
	}
	
	@RequestMapping(value = "/getRelatedAlbum", method = RequestMethod.POST)
	public @ResponseBody String getRelatedAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		
		String aid = request.getParameter("albumId");
		Album curAlbum = (Album) albumService.getAlbum(Integer.parseInt(aid));
		String genre = curAlbum.getGenre();
		String display = environment.getProperty("displayAmount");
		int displayAmount = Integer.parseInt(display);
		List<Album> retAlbum = (List<Album>) albumService.getTopGenreAlbum(genre, 1, displayAmount);
		Iterator<Album> iter = retAlbum.iterator();

		while (iter.hasNext()) {
		    Album a = iter.next();

		    if (a.getAid() == Integer.parseInt(aid))
		        iter.remove();
		}
		session.setAttribute("album_list", retAlbum);
		return "ok";
	}
	
	@RequestMapping(value = "/getRelatedArtist", method = RequestMethod.POST)
	public @ResponseBody String getRelatedArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {	
		String artistEmail = request.getParameter("artistEmail");		
		List<Album> artAlbum = (List<Album>) albumService.getAllAlbumArtist(artistEmail);
		List<String> genreList = new ArrayList();
		for(Album a: artAlbum) {
			if(!genreList.contains(a.getGenre())) {
				genreList.add(a.getGenre());
			}
		}
		String display = environment.getProperty("displayAmount");
		int displayAmount = Integer.parseInt(display);
		List<User> relatedAlbumsArtist = new ArrayList();
		for(String genre: genreList) {
			List<Album> tempAlbums = albumService.getTopGenreAlbum(genre, 1, displayAmount);
						
		    for(Album album : tempAlbums) {
		    	List<User> tempArtist = albumService.getArtistsCollection(album.getAid());
		    	
		    	for(User u : tempArtist) {
		    		if(!relatedAlbumsArtist.contains(u) && !u.getEmail().equals(artistEmail)) {
		    			relatedAlbumsArtist.add(u);
		    		}
		    	}
		    }		
		}
		
		session.setAttribute("artist_list", relatedAlbumsArtist);
		return "ok";
	}
	
	
	@RequestMapping(value = "/addConcert", method = RequestMethod.POST)
	public ModelAndView addConcert(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");//artist page only
		Concert c = new Concert();
		String address = request.getParameter("address");	
		String cname = request.getParameter("cname");
		String ctimeTmp = request.getParameter("ctime") + " 00:00:00";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		
		Date ctime = null;
		try {
			ctime = (Date) dateFormat.parse(ctimeTmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setAddress(address);
		c.setCname(cname);
		c.setCtime(ctime);
		c.setUemail(user);
		c.setLid(null);
		userService.addConcert(c);
		mav.addObject("username", user.getUserName());
		mav.setViewName("artistMainPage");
		return mav;
	}
	
	@RequestMapping(value = "/getConcertInfo", method = RequestMethod.POST)
	public @ResponseBody String getConcertInfo(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");//normal user page only
		String artistEmail = request.getParameter("artistEmail");	
		System.out.println("HI");
		User u = userService.getUser(artistEmail);
		List<Concert> c = userService.getConcerts(u);
		
		String json = JSONHelper.getConcertInfo(c);
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value = "/findPayment", method = RequestMethod.POST)
	public @ResponseBody String findPayment(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");//normal user page only
		Payment userPayment = userService.findPayment(user.getEmail());
				
		return JSONHelper.getPayment(userPayment,user);
	}
	
	@RequestMapping(value = "/sortAlbum", method = RequestMethod.POST)
	public @ResponseBody String sortAlbum( ModelAndView mav,HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");//normal user page only
		List<Album> albums = (List<Album>)session.getAttribute("album_list");
		albums = SortTitle.sortAlbumAlpha(albums);
		session.setAttribute("album_list",albums);
		
		return "ok";
	}
	@RequestMapping(value = "/repeatTrack", method = RequestMethod.POST)
	public @ResponseBody String repeatTrack( ModelAndView mav,HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ArrayList<SongQueue> que = (ArrayList<SongQueue>) user.getSongQueueCollection();
		ArrayList<SongQueue> newSq = new ArrayList<SongQueue>();
		Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
		boolean isLast = false;
		if (que.size()>0) {
			isLast = que.get(que.size()-1).getIsPlay();
		}
		if(isLast) {
			SongQueue last = que.get(que.size()-1);
			que.remove(que.size()-1);
			newSq.add(last);
			newSq.addAll(que);
			JSONObject result = JSONHelper.songQueueToJSON(isLast? newSq : que);
			user.setSongQueueCollection(newSq);
			session.setAttribute("queueJSON", result);
			session.setAttribute("user", user);
			return result.toString();
		}else {
			//JSONObject result = JSONHelper.songQueueToJSON(que);
			JSONObject obj = (JSONObject) session.getAttribute("queueJSON");
			return obj.toString();
		}
	}
	@RequestMapping(value = "/unrepeatTrack", method = RequestMethod.POST)
	public @ResponseBody String unrepeatTrack( ModelAndView mav,HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ArrayList<SongQueue> que = (ArrayList<SongQueue>) user.getSongQueueCollection();
		SongQueue first = new SongQueue();
		if (que.size()>0)
			first = que.get(0);
		if (first.getIsPlay()) {
			que.remove(0);
			que.add(first);
			user.setSongQueueCollection(que);
			session.setAttribute("user", user);
		}
		JSONObject result = JSONHelper.songQueueToJSON(que);
		user.setSongQueueCollection(que);
		session.setAttribute("queueJSON", result);
		return result.toString();
	}
	
	@RequestMapping(value = "/banUser", method = RequestMethod.POST)
	public ModelAndView banUser(ModelAndView mav, HttpServletRequest request, HttpSession session)
			throws ServletException, IOException {
		String userEmail = request.getParameter("userID");
		User user = userService.getUser(userEmail);
		user.setIsBan(true);
		userService.updateSpecificUser(user);
		
		mav.setViewName("admin");
		return mav;
	}
	@RequestMapping(value = "/addToHistory", method = RequestMethod.POST)
	public @ResponseBody String addToHistory(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		JSONObject json = (JSONObject) session.getAttribute("queueJSON");
		User user  = (User) session.getAttribute("user");
		if(json.length()>0) {
			JSONObject nowPlay = json.getJSONObject("nowPlay");
			System.out.println("nowPlay: "+nowPlay.toString());
			if(nowPlay!=null && nowPlay.length()>0) {
				JSONObject nowSong = nowPlay.getJSONObject("song");
				System.out.println("nowSong123: "+nowSong.toString());
				int sid = nowSong.getInt("sid");
				
				if(user.getPrivateSession() == false) {
					songService.addToHistory(user.getEmail(), sid);
				}
				
				return nowSong.toString();
			}
		}
		return null;
	}
	@RequestMapping(value = "/sortSongInPlaylist", method = RequestMethod.POST)
	public @ResponseBody String sortSongInPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
		Playlist playlist = (Playlist)session.getAttribute("selectedPlaylist");
		List<Song> songs = playlistService.getSongInPlaylist(playlist.getPid());
		List<Song> sortedSong = SortTitle.sortSongAlpha(songs);
		//playlist.setSongCollection(sortedSong);
		//session.setAttribute("selectedPlaylist", playlist);
		//String obj = JSONHelper.singlePlaylistToJSON(playlist);
		//System.out.println(obj);
		JSONArray obj = JSONHelper.new_pendingSongsToJSON1(sortedSong, songService);
		session.setAttribute("jsonList", obj);
		return obj.toString();
	}
        
          //TODO
    @RequestMapping(value = "/removeAlbum", method = RequestMethod.POST)
	public @ResponseBody String removeAlbum( ModelAndView mav,HttpServletRequest request, HttpSession session) {
		int albumID = Integer.parseInt(request.getParameter("albumID"));
		Album album = albumService.getAlbum(albumID);
		albumService.removeAlbum(album);
		
		return "ok";
	}
    
    @RequestMapping(value = "/supportTicket", method = RequestMethod.POST)
   	public ModelAndView supportTicket( ModelAndView mav,HttpServletRequest request, HttpSession session) {
   		String sendInfo = request.getParameter("sendInfo");
   		userService.sendSupportEmail("panthersSpotify@gmail.com", sendInfo);
   		mav.setViewName("main");
   		return mav;
   	}
    
    @RequestMapping(value = "/getArtistFollowers", method = RequestMethod.POST)
  	public  @ResponseBody String getArtistFollowers( ModelAndView mav,HttpServletRequest request, HttpSession session) {
   		User user = (User) session.getAttribute("user");
  		List<User> users = userService.getArtistFollowers(user.getEmail());
   		String jsonRet = JSONHelper.userListToJSON(users);

   		return jsonRet;
     }
    
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
  	public  @ResponseBody String getInfo( ModelAndView mav,HttpServletRequest request, HttpSession session) {
   		User user = (User) session.getAttribute("user");
  		List<User> users = userService.getArtistFollowers(user.getEmail());
  		List ret = songService.getGenreSongs();
   		String jsonRet = JSONHelper.statisticsToJSON(ret, userService, playlistService, songService, albumService);

   		return jsonRet;
     }
}