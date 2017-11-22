package com.services;

import java.io.File;
import java.sql.Time;
import java.util.Date;
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
		
		System.out.println("Album Service create invoked:"+albumName);
		Album Album  = new Album();
		List<Album> userAlbum = (List<Album>)(user.getAlbumCollection());

    	String filename = file.getOriginalFilename();
    	File albumFile = new File("Users/"+user.getEmail()+"/Album"+userAlbum.size());
    	if(!albumFile.exists())
    	{
    		albumFile.mkdirs();//PanthersSpotify
    	}
    	else
    	{
    		System.out.println("exist fail");
    	}
    	
    	System.out.println(albumFile.getAbsolutePath());
    	
    	String photoUrl = UploadFile.upload("Users/"+user.getEmail()+"/Album"+userAlbum.size(),filename,file);
	Time t = new Time(0);	
    	Album album = new Album(albumName,description,photoUrl,0,0,date,t);
		System.out.println(photoUrl);
		
		Album = albumDAO.addAlbum(album);
		return albumDAO.getAlbums();
	}
	@Transactional
	public List<Album> updateAlbum(int pid, String des,CommonsMultipartFile file, String pname, User user) {
		
		System.out.println("Cusomer Service Update invoked:"+pid);
		
		Album p=new Album();
	  	String photoUrl=null;
	  	int num=findAlbum(pid,albumDAO.getAlbums());
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
			File AlbumFile = new File("Users/"+user.getEmail()+"/Album"+num);
			
			String filename = file.getOriginalFilename();
		  	if(!AlbumFile.exists())
		  	{
		  		System.out.println("exist fail");
		  	}
		  	else
		  	{
		  		photoUrl = UploadFile.upload("Users/"+user.getEmail()+"/Album"+num,filename,file);
		  	}
			p.setPhotoUrl(photoUrl);
		}	
		if(pname!=null)
				p.setAname(pname);
		
		
		
		Album Album = albumDAO.updateAlbum(p);
		return albumDAO.getAlbums();
	}
	@Transactional
	public List<Album> removeAlbum(Album Album) {
		albumDAO.deleteAlbum(Album);
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
}

