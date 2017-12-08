package com.services;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dao.AlbumDAO;
import com.model.Album;
import com.model.Playlist;
import com.model.User;
import com.helper.UploadFile;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
	@Autowired(required=true)
	@Qualifier("albumDAO")
	private AlbumDAO albumDAO;
	
	@Transactional
	public List<Album> addAlbum(String albumName,User user, String description,CommonsMultipartFile file,Date date) {
		
	
    	String filename = file.getOriginalFilename();
    	String dir = System.getProperty("user.dir");
    	String albumPath = dir+"/src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail()+"/album"+date.getTime();
    	File albumFile = new File(albumPath);
    	albumFile.mkdirs();
    	String photoUrl = UploadFile.upload(albumPath,filename,file);
    	if(photoUrl==null)
    	{
    		albumFile.delete();
    	}
    	Time t = new Time(0);	
	Album album = new Album(albumName,description,photoUrl,0,0,date,t);
	album = albumDAO.addAlbum(album);
	//userPlaylists.add(playlist);
    	
		return albumDAO.getAlbums();
	}
	@Transactional
	public List<Album> updateAlbum(int aid, String des,CommonsMultipartFile file, String pname, User user) {
		
		Album album=albumDAO.getAlbum(aid);
		
	  	String photoUrl=album.getPhotoUrl();
	  	File albumFile = null;
	  	if(des!=null)
	  	{
	  		album.setDes(des);
	  	}
	  	if(photoUrl!=null)
	  	{
	  		File deleteFile = new File(photoUrl);
	  		if(deleteFile.exists())
	  		{
	  			deleteFile.delete();
	  		}
	  		File photo = new File(photoUrl);
	  		albumFile = photo.getParentFile();
	  	}
	  	
		if(file!=null&&albumFile!=null)
		{
			
			String filename = file.getOriginalFilename();
		  	System.out.println("it should be + "+albumFile.getAbsolutePath());
		  	photoUrl = UploadFile.upload(albumFile.getAbsolutePath(),filename,file);
		  	
		  	album.setPhotoUrl(photoUrl);
		}
		else if(file!=null&&albumFile==null)
		{
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			String albumPath = "src/main/webapp/WEB-INF/resources/data/Users/"+user.getEmail()+"/album"+date.getTime();
			File albumF = new File(albumPath);
			albumF.mkdirs();
	    		String filename = file.getOriginalFilename();
	    		
	    		photoUrl = UploadFile.upload(albumF.getAbsolutePath(),filename,file);
			  	
	    		album.setPhotoUrl(photoUrl);
		}
		
		if(pname!=null)
		{
			album.setAname(pname);
		}
		album = albumDAO.updateAlbum(album);
		
		
		return albumDAO.getAlbums();
	}
	@Transactional
	public List<Album> removeAlbum(Album album) {
		albumDAO.deleteAlbum(album);
		if(album.getPhotoUrl()!=null)
		{
			File deleteFile = new File(album.getPhotoUrl());
			File deleteFileParent = deleteFile.getParentFile();
			
	  		if(deleteFile.exists())
	  		{
	  			deleteFile.delete();
	  		}
	  		if(deleteFileParent!=null&&deleteFileParent.exists())
	  			deleteFileParent.delete();
		}
		return albumDAO.getAlbums();
	}
	
	@Transactional
	 public List<Album> getTopFollowedAlbum(int numberOfAlbum){
		  return albumDAO.getTopFollowedAlbum(numberOfAlbum);
	  }

	public Album getAlbum(int pid) {
		return albumDAO.getAlbum(pid);
	}

	public List<Album> getAllAlbums() {
		return albumDAO.getAlbums();
	}
	
	private int findAlbum(int AlbumId, List<Album> Albums)
	  {
		  int num=-1;
		  for(int i=0;i<Albums.size();i++)
		  {
			  if(Albums.get(i).getAid()==AlbumId)
			  {
				  num=i;
				  break;
			  }
		  }
		  return num;
	  }
	@Transactional
	public List<Album> findRelative(String input)
	{
		return albumDAO.findRelative(input);
	}
	
	
	@Transactional
	public List<Album> getHistoryAlbums(String userEmail)
	{
		return albumDAO.getHistoryAlbums(userEmail);
	}
	
	@Transactional
	public List<Album> addHistoryAlbum(Album album,User user,Date date) {
		albumDAO.addAlbumHistory(album, user, date);
		return albumDAO.getHistoryAlbums(user.getEmail());
	}
	
	@Transactional
	public List<Album> deleteHistoryAlbum(Album album,User user) {
		albumDAO.deleteAlbumHistory(album, user);
		return albumDAO.getHistoryAlbums(user.getEmail());
	}
	
	@Transactional
	public List<User> getArtistsCollection(int aid) {
		return (List<User>)albumDAO.getAlbumArtists(aid);
	}
	
	@Transactional
	public List<Album> getTopGenreAlbum(String genre,int pageNum,int numRet)
	{
		return albumDAO.getTopGenreAlbum(genre,pageNum,numRet);
	}
	
	@Transactional
	public List<Album> getNewsRelease(Date date)
	{
		return albumDAO.getNewsRelease(date);
	}
	
	@Transactional
	public Collection<Album> getAllAlbumArtist(String artistEmail)
	{
		return albumDAO.getAllAlbumArtist(artistEmail);
	}
}

