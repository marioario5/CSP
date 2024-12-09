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

		String songs = Files.readString(Paths.get("src/main/songs/StreamingHistory_music_0.json"));
		JsonArray jsonarray = JsonParser.parseString(songs).getAsJsonArray();
		List<Song> songList = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		for(JsonElement element : jsonarray) {
			Song song = new Gson().fromJson(element, Song.class);
			songList.add(song);
		}
		
		getSongs(songList, songs, scanner);
		
	}
	
	public static void getSongs(List<Song> songList, String date, Scanner scanner) {
		String requestedArtist;
		int totalms = 0;
		Boolean found = false;
		
		System.out.println("Which artist?");
		requestedArtist = scanner.nextLine();
		
		for(int x = 0; x<songList.size(); x++) {
			String songArtist = songList.get(x).getArtist();
			if (songArtist.equals(requestedArtist) && songList.get(x).msPlayed>1000) {
				System.out.println(songList.get(x));
				totalms += songList.get(x).msPlayed;
				found = true;
			} 
		}
		
		if(!found) {
			System.out.println("not found :(");
		} else {
			System.out.println("\n\n\nTotal time spent listening to " + requestedArtist + ": " + Integer.toString(totalms/60000) +" minutes");
		}
	}
	
	public class Song{
		
		private String endTime;
		private String artistName;
		private String trackName;
		private int msPlayed;
		
		public Song() {}
		
		public String getDate() {
			return endTime;
		}
		
		public String getArtist() {
			return artistName;
		}
		
		public String getName() {
			return trackName;
		}
		
		public String getPlaytime() {
			int secondsPlayed = msPlayed/1000;
			if(secondsPlayed<60) {
				return Integer.toString(secondsPlayed) + " seconds";
			} else if(59<secondsPlayed && secondsPlayed< 120){
				return Integer.toString(secondsPlayed/60) + " minute";
			} else {
				return Integer.toString(secondsPlayed/60) + " minutes";
			}
		}
		
		@Override
		public String toString() {
			return "\ndate: "+ this.getDate() + "\nartist: " + this.getArtist() + "\nname: " + this.getName() + "\nlisten time: " + this.getPlaytime();
		}
	}
}
