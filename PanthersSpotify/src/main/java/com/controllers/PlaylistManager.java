/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

 
import com.model.User;

import com.helper.UploadFile;
import com.model.Followplaylist;
import com.model.Playlist;
import com.model.Playlistsong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Weichao ZHao
 */
@Component
public class PlaylistManager {
//	@Autowired
//   @PersistenceContext private EntityManager em;
//    EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
//    EntityManager em = entityManagerFactory.createEntityManager();
//	 @PersistenceUnit(unitName = "pan")
//	    private EntityManagerFactory entityManagerFactory;
//    EntityTransaction userTransaction = em.getTransaction();
    @Transactional
    public List<Playlist> add(String playlistName,User user, String description,CommonsMultipartFile file,Date date) {
    		List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	    	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	    	EntityManager em = emf.createEntityManager();
	    	String filename = file.getOriginalFilename();
	    	//String dir = System.getProperty("user.dir");
	    //	System.out.println("file type is "+file.getContentType());
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
//   		File f = new File(photoUrl);
//   		BufferedImage image=null;
//   		try {
//			image = ImageIO.read(f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
   		em.getTransaction().begin();
	    em.persist(playlist);
	    em.flush();
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    
	    
	    
	    
	    user_playlist.add(playlist);
	    return user_playlist;
    }
    
    //remove playlist
    public void removePlaylist(int pid,User user)
    {
    		List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	    	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	    	  EntityManager em = emf.createEntityManager(); 
	    	  Playlist playlist = em.find(Playlist.class, pid);
	  	  em.getTransaction().begin();
	  	  em.remove(playlist);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  em.close();
	  	  emf.close();
	  	  int num=0;
	  	  for(int i=0;i<user_playlist.size();i++)
	  	  {
	  		  if(user_playlist.get(i).getPid()==pid)
	  		  {
	  			  user_playlist.remove(i);
	  			  num=i;
	  			  break;
	  		  }
	  	  }
	  	File index = new File("Users/"+user.getEmail()+"/playlist"+num);
	  	if(index.exists())
	  	{
		  	String[]entries = index.list();
		  	for(String s: entries){
		  	    File currentFile = new File(index.getPath(),s);
		  	    currentFile.delete();
		  	}
		  	index.delete();
	  	}
	  	else
	  	{
	  		System.out.println("error folder is missing");
	  	}
	  	  
	  	  
    }
    
  public List<Playlist> edit(int pid, String des,CommonsMultipartFile file, String pname, User user) {
	  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
	  	Playlist p=null;
	  	String photoUrl=null;
	  	int num=0;
	  	for(int i=0;i<user_playlist.size();i++)
	  	{
	  		p=user_playlist.get(i);
	  		if(p.getPid()==pid)
	  		{
	  			num=i;
	  			break;
	  		}
	  	}
	  	if(des!=null)
			p.setDes(des);
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
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  EntityManager em = emf.createEntityManager();
	    Playlist playlist = em.find(Playlist.class, pid);
	    em.getTransaction().begin();
	    if(des!=null)
	    		playlist.setDes(des);
	    if(photoUrl!=null)
	    		playlist.setPhotoUrl(photoUrl);
	    if(pname!=null)
	    		playlist.setPname(pname);
	    em.getTransaction().commit();
  	em.close();
  	emf.close();
  	return user_playlist;
  }

  //get the playlist for specific pid
  public Playlist getPlaylist(int pid)
  {
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
	  EntityManager em = emf.createEntityManager();
  	TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
       		.setParameter("pid", pid);
	    List<Playlist> result = query1.getResultList();
	    em.close();
	    emf.close();
	    return result.get(0);
  }
  
  //add song to playlist
  public void addSongPlaylist(int playlistId,int songId)
  {
  	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager(); 
  	  Playlistsong ps = new Playlistsong(playlistId, songId);
	  	  em.getTransaction().begin();
	  	  em.persist(ps);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  System.out.println("want to add song to playlist");
	  	  em.close();
	  	  emf.close();
  }
  
//re song to playlist
  public void removeSongPlaylist(int playlistId,int songId)
  {
  	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager(); 
  	  Playlistsong ps = new Playlistsong(playlistId, songId);
	  	  em.getTransaction().begin();
	  	  em.remove(ps);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  System.out.println("want to add song to playlist");
	  	  em.close();
	  	  emf.close();
  }
  
  public List<Playlist> getTopFollowedPlaylist(int numberOfPlaylist){
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager(); 
  	  TypedQuery<Playlist> query = 
  			  (TypedQuery<Playlist>)em.createQuery("SELECT playlist FROM Playlist playlist ORDER BY playlist.followers DESC" );
  	  query.setMaxResults(numberOfPlaylist);
	  List<Playlist> result = query.getResultList();
	  
	  em.close();
	  emf.close();
	  return result;
  }
  
  
  
//follow playlist
  public boolean followPlaylist(int playlistId,User user)
  {
	  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
  	  int playlistIndex = findPlaylist(playlistId,user_playlist);
  	  
  	  if(playlistIndex!=-1)
  	  {
  		  return false;
  	  }
  	  
  	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager();
  	  
  	TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
       		.setParameter("pid", playlistId);
	    List<Playlist> result = query1.getResultList();
	    
	    TypedQuery<Followplaylist> query2 = em.createNamedQuery("Followplaylist.findByPid", Followplaylist.class)
	       		.setParameter("pid", playlistId);
	    if(query2.getResultList().get(0).getUser().equals(user.getEmail()))
	    	{
	    		System.out.println("false");
	    	}
	    Followplaylist fp = new Followplaylist(playlistId,user.getEmail());
	    
	  	  em.getTransaction().begin();
	  	  em.persist(fp);;
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  em.close();
	  	  emf.close();
	  	  
	  	user_playlist.add((Playlist)result.get(0));
	  	return true;
  }
  
//unfollow playlist
  public boolean unfollowPlaylist(int playlistId,User user)
  {
	  List<Playlist> user_playlist = (List<Playlist>)(user.getUserPlaylistCollection());
  	  int playlistIndex = findPlaylist(playlistId,user_playlist);
  	  
  	  if(playlistIndex==-1)
  	  {
  		  return false;
  	  }
  	  
  	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager();
  	  
  	TypedQuery<Playlist> query1 = em.createNamedQuery("Playlist.findByPid", Playlist.class)
       		.setParameter("pid", playlistId);
	    List<Playlist> result = query1.getResultList();
	    Followplaylist fp = new Followplaylist(playlistId,user.getEmail());
	  	  em.getTransaction().begin();
	  	  em.remove(fp);
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  em.close();
	  	  emf.close();
	  	
	  	  
	  	user_playlist.remove((Playlist)result.get(0));
	  	return true;
  }
  
  public List<Playlist> getUserFollowPlaylist(User user)
  {
	  EntityManagerFactory emf =  Persistence.createEntityManagerFactory("pan");
  	  EntityManager em = emf.createEntityManager();
  	 
  	TypedQuery<Followplaylist> query1 = em.createNamedQuery("Followplaylist.findByUemail", Followplaylist.class)
       		.setParameter("uemail", user.getEmail());
  	List<Followplaylist> result = query1.getResultList();
  	List<Playlist> ret = new ArrayList<Playlist>();
  	for(int i =0;i<result.size();i++)
  	{
  		ret.add(result.get(i).getPlaylist());
  	}
	    //List<Followplaylist> result = query1.getResultList();
  	  //SELECT * FROM panthers.playlist pl where pl.pid in (select pid from panthers.followplaylist fp where fp.uemail = '4@qq.com');
	  	  em.getTransaction().begin();
	  	  em.flush();
	  	  em.getTransaction().commit();
	  	  em.close();
	  	  emf.close();
	  	  return ret;
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
  
  @SuppressWarnings("unchecked")
 	public List<Playlist> getAllPlaylists(){
 		EntityManager em = EMF.createEntityManager();
     	List<Playlist> list = new ArrayList<Playlist>();	
     	Query query = em.createQuery("SELECT a FROM Playlist a");
     	list = (List<Playlist>)query.getResultList();
     	em.close();
     	return list;
     }
  
}
