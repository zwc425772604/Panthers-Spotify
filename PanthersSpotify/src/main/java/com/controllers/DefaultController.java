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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
   public String userLogin(ModelMap map,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password) {
       List<User> li = UserDAO.getUser(email,password);
       //if the user is not in the database
       System.out.println("li is "  + li);
//       System.out.println("li size is "  + li.size());
       if (li.size() == 0)
       {
           map.put("error_message", "Your email and password does not match. Please try again.");
           return "index";
       }
       else if (li.get(0).getUpassword().equals(password))
       {
           map.put("username", li.get(0).getUname());
           return "main";
       }
       else
       {
           map.put("username", li.get(0).getUname());
           return "main";
       }
      
            
            
   }
   
    /* user logout */
   @RequestMapping(value = "/home", method = RequestMethod.GET)
   public String userLogout(ModelMap map) {
            
            return "index";
   }
   
      /* display sign up page */
   @RequestMapping(value = "/signup", method = RequestMethod.GET)
   public String displaySignUp(ModelMap map) {
            
            return "SignUp";
   }
   
       /* user sign up */
   @RequestMapping(value = "/userSignUp", method = RequestMethod.POST)
   public String userSignUp(ModelMap map,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password) 
   {                                                                                    
//    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//    EntityTransaction userTransaction = em.getTransaction();
    User user = new User();
    user.setUname(username);
    user.setEmail(email);
    user.setUpassword(password);
    UserDAO.add(user);
//     userTransaction.begin();
//    em.persist(user);
//    userTransaction.commit();
//    em.close();
            map.put("signUpMessage", "congratulation, sign up successfully");
            return "SignUp";
   }
   
   
   
    
}
