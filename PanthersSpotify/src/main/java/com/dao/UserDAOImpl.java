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

import com.model.Artist;
import com.model.Followartist;
import com.model.Followplaylist;
import com.model.Friend;
import com.model.FriendPK;
import com.model.Payment;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.SongQueue;
import com.model.User;
import com.model.UserType;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {
	private SongDAO songDAO;
	@PersistenceContext
	public EntityManager entityManager;

	
	@Transactional(readOnly = false)
	public User addUser(User User) {

		entityManager.persist(User);
		return User;
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
		System.out.println("user email is :" + userEmail);
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

		Followartist followArtist = new Followartist(artistEmail, userEmail);

		entityManager.persist(followArtist);
	}

	@Transactional(readOnly = false)
	public void unfollowArtist(String artistEmail, String userEmail) {
		Followartist followArtist = new Followartist(artistEmail, userEmail);
		entityManager.remove(followArtist);
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
	public Collection<SongQueue> getSongQueue(String userEmail) {
		Query query = entityManager.createNamedQuery("SongQueue.findByUemail", SongQueue.class);
		query.setParameter("uemail", userEmail);
		Collection<SongQueue> result = (ArrayList<SongQueue>) query.getResultList();
		return result;
	}
	
	@Transactional(readOnly = false)
	public void addPayment(Payment payment) {
		entityManager.persist(payment);		
	}
	
	@Transactional(readOnly = false)
	public void updatePayment(Payment payment) {
		entityManager.merge(payment);		
	}
	
	@Transactional(readOnly = false)
	public void removePayment(Payment payment) {
		entityManager.remove(payment);		
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

}
