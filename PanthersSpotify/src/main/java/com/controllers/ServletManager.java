/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.helper.Security;
import com.model.Playlist;
import com.model.Song;
import com.model.User;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServletManager {
    @Autowired
    private UserManager userManager;
    @Autowired
    private PlaylistManager playlistManager;
    @Autowired
    private SongManager songManager;
    private EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
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
	   EntityManager em = entityManagerFactory.createEntityManager();
       List<User> li = userManager.getUser(email,password, em);
      
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
    	   session.setAttribute("user", li.get(0));
    	   //user page
    	   if (li.get(0).getUtype() == 0)
    	   {
    		   mav.addObject("username", li.get(0).getUname());
    		   List<Playlist> user_playlist = (List<Playlist>)(li.get(0).getUserPlaylistCollection());
    		   
//    		   mav.addObject("user_playlist", user_playlist);
    		   session.setAttribute("user_playlist", user_playlist);
    		
//    		   session.update("user_playlist");
    		   System.out.println(session.getId().length());
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
       em.close();
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
	
    userManager.add(username,encPwd,email,utype,gender,first_name,last_name);

    String message = "Congratulation, sign up successfully. Please return to homepage for login.";
    return message; //handle in SignUp.jsp
   }
   
   /* create Playlist */
   @RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
   public ModelAndView createPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		String playlist_name = request.getParameter("playlist_name");
	   		String description = request.getParameter("playlist_description");
	   		String pic = request.getParameter("pic");
	   		//System.out.println("picture address is "+pic);
	   		User user = (User) session.getAttribute("user");
	   		
	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	   		
	   		
	   		
	   		List<Playlist> user_playlist = playlistManager.add(playlist_name,user,description,pic,date);
	   		
 		    mav.addObject("user_playlist", user_playlist);
            mav.setViewName("main");
            return mav;
   }
   
   
   /* get specific playlist */
   @RequestMapping(value = "/getSpecificPlaylist", method = RequestMethod.GET)
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
   public ModelAndView addSongToDatabase(ModelAndView mav, HttpServletRequest request, HttpSession session) {
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
   
}
