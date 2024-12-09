package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String requestedArtist; //variable that stores the artist the user requests
		String songs = Files.readString(Paths.get("src/main/songs/StreamingHistory_music_0.json")); //converts the json file to a string
		JsonArray jsonarray = JsonParser.parseString(songs).getAsJsonArray(); //creates a new array filled with all the json objects from the file
		List<Song> songList = new ArrayList<>();//creates a new array where the song objects will be stored
		Scanner scanner = new Scanner(System.in);//creates the scanner that will be used for user input

		for(JsonElement element : jsonarray) {    //repeats for every element in the json array
			Song song = new Gson().fromJson(element, Song.class);  //creates a new song object for each song in the json file
			songList.add(song); // adds the song object to the array
		}
		
		System.out.println("Which artist?"); // prompts the user for input
		requestedArtist = scanner.nextLine();// fills the user responce as a variable
		getSongs(songList, requestedArtist); // gets all the song, passing the array with all the songs, and the artist the user requested
		
	}
	
	public static void getSongs(List<Song> songList, String requestedArtist) {

		int totalms = 0; // total time spent listing to the requested artist
		Boolean found = false; // checks to see if the requested artist was found
		
		for(int x = 0; x<songList.size(); x++) {  //repeats the entire length of the array
			String songArtist = songList.get(x).getArtist(); // stores the artist of the current song in the variable
			if (songArtist.equals(requestedArtist) && songList.get(x).msPlayed>2000) { //checks to see if the current song artist is the one requested, and to see if the user listened to it for more than two seconds
				System.out.println(songList.get(x)); //prints out the song
				totalms += songList.get(x).msPlayed; //increases the total time spend listening by the time spend listening to the song
				found = true; //the artist was found, so the variable is true
			} 
		}
		
		if(!found) {
			System.out.println("not found :("); //the artist wasn't found :(
		} else {
			System.out.println("\n\n\nTotal time spent listening to " + requestedArtist + ": " + Integer.toString(totalms/60000) +" minutes"); // prints out time spend listening to the artist
		}
	}
	
	public class Song{
		
		private String endTime; //these variables are the same found in the json file
		private String artistName;
		private String trackName;
		private int msPlayed;
		
		public Song() {}
		
		public String getDate() { //these functions return their specific value
			return endTime;
		}
		
		public String getArtist() {
			return artistName;
		}
		
		public String getName() {
			return trackName;
		}
		
		public String getPlaytime() { //this returns a string, so there will have to be conversion. This allows for better use in println, but maintains the integer operations for msplayed
			int secondsPlayed = msPlayed/1000; // divide milliseconds/1000 to get seconds
			if(secondsPlayed<60) {
				return Integer.toString(secondsPlayed) + " seconds"; //less than a minute, so it needs to say seconds
			} else if(59<secondsPlayed && secondsPlayed< 120){
				return Integer.toString(secondsPlayed/60) + " minute"; //between 60 and 120 seconds, so one minute
			} else {
				return Integer.toString(secondsPlayed/60) + " minutes"; //over a minute, so minutes
			}
		}
		
		@Override   //overrides the object method so we get the data we want, instead of the object code
		public String toString() {
			return "\ndate: "+ this.getDate() + "\nartist: " + this.getArtist() + "\nname: " + this.getName() + "\nlisten time: " + this.getPlaytime(); //returns all the values we want
		}
	}
}
