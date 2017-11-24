package com.services;

import java.util.List;

import com.model.Artist;
import com.model.User;

public interface UserService {
	public User addUser(String username, String email,String encPwd,int utype,char gender,String firstName, String middleName, String lastName, String dob);
	public User updateUser(User user, String username, int utype,char gender,String firstName, String lastName,boolean isPublic);
	public void removeUser(User user);
	public User getUser(String email);
	public boolean isEmailRegistered(String email);
	public List<User> getAllUsers();
	public User editUserPassword(User user, String pwd);
	public List<User> getFriend(String uemail);
	public List<User> addFriend(String uemail,String femail);
	public List<User> deleteFriend(String uemail,String femail);
	public List<User> getUsersByType(int usertype);
	public List<User> getAllArtist();
	public void followArtist(String artistEmail,User user);
	public void unfollowArtist(String artistEmail,User user);
	public List<Artist> getFollowArtists(User user);
}
