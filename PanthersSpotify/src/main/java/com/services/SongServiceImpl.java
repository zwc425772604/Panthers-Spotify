package com.services;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
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

import com.dao.SongDAO;
import com.model.Album;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.User;

@Service("songService")
@Transactional
public class SongServiceImpl implements SongService{
	@Autowired(required=true)
	@Qualifier("songDAO")
	private SongDAO SongDAO;
	
	@Transactional
	public Song addSong(String stitle, Time stime, Date releaseDate,Album aid,int monthlyPlayed,String genre,String stype,String surl) {
		
		System.out.println("Song Service create invoked:"+stitle);
		Song song  = new Song();
		song.setStitle(stitle);
		song.setStime(stime);
		song.setReleaseDay(releaseDate);
		song.setAlbumId(aid);
		song.setMonthlyPlayed(monthlyPlayed);
		song.setGener(genre);
		song.setStype(stype);
		song.setSurl(surl);
		
		final String dir = System.getProperty("user.dir");
        
        File f1 = new File(dir+"/Songs");
        if(f1.exists())
        {
        		System.out.println("here");
        }
        //File SongDir = new File(f1, email);
        //boolean SongSuccess = SongDir.mkdirs();
		
		song = SongDAO.addSong(song);
		return song;
	}
	@Transactional
	public Song updateSong(String stitle, Time stime, Date releaseDate,Album aid,int monthlyPlayed,String genre,String stype,String surl) {
		
		System.out.println("Cusomer Service Update invoked:"+stitle);
		Song song  = new Song();
		song.setStitle(stitle);
		song.setStime(stime);
		song.setReleaseDay(releaseDate);
		song.setAlbumId(aid);
		song.setMonthlyPlayed(monthlyPlayed);
		song.setGener(genre);
		song.setStype(stype);
		song.setSurl(surl);
		song = SongDAO.updateSong(song);
		return song;
	}
	@Transactional
	public void removeSong(Song Song) {
		SongDAO.deleteSong(Song);
		
	}
	

	public Song getSong(int sid) {
		return SongDAO.getSong(sid);
	}

	public List<Song> getAllSongs() {
		return SongDAO.getSongs();
	}
	@Transactional
	public List<Song> findRelative(String input)
	{
		return SongDAO.findRelative(input);
	}
	
	@Transactional
	public Song uploadSong(User user, String songTitle, String songTime, String releaseDay, String songGenre, String songType, CommonsMultipartFile file) 
    {

      final String dir = System.getProperty("user.dir");
      File f1 = new File(dir+"/src/main/webapp/WEB-INF/resources/data/audios/Artists");
      if(f1.exists())
      {
      		System.out.println("here");
      }
      File userDir = new File(f1, user.getEmail());
      boolean userSuccess = userDir.mkdirs();

	  String songUrl = "resources/data/audios/Artists/" + user.getEmail() + "/" + file.getOriginalFilename();
      Song song = new Song(songTitle, null,null, 0, songGenre, songType, songUrl);

	  File pathToStore = new File(userDir, file.getOriginalFilename());
	  try {
	          file.transferTo(pathToStore);

	      } catch (IllegalStateException e) {
	            e.printStackTrace();
	           System.out.println("failed to save the song to dir");
	      } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("failed to load");
	      }
	  
	  SongDAO.addSong(song);


	    int sid = song.getSid();
	    String email = user.getEmail();
	    Releasesong release = new Releasesong(email, sid);
	    release.setStatus("pending");
	    SongDAO.addRelease(release);
	    
	    return song;
    }
	@Transactional
	public List<Releasesong> getAllSongsByStatus(String status)
	{
		
			return SongDAO.getAllSongsByStatus(status);
	}
}
