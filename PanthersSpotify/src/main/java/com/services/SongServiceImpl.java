package com.services;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.dao.PlaylistDAO;
import com.dao.SongDAO;
import com.helper.UploadFile;
import com.model.Album;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.SongQueue;
import com.model.User;

@Service("songService")
@Transactional
public class SongServiceImpl implements SongService {
	@Autowired(required = true)
	@Qualifier("songDAO")
	private SongDAO songDAO;
	
	@Autowired(required = true)
	@Qualifier("playlistDAO")
	private PlaylistDAO playlistDAO;

	@Transactional
	public Song addSong(String stitle, Time duration, Date releaseDate, Album aid, int monthlyPlayed, String genre,
			String stype, String surl) {

		System.out.println("Song Service create invoked:" + stitle);
		Song song = new Song();
		song.setStitle(stitle);
		song.setDuration(duration);
		song.setReleaseDay(releaseDate);
		song.setAlbumId(aid);
		song.setMonthlyPlayed(monthlyPlayed);
		song.setGener(genre);
		song.setStype(stype);
		song.setSurl(surl);

		final String dir = System.getProperty("user.dir");

		File f1 = new File(dir + "/Songs");
		if (f1.exists()) {
			System.out.println("here");
		}
		// File SongDir = new File(f1, email);
		// boolean SongSuccess = SongDir.mkdirs();

		song = songDAO.addSong(song);
		return song;
	}

	@Transactional
	public Song updateSong(String stitle, Time duration, Date releaseDate, Album aid, int monthlyPlayed, String genre,
			String stype, String surl) {

		Song song = new Song();
		song.setStitle(stitle);
		song.setDuration(duration);
		song.setReleaseDay(releaseDate);
		song.setAlbumId(aid);
		song.setMonthlyPlayed(monthlyPlayed);
		song.setGener(genre);
		song.setStype(stype);
		song.setSurl(surl);
		song = songDAO.updateSong(song);
		return song;
	}
	
	@Transactional
	public Song updateMontlySong(int monthlyPlayed, Song song) {

		song.setMonthlyPlayed(monthlyPlayed);
		song = songDAO.updateSong(song);
		return song;
	}

	@Transactional
	public void removeSong(Song Song) {
		
		songDAO.deleteSong(Song);

	}

	public Song getSong(int sid) {
		return songDAO.getSong(sid);
	}
	
	public List<Song> getSongs(Album album) {
		return songDAO.getSongs(album);
	}

	public List<Song> getAllSongs() {
		return songDAO.getSongs();
	}

	@Transactional
	public List<Song> findRelative(String input) {
		return songDAO.findRelative(input);
	}

	@Transactional
	public Song uploadSong(User user, String songTitle, String songTime, String releaseDay, String songGenre,
			String songType, CommonsMultipartFile file) {

		final String dir = System.getProperty("user.dir");
		char first = Character.toUpperCase(file.getOriginalFilename().charAt(0));
		File f1 = new File(dir + "/song/"+first);
		String songUrl = f1.getAbsolutePath()+"/"+file.getOriginalFilename();
		Song song = new Song(songTitle, null, null, 0, songGenre, songType, songUrl);

		songUrl = UploadFile.upload(f1.getAbsolutePath(), file.getOriginalFilename(), file);

		songDAO.addSong(song);

		int sid = song.getSid();
		String email = user.getEmail();
		Releasesong release = new Releasesong(email, sid);
		release.setStatus("pending");
		songDAO.addRelease(release);

		return song;
	}

	
	@Transactional
	public void uploadRemoveSong(User user, int songId) {


		Releasesong s = songDAO.getSongInRelease(songId, user.getEmail());
		
		s.setStatus("removePending");
		songDAO.updateReleaseSong(s);
	}
	
	@Transactional
	public List<Song> getStatusSongs(String status) 
	{
		return songDAO.getRemoveRequestSongs(status);
	}
	
	@Transactional
	public List<Releasesong> getAllSongsByStatus(String status) {

		return songDAO.getAllSongsByStatus(status);
	}

	@Transactional
	public List<Song> getHistorySongs(String userEmail) {
		return songDAO.getHistorySongs(userEmail);
	}

	@Transactional
	public List<Song> addHistorySong(Song song, User user, Date date) {
		songDAO.addSongHistory(song, user, date);
		return songDAO.getHistorySongs(user.getEmail());
	}

	@Transactional
	public List<Song> deleteHistorySong(Song song, User user) {
		songDAO.deleteSongHistory(song, user);
		return songDAO.getHistorySongs(user.getEmail());
	}

	@Transactional
	public void addSongToQueue(Collection<SongQueue> que, int sid, String email) {
		//System.out.println(sid);
		SongQueue newSq = songDAO.addSongToQueue(sid, email);
		
		Collection<User> artists = songDAO.getSongArtists(sid);
		newSq.setSong(songDAO.getSong(sid));//i think it should be this way
		newSq.setArtistsCollection(artists);
		que.add(newSq);
	}

	@Transactional
	public void addPlaylistToQueue(Collection<SongQueue> que, int pid, String email) {
		//System.out.println(pid);
		List<Song> songs = playlistDAO.getSongInPlaylist(pid);
		
		for (Song s : songs) {
			addSongToQueue(que, s.getSid(), email);
		}
	}

	@Transactional
	public Song setNowPlay(Collection<SongQueue> que, int sid) {
		return songDAO.setNowPlay(que, sid);
	}

	@Transactional
	public Song nextSongInQueue(Collection<SongQueue> que) {
		Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
		while (it.hasNext()) {
			SongQueue temp = it.next();
			if (temp.getIsPlay()) {
				if (it.hasNext())
					return setNowPlay(que, it.next().getSong().getSid());
				else
					return new Song();
			}
		}
		return new Song();
	}

	@Transactional
	public Song preSongInQueue(Collection<SongQueue> que) {
		if (que.size() > 0) {
			Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
			SongQueue preSong = it.next();
			while (it.hasNext()) {
				SongQueue temp = it.next();
				if (temp.getIsPlay()) {
					return setNowPlay(que, preSong.getSong().getSid());
				}
				preSong = temp;
			}
		}
		return new Song();
	}

	@Transactional
	public Collection<SongQueue> shuffleQueue(Collection<SongQueue> que) {
		Collection<SongQueue> collection = new ArrayList<SongQueue>();
		Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
		while (it.hasNext()) {
			SongQueue temp = it.next();
			if (temp.getIsPlay()) {
				collection.add(temp);
				it.remove();
				break;
			}
			collection.add(temp);
			it.remove();
		}

		ArrayList<SongQueue> source = (ArrayList<SongQueue>) que;

		while (source.size() > 0) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, source.size());
			collection.add(source.get(randomNum));
			source.remove(randomNum);
		}

		return collection;
	}

	@Transactional
	public Collection<SongQueue> removeAllQueue(Collection<SongQueue> que, String email) {
		int count = songDAO.removeAllQueue(email);
		if (count == que.size()) {
			return new ArrayList<SongQueue>();
		} else
			return new ArrayList<SongQueue>();
	}

	@Transactional
	public Song getNowPlay(Collection<SongQueue> que) {
		Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
		while (it.hasNext()) {
			SongQueue temp = (SongQueue) it.next();
			if (temp.getIsPlay()) {
				return temp.getSong();
			}
		}
		return new Song();
	}

	@Transactional
	public Collection<SongQueue> nextUp(Collection<SongQueue> que) {
		Collection<SongQueue> collection = new ArrayList<SongQueue>();
		Iterator<SongQueue> it = (Iterator<SongQueue>) que.iterator();
		while (it.hasNext()) {
			SongQueue temp = (SongQueue) it.next();
			if (temp.getIsPlay()) {
				while (it.hasNext()) {
					SongQueue next = (SongQueue) it.next();
					collection.add(next);
				}
			}
		}
		return collection;
	}

	@Transactional
	public void updateReleaseSong(int songID, String status) {
		List<Releasesong> releaseSong = songDAO.getAllSongsByID(songID);
		for (Releasesong rs : releaseSong)
		{
			rs.setStatus(status);
			songDAO.updateReleaseSong(rs);
		}
	}
	
	@Transactional
	public void setArtistsCollection(Collection<SongQueue> songQueue) {
		Iterator<SongQueue> it = (Iterator<SongQueue>)songQueue.iterator();
		while (it.hasNext()) {
			SongQueue que = it.next();
			Collection<User> artists = songDAO.getSongArtists(que.getSong().getSid());
			que.setArtistsCollection(artists);
		}
	}
	
	@Transactional
	public List<User> getArtistsCollection(int sid) {
		return (List<User>)songDAO.getSongArtists(sid);
	}
	
	public List<Song> getSongByArtist(String artistEmail, String status)
	{
		return songDAO.getSongByArtist(artistEmail, status);
	}
}
