package com.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Artist;
import com.model.Followartist;
import com.model.Followplaylist;
import com.model.Friend;
import com.model.Playlist;
import com.model.Squeue;
import com.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO{
	
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly=false)
	public User addUser(User User) {
		
		entityManager.persist(User);
		return User;
	}

	@Transactional(readOnly=false)
	public User updateUser(User User) {
		entityManager.merge(User);
		return User;
	}

	@Transactional(readOnly=false)
	public void deleteUser(User user) {
		entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
		
	}

	@Transactional(readOnly=true)
	public User getUser(String email) {
		TypedQuery<User> query1 = entityManager.createNamedQuery("User.findByEmail", User.class)
          		.setParameter("email", email);

         List<User> results = query1.getResultList();
         return results.size() == 0 ? null : results.get(0);
//		return results.get(0);
	}
	
	@Transactional(readOnly=true)
	public List<User> getUserByUserType(int userType) {
		TypedQuery<User> query1 = entityManager.createNamedQuery("User.findByUtype", User.class)
          		.setParameter("utype", userType);

         List<User> results = query1.getResultList();
         
		return results;
	}

	@Transactional(readOnly=true)
	public List<User> getUsers() {
		
		TypedQuery<User> query1 = entityManager.createNamedQuery("User.findAll", User.class);

		 List<User> results = query1.getResultList();
		 return results;
	}
	@Transactional(readOnly=true)
    public List<User> getFriend(String uemail)
    {
		System.out.println("user email is :" + uemail);
	    	String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
	    	Query query = entityManager.createQuery(queryString);
	    	query.setParameter("uemail", uemail);
	    	
	       //  Query query1 =
	        //em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
	    	List<User> results = query.getResultList();
	    	return results;
    }
	
	
	@Transactional(readOnly=true)
	public List<User> getAllArtist(){
    	List<User> list=  new ArrayList<User>();
    	
    	Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.utype = 2");
    	list = (List<User>)query.getResultList();
    	
    	return list;
    }
	@Transactional(readOnly=true)
    public void addFriend(String uemail,String femail)
    {
		Friend ret = new Friend(uemail, femail);
		entityManager.persist(ret);
    }
	@Transactional(readOnly=true)
    public void deleteFriend(String uemail,String femail)
    {
		Friend ret = new Friend(uemail, femail);
		entityManager.remove(ret);
    }
	
	@Transactional(readOnly=false)
	public void followArtist(String artistEmail,String userEmail) {
		
		Followartist followArtist = new Followartist(artistEmail,userEmail);
		
		entityManager.persist(followArtist);
	}
	
	@Transactional(readOnly=false)
	public void unfollowArtist(String artistEmail,String userEmail) {
		
		Followartist followArtist = new Followartist(artistEmail,userEmail);
		
		entityManager.remove(followArtist);
	}
	@Transactional(readOnly=true)
	public List<Artist> getFollowArtists(String userEmail)
	{
		 
		String queryString = "SELECT a FROM Artist a where a.artistEmail in (SELECT f.followartistPK.aemail from Followartist f where f.followartistPK.uemail=:uemail)";
	    	Query query = entityManager.createQuery(queryString);
	    	query.setParameter("uemail", userEmail);
	    	List<Artist>	list = (List<Artist>)query.getResultList();
	    	return list;
	}
	
	@Transactional(readOnly=true)
	public Collection<Squeue> getSqueue(String email) {
		Query query = entityManager.createNamedQuery("Squeue.findByUemail", Squeue.class);
		query.setParameter("uemail", email);
		List<Squeue> result = (ArrayList<Squeue>)query.getResultList();
		return result;
	}
	
}
