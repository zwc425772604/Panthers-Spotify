package com.helper;

import java.util.List;

import com.model.Album;
import com.model.Playlist;
import com.model.Song;

public class SortTitle {
	public static List<Playlist> sortPlaylistAlpha(List<Playlist> input)
	{
		
		for (int i = 0; i < input.size(); i++) 
        {
            for (int j = i + 1; j < input.size(); j++) 
            {
                if (input.get(i).getPname().compareTo(input.get(j).getPname())>0) 
                {
                		Playlist tmp = input.get(i);
                    input.set(i, input.get(j));
                    input.set(j, tmp);
                }
            }
        }
		return input;
	}
	
	public static List<Album> sortAlbumAlpha(List<Album> input)
	{
		
		for (int i = 0; i < input.size(); i++) 
        {
            for (int j = i + 1; j < input.size(); j++) 
            {
                if (input.get(i).getAname().compareTo(input.get(j).getAname())>0) 
                {
                		Album tmp = input.get(i);
                    input.set(i, input.get(j));
                    input.set(j, tmp);
                }
            }
        }
		return input;
	}
	
	public static List<Song> sortSongAlpha(List<Song> input)
	{
		
		for (int i = 0; i < input.size(); i++) 
        {
            for (int j = i + 1; j < input.size(); j++) 
            {
                if (input.get(i).getStitle().compareTo(input.get(j).getStitle())>0) 
                {
                		Song tmp = input.get(i);
                    input.set(i, input.get(j));
                    input.set(j, tmp);
                }
            }
        }
		return input;
	}
}
