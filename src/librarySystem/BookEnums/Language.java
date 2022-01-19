package librarySystem.BookEnums;

import java.util.ArrayList;

public enum Language {
	ENGLISH ("English"),
	FRENCH ("French");
	
	private String displayName;
	
	Language(String displayName) {
		this.displayName = displayName;
	}
	
	@Override public String toString() {
		return displayName;
		}

	public static ArrayList<String> createLangArray() {
		ArrayList<String> langArray = new ArrayList<String>();
		langArray.add(ENGLISH.toString());
		langArray.add(FRENCH.toString());
		return langArray;
	}
	
}