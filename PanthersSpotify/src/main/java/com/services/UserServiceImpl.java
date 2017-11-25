package com.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Artist;
import com.model.Playlist;
import com.model.Squeue;
import com.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired(required=true)
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Transactional
	public User addUser(String username, String email,String encPwd,int utype,char gender,String firstName, String middleName, String lastName, String dob) {
		
		System.out.println("User Service create invoked:"+username);
		User user  = new User();
		user.setEmail(email);
		user.setUname(username);
		user.setUpassword(encPwd);
		user.setUtype(utype);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setPublic(true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss", Locale.US);
		Date parsedBirthday = null;
	    try { 
	    	parsedBirthday = dateFormat.parse(dob);} 
	    catch (ParseException e) {
	        e.printStackTrace();
	    }
	    user.setDob(parsedBirthday);
		final String dir = System.getProperty("user.dir");
        
        File f1 = new File(dir+"/Users");
        if(f1.exists())
        {
        		System.out.println("here");
        }
        File userDir = new File(f1, email);
        boolean userSuccess = userDir.mkdirs();
		
		user = userDAO.addUser(user);
		return user;
	}
	@Transactional
	public User updateUser(User user, String username, int utype,char gender,String firstName, String lastName,boolean isPublic) {
		
		System.out.println("Cusomer Service Update invoked:"+username);
		//User User  = new User();
		user.setUname(username);
		user.setUtype(utype);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPublic(isPublic);
		user = userDAO.updateUser(user);
		return user;
	}
	
	/* More info to be added */
	@Transactional
    public User editUserPassword(User user, String pwd)
    {
			
		user.setUpassword(pwd);
		user = userDAO.updateUser(user);
		return user;
    }
	@Transactional
	public void removeUser(User user) {
		userDAO.deleteUser(user);
		
	}
	

	public User getUser(String email) {
		return userDAO.getUser(email);
	}

	public List<User> getAllUsers() {
		return userDAO.getUsers();
	}
	
	
	@Transactional
    public List<User> getFriend(String uemail)
    {
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    	List<User> results = userDAO.getFriend(uemail);
    
  	    for(int i=0;i<results.size();i++)
  	    {
  	    		System.out.println(results.get(i).getEmail());
  	    }
  	    if(results.size()==0)
  	    {
  	    		System.out.println("no friends");
  	    }
        return results;
    }
	
	@Transactional
    public List<User> addFriend(String uemail,String femail)
    {
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    		userDAO.addFriend(uemail,femail);
    		List<User> results = userDAO.getFriend(uemail);
    
  	    for(int i=0;i<results.size();i++)
  	    {
  	    		System.out.println(results.get(i).getEmail());
  	    }
  	    if(results.size()==0)
  	    {
  	    		System.out.println("no friends");
  	    }
        return results;
    }
	
	@Transactional
    public List<User> deleteFriend(String uemail,String femail)
    {
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    		userDAO.deleteFriend(uemail,femail);
    		List<User> results = userDAO.getFriend(uemail);
    
  	    for(int i=0;i<results.size();i++)
  	    {
  	    		System.out.println(results.get(i).getEmail());
  	    }
  	    if(results.size()==0)
  	    {
  	    		System.out.println("no friends");
  	    }
        return results;
    }

	@Transactional
	public List<User> getUsersByType(int usertype){
	    	List<User> list = new ArrayList<User>();
	    	list = userDAO.getUserByUserType(usertype);
	    	
	    	return list;
    }
	@Transactional
	public List<User> getAllArtist(){
		return userDAO.getAllArtist();
	}
	
	@Transactional
	public boolean isEmailRegistered(String email) {
		User user = userDAO.getUser(email);
		return (user == null) ?  false :  true;
	}
	@Transactional
	public void followArtist(String artistEmail,User user)
	  {
		    userDAO.followArtist(artistEmail, user.getEmail());
		  	
	  }
	@Transactional
	public void unfollowArtist(String artistEmail,User user)
	  {
		    userDAO.unfollowArtist(artistEmail, user.getEmail());
		  	
	  }
	@Transactional
	public List<Artist> getFollowArtists(User user)
	{
		return userDAO.getFollowArtists(user.getEmail());
	}
	
	@Transactional
	public Collection<Squeue> getQueue(String email) {
		return userDAO.getSqueue(email);
	}
}
