/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author john
 */
@Controller
public class DefaultController {
    
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(ModelMap map) {
       map.put("msg", "Hello Spring 4 Web MVC!");
       return "index";
   }
   @RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
   public String adminLogin(ModelMap map) {
       map.put("msg", "Hello Weichao 4 Web MVC!");
       return "adminlogin";
   }
   
   @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
   public String submitAdminLogin(ModelMap map) {
       map.put("adminmessage", "Login Credentials");
     
            return "adminmessage";
   }
   
   @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
   public String checkUserLogin(ModelMap map) {
       map.put("username", "zhao");
            
            return "main";
   }
    
    
}
