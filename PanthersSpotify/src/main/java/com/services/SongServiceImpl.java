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
import com.dao.SongDAO;
import com.model.Album;
import com.model.Playlist;
import com.model.Releasesong;
import com.model.Song;
import com.model.Squeue;
import com.model.User;

@Service("songService")
@Transactional
public class SongServiceImpl implements SongService {
	@Autowired(required = true)
	@Qualifier("songDAO")
	private SongDAO songDAO;
	private PlaylistDAO playlistDAO;

	@Transactional
	public Song addSong(String stitle, Time stime, Date releaseDate, Album aid, int monthlyPlayed, String genre,
			String stype, String surl) {

		System.out.println("Song Service create invoked:" + stitle);
		Song song = new Song();
		song.setStitle(stitle);
		song.setStime(stime);
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
	public Song updateSong(String stitle, Time stime, Date releaseDate, Album aid, int monthlyPlayed, String genre,
			String stype, String surl) {

		System.out.println("Cusomer Service Update invoked:" + stitle);
		Song song = new Song();
		song.setStitle(stitle);
		song.setStime(stime);
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
	public void removeSong(Song Song) {
		songDAO.deleteSong(Song);

	}

	public Song getSong(int sid) {
		return songDAO.getSong(sid);
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
		File f1 = new File(dir + "/src/main/webapp/WEB-INF/resources/data/audios/Artists");
		if (f1.exists()) {
			System.out.println("here");
		}
		File userDir = new File(f1, user.getEmail());
		boolean userSuccess = userDir.mkdirs();

		String songUrl = "resources/data/audios/Artists/" + user.getEmail() + "/" + file.getOriginalFilename();
		Song song = new Song(songTitle, null, null, 0, songGenre, songType, songUrl);

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

		songDAO.addSong(song);

		int sid = song.getSid();
		String email = user.getEmail();
		Releasesong release = new Releasesong(email, sid);
		release.setStatus("pending");
		songDAO.addRelease(release);

		return song;
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
	public void addSongToQueue(Collection<Squeue> que, int sid, String email) {
		Squeue newSq = songDAO.addSongToQueue(sid, email);
		que.add(newSq);
	}

	@Transactional
	public void addPlaylistToQueue(Collection<Squeue> que, int pid, String email) {
		Playlist playlist = playlistDAO.getPlaylist(pid);
		Collection<Song> songs = playlist.getSongCollection();
		for (Song s : songs) {
			addSongToQueue(que, s.getSid(), email);
		}
	}

	@Transactional
	public Song setNowPlay(Collection<Squeue> que, int sid) {
		return songDAO.setNowPlay(que, sid);
	}

	@Transactional
	public Song nextSongInQueue(Collection<Squeue> que) {
		Iterator<Squeue> it = (Iterator<Squeue>) que.iterator();
		while (it.hasNext()) {
			Squeue temp = it.next();
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
	public Song preSongInQueue(Collection<Squeue> que) {
		if (que.size() > 0) {
			Iterator<Squeue> it = (Iterator<Squeue>) que.iterator();
			Squeue preSong = it.next();
			while (it.hasNext()) {
				Squeue temp = it.next();
				if (temp.getIsPlay()) {
					return setNowPlay(que, preSong.getSong().getSid());
				}
				preSong = temp;
			}
		}
		return new Song();
	}

	@Transactional
	public Collection<Squeue> shuffleQueue(Collection<Squeue> que) {
		Collection<Squeue> collection = new ArrayList<Squeue>();
		Iterator<Squeue> it = (Iterator<Squeue>) que.iterator();
		while (it.hasNext()) {
			Squeue temp = it.next();
			if (temp.getIsPlay()) {
				collection.add(temp);
				it.remove();
				break;
			}
			collection.add(temp);
			it.remove();
		}

		ArrayList<Squeue> source = (ArrayList<Squeue>) que;

		while (source.size() > 0) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, source.size());
			collection.add(source.get(randomNum));
			source.remove(randomNum);
		}

		return collection;
	}

	@Transactional
	public Collection<Squeue> removeAllQueue(Collection<Squeue> que, String email) {
		int count = songDAO.removeAllQueue(email);
		if (count == que.size()) {
			return new ArrayList<Squeue>();
		} else
			return new ArrayList<Squeue>();
	}

	@Transactional
	public Song getNowPlay(Collection<Squeue> que) {
		Iterator<Squeue> it = (Iterator<Squeue>) que.iterator();
		while (it.hasNext()) {
			Squeue temp = (Squeue) it.next();
			if (temp.getIsPlay()) {
				return temp.getSong();
			}
		}
		return new Song();
	}

	@Transactional
	public Collection<Squeue> nextUp(Collection<Squeue> que) {
		Collection<Squeue> collection = new ArrayList<Squeue>();
		Iterator<Squeue> it = (Iterator<Squeue>) que.iterator();
		while (it.hasNext()) {
			Squeue temp = (Squeue) it.next();
			if (temp.getIsPlay()) {
				while (it.hasNext()) {
					Squeue next = (Squeue) it.next();
					collection.add(next);
				}
			}
		}
		return collection;
	}

	@Transactional
	public Collection<Releasesong> getSongArtists(Song song) {
		return songDAO.getSongArtists(song);
	}
}
