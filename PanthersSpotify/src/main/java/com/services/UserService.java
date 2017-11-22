package com.services;

import java.util.List;

import com.model.User;

public interface UserService {
	public User addUser(String username, String email,String encPwd,int utype,char gender,String firstName, String lastName);
	public User updateUser(User user, String username, int utype,char gender,String firstName, String lastName);
	public void removeUser(User user);
	public User getUser(String email);
	public List<User> getAllUsers();
	public User editUserPassword(User user, String pwd);
	public List<User> getFriend(String uemail);
	public List<User> addFriend(String uemail,String femail);
	public List<User> deleteFriend(String uemail,String femail);
	public List<User> getUsersByType(int usertype);
	public List<User> getAllArtist();
}
