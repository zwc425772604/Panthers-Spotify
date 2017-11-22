package com.services;

import java.io.File;
import java.util.Date;
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
import com.model.Followplaylist;
import com.model.Playlist;
import com.model.Playlistsong;
import com.model.User;
import com.helper.UploadFile;

@Service("playlistService")
@Transactional
public class PlaylistServiceImpl implements PlaylistService {
	@Autowired(required=true)
	@Qualifier("playlistDAO")
	private PlaylistDAO PlaylistDAO;
	
	@Transactional
	public List<Playlist> addPlaylist(String playlistName,User user, String description,CommonsMultipartFile file,Date date) {
		
		System.out.println("Playlist Service create invoked:"+playlistName);
		Playlist Playlist  = new Playlist();
		List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());

    	String filename = file.getOriginalFilename();
    	File playlistFile = new File("Users/"+user.getEmail()+"/playlist"+user_playlist.size());
    	if(!playlistFile.exists())
    	{
    		playlistFile.mkdirs();
    	}
    	else
    	{
    		System.out.println("exist fail");
    	}
    	
    	System.out.println(playlistFile.getAbsolutePath());
    	
    	String photoUrl = UploadFile.upload("Users/"+user.getEmail()+"/playlist"+user_playlist.size(),filename,file);
		Playlist playlist = new Playlist(playlistName,description,photoUrl,0,0,date,user);
		System.out.println(photoUrl);
		
		Playlist = PlaylistDAO.addPlaylist(playlist);
		return PlaylistDAO.getPlaylists();
	}
	@Transactional
	public List<Playlist> updatePlaylist(int pid, String des,CommonsMultipartFile file, String pname, User user) {
		
		System.out.println("Cusomer Service Update invoked:"+pid);
		
		Playlist p=new Playlist();
	  	String photoUrl=null;
	  	int num=findPlaylist(pid,PlaylistDAO.getPlaylists());
	  	if(des!=null)
	  	{
			p.setDes(des);
	  	}
	  	if(p.getPhotoUrl()!=null)
	  	{
	  		File deleteFile = new File(p.getPhotoUrl());
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
			p.setPhotoUrl(photoUrl);
		}	
		if(pname!=null)
				p.setPname(pname);
		
		
		
		Playlist playlist = PlaylistDAO.updatePlaylist(p);
		return PlaylistDAO.getPlaylists();
	}
	@Transactional
	public List<Playlist> removePlaylist(Playlist Playlist) {
		PlaylistDAO.deletePlaylist(Playlist);
		return PlaylistDAO.getPlaylists();
	}
	
	@Transactional
	 public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist){
		  return PlaylistDAO.getTopFollowedPlaylist(numberOfPlaylist);
	  }

	public Playlist getPlaylist(int pid) {
		return PlaylistDAO.getPlaylist(pid);
	}

	public List<Playlist> getAllPlaylists() {
		return PlaylistDAO.getPlaylists();
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
		  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	  int playlistIndex = findPlaylist(playlistId,user_playlist);
	  	  
	  	  if(playlistIndex!=-1)
	  	  {
	  		  return false;
	  	  }
		    Playlist result = PlaylistDAO.getPlaylist(playlistId);
		    
		    PlaylistDAO.followPlaylist(playlistId, user.getEmail());
		  	user_playlist.add(result);
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
	  	  
	  	  
	  	  Playlist result = PlaylistDAO.getPlaylist(playlistId);
	  	  PlaylistDAO.unfollowPlaylist(playlistId, user.getEmail());
		  	
		  	  
		  	user_playlist.remove(result);
		  	return true;
	  }
	
	//add song to playlist
	@Transactional
	  public void addSongToPlaylist(int playlistId,int songId)
	  {
			PlaylistDAO.addSongToPlaylist(playlistId, songId);
	  }
	
	@Transactional
	  public void removeSongFromPlaylist(int playlistId,int songId)
	  {
			PlaylistDAO.removeSongFromPlaylist(playlistId, songId);
	  }
	@Transactional
	public List<Playlist> findRelative(String input)
	{
		return PlaylistDAO.findRelative(input);
	}
}
