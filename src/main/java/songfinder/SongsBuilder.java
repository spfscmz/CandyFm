package songfinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SongsBuilder {
	
	private SortedSongs songsLibrary;; 
	
	public SongsBuilder(String directory) {
		this.readFile(directory);
	}
	
	//This method takes path as an input, add new songInfo object to the data menber - songLibrary.
	//This method is only invoked in the readFile method.
	private void processPath(Path path) {
		File song = path.toFile();
		try(FileReader file = new FileReader(song)) {
			JsonParser parser = new JsonParser();
			JsonElement elt = parser.parse(file.toString());
			//!!! instanceof!!!
			if(elt instanceof JsonObject) {
				JsonObject jObject = (JsonObject)elt;
				SongInfo newSong = new SongInfo(jObject.get("artist").getAsString(),
						jObject.get("title").getAsString(), jObject.get("tag").getAsString(),
						jObject.get("trackId").getAsString());
				songsLibrary.addSong(newSong);
				
			}
		} catch(IOException ioe) {
			System.out.println("Exception in processPath: " + ioe);
		}
		
	}
	
	
	// This method take a directory as an input, walk trough each file in this directory, and
	// call processPath method to convert each file to songInfo then add it to songLibrary.
	//This method is only invoked in this class constructor.
	private void readFile(String directory) {
		Path path = Paths.get(directory);
		try(Stream<Path> paths = Files.walk(path)) {
			path.forEach(p -> processPath(p));
		} catch(Exception e) {
			System.out.println("scanFile exception: " + e);
		}
	}
	
	
	// This method takes sortWay and writePath as parameters, write songs info in the given writePath
	// in a wanted sortWay.
	public void writeFile(String order,String writePath) {
		
		ArrayList<SongInfo> songsList = new ArrayList<SongInfo>();
		if(order == "artist") {
			songsList = this.songsLibrary.getSortedByArtist();
		}
		
		Path outpath = Paths.get(order);
		outpath.getParent().toFile().mkdir();
		try(BufferedWriter output = Files.newBufferedWriter(outpath)){
			// write files in different ways according to command.
			
			//1. When we write a songsByTitle.
			if(order == "title") {
				songsList = this.songsLibrary.getSortedByTitle();
			}	
			//2. When we write a songsByArtist.
			if(order == "artist") {
				songsList = this.songsLibrary.getSortedByArtist();
			}
			// When we want to wirte songsByArtist or songsByTitle.
			if(order != "tag") {
				for(SongInfo song: songsList) {
					output.write(song.getArist() + " - " + song.getTitle() + "\n");
				}
			} else {
				TreeMap<String, TreeSet<String>> songsMap = songsLibrary.getSortedByTag();
				Set<String> tags = songsLibrary.getSortedByTag().keySet();
				for(String tag: tags) {
					//Write every tag in the songsMap.
					output.write(tag + ": ");
					// For each tag, write every song's trackId which is in that tag.
					for(String trackId: songsMap.get(tag)) {
						output.write(trackId + " ");
					}
					output.write("\n");
				}
			}
		} catch(IOException ioe) {
			System.out.println("Exception in writting file" + ioe);
		}
	}
}