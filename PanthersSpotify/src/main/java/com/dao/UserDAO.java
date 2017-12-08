package com.dao;

import java.util.Collection;
import java.util.List;

import com.model.Artist;
import com.model.Concert;
import com.model.Payment;
import com.model.Releasesong;
import com.model.SongQueue;
import com.model.User;

public interface UserDAO {
	public User addUser(User User);
	public User updateUser(User User);
	public void deleteUser(User User);
	public User getUser(String userEmail);
	public List<User> getUsers();
	public List<User> getFriend(String userEmail);
	public void addFriend(String userEmail,String friendEmail);
	public void deleteFriend(String userEmail,String friendEmail);
	public List<User> getUserByUserType(int userType);
	public List<User> getAllArtist();
	public void unfollowArtist(String artistEmail,String userEmail) ;
	public void followArtist(String artistEmail,String userEmail);
	public List<Artist> getFollowArtists(String userEmail);
	public Collection<SongQueue> getSongQueue(String userEmail);
	public void addPayment(Payment payment);
	public void upgrade(User user);
	public void downgrade(User user);
	public void updatePayment(Payment payment);
	public void removePayment(String userEmail);
	public List<Releasesong> getArtistRelease(Artist artist);
	public double getRoyalty(Artist artist);
	public void setRoyalty(Artist artist,double royalty) ;
	public Artist getArtist(User artist);
	public Artist editArtist(User artist,String bio);
	public Concert getConcert(int cid);
	public Concert addConcert(Concert c);
	public Concert deleteConcert(Concert c);
	public List<Concert> getConcerts(User user);
	public Payment findPayment(String userEmail);
	public User addArtist(User user,String bio);
	public List getMonthlyPlay();
	public Object[] getArtistMonthlyPlay(String artistEmail);
	public List<User> getAllPremium();
	public List<User> findRelative(String input);
	public List<User> getArtistFollowers(String userEmail);
}
