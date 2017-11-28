package com.services;

import java.io.File;
import java.sql.Date;
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
	    	File playlistFile = new File("Users/"+user.getEmail()+"/playlist"+userPlaylists.size());
	    	playlistFile.mkdirs();
	    	String photoUrl = UploadFile.upload(playlistFile.getAbsolutePath(),filename,file);
		Playlist playlist = new Playlist(playlistName,description,photoUrl,0,0,date,user);
		playlist = playlistDAO.addPlaylist(playlist);
		userPlaylists.add(playlist);
		return userPlaylists;
	}
	
	
	@Transactional
	public List<Playlist> updatePlaylist(int pid, String des,CommonsMultipartFile file, String pname, User user) {
		
		List<Playlist> userPlaylist = (List<Playlist>)(user.getUserPlaylistCollection());
		
		Playlist playlist=playlistDAO.getPlaylist(pid);
		userPlaylist.remove(playlist);
	  	String photoUrl=null;
	  	int num=findPlaylist(pid,playlistDAO.getPlaylists());
	  	if(des!=null)
	  	{
	  		playlist.setDes(des);
	  	}
	  	if(playlist.getPhotoUrl()!=null)
	  	{
	  		File deleteFile = new File(playlist.getPhotoUrl());
	  		if(deleteFile.exists())
	  		{
	  			deleteFile.delete();
	  		}
	  	}
		if(file!=null)
		{
			File playlistFile = new File("Users/"+user.getEmail()+"/playlist"+num);
			String filename = file.getOriginalFilename();
		  	if(!playlistFile.exists())
		  	{
		  		System.out.println("exist fail");
		  	}
		  	else
		  	{
		  		photoUrl = UploadFile.upload("Users/"+user.getEmail()+"/playlist"+num,filename,file);
		  	}
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
	public List<Playlist> removePlaylist(Playlist Playlist) {
		playlistDAO.deletePlaylist(Playlist);
		return playlistDAO.getPlaylists();
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
	public boolean followPlaylist(int playlistId,User user)
	  {
		  List<Playlist> userPlaylist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	  int playlistIndex = findPlaylist(playlistId,userPlaylist);
	  	  
	  	  if(playlistIndex!=-1)
	  	  {
	  		  return false;
	  	  }
		    Playlist result = playlistDAO.getPlaylist(playlistId);
		    
		    playlistDAO.followPlaylist(playlistId, user.getEmail());
		  	userPlaylist.add(result);
		  	return true;
	  }
	@Transactional
	public boolean unfollowPlaylist(int playlistId,User user)
	  {
		  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	  int playlistIndex = findPlaylist(playlistId,user_playlist);
	  	  
	  	  if(playlistIndex==-1)
	  	  {
	  		  return false;
	  	  }
	  	  
	  	  
	  	  Playlist result = playlistDAO.getPlaylist(playlistId);
	  	  playlistDAO.unfollowPlaylist(playlistId, user.getEmail());
		  	
		  	  
		  	user_playlist.remove(result);
		  	return true;
	  }
	
	//add song to playlist
	@Transactional
	  public void addSongToPlaylist(int playlistId,int songId)
	  {
			playlistDAO.addSongToPlaylist(playlistId, songId);
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
}
