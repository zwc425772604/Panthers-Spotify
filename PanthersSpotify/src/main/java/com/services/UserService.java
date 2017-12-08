package com.services;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Artist;
import com.model.Concert;
import com.model.Payment;
import com.model.Song;
import com.model.SongQueue;
import com.model.User;

public interface UserService {
	public User addUser(String userName, String email, String encPassword, int userType, char gender, String firstName,
			String middleName, String lastName, String dob,String bio);

	public User updateUser(User user, String userName, int userType, char gender, String firstName, String lastName,
			boolean isPublic,String photoUrl);

	public void removeUser(User user);

	public User getUser(String userEmail);

	public boolean isEmailRegistered(String userEmail);

	public List<User> getAllUsers();

	public User editUserPassword(User user, String pwd);

	public List<User> getFriend(String userEmail);

	public List<User> addFriend(String userEmail, String friendEmail);

	public List<User> deleteFriend(String userEmail, String friendEmail);

	public List<User> getUsersByType(int userType);

	public List<User> getAllArtist();

	public void followArtist(String artistEmail, User user);

	public void unfollowArtist(String artistEmail, User user);

	public List<Artist> getFollowArtists(User user);

	public Collection<SongQueue> getQueue(String userEmail);

	public void addPayment(Payment payment);

	public void upgrade(User user);

	public void downgrade(User user);
	
	public void setArtistRoylties(Artist artist,double factor);
	
	public void updatePayment(Payment payment);
	
	public void removePayment(String uEmail);
	
	public List<Song> getReleaseSong(Artist artist);
	
	public Artist getArtistInfo(User artist);
	
	public Artist editArtist(User artist,String bio);
	
	public void sendEmail(String sentToEmail,String token);
	
	public User editUserToken(User user);
	
	public Concert getConcert(int cid);
	
	public void addConcert(Concert c);
	
	public void deleteConcert(Concert c);
	
	public List<Concert> getConcerts(User user);
	
	public User updateSpecificUser(User user);
	
	public Payment findPayment(String userEmail);
	
	public List getMonthlyPlay();
	
	public List setArtistsRoylties(double factor);
	
	public List<User> getAllPremium();
	
	public void sendSupportEmail(String sentToEmail,String content);
	
	public List<User> findRelative(String input);
	
	public List<User> getArtistFollowers(String userEmail);
}
