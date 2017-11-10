/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.helper.Security;
import com.model.Playlist;
import com.model.User;

import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
       List<User> li = userManager.getUser(email,password);
      
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
       return mav;
      
            
            
   }
 
    /* user logout */
   @RequestMapping(value = "/home", method = RequestMethod.GET)
   public ModelAndView userLogout(ModelAndView mav, HttpSession session) {
	   		if (session.getAttribute("user") != null)
	   		{
	   			session.setAttribute("user", null);
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
    User user = new User();
    user.setUname(username);
    user.setEmail(email);
    user.setUpassword(encPwd);
    user.setUtype(0);
    user.setGender(gender);
    user.setFirstName(first_name);
    user.setLastName(last_name);
    userManager.add(user);

    String message = "Congratulation, sign up successfully. Please return to homepage for login.";
    return message; //handle in SignUp.jsp
   }
   
   /* display sign up page */
   @RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
   public ModelAndView createPlaylist(ModelAndView mav, HttpServletRequest request, HttpSession session) {
	   		String playlist_name = request.getParameter("playlist_name");
	   		//String description = request.getParameter("description");
	   		Playlist playlist = new Playlist();
	   		playlist.setPname(playlist_name);
	   		User user = (User) session.getAttribute("user");
	   		playlist.setPowner(user);
	   		playlist.setDes(user.getEmail());
	   		playlist.setFollowers(0);
	   		playlist.setNSongs(0);
	   		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	   		playlist.setCreateDate(date);
	   		playlistManager.add(playlist);
            mav.setViewName("main");
            return mav;
   }
   
   
   
    
}
