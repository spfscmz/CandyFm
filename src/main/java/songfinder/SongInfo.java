package songfinder;


import com.google.gson.JsonArray;


/*
 * This class store single song infomation
 */

public class SongInfo {

	private String artist;
	private String title;
	private JsonArray tags;
	private String trackId;
	
	public SongInfo(String artist, String title, JsonArray tags, String trackId) {
		this.artist = artist;
		this.title = title;
		this.tags = tags;
		this.trackId = trackId;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public JsonArray getTag() {
		return this.tags;
	}
	
	public String getTrackId() {
		return this.trackId;
	}
}
