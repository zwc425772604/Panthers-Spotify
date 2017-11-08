/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.User;
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
import org.springframework.web.servlet.ModelAndView;


@Controller
public class DefaultController {
    @Autowired
    private UserDAOImplementation UserDAO;
    
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(ModelMap map) {
      
       return "index";
   }
   
   /* user login */
   @RequestMapping(value = "/main", method = RequestMethod.POST)
   public ModelAndView userLogin(ModelAndView mav,
                           HttpServletRequest request, HttpSession session) {
	   String email = request.getParameter("email");
	   String password = request.getParameter("password");
       List<User> li = UserDAO.getUser(email,password);
      
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
           mav.addObject("username", li.get(0).getUname());
           mav.setViewName("main");
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
            mav.setViewName("SignUp");
            return mav;
   }
   
       /* user sign up */
   @RequestMapping(value = "/userSignUp", method = RequestMethod.POST)
   public ModelAndView userSignUp(ModelAndView mav,
                  HttpServletRequest request, HttpSession session) 
   {      
	System.out.println("here in the sign up page");
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String email = request.getParameter("email");
	char gender = request.getParameter("gender").charAt(0);
//	String first_name = request.getParameter("first_name");
//	String last_name = request.getParameter("last_name");
    User user = new User();
    user.setUname(username);
    user.setEmail(email);
    user.setUpassword(password);
    user.setUtype(1);
    user.setGender(gender);
    UserDAO.add(user);
            mav.addObject("signUpMessage", "congratulation, sign up successfully");
          
            mav.setViewName("SignUp");
            return mav;
   }
   
   
   
    
}
