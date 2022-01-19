package librarySystem.BookEnums;

import java.util.ArrayList;

public enum Genre {
	POLITICS ("Politics"),
	MEDICINE ("Medicine"),
	BUSINESS ("Business"),
	COMPUTER_SCIENCE ("Computer Science"),
	BIOGRPAHY ("Biography");

	private String displayName;
	
	Genre(String displayName) {
	    this.displayName = displayName;
	}
	//public String getDisplayName() {
	//	return displayName; 
	//	}
	
	@Override public String toString() { // https://stackoverflow.com/questions/8389150/java-enum-elements-with-spaces
		return displayName;
		}
	public static ArrayList<String> createGenreArray() {
		ArrayList<String> genreArray = new ArrayList<String>();
		genreArray.add(POLITICS.toString());
		genreArray.add(MEDICINE.toString());
		genreArray.add(BUSINESS.toString());
		genreArray.add(COMPUTER_SCIENCE.toString());
		genreArray.add(BIOGRPAHY.toString());
		return genreArray;
	}
}
