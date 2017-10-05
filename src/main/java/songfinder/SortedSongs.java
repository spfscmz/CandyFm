package songfinder;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class SortedSongs {
	
	private ArrayList<SongInfo> sortedByTitle;
	private ArrayList<SongInfo> sortedByArtist;
	//TreeMap sortedByTag key = Tag, value = trackId. 
	private TreeMap<String, TreeSet<String>> sortedByTag;
	
	public SortedSongs() {
	}
	
	public void addSong(SongInfo newSong) {
		this.addTitle(newSong);
		this.addArtist(newSong);
		this.addTag(newSong);
	}
/*
	 * Data sorted by title will list the artist name, followed by a space, 
	 * followed by -, followed by a space, followed by title, followed by a new line, 
	 * and will be in alphabetical order by title. If two songs have the same title, 
	 * they will then be sorted by the artist. If two songs have the same title and artist, 
	 * they will be sorted by the track_id. Example:
	 * 
	 *	Hushabye Baby - Cry, Cry, Cry
	 *	Aerosmith - Cryin'
	 */
	//this method add SongInfo to arraylist and sort the arraylist as above.
	private void addTitle(SongInfo newSong) {
		this.sortedByTitle.add(newSong);
		//Collections! (!= Collection)
		Collections.sort(sortedByTitle, new Comparator<SongInfo>() {
			public int compare(SongInfo song1, SongInfo song2) {
				if(song1.getTitle().compareTo(song2.getTitle()) != 0) {
					return song1.getTitle().compareTo(song2.getTitle());
				} else if(song1.getArist().compareTo(song2.getArist()) != 0) {
					return song1.getArist().compareTo(song2.getArist());
				} else {
					return song1.getTrackId().compareTo(song2.getTrackId());
				}
			}
		});
	}
	/*
	 * Data sorted by artist will list the artist name, followed by a space, 
	 * followed by -, followed by a space, followed by title, followed by a new 
	 * line, and will be in alphabetical order by artist. If two songs have the 
	 * same artist, they will then be sorted by the title. If two songs have the 
	 * same artist and title, they will be sorted by the track_id. Example:
	 * Steel Rain - Loaded Like A Gun
	 * Tom Petty - A Higher Place (Album Version)
	 */
	//this method add SongInfo object and sort arraylist as above.
	private void addArtist(SongInfo newSong) {
		this.sortedByArtist.add(newSong);
		Collections.sort(sortedByArtist, new Comparator<SongInfo>() {
			public int compare(SongInfo song1, SongInfo song2) {
				if(song1.getArist().compareTo(song2.getArist()) != 0) {
					return song1.getArist().compareTo(song2.getArist());
				} else if(song1.getTitle().compareTo(song2.getTitle()) != 0) {
					return song1.getTitle().compareTo(song2.getTitle());
				} else {
					return song1.getTrackId().compareTo(song2.getTrackId());
				}
			}
		});
	}
	
	//this method add SongInfo object into treemap and sort its key and its  
	//value (arraylist).  
	private void addTag(SongInfo newSong) {
		
		TreeSet value = this.sortedByTag.get(newSong.getTag());
		value.add(newSong.getTrackId());
	}
	
	//These method is for getting wanted songs' ArrayList.
	
	public ArrayList<SongInfo> getSortedByTitle(){
		return this.sortedByTitle;
	}
	
	public ArrayList<SongInfo> getSortedByArtist(){
		return this.sortedByArtist;
		
	}
	
	public TreeMap<String, TreeSet<String>> getSortedByTag(){
		return this.sortedByTag;
	}
	
}