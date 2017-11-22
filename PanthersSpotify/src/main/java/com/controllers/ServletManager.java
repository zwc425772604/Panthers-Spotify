/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.helper.JSONHelper;
import com.helper.Security;
import com.model.Album;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.ReleasesongPK;
import com.model.Song;
import com.model.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServletManager {
    @Autowired
    private UserManager userManager;
    @Autowired
    private PlaylistManager playlistManager;
    @Autowired
    private SongManager songManager;
    @Autowired
    private AlbumManager albumManager;
    //private EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(ModelMap map) {

       return "index";
   }

   /* user login */
   @RequestMapping(value = "/main", method = RequestMethod.POST)
   public ModelAndView userLogin(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   String email = request.getParameter("email");
	   String nonEncPwd = request.getParameter("password");
	   String password = Security.encryptPassword(nonEncPwd);
       List<User> li = userManager.getUser(email);

       System.out.println("li is "  + li);

       //case 0: if the email is not registered
       if (li.size() == 0)
       {
            mav.setViewName("index");
    	   	mav.addObject("error_message", "This email does not register on our site!");
       }
       //case 1: email and password match in database record
       //if true - get the playlist, etc from the database and update the DOM
       else if (li.get(0).getUpassword().equals(password))
       {
    	   User user = li.get(0);
    	   session.setAttribute("user", user);
    	   //user page
    	   if (user.getUtype() == 0)
    	   {
    		   mav.addObject("username", user.getUname());
    		   List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
    		   List<Playlist> follow_playlist = playlistManager.getUserFollowPlaylist(user);
    		   user_playlist.addAll(follow_playlist);
    		   session.setAttribute("user_playlist", user_playlist);
    		   session.setAttribute("userFollowedPlaylists", follow_playlist);
    		   List<Playlist> userFollowingPlaylists = (List<Playlist>) user.getPlaylistCollection();
    		   System.out.println("number of following playlist : " + userFollowingPlaylists.size());
           mav.setViewName("main");
    	   }
         else if (user.getUtype() == 2)
         {
           mav.addObject("username", user.getUname());
           mav.setViewName("artistMainPage");
         }
    	   //display admin page
    	   if (user.getUtype() == 3)
    	   {
    		   mav.addObject("username", user.getUname());
           mav.setViewName("admin");
    	   }

       }
       //case 2: incorrect email or password
       else
       {
    	   System.out.println(password);
    	   System.out.println(li.get(0).getUpassword());
           mav.addObject("error_message", "Incorrect email or password!");
           mav.setViewName("index");
       }
       System.out.println("here");
       return mav;



   }
   @RequestMapping(value = "/main", method = RequestMethod.GET)
   public ModelAndView mainPage(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   mav.setViewName("main");
	   return mav;
   }

    /* user logout */
   @RequestMapping(value = "/home", method = RequestMethod.GET)
   public ModelAndView userLogout(ModelAndView mav, HttpSession session) {
	   		if (session.getAttribute("user") != null)
	   		{
	   			session.removeAttribute("user");
	   		}
            mav.setViewName("index");
            return mav;
   }

      /* display sign up page */
   @RequestMapping(value = "/signup", method = RequestMethod.GET)
   public ModelAndView displaySignUp(ModelAndView mav) {
	   		mav.addObject("signUpMessage", "Welcome to Panthers Spotify!");
            mav.setViewName("SignUp");
            return mav;
   }


       /* user sign up */ /* user type: 0=basic 1=premium 2=artist 3=admin */
   @RequestMapping(value = "/userSignUp", method = RequestMethod.POST)
   public @ResponseBody String userSignUp(ModelAndView mav,
                  HttpServletRequest request, HttpSession session){
    String username = request.getParameter("username");
	String password = request.getParameter("password");
	// encrypt password

	String encPwd = Security.encryptPassword(password);
	String email = request.getParameter("email");
	char gender = request.getParameter("gender").charAt(0);
	String first_name = request.getParameter("first_name");
	String last_name = request.getParameter("last_name");
	int utype = 0;

    userManager.add(username,email,encPwd,utype,gender,first_name,last_name);

    String message = "Congratulation, sign up successfully. Please return to homepage for login.";
    return message; //handle in SignUp.jsp
   }

   /* create Playlist */
   @RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
   public ModelAndView createPlaylist(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request, HttpSession session) {
	   		String playlist_name = request.getParameter("playlist_name");
	   		String description = request.getParameter("playlist_description");
//	   		String pic = request.getParameter("pic");
	   		//System.out.println("picture address is "+pic);
	   		User user = (User) session.getAttribute("user");
	   		//String path=session.getServletContext().getRealPath("/");

	        //String filename=file.getOriginalFilename();


	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

	   		List<Playlist> user_playlist = playlistManager.add(playlist_name,user,description,file,date);

 		    mav.addObject("user_playlist", user_playlist);
 		    session.setAttribute("user_playlist", user_playlist);
            mav.setViewName("main");
            return mav;
   }

   @RequestMapping(value = "/editPlaylistDetails", method = RequestMethod.POST)
   public ModelAndView editPlaylist(ModelAndView mav, @RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request, HttpSession session) {

	   		String playlist_name = request.getParameter("playlist_name");
	   		String description = request.getParameter("playlist_description");
	   		User user = (User) session.getAttribute("user");
//	   		String path=session.getServletContext().getRealPath("/");
//	        String filename=file.getOriginalFilename();
//
//
//	        System.out.println(path+"/"+filename);

	        Playlist playlistOne = (Playlist)session.getAttribute("selected_playlist");
	        //System.out.println(path+" "+filename);
	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	   		int pid = playlistOne.getPid();

	   		//String pic = "test";


	   		List<Playlist> user_playlist = playlistManager.edit(pid,description,file,playlist_name,user);
//
// 		    mav.addObject("user_playlist", user_playlist);
// 		    session.setAttribute("user_playlist", user_playlist);

	   		mav.addObject("user_playlist", user_playlist);
 		    session.setAttribute("user_playlist", user_playlist);
            mav.setViewName("main");
            return mav;
   }


   /* get specific playlist */
   @RequestMapping(value = "/getSpecificPlaylist", method = RequestMethod.POST)
   public  @ResponseBody String getSpecificPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		int playlist_id = Integer.parseInt(request.getParameter("playlist_id"));

	   		Playlist playlist = playlistManager.getPlaylist(playlist_id);
	   		String pname = playlist.getPname();
	   		System.out.println("playlist name is :" + pname);
	   		System.out.println("playlist num songs :" + playlist.getNSongs());
 		    session.setAttribute("selectedPlaylist", playlist);
 		   session.setAttribute("selectedPlaylistNumSongs", playlist.getNSongs());
            return "ok";
   }


   /* add song to database */

   @RequestMapping(value = "/addSongToDatabase", method = RequestMethod.POST)
   public ModelAndView addSongToDatabase(ModelAndView mav, HttpServletRequest request, HttpSession session) throws ServletException, IOException {
	   String songTitle = request.getParameter("song_title");
	   String songTime = request.getParameter("song_time");
	   String releaseDay = request.getParameter("release_day");
	   String songGenre = request.getParameter("song_genre");
	   String songType = request.getParameter("song_type");
	   String songUrl = request.getParameter("song_file");

	   //Song songTime and release Day to be Fixed
	   Song song = new Song(songTitle, null,null, 0, songGenre, songType, songUrl);
	   try {
			song = songManager.add(song);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   mav.setViewName("admin");
	   return mav;
   }

   /* remove specific playlist */
   @RequestMapping(value = "/removeSpecificPlaylist", method = RequestMethod.POST)
   public  @ResponseBody String removeSpecificPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
	   		Playlist playlist = playlistManager.getPlaylist(playlistID);
	   		User user = (User) session.getAttribute("user");
	   		playlistManager.removePlaylist(playlistID,user);
	   		//playlistManager.remove(playlist_id);
	   		List<Playlist> user_playlist = (List<Playlist>) (session.getAttribute("user_playlist"));
	   		user_playlist.remove(playlist);
	   		session.setAttribute("user_playlist", user_playlist);
 		    mav.addObject("user_playlist", user_playlist);
	   		System.out.println("remove success");
            return "ok";
   }

   /* Load song from database */
   @RequestMapping(value="/loadSong", method = RequestMethod.POST)
   public @ResponseBody String loadSongs(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   List<Song> songs = songManager.getAllSongs();
	   session.setAttribute("songs", songs);
	   System.out.println("loadsongs" + songs.size());
	   return "ok";
   }

   @RequestMapping(value="/loadAlbum", method = RequestMethod.POST)
   public @ResponseBody String loadAlbum(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   List<Album> albums = albumManager.getAllAlbums();
	   session.setAttribute("album_list", albums);
	   System.out.println("loadAlbum" + albums.size());
  	   return "ok";
   }

   @RequestMapping(value="/editUserAccount", method = RequestMethod.POST)
   public ModelAndView editUserAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   User user = (User)session.getAttribute("user");
	   Character gender = request.getParameter("gender").charAt(0);
	   String firstName = request.getParameter("firstName");
	   String middleName = request.getParameter("middleName");
	   String lastName = request.getParameter("lastName");
	   user = userManager.editUserAccount(user, gender, firstName, middleName, lastName);

	   session.setAttribute("user", user);
	   mav.setViewName("main");
	   mav.addObject("username", user.getUname());
       return mav;
   }

   @RequestMapping(value="/editUserPassword", method = RequestMethod.POST)
   public ModelAndView editUserPassword(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   User user = (User)session.getAttribute("user");
	   String pwd = request.getParameter("password");
	   String encPwd = Security.encryptPassword(pwd);
	   user = userManager.editUserPassword(user, encPwd);
	   session.setAttribute("user", user);
	   mav.setViewName("main");
	   mav.addObject("username", user.getUname());
       return mav;
   }

   @RequestMapping(value="/deleteUserAccount", method = RequestMethod.POST)
   public ModelAndView deleteUserAccount(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   System.out.println("deleting user account");
	   User user = (User)session.getAttribute("user");
	   if (session.getAttribute("user") != null)
		{
			session.removeAttribute("user");
		}
	   userManager.remove(user);
       mav.setViewName("index");
       return mav;
   }

   @RequestMapping(value="/getOverviewPlaylist", method = RequestMethod.POST)
   public ModelAndView getOverviewPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   //Get Top Follower Playlist
	   int NUMBER_OF_TOP_FOLLOWED = 4;
	   List<Playlist> userPlaylist = (List<Playlist>)(playlistManager.getTopFollowedPlaylist(NUMBER_OF_TOP_FOLLOWED));

	   session.setAttribute("overviewPlaylist", userPlaylist);
	   mav.setViewName("main");
	   return mav;
   }

   @RequestMapping(value="/getUserFriendList", method = RequestMethod.POST)
   public @ResponseBody String getUserFriendList(ModelAndView mav, HttpServletRequest request, HttpSession session) {

	   System.out.println("Getting user friendlist");
	   User user = (User) session.getAttribute("user");

	   List<User> temp = userManager.getFriend(user.getEmail());
	   session.setAttribute("userFriendList", temp);
  	   return "ok";
   }

   @RequestMapping(value = "/findFriend", method = RequestMethod.POST)
   public ModelAndView findFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
  			User user = (User) session.getAttribute("user");
	   		String username = request.getParameter("username");
	   		User temp = userManager.getUserByUsername(username);
	   		if(temp == null) {
	   			System.out.println("GG");
	   		}
	   		else {
	   			session.setAttribute("selectedFriend", temp);
	   		}
	   		mav.setViewName("main");
	   		mav.addObject("username",user.getEmail());
            return mav;
   }

   @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
   public ModelAndView addFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		System.out.println("HI");
	   		User user = (User) session.getAttribute("user");
	   		String femail = request.getParameter("femail");//friend email
	   		List<User> temp = userManager.addFriend(user.getEmail(),femail);
	   		mav.setViewName("main");
	   		mav.addObject("username",user.getEmail());
            return mav;
   }

   @RequestMapping(value = "/deleteFriend", method = RequestMethod.POST)
   public ModelAndView deleteFriend(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		System.out.println("Delete Friend");
	   		User user = (User) session.getAttribute("user");
	   		String femail = request.getParameter("femail");//friend email
	   		System.out.println("Friend to be deleted" + femail);
	   		boolean temp = userManager.deleteFriend(user.getEmail(),femail);
	   		//remove the
	   		List<User> newFriendlist = userManager.getFriend(user.getEmail());
	 	    session.setAttribute("userFriendList", newFriendlist);
	   		mav.setViewName("main");
	   		mav.addObject("username",user.getUname());
            return mav;
   }

   @RequestMapping(value="/getUserPage", method = RequestMethod.POST)
   public @ResponseBody String getUserPage(ModelAndView mav, HttpServletRequest request, HttpSession session) {

	   String username = request.getParameter("username");
	   String email = request.getParameter("userEmail");
	   List<User> users = userManager.getUser(email);
	   User user = (users.size() != 0 )?users.get(0):null;
	   session.setAttribute("selectedFriend", user);
  	   return "ok";
   }


   @RequestMapping(value="/followSpecificPlaylist", method=RequestMethod.POST)
   public @ResponseBody String followSpecificPlaylist(HttpServletRequest request, HttpSession session) {
	   int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
	   User user = (User) session.getAttribute("user");
	   boolean ret = playlistManager.followPlaylist(playlistID,user);
	   System.out.println("playlist id to be follow is : " + playlistID);
	   return "ok";
   }

   @RequestMapping(value="/unfollowSpecificPlaylist", method=RequestMethod.POST)
   public @ResponseBody String unfollowSpecificPlaylist(HttpServletRequest request, HttpSession session) {
	   int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
	   User user = (User) session.getAttribute("user");
	   boolean ret = playlistManager.unfollowPlaylist(playlistID,user);
	   System.out.println("here is unfollow");
	   System.out.println("playlist id to be unfollow is : " + playlistID);
	   return "ok";
   }

   @RequestMapping(value="/addSongToPlaylist", method=RequestMethod.POST)
   public @ResponseBody String addSongToPlaylist(HttpServletRequest request, HttpSession session) {
	   int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
	   int songID = Integer.parseInt(request.getParameter("songID").trim());
	   User user = (User) session.getAttribute("user");
	   playlistManager.addSongPlaylist(playlistID, songID);
	   return "ok";
   }

   @RequestMapping(value="/removeSongToPlaylist", method=RequestMethod.POST)
   public @ResponseBody String removeSongToPlaylist(HttpServletRequest request, HttpSession session) {
	   int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
	   int songID = Integer.parseInt(request.getParameter("songID").trim());
	   User user = (User) session.getAttribute("user");
	   playlistManager.removeSongPlaylist(playlistID, songID);
	   return "ok";
   }

   @RequestMapping(value="/loadUserTables/{userType}", method = RequestMethod.POST)
   public @ResponseBody String loadAllUsers(@PathVariable int userType, HttpServletRequest request, HttpSession session) throws JSONException {
     System.out.println("user type" + userType);
	   List<User> users = userManager.getUsersByType(userType);
     String userJsonArray;
     userJsonArray =  userType == 2 ? JSONHelper.artistListToJSON(users) : JSONHelper.userListToJSON(users) ;
	   System.out.println("basicUserJsonArray is :" + userJsonArray);
  	   return userJsonArray;
   }

   @RequestMapping(value="/loadAllPlaylists", method = RequestMethod.POST)
   public @ResponseBody String loadAllPlaylists(HttpServletRequest request, HttpSession session) throws JSONException {
	   List<Playlist> playlists = playlistManager.getAllPlaylists();
	   String playlistsJsonArray = JSONHelper.playlistListToJSON(playlists);
	   System.out.println("playlistJsonArray is :" + playlistsJsonArray);
  	   return playlistsJsonArray;
   }

   /*fire from admin.js admin action*/
   @RequestMapping(value="/deleteSelectedUserAccount", method = RequestMethod.POST)
   public @ResponseBody String deleteSelectedUserAccount(HttpServletRequest request, HttpSession session) throws JSONException {
	   String userID = request.getParameter("userID");
	   List<User> li = userManager.getUser(userID);
	   userManager.remove(li.get(0));
	   System.out.println("removevvv");
  	   return "remove success";
   }

   /* fire from admin.js admin action*///did it
   @RequestMapping(value = "/deleteSelectedPlaylist", method = RequestMethod.POST)
   public  @ResponseBody String deleteSelectedUserPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		int playlistID = Integer.parseInt(request.getParameter("playlistID").trim());
//	   		Playlist playlist = playlistManager.getPlaylist(playlistID);
//	   		User user = (User) session.getAttribute("user");
//	   		playlistManager.removePlaylist(playlistID,user);
//	   		//playlistManager.remove(playlist_id);
//	   		List<Playlist> user_playlist = (List<Playlist>) (session.getAttribute("user_playlist"));
//	   		user_playlist.remove(playlist);
//	   		session.setAttribute("user_playlist", user_playlist);
// 		    mav.addObject("user_playlist", user_playlist);
//	   		System.out.println("remove success");
            return "ok";
   }




 //Servlet
 @RequestMapping(value="/search", method = RequestMethod.POST)
    public ModelAndView search(ModelAndView mav, HttpServletRequest request, HttpSession session) {
 	   System.out.println("search");
 	   User user = (User)session.getAttribute("user");
 	   String input = request.getParameter("input");
 	   List<Playlist>retPlaylist = playlistManager.findRelative(input);
 	   if(retPlaylist.isEmpty())
 	   {
 		   System.out.println("playlist is empty");
 	   }
 	  List<Song>retSong = songManager.findRelative(input);
 	 if(retPlaylist.isEmpty())
	   {
		   System.out.println("song is empty");
	   }
 	 List<Album>retAlbum = albumManager.findRelative(input);
 	if(retPlaylist.isEmpty())
	   {
		   System.out.println("album is empty");
	   }
        mav.setViewName("index");
        return mav;
    }


 //admin action, add artist
 @RequestMapping(value="/addArtistToDatabase", method = RequestMethod.POST)
    public ModelAndView addNewArtist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	    String artistName = request.getParameter("artist-name");
      String artistEmail = request.getParameter("artist-email");
      String artistPassword = request.getParameter("artist-password");
      String encPwd = Security.encryptPassword(artistPassword);
      String artistFirstName = request.getParameter("artist-first-name");
      String artistLastName = request.getParameter("artist-last-name");
      String artistBiography = request.getParameter("artist-biography"); //missing bio column in user table
      char gender = request.getParameter("gender").charAt(0);
      int userType = 2;
      userManager.add(artistName,artistEmail,encPwd,userType,gender,artistFirstName,artistLastName);
 	  	mav.setViewName("admin");
      System.out.println("adding artist successfully");
      return mav;
    }



    @RequestMapping(value = "/submitSongForUploading", method = RequestMethod.POST)
    public ModelAndView submitSongForUploading(ModelAndView mav,@RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request, HttpSession session) throws ServletException, IOException {
      User user = (User) session.getAttribute("user");
      String songTitle = request.getParameter("song_title");
      String songTime = request.getParameter("song_time");
      String releaseDay = request.getParameter("release_day");
      String songGenre = request.getParameter("song_genre");
      String songType = request.getParameter("song_type");

      songManager.uploadSong(user, songTitle, songTime, releaseDay, songGenre, songType, file);

      mav.setViewName("artistMainPage");
      return mav;
    }

    @RequestMapping(value="/loadPendingSongs", method = RequestMethod.POST)
    public @ResponseBody String loadPendingSongs(HttpServletRequest request, HttpSession session) throws JSONException {
        List<Releasesong> releaseSongs = songManager.getAllSongsByStatus("pending");
        List<Song> songs = new ArrayList<Song>();
        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        for (Releasesong rs : releaseSongs)
        {
          ReleasesongPK rpk = rs.getReleasesongPK();
          int sid = rpk.getSid();
          Song s = songManager.getSong(sid);
          songs.add(s);
          //use hashmap<String, arraylist> to store the artist in a song
          if (map.get(sid) == null)
          {
        	  map.put(sid, new ArrayList<String>());
          }
          map.get(sid).add(rpk.getUemail()); //add the artist email to the map
        }
        System.out.println("map size " + map.size());
        System.out.println("map" + map.values());
        String pendingSongsJsonArray = JSONHelper.pendingSongsToJSON(songs, map);
        return pendingSongsJsonArray;
    }

    @RequestMapping(value="/loadArtist", method = RequestMethod.POST)
    public @ResponseBody String loadArtists(HttpServletRequest request, HttpSession session) throws JSONException {
        List<User> artists = userManager.getAllArtist();
        session.setAttribute("artistsList", artists);
        return "ok";
    }


}
