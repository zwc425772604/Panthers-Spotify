package com.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired(required=true)
	@Qualifier("userDAO")
	private UserDAO UserDAO;
	
	@Transactional
	public User addUser(String username, String email,String encPwd,int utype,char gender,String firstName, String lastName) {
		
		System.out.println("User Service create invoked:"+username);
		User User  = new User();
		User.setEmail(email);
		User.setUname(username);
		User.setUpassword(encPwd);
		User.setUtype(utype);
		User.setGender(gender);
		User.setFirstName(firstName);
		User.setLastName(lastName);
		
		final String dir = System.getProperty("user.dir");
        
        File f1 = new File(dir+"/Users");
        if(f1.exists())
        {
        		System.out.println("here");
        }
        File userDir = new File(f1, email);
        boolean userSuccess = userDir.mkdirs();
		
		User = UserDAO.addUser(User);
		return User;
	}
	@Transactional
	public User updateUser(User user, String username, int utype,char gender,String firstName, String lastName) {
		
		System.out.println("Cusomer Service Update invoked:"+username);
		//User User  = new User();
		user.setUname(username);
		user.setUtype(utype);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user = UserDAO.updateUser(user);
		return user;
	}
	
	/* More info to be added */
	@Transactional
    public User editUserPassword(User user, String pwd)
    {
			
		user.setUpassword(pwd);
		user = UserDAO.updateUser(user);
		return user;
    }
	@Transactional
	public void removeUser(User user) {
		UserDAO.deleteUser(user);
		
	}
	

	public User getUser(String email) {
		return UserDAO.getUser(email);
	}

	public List<User> getAllUsers() {
		return UserDAO.getUsers();
	}
	
	
	@Transactional
    public List<User> getFriend(String uemail)
    {
       //  Query query1 =
        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
    	List<User> results = UserDAO.getFriend(uemail);
    
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
    		UserDAO.addFriend(uemail,femail);
    		List<User> results = UserDAO.getFriend(uemail);
    
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
    		UserDAO.deleteFriend(uemail,femail);
    		List<User> results = UserDAO.getFriend(uemail);
    
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
	    	list = UserDAO.getUserByUserType(usertype);
	    	
	    	return list;
    }
	@Transactional
	public List<User> getAllArtist(){
		return UserDAO.getAllArtist();
	}
}
