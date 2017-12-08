package com.services;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dao.PlaylistDAO;
import com.model.Album;
import com.model.Followplaylist;
import com.model.Playlist;
import com.model.Playlistsong;
import com.model.Song;
import com.model.User;
import com.helper.SubString;
import com.helper.UploadFile;

@Service("playlistService")
@Transactional
public class PlaylistServiceImpl implements PlaylistService {
	@Autowired(required=true)
	@Qualifier("playlistDAO")
	private PlaylistDAO playlistDAO;
	
	@Transactional
	public List<Playlist> addPlaylist(String playlistName,User user, String description,CommonsMultipartFile file,Date date) 
	{
		List<Playlist> userPlaylists = (List<Playlist>)(user.getUserPlaylistCollection());
	    	String filename = file.getOriginalFilename();
	    	String dir = System.getProperty("user.dir");
	    	String playlistPath = dir+"/src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail()+"/playlist"+date.getTime();
	    	File playlistFile = new File(playlistPath);
	    	playlistFile.mkdirs();
	    	
	    	String photoUrl = UploadFile.upload(playlistPath,filename,file);
	    	if(photoUrl==null)
	    	{
	    		playlistFile.delete();
	    	}
	    	else
	    	{
	    		System.out.println(photoUrl);
	    		photoUrl = SubString.getSubstring(photoUrl);
	    	}
		Playlist playlist = new Playlist(playlistName,description,photoUrl,0,0,date,user);
		playlist = playlistDAO.addPlaylist(playlist);
		userPlaylists.add(playlist);
		return userPlaylists;
	}
	
	
	@Transactional
	public List<Playlist> updatePlaylist(int pid, String des,CommonsMultipartFile file, String pname, User user) {
		
		List<Playlist> userPlaylist = (List<Playlist>)(user.getUserPlaylistCollection());
		String dir = System.getProperty("user.dir");
		String path = dir+"/src/main/webapp/WEB-INF/resources/data/";
		Playlist playlist=playlistDAO.getPlaylist(pid);
		userPlaylist.remove(playlist);
	  	String photoUrl= playlist.getPhotoUrl() != null? playlist.getPhotoUrl().substring(1,playlist.getPhotoUrl().length()):playlist.getPhotoUrl();
	  	File playlistFile = null;
	  	System.out.println(photoUrl);
	  	if(des!=null)
	  	{
	  		playlist.setDes(des);
	  	}
	  	if(photoUrl!=null)
	  	{
	  		File deleteFile = new File(path+photoUrl);
	  		System.out.println("edit inside is "+path+photoUrl);
	  		if(deleteFile.exists())
	  		{
	  			deleteFile.delete();
	  		}
	  		else
	  		{
	  			System.out.println("edit is wrong");
	  		}
	  		File photo = new File(path+photoUrl);
		  	playlistFile = photo.getParentFile();
	  	}
	  	
		if(file!=null&&playlistFile!=null)
		{
			
			String filename = file.getOriginalFilename();
		  	System.out.println("it should be + "+playlistFile.getAbsolutePath());
		  	System.out.println("file name is "+filename);
		  	photoUrl = UploadFile.upload(playlistFile.getAbsolutePath(),filename,file);
		  	
		  	photoUrl = SubString.getSubstring(photoUrl);
		  	playlist.setPhotoUrl(photoUrl);
		}
		else if(file!=null&&playlistFile==null)
		{
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			String playlistPath = "src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail()+"/playlist"+date.getTime();
			File playlistF = new File(playlistPath);
	    		playlistF.mkdirs();
	    		String filename = file.getOriginalFilename();
	    		
	    		photoUrl = UploadFile.upload(playlistF.getAbsolutePath(),filename,file);
	    		photoUrl = SubString.getSubstring(photoUrl);
			playlist.setPhotoUrl(photoUrl);
		}
		
		if(pname!=null)
		{
			playlist.setPname(pname);
		}
		playlist = playlistDAO.updatePlaylist(playlist);
		userPlaylist.add(playlist);
		return userPlaylist;
	}
	@Transactional
	public List<Playlist> removePlaylist(Playlist playlist) {
		playlistDAO.deletePlaylist(playlist);
		if(playlist.getPhotoUrl()!=null)
		{
			String dir = System.getProperty("user.dir");
			String path = dir+"/src/main/webapp/WEB-INF/resources/data/";
			File deleteFile = new File(path+playlist.getPhotoUrl());
			File deleteFileParent = deleteFile.getParentFile();
	  		if(deleteFile.exists())
	  		{
	  			deleteFile.delete();
	  		}
	  		deleteFileParent.delete();
		}
		return playlistDAO.getPlaylists();
	}
	
	@Transactional
	 public void updateSpecificPlaylist(Playlist p){
		  playlistDAO.updatePlaylist(p);
	  }
	
	@Transactional
	 public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist){
		  return playlistDAO.getTopFollowedPlaylist(numberOfPlaylist);
	  }

	public Playlist getPlaylist(int pid) {
		return playlistDAO.getPlaylist(pid);
	}

	public List<Playlist> getAllPlaylists() {
		return playlistDAO.getPlaylists();
	}
	
	private int findPlaylist(int playlistId, List<Playlist> playlists)
	  {
		  int num=-1;
		  for(int i=0;i<playlists.size();i++)
		  {
			  if(playlists.get(i).getPid()==playlistId)
			  {
				  num=i;
				  break;
			  }
		  }
		  return num;
	  }
	@Transactional
	public Playlist followPlaylist(int playlistId,User user)
	  {
		  List<Playlist> userPlaylist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	  int playlistIndex = findPlaylist(playlistId,userPlaylist);
	  	  
	  	  if(playlistIndex!=-1)
	  	  {
	  		  return null;
	  	  }
		    Playlist result = playlistDAO.getPlaylist(playlistId);
		    
		    playlistDAO.followPlaylist(playlistId, user.getEmail());
		    
		    result.setFollowers(result.getFollowers()+1);
		    playlistDAO.updatePlaylist(result);
		    
		  	userPlaylist.add(result);
		  	return result;
	  }
	@Transactional
	public Playlist unfollowPlaylist(int playlistId,User user)
	  {
		  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	  int playlistIndex = findPlaylist(playlistId,user_playlist);
	  	  
	  	  if(playlistIndex==-1)
	  	  {
	  		  return null;
	  	  }
	  	  Playlist result = playlistDAO.getPlaylist(playlistId);
	  	  playlistDAO.unfollowPlaylist(playlistId, user.getEmail());
	  	result.setFollowers(result.getFollowers()-1);
	    playlistDAO.updatePlaylist(result);
	    
		  user_playlist.remove(result);
		  	return result;
	  }
	
	//add song to playlist
	@Transactional
	  public String addSongToPlaylist(int playlistId,int songId)
	  {
			return playlistDAO.addSongToPlaylist(playlistId, songId);
	  }
	
	@Transactional
	  public void removeSongFromPlaylist(int playlistId,int songId)
	  {
			playlistDAO.removeSongFromPlaylist(playlistId, songId);
	  }
	
	@Transactional
	  public List<Song> getSongInPlaylist(int playlistId)
	  {
			return playlistDAO.getSongInPlaylist(playlistId);
	  }
	
	
	@Transactional
	public List<Playlist> findRelative(String input)
	{
		return playlistDAO.findRelative(input);
	}
	
	@Transactional
	public List<Playlist> getHistoryPlaylists(String userEmail)
	{
		return playlistDAO.getHistoryPlaylists(userEmail);
	}
	
	@Transactional
	public List<Playlist> addHistoryPlaylist(Playlist playlist,User user,Date date) {
		playlistDAO.addPlaylistHistory(playlist, user, date);
		return playlistDAO.getHistoryPlaylists(user.getEmail());
	}
	
	@Transactional
	public List<Playlist> deleteHistoryPlaylist(Playlist playlist,User user) {
		playlistDAO.deletePlaylistHistory(playlist, user);
		return playlistDAO.getHistoryPlaylists(user.getEmail());
	}
	
	@Transactional
	public Collection<Playlist> getUserPlaylists(String email) {
		return playlistDAO.getUserPlaylists(email);
	}
	
	@Transactional
	public Collection<Playlist> getFollowPlaylists(String userEmail) {
		return playlistDAO.getFollowedPlaylist(userEmail);
	}
	
	@Transactional
	public List<Playlist> getTopGenrePlaylist(String genre)
	{
		return playlistDAO.getTopGenrePlaylist(genre);
	}
	
	@Transactional
	public Time setPlaylistTimeLength(Playlist playlist,List<Song> s)
	{
		Time constant = new Time(18000000);
		Time ret = new Time(0);
		for(int i=0;i<s.size();i++)
		{
			Song song = s.get(i);
			if(song.getDuration().getTime()!=0)
				ret.setTime(ret.getTime()+song.getDuration().getTime()-constant.getTime());
		}
		return ret;
	}
	
	
	
}
