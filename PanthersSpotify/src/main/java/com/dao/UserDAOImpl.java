package com.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Album;
import com.model.Artist;
import com.model.Concert;
import com.model.Followartist;
import com.model.Followplaylist;
import com.model.Friend;
import com.model.FriendPK;
import com.model.Payment;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.SongQueue;
import com.model.User;
import com.model.UserType;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	public EntityManager entityManager;

	
	@Transactional(readOnly = false)
	public User addUser(User User) {

		entityManager.persist(User);
		return User;
	}
	


	//asdfasdf ihsidfhsdfihsdih royalty
		@Transactional(readOnly = false)
		public List getMonthlyPlay() {
			//where ra.releasealbumPK.aid=s.albumId and ra.releasealbumPK.uemail=a.artistEmail 
			String queryString = "SELECT a.artistEmail,SUM(s.monthlyPlayed) FROM Song s,Releasealbum ra,Artist a where ra.releasealbumPK.aid=s.albumId and ra.releasealbumPK.uemail=a.artistEmail group by s.albumId";
			Query query = entityManager.createQuery(queryString);
			List results = query.getResultList();
			//List<String> ret = query.getResultList();
			return results;
//			for (int i=0;i<results.size();i++) {
//				Object[] ret = (Object[])results.get(i);
//			    System.out.println(ret[0]+"  "+ret[1]);
//			}
			
		}
		
		@Transactional(readOnly = false)
		public Object[] getArtistMonthlyPlay(String artistEmail) {
			//where ra.releasealbumPK.aid=s.albumId and ra.releasealbumPK.uemail=a.artistEmail 
			String queryString = "SELECT a.artistEmail,SUM(s.monthlyPlayed) FROM Song s,Releasealbum ra,Artist a where ra.releasealbumPK.aid=s.albumId and ra.releasealbumPK.uemail=a.artistEmail and a.artistEmail=:artistEmail group by s.albumId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("artistEmail", artistEmail);
			Object[] results = (Object[])query.getSingleResult();
			//List<String> ret = query.getResultList();
			return results;
//			for (int i=0;i<results.size();i++) {
//				Object[] ret = (Object[])results.get(i);
//			    System.out.println(ret[0]+"  "+ret[1]);
//			}
			
		}
	
	@Transactional(readOnly = false)
	public User addArtist(User user,String bio) {
		Artist a = new Artist();
		a.setBio(bio);
		a.setFollowers(0);
		a.setRoyalty(0);
		a.setUser(user);
		a.setArtistEmail(user.getEmail());
		entityManager.persist(a);
		return user;
	}

	@Transactional(readOnly = false)
	public User updateUser(User User) {
		entityManager.merge(User);
		return User;
	}

	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));

	}

	@Transactional(readOnly = true)
	public User getUser(String userEmail) {
		TypedQuery<User> query1 = entityManager.createNamedQuery("User.findByEmail", User.class).setParameter("email",
				userEmail);
		User ret = null;
		try
		{
			ret = query1.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
		return ret;
	}

	@Transactional(readOnly = true)
	public List<User> getUserByUserType(int userType) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByUserType", User.class)
				.setParameter("userType", userType);

		List<User> results = query.getResultList();

		return results;
	}

	@Transactional(readOnly = true)
	public List<User> getUsers() {

		TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);

		List<User> results = query.getResultList();
		return results;
	}

	@Transactional(readOnly = true)
	public List<User> getFriend(String userEmail) {
		
		String queryString = "SELECT u FROM User u WHERE u.email in (SELECT f.friendPK.femail FROM Friend f WHERE f.friendPK.uemail=:uemail)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("uemail", userEmail);
		List<User> results = query.getResultList();
		return results;
	}
	

	@Transactional(readOnly = true)
	public List<User> getAllArtist() {
		List<User> list = new ArrayList<User>();
		Query query = entityManager.createNamedQuery("User.findByUserType")
				.setParameter("userType", 2);
		list = (List<User>) query.getResultList();
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<User> getAllPremium() {
		List<User> list = new ArrayList<User>();
		Query query = entityManager.createNamedQuery("User.findByUserType")
				.setParameter("userType", 1);
		list = (List<User>) query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public void addFriend(String userEmail, String friendEmail) {
		Friend friend = new Friend(userEmail, friendEmail);
		entityManager.persist(friend);
	}

	@Transactional(readOnly = true)
	public void deleteFriend(String userEmail, String friendEmail) {
		Query query = entityManager.createNamedQuery("Friend.findByUemailFemail")
				.setParameter("uemail",userEmail)
				.setParameter("femail",friendEmail);
		Friend friend = (Friend)query.getSingleResult();
		entityManager.remove(entityManager.contains(friend) ? friend : entityManager.merge(friend));
		
	}

	@Transactional(readOnly = false)
	public void followArtist(String artistEmail, String userEmail) {

		Followartist followArtist = new Followartist(userEmail, artistEmail);
		
		entityManager.persist(followArtist);
		
		
		Query query = entityManager.createNamedQuery("Artist.findByArtistEmail").setParameter("artistEmail", artistEmail);
		
		Artist retArtist = (Artist)query.getSingleResult();
		
		retArtist.setFollowers(retArtist.getFollowers()+1);
		entityManager.merge(retArtist);
		
	}

	@Transactional(readOnly = false)
	public void unfollowArtist(String artistEmail, String userEmail) {
		Query query1 = entityManager.createNamedQuery("Followartist.findByUemailAemail")
				.setParameter("aemail", artistEmail)
				.setParameter("uemail", userEmail);
		Followartist followArtist = (Followartist)query1.getSingleResult();
		
		if(entityManager.contains(followArtist))
		{
			entityManager.remove(followArtist);
		
			Query query = entityManager.createNamedQuery("Artist.findByArtistEmail").setParameter("artistEmail", artistEmail);
			
			Artist retArtist = (Artist)query.getSingleResult();
			
			retArtist.setFollowers(retArtist.getFollowers()-1);
			entityManager.merge(retArtist);
		}
		
	}

	@Transactional(readOnly = true)
	public List<Artist> getFollowArtists(String userEmail) {
		String queryString = "SELECT a FROM Artist a where a.artistEmail in (SELECT f.followartistPK.aemail from Followartist f where f.followartistPK.uemail=:uemail)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("uemail", userEmail);
		List<Artist> list = (List<Artist>) query.getResultList();
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<User> getArtistFollowers(String userEmail) {
		String queryString = "SELECT a FROM User a where a.email in (SELECT f.followartistPK.uemail from Followartist f where f.followartistPK.aemail=:aemail)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("aemail", userEmail);
		List<User> list = (List<User>) query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public Collection<SongQueue> getSongQueue(String userEmail) {
		Query query = entityManager.createNamedQuery("SongQueue.findByUemail", SongQueue.class);
		query.setParameter("uemail", userEmail);
		Collection<SongQueue> result = (ArrayList<SongQueue>) query.getResultList();
		return result;
	}
	
	
	
	
	@Transactional(readOnly = true)
	public Payment findPayment(String userEmail) {
		Query query = entityManager.createNamedQuery("Payment.findByUemail", Payment.class);
		query.setParameter("uemail", userEmail);
		Payment result=null;
		try {
			result = (Payment) query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
		return result;
	}
	
	@Transactional(readOnly = false)
	public void addPayment(Payment payment) {
		if(!entityManager.contains(payment))
			entityManager.persist(payment);	
		else
			entityManager.merge(payment);	
	}
	
	@Transactional(readOnly = false)
	public void updatePayment(Payment payment) {
		entityManager.merge(payment);		
	}
	
	@Transactional(readOnly = false)
	public void removePayment(String userEmail) {
		String queryString = "DELETE FROM Payment WHERE uemail=:uemail";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("uemail", userEmail);
		
		query.executeUpdate();
		
	}

	@Transactional(readOnly = false)
	public void upgrade(User user) {
		user.setUserType(UserType.PREMIUM.getTypeCode()); 
		Date date = new Date();
		user.setUpgradeDate(date);
		entityManager.merge(user);
	}

	@Transactional(readOnly = false)
	public void downgrade(User user) {
		user.setUserType(UserType.BASIC.getTypeCode()); //here magic number 0:basic user
		entityManager.merge(user);
	}

	@Transactional(readOnly = true)
	public List<Releasesong> getArtistRelease(Artist artist) {
		String a = artist.getArtistEmail();
		Query query = entityManager.createNamedQuery("Releasesong.findByUemail").setParameter("uemail", artist.getArtistEmail());
		
		List<Releasesong> songs = query.getResultList();
			
		return songs;
	}
	
	@Transactional(readOnly = true)
	public double getRoyalty(Artist artist) {
		String queryString = "SELECT p.royalty from Artist p WHERE p.artistEmail=:artistEmail";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("artistEmail", artist.getArtistEmail());
		double royalty = (double)query.getSingleResult();
			
		return royalty;
	}
	
	@Transactional(readOnly = false)
	public void setRoyalty(Artist artist,double royalty) {
		artist.setRoyalty(royalty);
		entityManager.merge(artist);
	}
	
	@Transactional(readOnly = true)
	public Artist getArtist(User artist) {
		Query query = entityManager.createNamedQuery("Artist.findByArtistEmail").setParameter("artistEmail", artist.getEmail());
		
		Artist retArtist = (Artist)query.getSingleResult();
			
		
		return retArtist;
	}
	
	@Transactional(readOnly = false)
	public Artist editArtist(User artist,String bio) {
		Query query = entityManager.createNamedQuery("Artist.findByArtistEmail").setParameter("artistEmail", artist.getEmail());
		
		Artist retArtist = (Artist)query.getSingleResult();
		retArtist.setBio(bio);
		entityManager.merge(retArtist);
		
		return retArtist;
	}
	
	@Transactional(readOnly = true)
	public Concert getConcert(int cid) {
		Query query = entityManager.createNamedQuery("Concert.findByCid").setParameter("cid", cid);
		
		Concert retConcert = (Concert)query.getSingleResult();
			
		
		return retConcert;
	}
	
	@Transactional(readOnly = false)
	public Concert addConcert(Concert c) {
		entityManager.persist(c);
		return c;
	}
	
	@Transactional(readOnly = false)
	public Concert deleteConcert(Concert c) {
		entityManager.remove(c);
		return c;
	}
	
	@Transactional(readOnly = true)
	public List<Concert> getConcerts(User user) {
		
		
		TypedQuery<Concert> query1 = entityManager
				.createQuery("SELECT s from Concert s WHERE s.uemail=:uemail", Concert.class)
				.setParameter("uemail", user);

		List<Concert> result = query1.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<User> findRelative(String input)
	  {
	  	  
		TypedQuery<User> query1 = entityManager.createQuery("SELECT p from User p WHERE p.userType=2 and p.userName LIKE CONCAT('%',:searchTerm,'%')", User.class)
	       		.setParameter("searchTerm", input);
	  	List<User> result = query1.getResultList();
	  	return result;
	  }
	

}
