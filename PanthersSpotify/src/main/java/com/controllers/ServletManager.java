/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.helper.Security;
import com.model.Album;
import com.model.Playlist;
import com.model.Song;
import com.model.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
       System.out.println(li.get(0));
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
    	   session.setAttribute("user", li.get(0));
    	   //user page
    	   if (li.get(0).getUtype() == 0)
    	   {
    		   mav.addObject("username", li.get(0).getUname());
    		   List<Playlist> user_playlist = (List<Playlist>)(li.get(0).getUserPlaylistCollection());
    		   
//    		   mav.addObject("user_playlist", user_playlist);
    		   session.setAttribute("user_playlist", user_playlist);
    		
    		
//    		   session.setAttribute("song_page_title", "session testing"); //display in song.jsp
               mav.setViewName("main");
    	   }
    	   //display admin page
    	   if (li.get(0).getUtype() == 3)
    	   {
    		   mav.addObject("username", li.get(0).getUname());
               mav.setViewName("admin");
    	   }
          
       }
       //case 2: incorrect email or password
       else
       {
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
	   		String dir = System.getProperty("user.dir");
	        String filename=file.getOriginalFilename();  
	        
	          
	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	   		
	   		
	   		String pic = dir+"/"+filename;
	   		
	   		
	   		System.out.println(pic);
	   		List<Playlist> user_playlist = playlistManager.add(playlist_name,user,description,pic,date);
	   		
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
	   		String path=session.getServletContext().getRealPath("/");  
	        String filename=file.getOriginalFilename(); 
	        try {
	        		byte[] barr = file.getBytes();
				BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path+"/"+filename));
				bout.write(barr);
				bout.flush();
				bout.close();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(path+"/"+filename);
	         
	        Playlist playlistOne = (Playlist)session.getAttribute("selected_playlist");
	        //System.out.println(path+" "+filename);  
	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	   		int pid = playlistOne.getPid();
	   		
	   		String pic = "test";
	   		
	   		
	   		List<Playlist> user_playlist = playlistManager.edit(pid,description,pic,playlist_name,user);
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
 		    session.setAttribute("selected_playlist", playlist);
 		   session.setAttribute("selected_playlist_nsongs", playlist.getNSongs());
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
	   String songUrl = request.getParameter("song_url");
	   
	   //Song songTime and release Day to be Fixed 
	   Song song = new Song(songTitle, null,null, songGenre, songType, songUrl);
	   try {
			song = songManager.add(song);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   mav.setViewName("admin");
	   return mav;
   }
   
   /* get specific playlist */
   @RequestMapping(value = "/removeSpecificPlaylist", method = RequestMethod.POST)
   public  @ResponseBody String removeSpecificPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		int playlist_id = Integer.parseInt(request.getParameter("playlist_id").trim());
	   		Playlist playlist = playlistManager.getPlaylist(playlist_id);
	   		playlistManager.removePlaylist(playlist_id);
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
	   System.out.println("editing user account");
	   User user = (User)session.getAttribute("user");
	   String pwd = request.getParameter("password");
	   String encPwd = Security.encryptPassword(pwd);
	   userManager.editUser(user, encPwd);
	   mav.setViewName("main");
       return mav;
   }
   
}