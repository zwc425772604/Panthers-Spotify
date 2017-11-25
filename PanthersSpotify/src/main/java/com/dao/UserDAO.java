package com.dao;

import java.util.Collection;
import java.util.List;

import com.model.Artist;
import com.model.Squeue;
import com.model.User;

public interface UserDAO {
	public User addUser(User User);
	public User updateUser(User User);
	public void deleteUser(User User);
	public User getUser(String email);
	public List<User> getUsers();
	public List<User> getFriend(String uemail);
	public void addFriend(String uemail,String femail);
	public void deleteFriend(String uemail,String femail);
	public List<User> getUserByUserType(int usertype);
	public List<User> getAllArtist();
	public void unfollowArtist(String artistEmail,String userEmail) ;
	public void followArtist(String artistEmail,String userEmail);
	public List<Artist> getFollowArtists(String userEmail);
	public Collection<Squeue> getSqueue(String email);
}
