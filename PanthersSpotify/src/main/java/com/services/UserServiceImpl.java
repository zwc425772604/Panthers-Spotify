package com.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.dao.SongDAO;
import com.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.model.Artist;
import com.model.Payment;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.SongQueue;
import com.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired(required = true)
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired(required = true)
	@Qualifier("songDAO")
	private SongDAO songDAO;

	@Transactional
	public User addUser(String userName, String email, String encPassword, int userType, char gender, String firstName,
			String middleName, String lastName, String dob) {
		System.out.println("User Service create invoked:" + userName);
		User user = new User();
		user.setEmail(email);
		user.setUserName(userName);
		user.setUserPassword(encPassword);
		user.setUserType(userType);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setPublic(true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss", Locale.US);
		Date parsedBirthday = null;
		try {
			parsedBirthday = dateFormat.parse(dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setDob(parsedBirthday);
		String dir = System.getProperty("user.dir");
		File f1 = new File(dir + "/Users");
		File userDir = new File(f1, email);
		userDir.mkdirs();
		user = userDAO.addUser(user);
		return user;
	}

	@Transactional
	public User updateUser(User user, String userName, int userType, char gender, String firstName, String lastName,
			boolean isPublic) {

		System.out.println("Cusomer Service Update invoked:" + userName);
		// User User = new User();
		user.setUserName(userName);
		user.setUserType(userType);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPublic(isPublic);
		user = userDAO.updateUser(user);
		return user;
	}

	/* More info to be added */
	@Transactional
	public User editUserPassword(User user, String pwd) {

		user.setUserPassword(pwd);
		user = userDAO.updateUser(user);
		return user;
	}

	@Transactional
	public void removeUser(User user) {
		userDAO.deleteUser(user);

	}

	public User getUser(String userEmail) {
		return userDAO.getUser(userEmail);
	}

	public List<User> getAllUsers() {
		return userDAO.getUsers();
	}

	@Transactional
	public List<User> getFriend(String userEmail) {
		// Query query1 =
		// em.createQuery("SELECT u FROM User u WHERE u.email =
		// :email").setParameter("email", email);
		List<User> results = userDAO.getFriend(userEmail);

		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).getEmail());
		}
		if (results.size() == 0) {
			System.out.println("no friends");
		}
		return results;
	}

	@Transactional
	public List<User> addFriend(String userEmail, String friendEmail) {
		// Query query1 =
		// em.createQuery("SELECT u FROM User u WHERE u.email =
		// :email").setParameter("email", email);
		userDAO.addFriend(userEmail, friendEmail);
		List<User> results = userDAO.getFriend(userEmail);

		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).getEmail());
		}
		if (results.size() == 0) {
			System.out.println("no friends");
		}
		return results;
	}

	@Transactional
	public List<User> deleteFriend(String userEmail, String friendEmail) {
		// Query query1 =
		// em.createQuery("SELECT u FROM User u WHERE u.email =
		// :email").setParameter("email", email);
		userDAO.deleteFriend(userEmail, friendEmail);
		List<User> results = userDAO.getFriend(userEmail);

		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).getEmail());
		}
		if (results.size() == 0) {
			System.out.println("no friends");
		}
		return results;
	}

	@Transactional
	public List<User> getUsersByType(int usertype) {
		List<User> list = new ArrayList<User>();
		list = userDAO.getUserByUserType(usertype);

		return list;
	}

	@Transactional
	public List<User> getAllArtist() {
		return userDAO.getAllArtist();
	}

	@Transactional
	public boolean isEmailRegistered(String userEmail) {
		User user = userDAO.getUser(userEmail);
		return (user == null) ? false : true;
	}

	@Transactional
	public void followArtist(String artistEmail, User user) {
		userDAO.followArtist(artistEmail, user.getEmail());

	}

	@Transactional
	public void unfollowArtist(String artistEmail, User user) {
		userDAO.unfollowArtist(artistEmail, user.getEmail());

	}

	@Transactional
	public List<Artist> getFollowArtists(User user) {
		return userDAO.getFollowArtists(user.getEmail());
	}

	@Transactional
	public Collection<SongQueue> getQueue(String userEmail) {
		return userDAO.getSongQueue(userEmail);
	}
	
	@Transactional
	public void addPayment(Payment payment) {
		userDAO.addPayment(payment);		
	}
	
	@Transactional
	public void updatePayment(Payment payment) {
		userDAO.updatePayment(payment);		
	}
	
	@Transactional
	public void removePayment(Payment payment) {
		userDAO.removePayment(payment);		
	}

	@Transactional
	public void upgrade(User user) {
		userDAO.upgrade(user);		
	}

	@Transactional
	public void downgrade(User user) {
		userDAO.downgrade(user);
	}

	@Transactional
	public void setArtistRoylties(Artist artist,double factor) {
		
		List<Releasesong> songs = userDAO.getArtistRelease(artist);
		
		for(Releasesong rs: songs) {
			Collection<User> artists = songDAO.getSongArtists(rs.getReleasesongPK().getSid());
			double royalties = artist.getRoyalty();
			
				royalties += (songDAO.getSong(rs.getReleasesongPK().getSid()).getMonthlyPlayed())/artists.size();
				
				royalties = royalties * factor;
				
				userDAO.setRoyalty(artist, royalties);
		}
		
		
	}
	
	@Transactional
	public List<Song> getReleaseSong(Artist artist) {
		
		List<Releasesong> songs = userDAO.getArtistRelease(artist);
		List<Song> retSongs = new ArrayList<Song>();
		for(Releasesong rs: songs) {
			
			
			retSongs.add(songDAO.getSong(rs.getReleasesongPK().getSid()))	;
				
		}
		return retSongs;
		
	}
	
	
}
