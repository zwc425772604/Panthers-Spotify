package com.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.dao.SongDAO;
import com.dao.UserDAO;
import com.helper.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Artist;
import com.model.Concert;
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
	
	@Autowired
    public JavaMailSender sender;

	@Transactional
	public User addUser(String userName, String email, String encPassword, int userType, char gender, String firstName,
			String middleName, String lastName, String dob,String bio) {
		User user = new User();
		user.setEmail(email);
		user.setUserName(userName);
		user.setUserPassword(encPassword);
		user.setUserType(userType);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setIsPublic(true);
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date parsedBirthday = null;
		try {
			parsedBirthday = dateFormat.parse(dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setDob(parsedBirthday);
		String dir = System.getProperty("user.dir");
		File f1 = new File(dir + "/src/main/webapp/WEB-INF/resources/data/Users");
		File userDir = new File(f1, email);
		userDir.mkdirs();
		
		
		user = userDAO.addUser(user);
		if(bio!=null)
			userDAO.addArtist(user, bio);
		return user;
	}

	@Transactional
	public User updateUser(User user, String userName, int userType, char gender, String firstName, String lastName,
			boolean isPublic,String photoUrl) {

		// User User = new User();
		user.setUserName(userName);
		user.setUserType(userType);
		user.setGender(gender);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setIsPublic(isPublic);
		
	    	
	    	user.setPhotoUrl(photoUrl);
		user = userDAO.updateUser(user);
		return user;
	}
	
	@Transactional
	public User editUserToken(User user) {
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		sendEmail(user.getEmail(),token);
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
		String dir = System.getProperty("user.dir");
		File f1 = new File(dir + "/src/main/webapp/WEB-INF/resources/data/Users"+user.getEmail());
		if(f1.exists())
		{
			String[]entries = f1.list();
			for(String s: entries){
			    File currentFile = new File(f1.getPath(),s);
			    currentFile.delete();
			}
			f1.delete();
		}
		else
		{
			System.out.println("error in delete user");
		}
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

//		for (int i = 0; i < results.size(); i++) {
//			System.out.println(results.get(i).getEmail());
//		}
//		if (results.size() == 0) {
//			System.out.println("no friends");
//		}
		return results;
	}

	@Transactional
	public List<User> addFriend(String userEmail, String friendEmail) {
		// Query query1 =
		// em.createQuery("SELECT u FROM User u WHERE u.email =
		// :email").setParameter("email", email);
		userDAO.addFriend(userEmail, friendEmail);
		List<User> results = userDAO.getFriend(userEmail);

//		for (int i = 0; i < results.size(); i++) {
//			System.out.println(results.get(i).getEmail());
//		}
//		if (results.size() == 0) {
//			System.out.println("no friends");
//		}
		return results;
	}

	@Transactional
	public List<User> deleteFriend(String userEmail, String friendEmail) {
		// Query query1 =
		// em.createQuery("SELECT u FROM User u WHERE u.email =
		// :email").setParameter("email", email);
		userDAO.deleteFriend(userEmail, friendEmail);
		List<User> results = userDAO.getFriend(userEmail);

//		for (int i = 0; i < results.size(); i++) {
//			System.out.println(results.get(i).getEmail());
//		}
//		if (results.size() == 0) {
//			System.out.println("no friends");
//		}
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
		User u = getUser(artistEmail);
		Artist a = getArtistInfo(u);
		
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
	public void removePayment(String uEmail) {
		userDAO.removePayment(uEmail);		
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
		double royalties = 0;
		for(Releasesong rs: songs) {
			Collection<User> artists = songDAO.getSongArtists(rs.getReleasesongPK().getSid());
			
				royalties += (songDAO.getSong(rs.getReleasesongPK().getSid()).getMonthlyPlayed())/artists.size();
				
				
		}
		royalties = royalties * factor;
		
		userDAO.setRoyalty(artist, royalties);
		
		
	}
	
	@Transactional
	public List setArtistsRoylties(double factor) {
		System.out.println("ok1");
		List ret = userDAO.getMonthlyPlay();
		System.out.println("ok2");
		for(int i=0;i<ret.size();i++)
		{
			Object[] artistInfo = (Object[])ret.get(i);
			String monthlyPlayedString = artistInfo[1].toString();
			int monthlyPlayed = Integer.parseInt(monthlyPlayedString);
			double royalties = monthlyPlayed * factor;
			artistInfo[1]=royalties;
			//userDAO.setRoyalty(a, royalties);
		}
		System.out.println("finished load");
		return ret;
		
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
	
	@Transactional
	public Artist getArtistInfo(User artist) {
		
		return userDAO.getArtist(artist);
		
	}
	@Transactional
	public Artist editArtist(User artist,String bio)
	{
		return userDAO.editArtist(artist, bio);
	}
	
	@Transactional
	public void sendEmail(String sentToEmail,String token)
	{
//		JavaMailSenderImpl sender = new JavaMailSenderImpl();
//		sender.setHost("smtp.gmail.com");
//		sender.setPort(587);
//		sender.setUsername("xiangtingjin@gmail.com");
//		sender.setPassword("229853300");
//		
//		Properties props = sender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
	    
	    SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(sentToEmail); 
        message.setSubject("PantherSpotify company"); 
        message.setText("your varification code is : "+token);
        sender.send(message);
	}
	
	@Transactional
	public void sendSupportEmail(String sentToEmail,String content)
	{
	    
	    SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(sentToEmail); 
        message.setSubject("User Support Ticket"); 
        message.setText(content);
        sender.send(message);
	}
	
	@Transactional
	public void addConcert(Concert c)
	{
	    
	    userDAO.addConcert(c);
	}
	
	@Transactional
	public void deleteConcert(Concert c)
	{
	    
	    userDAO.deleteConcert(c);
	}
	
	@Transactional
	public Concert getConcert(int cid)
	{
	    return userDAO.getConcert(cid);
	}
	
	@Transactional
	public List<Concert> getConcerts(User user)
	{
	    return userDAO.getConcerts(user);
	}
	
	@Transactional
	public User updateSpecificUser(User user)
	{
	    return userDAO.updateUser(user);
	}
	
	@Transactional
	public Payment findPayment(String userEmail)
	{
	    return userDAO.findPayment(userEmail);
	}
	
	@Transactional
	public List getMonthlyPlay()
	{
		return userDAO.getMonthlyPlay();
	}
	
	@Transactional
	public List<User> getAllPremium()
	{
		return userDAO.getAllPremium();
	}
	
	@Transactional
	public List<User> findRelative(String input)
	{
		return userDAO.findRelative(input);
	}
	
	@Transactional
	public List<User> getArtistFollowers(String userEmail)
	{
		return userDAO.getArtistFollowers(userEmail);
	}
	
}