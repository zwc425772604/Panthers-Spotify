package com.services;

import java.util.Collection;
import java.util.List;

import com.model.Artist;
import com.model.Playlist;
import com.model.SongQueue;
import com.model.User;

public interface UserService {
	public User addUser(String userName, String email,String encPassword,int userType,char gender,String firstName, String middleName, String lastName, String dob);
	public User updateUser(User user, String userName, int userType,char gender,String firstName, String lastName,boolean isPublic);
	public void removeUser(User user);
	public User getUser(String userEmail);
	public boolean isEmailRegistered(String userEmail);
	public List<User> getAllUsers();
	public User editUserPassword(User user, String pwd);
	public List<User> getFriend(String userEmail);
	public List<User> addFriend(String userEmail,String friendEmail);
	public List<User> deleteFriend(String userEmail,String friendEmail);
	public List<User> getUsersByType(int userType);
	public List<User> getAllArtist();
	public void followArtist(String artistEmail,User user);
	public void unfollowArtist(String artistEmail,User user);
	public List<Artist> getFollowArtists(User user);
	public Collection<SongQueue> getQueue(String userEmail);
}
